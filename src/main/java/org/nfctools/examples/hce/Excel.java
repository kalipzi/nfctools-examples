/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nfctools.examples.hce;

/**
 *
 * @author Zahra
 */


import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
public class Excel {
    
        private static final String FILE_NAME = "C:\\Users\\Zahra\\Documents\\NetBeansProjects\\rollcall.xlsx";
    
        String date;
        String entry;
        int r=0;
        String i;
        
        public void main(){
       
        }
    
    public void Excel(String dataIn) throws IOException{
        FileInputStream fsIP= new FileInputStream(new File(FILE_NAME)); 
           XSSFWorkbook workbook = new XSSFWorkbook(fsIP);
        XSSFSheet sheet = workbook.getSheet("rollcall");
        
       DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
LocalDate localDate = LocalDate.now();
        date=dtf.format(localDate);

        
        Calendar cal=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
       entry=sdf.format(cal.getTime());
        Object[][] datatypes = {
        
                {dataIn, entry, date, ""}
               
               
        };

        int rowNum =sheet.getPhysicalNumberOfRows();
        System.out.println("Creating excel");
           Iterator<Row> iterator =sheet.iterator();
           
           f:
        while( r != 1){
          
            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
//                Iterator<Cell> cellIterator = currentRow.iterator();

//                while (cellIterator.hasNext()) {
//
                    Cell currentCell = currentRow.getCell(0);
                  Cell currentCel2= currentRow.getCell(2);
                    DataFormatter fmt = new DataFormatter();
                   String valueAsSeenInExcel = fmt.formatCellValue(currentCell);
                   String valueAsSeenInExcel2=fmt.formatCellValue(currentCel2);
                    if( valueAsSeenInExcel.equals(dataIn) && valueAsSeenInExcel2.equals(date)){
                   
                       Cell currentCell3 = currentRow.getCell(3); 
                       currentCell3.setCellValue(entry);
                        
                     break f;
                      
                    }}
            
            
            for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }
        r=1;}
        
               
             
                    
    

               
            
            
        
    
           
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
        
    }

