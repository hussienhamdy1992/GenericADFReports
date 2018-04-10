package com.sadek.reports.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;

import com.itextpdf.text.DocumentException;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;

import com.itextpdf.text.Phrase;

import com.sadek.reports.BaseBean;
import com.sadek.reports.ReportBilder;


import com.sadek.reports.utils.Constants;


import com.sadek.reports.utils.FontStyle;

import java.io.ByteArrayOutputStream;

import java.util.ArrayList;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.output.RichInlineFrame;

import oracle.jbo.Row;

import com.sadek.reports.utils.FontStyle;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class showAllEmpByDepBean extends BaseBean {
    ReportBilder reportBuild = null;
    private ReportBilder className;
    private RichPopup popUp;

    public showAllEmpByDepBean() {
    }

    private ReportBilder initReportBuilder() {
        // Add event code here...
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        DCIteratorBinding iterator =
            (DCIteratorBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("DepartmentsEmployeeROVO1Iterator");
        Row[] rows = iterator.getAllRowsInRange();
        ArrayList<String> attributesName = new ArrayList<String>();
        attributesName.add("EmployeeId");
        attributesName.add("FirstName");
        attributesName.add("HireDate");
        attributesName.add("DepartmentId");
        attributesName.add("DepartmentName");
        attributesName.add("JobId");
        attributesName.add("Salary");
        attributesName.add("ManagerId");

        String getRepName = loadKey("reportName");
        String repDescription = loadKey("paragraph");
        className = new ReportBilder();

        className.setArialFontPath(getArialFontPath());
        className.setAttributes(attributesName);
        className.setTitle(getRepName);
        className.setDescription(repDescription);
        className.setRows(rows);
        /**
         * Build PDF Report
         * asadek
         * */
        try {
            /**
             * intiate document and add component
             **/
            document = className.openDocument(attributesName);
            className.addHeader(document, repDescription, Element.ALIGN_CENTER, getMomraLogoImgPath("momra.jpeg"),
                                Element.ALIGN_RIGHT, getMomraLogoImgPath("invest.jpg"), Element.ALIGN_LEFT,
                                new FontStyle().createFont());
            className.addTitle(document, getRepName, Element.ALIGN_CENTER,
                               new FontStyle(14, Font.BOLD, BaseColor.BLACK).createFont());

            document.add(className.drawTable(attributesName, rows, new FontStyle(12, Font.BOLD).createFont(),
                                             new FontStyle().createFont()));
            className.addNewLine(document);
            className.addNewLine(document);

            className.addDescription(document, repDescription, Element.ALIGN_RIGHT,
                                     new FontStyle(8, Font.ITALIC).createFont());

            className.closeDocument(document);
            className.setDocument(document);


        } catch (DocumentException e) {
        }
        return className;
    }

    public String runReport() {

        String path = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        path = ctx.getExternalContext().getRequestContextPath();
        String url = path + "/showpdfservlet";
        className.openWindow(url);

        return null;
    }

    private String loadKey(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle("com.sadek.test.bundle");
        return bundle.getString(key);
    }


    public void setClassName(ReportBilder className) {
        this.className = className;
    }

    public ReportBilder getClassName() {
        className = initReportBuilder();

        return className;
    }


    public void ShowPopup(ActionEvent actionEvent) {
        // process updates to get the selected value inside the popup
        FacesContext contxt = FacesContext.getCurrentInstance();
        // valueChangeEvent.getComponent().processUpdates(contxt);
        //show the popup


        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getPopUp().show(hints);

    }

    public void setPopUp(RichPopup popUp) {
        this.popUp = popUp;
    }

    public RichPopup getPopUp() {
        return popUp;
    }
}
