/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gfclacademy.payslipdispatch;

import java.util.ArrayList;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;  

/**
 *
 * @author DownTown
 */
public class GetEmails {
    public List getList(File emailsFile) throws IOException{
        ArrayList<List> outputArray = new ArrayList<List>();
        List<String> staffdetails = new ArrayList<String>();
        
        //obtaining input bytes from a file  
        FileInputStream fis=new FileInputStream(emailsFile);  
        //creating workbook instance that refers to .xls file  
        HSSFWorkbook wb=new HSSFWorkbook(fis);   
        //creating a Sheet object to retrieve the object  
        HSSFSheet sheet=wb.getSheetAt(0);  
        //evaluating cell type   
        FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();  
        for(Row row: sheet)  {   
            //retrieve cell 0 and cell 3 into arrays
            if(row.getRowNum()>0){
                staffdetails.add(row.getCell(0).getStringCellValue());
                staffdetails.add(row.getCell(3).getStringCellValue());
//                outputArray.add("staffid"+row.getRowNum(), row.getCell(0).getStringCellValue());
//                outputArray.put("staffemail"+row.getRowNum(), row.getCell(3).getStringCellValue());
            }
            else{
                continue;
            }
        }  
//        Set<Map.Entry<String,String>> s = outputArray.entrySet();
//        ArrayList<Map.Entry<String,String>> arrayoutput = new ArrayList<>(s);
        return staffdetails;
    }
}

