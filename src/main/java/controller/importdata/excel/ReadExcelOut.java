package controller.importdata.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	 
	public class ReadExcelOut {
	    public static final int COLUMN_INDEX_EMPLOYEE_ID = 0;
	    public static final int COLUMN_INDEX_TIMESTAMP = 1;
	 
	   
	    public static void readExcel(String excelFilePath, List<ExcelImportRow> lists) throws IOException {
	        List<ExcelImportRow> excelImportRows = new ArrayList<>();
	 
	        // Get file
	        InputStream inputStream = new FileInputStream(new File(excelFilePath));
	        
	 
	        // Get workbook
	        Workbook workbook = getWorkbook(inputStream, excelFilePath);
	 
	        // Get sheet
	        Sheet sheet = workbook.getSheetAt(0);
	        int numberOfColumns = sheet.getRow(0).getLastCellNum();
	        int number = 2 ;
	        if(numberOfColumns!=number) {
	        	workbook.close();
	 	        inputStream.close();
	        	return;
	        }
	        // Get all rows
	        Iterator<Row> iterator = sheet.iterator();
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            if (nextRow.getRowNum() == 0) {
	                // Ignore header
	                continue;
	            }
	 
	            // Get all cells
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	 
	            // Read cells and set value for book object
	            ExcelImportRow excelImportRow = new ExcelImportRow();
	            while (cellIterator.hasNext()) {
	                //Read cell
	                Cell cell = cellIterator.next();
	                Object cellValue = getCellValue(cell);
	                if (cellValue == null || cellValue.toString().isEmpty()) {
	                    continue;
	                }
	                // Set value for book object
	                int columnIndex = cell.getColumnIndex();
	                switch (columnIndex) {
	                case COLUMN_INDEX_EMPLOYEE_ID:
	                	excelImportRow.setEmployee_id((String) getCellValue(cell));
	                    break;
	                case COLUMN_INDEX_TIMESTAMP:
	                	String timestamp = (String) getCellValue(cell);
	                	String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
	                	boolean isMatch = Pattern.matches(pattern, timestamp);
	                	if(isMatch==true) {
	                	excelImportRow.setDate(timestamp.substring(0, 10));
	                	
	                	int a = -1;
	                	for(ExcelImportRow list : lists) {
	                		if(list.getEmployee_id().equals(excelImportRow.getEmployee_id())&&list.getDate().equals(excelImportRow.getDate())) {
	                			list.setTime_out(timestamp.substring(11,19));
	                			a = 1;
	                		}
	                	}
	                	if(a==-1) {
	                	excelImportRow.setTime_out(timestamp.substring(11,19));
	                	lists.add(excelImportRow);
	                	}
	                	else {
	                		break;
	                	}}
	                default:
	                    break;
	                }
	 
	            }
	        }
	 
	        workbook.close();
	        inputStream.close();
	 
	    }
	 
	    // Get Workbook
	    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
	        Workbook workbook = null;
	        if (excelFilePath.endsWith("xlsx")) {
	            workbook = new XSSFWorkbook(inputStream);
	        } else if (excelFilePath.endsWith("xls")) {
	            workbook = new HSSFWorkbook(inputStream);
	        } else {
	            throw new IllegalArgumentException("The specified file is not Excel file");
	        }
	 
	        return workbook;
	    }
	 
	    // Get cell value
	    private static Object getCellValue(Cell cell) {
	        CellType cellType = cell.getCellTypeEnum();
	        Object cellValue = null;
	        switch (cellType) {
	        case BOOLEAN:
	            cellValue = cell.getBooleanCellValue();
	            break;
	        case FORMULA:
	            Workbook workbook = cell.getSheet().getWorkbook();
	            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
	            cellValue = evaluator.evaluate(cell).getNumberValue();
	            break;
	        case NUMERIC:
	            cellValue = cell.getNumericCellValue();
	            break;
	        case STRING:
	            cellValue = cell.getStringCellValue();
	            break;
	        case _NONE:
	        case BLANK:
	        case ERROR:
	            break;
	        default:
	            break;
	        }
	 
	        return cellValue;
	    }
	}

