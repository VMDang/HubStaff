package controller.importdata.excel;


	import java.io.File;
	import java.io.FileInputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
	import java.util.Iterator;
	import java.util.List;
	 
	import org.apache.poi.hssf.usermodel.HSSFWorkbook;
	import org.apache.poi.ss.usermodel.Cell;
	import org.apache.poi.ss.usermodel.CellType;
	import org.apache.poi.ss.usermodel.FormulaEvaluator;
	import org.apache.poi.ss.usermodel.Row;
	import org.apache.poi.ss.usermodel.Sheet;
	import org.apache.poi.ss.usermodel.Workbook;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	 
	public class ReadExcelExample {
	    public static final int COLUMN_INDEX_ID = 0;
	    public static final int COLUMN_INDEX_TITLE = 1;
	    public static final int COLUMN_INDEX_DATE = 2;
	    public static final int COLUMN_INDEX_IN = 3;
	    public static final int COLUMN_INDEX_OUT = 4;
	 
	   
	    public static List<ChamCong> readExcel(String excelFilePath) throws IOException {
	        List<ChamCong> chamCongs = new ArrayList<>();
	 
	        // Get file
	        InputStream inputStream = new FileInputStream(new File(excelFilePath));
	        
	 
	        // Get workbook
	        Workbook workbook = getWorkbook(inputStream, excelFilePath);
	 
	        // Get sheet
	        Sheet sheet = workbook.getSheetAt(0);
	 
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
	            ChamCong chamCong = new ChamCong();
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
	                case COLUMN_INDEX_ID:
	                	chamCong.setId(new BigDecimal((double) cellValue).intValue());
	                    break;
	                case COLUMN_INDEX_TITLE:
	                	chamCong.setEmployee_id((String) getCellValue(cell));
	                    break;
	                case COLUMN_INDEX_DATE:
	                	chamCong.setDate((String) getCellValue(cell) );
	                    break;
	                case COLUMN_INDEX_IN:
	                	chamCong.setTime_in((String) getCellValue(cell));
	                    break;
	                case COLUMN_INDEX_OUT:
	                	chamCong.setTime_out((String) getCellValue(cell));
	                    break;
	                default:
	                    break;
	                }
	 
	            }
	            chamCongs.add(chamCong);
	        }
	 
	        workbook.close();
	        inputStream.close();
	 
	        return chamCongs;
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

