package com.sadek.test;

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

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

public class GenericReportBean extends BaseBean {
    ReportBilder reportBuild = null;
    Document doc = null;
    private ReportBilder className;
    ByteArrayOutputStream out = null;
    private RichPopup popUp;

    public GenericReportBean() {
    }

    private ReportBilder initReportBuilder() {
        // Add event code here...
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        DCIteratorBinding iterator =
            (DCIteratorBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("EmployeesView1Iterator");
        Row[] rows = iterator.getAllRowsInRange();
        ArrayList<String> attributesName = new ArrayList<String>();
        attributesName.add("EmployeeId");
        attributesName.add("FirstName");
        attributesName.add("LastName");
        attributesName.add("PhoneNumber");
        attributesName.add("HireDate");
        attributesName.add("Salary");
        attributesName.add("DepartmentId");
        String getRepName = loadKey("reportName");
        String repDescription = loadKey("paragraph");
        className = new ReportBilder();

        className.setArialFontPath(getArialFontPath());
        //className.setMomraLogoPath(getMomraLogoImgPath("momra.jpeg"));
        className.setAttributes(attributesName);
        className.setTitle(getRepName);
        className.setDescription(repDescription);
        className.setRows(rows); //draw rows  in table
        //init Document //
        try {
         //   FontStyle fontdesc = new FontStyle();
            FontStyle font2 = new FontStyle(12, Font.ITALIC, BaseColor.GREEN);

            document = className.openDocument(attributesName);
            className.addHeader(document, repDescription, Element.ALIGN_CENTER, getMomraLogoImgPath("momra.jpeg"),
                                Element.ALIGN_RIGHT, getMomraLogoImgPath("invest.jpg"), Element.ALIGN_LEFT ,new FontStyle().createFont() );
//          className.addNewLine(document);
            className.addTitle(document, getRepName, Element.ALIGN_CENTER, new FontStyle(14, Font.BOLD, BaseColor.BLACK).createFont());
//            className.addNewLine(document);
            
                document.add(className.drawTable(attributesName, rows,new FontStyle(14, Font.BOLD, BaseColor.BLACK).createFont(),new FontStyle(9, Font.NORMAL, BaseColor.BLUE).createFont()));
            className.addNewLine(document);
            className.addNewLine(document);
            
            className.addDescription(document, repDescription, Element.ALIGN_RIGHT,new FontStyle(8, Font.ITALIC, BaseColor.BLACK).createFont() );

            className.closeDocument(document);
            className.setDocument(document);

        } catch (DocumentException e) {
        }
        return className;
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
