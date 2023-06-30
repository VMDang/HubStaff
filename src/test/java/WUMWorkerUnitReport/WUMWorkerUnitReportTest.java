package WUMWorkerUnitReport;

import controller.report.unitmanager.workerunitreport.WUMWorkerUnitReportController;
import junit.framework.TestCase;
import model.logtimekeeping.LogTimekeepingWorker;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class WUMWorkerUnitReportTest extends TestCase {
    @Test
    public void testGetTimekeepingsAWorker_NotExistEmployeeID_BlackBox(){
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = null;

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        // Kiểm tra xem danh sách kết quả trả về có là mảng rỗng hoặc null không
        Assert.assertTrue((logTimekeepingWorkers == null) || (logTimekeepingWorkers.isEmpty()));

    }

    @Test
    public void testGetTimekeepingsAWorker_ExistTimekeeping_BlackBox(){
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = "nv54";

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        // Kiểm tra xem danh sách kết quả trả về có ít nhất một phần tử hay không
        Assert.assertTrue(logTimekeepingWorkers.size() > 0);
    }

    @Test
    public void testGetTimekeepingsAWorker_NotExistTimekeeping_BlackBox(){
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = "nv53";

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        // Kiểm tra xem danh sách kết quả trả về là một mảng rỗng không
        Assert.assertTrue(logTimekeepingWorkers.size() == 0);
    }

    @Test
    public void testGetTimekeepingsAWorker_NotExistEmployeeID_WhiteBox(){
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = null;

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        // Kiểm tra xem danh sách kết quả trả về có là mảng rỗng không
        Assert.assertTrue(logTimekeepingWorkers.isEmpty());
    }

    @Test
    public void testGetTimekeepingsAWorker_ExistTimekeeping_WhiteBox() {
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = "nv54";

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        Assert.assertTrue(logTimekeepingWorkers.size() > 0);

        for (LogTimekeepingWorker log : logTimekeepingWorkers) {
            Assert.assertTrue(log.getEmployee_id().equals(employee_id));
        }
    }

    @Test
    public void testGetTimekeepingAWorker_NotExistTimekeeping_WhiteBox(){
        WUMWorkerUnitReportController controller  = new WUMWorkerUnitReportController();

        String employee_id = "nv53";

        ArrayList<LogTimekeepingWorker> logTimekeepingWorkers = controller.getTimekeepingsAWorker(employee_id);

        // Kiểm tra xem danh sách kết quả trả về là một mảng rỗng không
        Assert.assertTrue(logTimekeepingWorkers.size() == 0);
    }
}