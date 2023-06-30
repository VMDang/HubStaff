package ExcelUnitTest;
import org.junit.Assert;
import org.junit.Test;

import controller.importdata.excel.ExcelImportRow;
import controller.importdata.excel.ReadExcelIn;
import controller.importdata.excel.ReadExcelOut;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("deprecation")
public class ExcelImportTest extends TestCase {
	@Test
	public void testReadExcelIn() {
		try {
			String filePath = "E:\\Desktop\\Java\\ITSS\\HubStaff\\src\\main\\java\\assets\\excel\\x.xlsx";
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
			String filePath = "E:\\Desktop\\Java\\ITSS\\HubStaff\\src\\main\\java\\assets\\excel\\abc.xlsx";
			List<ExcelImportRow> t = ReadExcelIn.readExcel(filePath);
			
			ExcelImportRow a = new ExcelImportRow();
			a.setEmployee_id("nv1");
			a.setDate("2023-06-30");
			a.setTime_in("07:30:00");
			//Kiem tra xem ham tra lai co rong hay khong
			Assert.assertTrue(t.get(0).getTime_in().equals(a.getTime_in()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	@Test
	public void testReadExcelAll() {
		try {
			String filePath1 = "E:\\Desktop\\Java\\ITSS\\HubStaff\\src\\main\\java\\assets\\excel\\abc.xlsx";
			String filePath2 = "E:\\Desktop\\Java\\ITSS\\HubStaff\\src\\main\\java\\assets\\excel\\bca.xlsx";
				List<ExcelImportRow> T =ReadExcelIn.readExcel(filePath1);
				ReadExcelOut.readExcel(filePath2, T);
				ExcelImportRow a = new ExcelImportRow();
				a.setEmployee_id("nv1");
				a.setDate("2023-06-30");
				a.setTime_in("07:30:00");
				a.setTime_out("17:30:00");
			//Kiem tra xem ham tra lai co rong hay khong
				Assert.assertTrue(T.get(0).getTime_out().equals(a.getTime_out()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
