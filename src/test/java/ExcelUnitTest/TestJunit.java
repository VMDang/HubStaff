package ExcelUnitTest;
import org.junit.Assert;
import org.junit.Test;

import controller.importdata.excel.ExcelImportController;
import controller.importdata.excel.ExcelImportRow;
import controller.importdata.excel.ReadExcelIn;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("deprecation")
public class TestJunit extends TestCase {
	@Test
	public void testReadExcelIn() {
		try {
			String filePath = "C:\\f\\abc.xlsx";
				List<ExcelImportRow> T =ReadExcelIn.readExcel(filePath);
			
			//Kiem tra xem ham tra lai co rong hay khong
				Assert.assertTrue(T==null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testReadExcelIn1() {
		try {
			String filePath = "C:\\f\\x.xlsx";
			List<ExcelImportRow> t = ReadExcelIn.readExcel(filePath);
			List<ExcelImportRow> t1 = new ArrayList<ExcelImportRow>();
			ExcelImportRow a = new ExcelImportRow();
			a.setEmployee_id("nv1");
			a.setDate("2023-06-30");
			a.setTime_in("07:30:00");
			t1.add(a);
			Assert.assertTrue(t1.get(0).getTime_in().equals(a.getTime_in()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}

	
}
