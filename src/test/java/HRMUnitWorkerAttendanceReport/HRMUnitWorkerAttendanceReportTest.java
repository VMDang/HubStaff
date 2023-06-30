package HRMUnitWorkerAttendanceReport;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert;

import controller.report.hrmanager.unitworkerattendance.HRMUnitWorkerAttendanceReportController;
import model.employee.worker.Worker;

import java.util.ArrayList;

public class HRMUnitWorkerAttendanceReportTest extends TestCase{
	
	@Test
	public void testgetAllWorkerUnit_NotExistUnitID_BlackBox() {
		HRMUnitWorkerAttendanceReportController controller = new HRMUnitWorkerAttendanceReportController();
		
		String unit_id = null;
		
		ArrayList<Worker> allworker = controller.getAllWorkerUnit(unit_id);
		
		// Kiểm tra xem danh sách kết quả trả về có là mảng rỗng hoặc null không
		Assert.assertTrue((allworker == null) || (allworker.isEmpty()));
	}
	
	@Test
	public void testgetAllWorkerUnit_ExistUnitID_BlackBox() {
		HRMUnitWorkerAttendanceReportController controller = new HRMUnitWorkerAttendanceReportController();
		
		String unit_id = "un1";
		
		ArrayList<Worker> allworker = controller.getAllWorkerUnit(unit_id);
		
		// Kiểm tra xem danh sách kết quả trả về có ít nhất một phần tử hay không
		Assert.assertTrue(allworker.size() > 0);
	}
	
	@Test
	public void testgetAllWorkerUnit_NotExistUnitID_WhiteBox() {
		HRMUnitWorkerAttendanceReportController controller = new HRMUnitWorkerAttendanceReportController();
		
		String unit_id = "unw01";
		
		ArrayList<Worker> allworker = controller.getAllWorkerUnit(unit_id);
		
		// Kiểm tra xem danh sách kết quả trả về có là mảng rỗng hoặc null không
		Assert.assertTrue((allworker == null) || (allworker.isEmpty()));
	}
	
	@Test
    public void testGetAllWorkerUnit_ExistUnitID_WhiteBox() {
        // Chuẩn bị dữ liệu mẫu
        String unitId = "un1";

        // Tạo đối tượng controller
        HRMUnitWorkerAttendanceReportController controller = new HRMUnitWorkerAttendanceReportController();

        // Gọi phương thức getAllWorkerUnit() với unitId cụ thể
        ArrayList<Worker> result = controller.getAllWorkerUnit(unitId);

        // Kiểm tra kết quả trả về đầu tiên có đúng như trên database hay không
        	
        Assert.assertEquals("nv1", result.get(0).getId());
        Assert.assertEquals("Le Thi Kinh", result.get(0).getName());
        Assert.assertEquals("un1", result.get(0).getUnit_id());
        Assert.assertEquals("1", result.get(0).getPassword());
        Assert.assertEquals(1, result.get(0).getStatus());
        Assert.assertEquals("nv12", result.get(1).getId());
        Assert.assertEquals("Do Van Nong", result.get(1).getName());
        Assert.assertEquals("un1", result.get(1).getUnit_id());
        Assert.assertEquals("1", result.get(1).getPassword());
        Assert.assertEquals(1, result.get(1).getStatus());
    }
}
