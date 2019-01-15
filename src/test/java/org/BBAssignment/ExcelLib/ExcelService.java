package org.BBAssignment.ExcelLib;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ExcelService {

    public Iterator<Object[]> readTestDataFromExcel(String sWorkBookName,String sWorkbookSheetName) {
    	String testDataFolderPath = System.getProperty("user.dir")+"\\"+"TestData";
        
        //System.out.println(new File(testDataFolderPath).listFiles().length);
        List<Hashtable<String, String>> testData = null;
        for (File testDataFile : new File(testDataFolderPath).listFiles()) {
        	//System.out.println("testDataFile.getAbsolutePath():::"+testDataFile.getAbsolutePath());
            if (!testDataFile.getAbsolutePath().contains("$")) {
                FileInputStream file = null;
                if(testDataFile.getPath().contains(sWorkBookName)){
                	try {
                    file = new FileInputStream(testDataFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Workbook workBook = null;
                try {
                    workBook = WorkbookFactory.create(file);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
                Sheet testDataSheet = workBook.getSheet(sWorkbookSheetName);
                testData = getTestDataBySheet(testDataSheet);
              
            }
        }
         
        }
        return getIterator(testData);
    }

    public List<Hashtable<String, String>> getTestDataBySheet(Sheet testDataSheet) {
        List<String> headers = getHeaders(testDataSheet);
        List<Hashtable<String, String>> testData = new ArrayList<Hashtable<String, String>>();
        for (int i = 1; i < testDataSheet.getPhysicalNumberOfRows(); i++) {
            Hashtable<String, String> map = new Hashtable<String, String>();
            Row dataRow = testDataSheet.getRow(i);
            Cell dataCell;
            for (int j = 0; j < headers.size(); j++) {
                try {
                    dataCell = dataRow.getCell(j);
                    dataCell.setCellType(Cell.CELL_TYPE_STRING);
                    map.put(headers.get(j), dataCell.getStringCellValue());

                } catch (Exception e) {

                }
            }
            testData.add(map);

        }
        return testData;
    }

    public Iterator<Object[]> getIterator(List<Hashtable<String, String>> testDataList) {
        List<Object[]> iteratorList = new ArrayList<Object[]>();
        for (Map map : testDataList) {
            iteratorList.add(new Object[]{map});
        }

        return iteratorList.iterator();

    }


    public List<String> getHeaders(Sheet sheet) {

        List<String> headers = new ArrayList<String>();
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
            Cell dataCell = headerRow.getCell(i);
            dataCell.setCellType(Cell.CELL_TYPE_STRING);
            headers.add(dataCell.getStringCellValue());
        }
        return headers;

    }

    public Iterator<Object[]> readTestDataFromExcel(String sWorkBookName,String sWorkbookSheetName,String tableName) {
    	String testDataFolderPath = System.getProperty("user.dir")+"\\"+"TestData";
        
        //System.out.println(new File(testDataFolderPath).listFiles().length);
        List<Hashtable<String, String>> testData = null;
        for (File testDataFile : new File(testDataFolderPath).listFiles()) {
        	//System.out.println("testDataFile.getAbsolutePath():::"+testDataFile.getAbsolutePath());
            if (!testDataFile.getAbsolutePath().contains("$")) {
                FileInputStream file = null;
                if(testDataFile.getPath().contains(sWorkBookName)){
                	try {
                    file = new FileInputStream(testDataFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Workbook workBook = null;
                try {
                    workBook = WorkbookFactory.create(file);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
                Sheet testDataSheet = workBook.getSheet(sWorkbookSheetName);
               testData = getTestDataBySheet(testDataSheet,tableName);
               //testData = getTestData(testDataSheet,tableName,workBook);
            }
        }
         
        }
        return getIterator(testData);
    }
    
    public List<Hashtable<String, String>> getTestDataBySheet(Sheet testDataSheet,String tableName) {
        List<Hashtable<String, String>> testData = new ArrayList<Hashtable<String, String>>();
        int startRow,endRow;
       // System.out.println("testDataSheet"+testDataSheet);
        startRow= getStartRow(testDataSheet,tableName);
        List<String> headers = getHeaders(testDataSheet,startRow+1);
        endRow=getEndRow(testDataSheet,tableName,startRow);
        for (int i = startRow+2; i < endRow; i++) {
            Hashtable<String, String> map = new Hashtable<String, String>();
            Row dataRow = testDataSheet.getRow(i);
            Cell dataCell;
            for (int j=1;j<headers.size();j++) {
                if(!headers.get(j).equals("CELL NOT FOUND") && !headers.get(j).equals("MISSING CONTENT")){
                try {
                	dataCell = dataRow.getCell(j,Row.RETURN_NULL_AND_BLANK);
                	if (dataCell!=null){
                	dataCell.setCellType(Cell.CELL_TYPE_STRING);
                	}
                    if(dataCell != null && !("".equals(dataCell.getStringCellValue().trim()))){
                    map.put(headers.get(j), dataCell.getStringCellValue());
                    }
                } catch (Exception e) {
                	e.printStackTrace();
                }
                }
            }
            testData.add(map);

        }
        return testData;
    }
	
	private int getEndRow(Sheet testDataSheet, String tableName, int startRow) {
		for (int i=startRow+1 ; i<testDataSheet.getLastRowNum()+1; i++){
			try{
			if(testDataSheet.getRow(i).getCell(0).getStringCellValue().toString().equalsIgnoreCase(tableName))
			return i;
			}catch(Exception e){
				
			}
		}
		return startRow;
	}

	public int getStartRow(Sheet testDataSheet, String tableName) {
		for (int i=0 ; i<testDataSheet.getLastRowNum()+1; i++){
			try{
				if(testDataSheet.getRow(i).getCell(0).getStringCellValue().toString().equalsIgnoreCase(tableName))
			return i;
			}catch(Exception e){
			}
		}
		return 0;
	}
	
	
	public List<String> getHeaders(Sheet sheet,int row) {

        List<String> headers = new ArrayList<String>();
        Row headerRow = sheet.getRow(row);
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell dataCell = headerRow.getCell(i,Row.RETURN_NULL_AND_BLANK);
            if(dataCell == null)
            	headers.add("CELL NOT FOUND");
            else if("".equals(dataCell.getStringCellValue().trim()))
            	headers.add("MISSING CONTENT");
            else{ 
            dataCell.setCellType(Cell.CELL_TYPE_STRING);
            headers.add(dataCell.getStringCellValue());
            }
        }
        return headers;

    }
	
    /**
    * returns sheet based on the file and sheet name
    * @param sWorkBookName
    * @param sWorkbookSheetName
    * @return
    */
	public static Sheet getSheetData(String sWorkBookName, String sWorkbookSheetName) {
		String testDataFolderPath = System.getProperty("user.dir") + "\\temp";
		System.out.println(new File(testDataFolderPath).listFiles().length);
		Sheet testDataSheet = null;
		for (File testDataFile : new File(testDataFolderPath).listFiles()) {
			System.out.println("testDataFile.getAbsolutePath():::" + testDataFile.getAbsolutePath());
			if (!testDataFile.getAbsolutePath().contains("$")) {
				FileInputStream file = null;
				if (testDataFile.getPath().contains(sWorkBookName)) {
					try {
						file = new FileInputStream(testDataFile);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					Workbook workBook = null;
					try {
						workBook = WorkbookFactory.create(file);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InvalidFormatException e) {
						e.printStackTrace();
					}
					if (sWorkbookSheetName == "") {
						testDataSheet = workBook.getSheet(sWorkbookSheetName);
					} else {
						testDataSheet = workBook.getSheetAt(0);
					}
					break;
				}
			}
		}
		return testDataSheet;
	}
    


}
