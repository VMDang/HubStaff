package createDB;

import model.logtimekeeping.LogTimekeepingWorker;
import java.sql.Date;
import java.sql.Time;

import services.logtimekeeping.LogTimekeepingWorkerService;
public class test {
	public static void main(String[] args) {
		
//		
//		for(int i = 1; i<=10; i++) {
//			LogTimekeepingWorker log = new LogTimekeepingWorker("LOG"+i,"nv"+i,Date.valueOf("2023-01-03"),Time.valueOf("08:00:00"),Time.valueOf("18:12:00"), 4, 4, 4);
//			LogTimekeepingWorkerDAO.getInstance().insert(log);
//		}
		
		
//		ArrayList<LogTimekeepingWorker> arr = LogTimekeepingWorkerDAO.getInstance().selectAll();
//		for(LogTimekeepingWorker log : arr) {
//			System.out.println(log.toString());
//		}
		
//		LogTimekeepingWorker log = new LogTimekeepingWorker("LOG1","nv1",Date.valueOf("2023-12-03"),Time.valueOf("08:00:00"),Time.valueOf("18:12:00"), 3, 2, 1);
//		LogTimekeepingWorkerDAO.getInstance().update(log);
		
//		LogTimekeepingWorker log = LogTimekeepingWorkerDAO.getInstance().selectById("LOG2");
//		System.out.println(log.toString());
		
//		ArrayList<LogTimekeepingWorker> arr = LogTimekeepingWorkerDAO.getInstance().selectByCondition("ID = 'LOG1'");
//		for(LogTimekeepingWorker log : arr) {
//			System.out.println(log.toString());
//		}
		
		
		
		for(int i = 1; i<=10; i++) {
			LogTimekeepingWorker log = new LogTimekeepingWorker("LOG"+i+99,"nv10",Date.valueOf("2023-01-03"),Time.valueOf("08:00:00"),Time.valueOf("18:12:00"), 4, 4, 4);
			LogTimekeepingWorkerService.getInstance().insert(log);
		}
		
	}
}
