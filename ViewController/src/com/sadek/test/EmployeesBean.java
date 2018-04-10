package com.sadek.test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;



//import br.com.waslleysouza.model.EmployeesViewImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Iterator;

import java.util.List;

import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.dom.DOMSource;

import javax.xml.transform.sax.SAXResult;

import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;

import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.jbo.Key;

import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;

import oracle.jbo.XMLInterface;

import oracle.xml.parser.v2.XMLNode;

import oracle.xml.pipeline.controller.Input;

////
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEvent;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URL;
import java.net.URLEncoder;

import java.nio.charset.Charset;

import java.util.ArrayList;

import java.util.ResourceBundle;

import javax.faces.context.ExternalContext;


import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.binding.BindingContainer;

import oracle.jbo.AttributeDef;
import oracle.jbo.Row;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewAttributeDefImpl;
import oracle.jbo.server.ViewObjectImpl;

import org.apache.myfaces.trinidad.model.RowKeySet;


public class EmployeesBean  implements PdfPCellEvent, PdfPTableEvent  {
public void tableLayout(PdfPTable table, float[][] widths, float[] heights,
            int headerRows, int rowStart, PdfContentByte[] canvases) {
            int columns;
            Rectangle rect;
            int footer = widths.length - table.getFooterRows();
            int header = table.getHeaderRows() - table.getFooterRows() + 1;
            for (int row = header; row < footer; row += 2) {
                columns = widths[row].length - 1;
                rect = new Rectangle(widths[row][0], heights[row],
                            widths[row][columns], heights[row + 1]);
                rect.setBackgroundColor(BaseColor.YELLOW);
                rect.setBorder(Rectangle.NO_BORDER);
                canvases[PdfPTable.BASECANVAS].rectangle(rect);
            }
        }
    public void cellLayout(PdfPCell cell, Rectangle rect,
                   PdfContentByte[] canvas) {
               PdfContentByte cb = canvas[PdfPTable.BACKGROUNDCANVAS];
               cb.roundRectangle(
                   rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                   rect.getHeight() - 3, 4);
               cb.setCMYKColorFill(0x00, 0x00, 0x00, 0x00);
               cb.fill();
           }
       
   
    class RoundRectangle implements PdfPCellEvent {
        public void cellLayout(PdfPCell cell, Rectangle rect,
                PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            cb.roundRectangle(
                rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                rect.getHeight() - 3, 4);
            cb.stroke();
        }
    }
       public static final String DEST = "D:/adf/ADFtable07.pdf";
       private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
       private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
       private static Font headerFont = null;// new Font( BaseFont.createFont("c://windows/fonts/arial.ttf", BaseFont.IDENTITY_H, true), 16, Font.BOLD);
       private static Font smallBold =null;// new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
       private static Font titleFont = null;// new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.LIGHT_GRAY);
       private static PdfWriter pdfWriter;
       
    public EmployeesBean() {
        try {
           titleFont=  new Font(BaseFont.createFont("c://windows/fonts/arial.ttf", BaseFont.IDENTITY_H, true), 9, Font.BOLD);
         headerFont =   new Font( BaseFont.createFont("c://windows/fonts/arial.ttf", BaseFont.IDENTITY_H, true),9, Font.BOLD);
            headerFont.setColor(BaseColor.WHITE);
         smallBold =   new Font(BaseFont.createFont("c://windows/fonts/arial.ttf", BaseFont.IDENTITY_H, true), 6, Font.NORMAL);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String exportPdf() {
          DCIteratorBinding it =
              (DCIteratorBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("EmployeesView1Iterator");
          ArrayList<String> attributesName = new ArrayList<String>();
          attributesName.add("EmployeeId");
          attributesName.add("FirstName");
          attributesName.add("LastName");
          attributesName.add("HireDate");
          attributesName.add("JobId");
          attributesName.add("Email");
          attributesName.add("PhoneNumber");
          attributesName.add("Salary");
         
          createPDF(it, attributesName);
return null;
      }
    public void createPDF (DCIteratorBinding iterator, ArrayList<String> attNames){
            try {
                      

                       File file = new File(DEST);
                       file.getParentFile().mkdirs();
                
                
                       Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                if(attNames.size()>3)
                       document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                
                       pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(DEST));
                       document.open();
                       addLogoMomra(document); // logo
                      
                       addParagraph(document, loadKey("paragraph"));
                       addNewLine(document);
                       addNewLine(document);
                       String repName = loadKey("reportName");
                       addTitlePage(document, repName); // report name
                       addNewLine(document);
                       document.add(drawTable(iterator, attNames)); // add table
                       
                       document.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (DocumentException e) {
                       e.printStackTrace();
                   }
        
        
      //  return null;
        }
  
    private String loadKey(String key) {
        ResourceBundle crunchifyResourceBundle =
            ResourceBundle.getBundle("com.sadek.test.bundle");
        return crunchifyResourceBundle.getString(key);
    }
       private static void addLogoMomra(Document document) {
           Paragraph logoParagraph = new Paragraph();
           logoParagraph.setAlignment(Element.ALIGN_RIGHT);
           Image image = null;
           try {
               image = Image.getInstance("D:/iiii.jpg");
               image.scaleToFit(100f, 100f);
               image.setAlignment(Element.ALIGN_RIGHT);
           } catch (Exception e) {
               e.printStackTrace();
           }
           logoParagraph.add(image);
           try {
               document.add(logoParagraph);
           } catch (DocumentException e) {
               e.printStackTrace();
           }
       }

       private static void addTitlePage(Document document, String reportTitle) {
           PdfPTable table = new PdfPTable(1);

           table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
           PdfPCell cell;
           cell = new PdfPCell(new Phrase(reportTitle, titleFont));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           cell.setBorder(0);
           table.addCell(cell);
           try {
               document.add(table);
           } catch (DocumentException e) {
               e.printStackTrace();
           }

       }


       private static void addNewLine(Document document) {
           try {
               document.add(Chunk.NEWLINE);
           } catch (DocumentException e) {
               e.printStackTrace();
           }
       }
    private String getDataType(Row[] rows ,String attName)
      {
          String lastWord ="";
          String datatype=rows[0].getStructureDef().findAttributeDef(attName).getJavaType().getName(); 
           lastWord = datatype.substring(datatype.lastIndexOf(".")+1);
            System.out.print(" "+lastWord);
          return lastWord;
      }
    private float getCellWidth( Row[] rows ,List<String> attNames){
            
            int numString = 0;
            int numDate   = 0;
            int numNumber = 0;
            float cellWdith = 0;
            float smallWidth = 0;

                for (int i=0; i<attNames.size();i++) {
            if("class java.lang.String".equalsIgnoreCase(rows[0].getStructureDef().getAttributeDef(i).getJavaType().toString()))
            {
                numString+=1;
 
              System.out.println("String");
            }
                    if("class oracle.jbo.domain.Date".equalsIgnoreCase(rows[0].getStructureDef().getAttributeDef(i).getJavaType().toString())){
                        numDate+=1;

                      System.out.println("Date");
                    }
                    if("class oracle.jbo.domain.Number".equalsIgnoreCase(rows[0].getStructureDef().getAttributeDef(i).getJavaType().toString())){
                        
                        numNumber+=1;
                      System.out.println("Number");
                    }       
                    
                }
                    int smallCell = numNumber+(numString*3)+(numDate*2);
                         smallWidth =100/smallCell;

        return smallWidth;
        }
    
    private PdfPTable drawTable(DCIteratorBinding iterator2, List<String> attNames) {
            
              DCIteratorBinding iterator =
                (DCIteratorBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get("EmployeesView1Iterator");
            Row[] rows = iterator.getAllRowsInRange();
            float[] columnWidth = new float[attNames.size()];
            PdfPTable table = new PdfPTable(attNames.size());
            float cellwidth = getCellWidth(rows,attNames);
            table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//            PdfPTable    outertable= new PdfPTable(1);
//            outertable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        // header row:
       
//        PdfPTableEvent event = new EmployeesBean(); 

//            table.setTableEvent(event); 
            
            PdfPCell cell = null;
//            PdfPCellEvent roundRectangle = new RoundRectangle();

            // add header
            for (String headerName : attNames) {
                String label = loadKey(headerName);
                cell = new PdfPCell(new Phrase(label, headerFont));
                cell.setBackgroundColor(new BaseColor(60, 128, 0));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setFixedHeight(25);

                table.addCell(cell);
                for (int i=0; i<attNames.size();i++) 
                { 
                String datatype = getDataType(rows ,attNames.get(i).toString());//getDataType("EmployeesView1Iterator",attNames.get(i));
                // System.out.println("cell width "+ cellwidth);
                     if(datatype.equalsIgnoreCase("Number"))
                         columnWidth[columnWidth.length-1-i]=cellwidth;
                     if(datatype.equalsIgnoreCase("String"))
                        columnWidth[columnWidth.length-1-i]=cellwidth*3;
                     if(datatype.equalsIgnoreCase("Date"))
                         columnWidth[columnWidth.length-1-i]=cellwidth*2;
            }
            }
         
            table.setHeaderRows(1);
            // end header

            /*
             * generate table rows from iterator
             */
            RowSetIterator rowSetIter = iterator.getViewObject().createRowSetIterator(null);
            boolean b = true;
            

            while (rowSetIter.hasNext()) {
                Row row = rowSetIter.next();
                for (int i=0; i<attNames.size();i++) 
                {      
                    cell = new PdfPCell(new Phrase(row.getAttribute(attNames.get(i)).toString(), smallBold));
                    cell.setBackgroundColor(b ? new BaseColor(215, 255, 179) : BaseColor.WHITE);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setVerticalAlignment(Element.ALIGN_RIGHT);
                    cell.setNoWrap(false);
                    table.addCell(cell);
                 
                    }
                b = !b;
               
//                cell = new PdfPCell(table);
//                
//                cell.setCellEvent(roundRectangle);
//                cell.setBorder(Rectangle.NO_BORDER);
//                cell.setPadding(8);
            }
            
            try {
                table.setWidths(columnWidth);
            } catch (DocumentException e) {
            }
//            outertable.addCell(cell);
            return table;
            
        }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    private static void addParagraph(Document document, String paragraphName) {
            PdfPTable table = new PdfPTable(1);
           table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
           PdfPCell cell;
           cell = new PdfPCell(new Phrase(paragraphName, smallBold));
           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
           cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
           cell.setBorder(0);
           table.addCell(cell);
           try {
               document.add(table);
           } catch (DocumentException e) {
               e.printStackTrace();
           } 
    }

}
