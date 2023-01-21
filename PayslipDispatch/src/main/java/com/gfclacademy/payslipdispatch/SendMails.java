/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gfclacademy.payslipdispatch;

import org.apache.pdfbox.multipdf.Splitter; 
import org.apache.pdfbox.pdmodel.PDDocument;  

import java.io.File; 
import java.io.IOException; 
import java.util.ArrayList;

import java.util.List; 
import java.util.Iterator;  
import org.apache.pdfbox.Loader;

/**
 *
 * @author DownTown
 */
public class SendMails {
    public void splitPdf(File pdfBatch, File emailsFile,String dirname, String trxndate) throws IOException{
        
        //get emails as an arraylist
        GetEmails e = new GetEmails();
        List aList = e.getList(emailsFile);
        
        System.out.println("Staff Size is "+aList.size()/2);
        
      EmailDispatcher ed = new EmailDispatcher(aList,pdfBatch);
    }
}
