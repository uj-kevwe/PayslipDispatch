/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gfclacademy.payslipdispatch;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
/**
 *
 * @author DownTown
 */
public class EmailDispatcher {
    private PDDocument pdfBatch;
    private int startPage = 1;
    private int endPage = 0;
    private int totalPages = 0;
    private File pdfBatchFile;
    private List emailslist;
    private String staffId;
    private String staffEmail;
    private String fileName;
    
    PDFTextStripper pageContent = new PDFTextStripper();
    
    
    
    public EmailDispatcher(List emailFiles, File pdfBatchFile)throws IOException{
        this.pdfBatch = Loader.loadPDF(pdfBatchFile);
        totalPages = pdfBatch.getNumberOfPages();
        this.pdfBatchFile = pdfBatchFile;
        this.emailslist = emailFiles;
        
        //Instantiating Splitter class
        Splitter splitter = new Splitter();


        //splitting the pages of a PDF document
        List<PDDocument> Pages = splitter.split(pdfBatch);
        //Creating an iterator
        Iterator<PDDocument> iterator = Pages.listIterator();

        int i = 1;
        while(iterator.hasNext()){
            PDDocument pd = iterator.next();
            pd.save("splitedpdfpage"+i++ +".pdf");
        }
        
        for(int staffno = 0; staffno<emailFiles.size();staffno++){
            staffId = emailFiles.get(staffno).toString();
            staffEmail = emailFiles.get(staffno++).toString();
            
            for(int pageno = 0;pageno<totalPages;pageno++){
                int page = pageno+1;
                int nextPage = page + 1;
                if(pageContent.getText(Pages.get(pageno)).contains(staffId)){
                    //System.out.println("Page "+page +" is Page 1 for Staff: "+staffId);
                    setStartPage(pageno);
                    setEndPage(pageno);
                    if(!pageContent.getText(Pages.get(page)).contains("IPPIS Number:")){
                       // System.out.println("Page "+nextPage +" is Page 2 for Staff: "+staffId);
                        setEndPage(page);
                    }
                    extract(staffId,getStartPage(),getEndPage());
                    break;
                }
                else{
                    continue;
                }
            }
        }
    }
    
    
    public void extract(String staffId,int startPage, int endPage) throws IOException{
        setFileName("Payslip_"+staffId+".pdf");
        fileName = getFileName();
        this.pdfBatch = Loader.loadPDF(pdfBatchFile);
        PDDocument extractedDocument = new PDDocument();
        extractedDocument.setDocumentInformation(pdfBatch.getDocumentInformation());
        extractedDocument.getDocumentCatalog().setViewerPreferences(pdfBatch.getDocumentCatalog().getViewerPreferences());
        
        for(int i = startPage; i <= endPage; i++){
            //JOptionPane.showMessageDialog(null, "About to Extract Pages "+startPage);
            PDPage page = pdfBatch.getPage(i);
            PDPage imported = extractedDocument.importPage(page);
            imported.setCropBox(page.getCropBox());
            imported.setMediaBox(page.getMediaBox());
            imported.setResources(page.getResources());
            imported.setRotation(page.getRotation());
        }
        extractedDocument.save("C:\\PayslipDoc\\"+fileName);
        String newFile = "C:\\PayslipDoc\\"+fileName;
        DispatchPayslip dp = new DispatchPayslip(extractedDocument,staffEmail,newFile);
        
    }
    
    public int getStartPage(){
        return startPage;
    }
    
    public void setStartPage (int startPage){
        this.startPage = startPage;
    }
    
    public int getEndPage(){
        return endPage;
    }
    
    public void setEndPage(int endPage){
        this.endPage = endPage;
    }
    
    public void setFileName(String filename){
        this.fileName = filename;
    }
    
    public String getFileName(){
        return fileName;
    }
}
