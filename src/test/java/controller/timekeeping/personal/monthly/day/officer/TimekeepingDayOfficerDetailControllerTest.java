package controller.timekeeping.personal.monthly.day.officer;

import java.sql.Date;
import java.sql.Time;

import javax.annotation.processing.Generated;

import org.junit.Test;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import model.logtimekeeping.LogTimekeepingOfficer;

@Generated(value = "org.junit-tools-1.1.0")
public class TimekeepingDayOfficerDetailControllerTest extends TestCase{

	private TimekeepingDayOfficerDetailController createTestSubject() {
		return new TimekeepingDayOfficerDetailController();
	}

	@Test
	public void testCalculateSalary_LogNull_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = null;
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 0);
	}
	@Test
	public void testCalculateSalary_OnlyWorkMorning_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("08:00:00"),Time.valueOf("11:00:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 90000);
	}
	@Test
	public void testCalculateSalary_OnlyWorkAfternoon_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("13:00:00"),Time.valueOf("16:00:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 90000);
	}
	@Test
	public void testCalculateSalary_Work2buoi_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("07:30:00"),Time.valueOf("16:00:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 210000);
	}
	@Test
	public void testCalculateSalary_Work2buoiOvertime_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("07:30:00"),Time.valueOf("18:30:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 300000);
	}
	@Test
	public void testCalculateSalary_WorkAfternoonOvertime_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("13:00:00"),Time.valueOf("21:30:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 360000);
	}
	@Test
	public void testCalculateSalary_WorkOnlyOvertime_BlackBox() throws Exception {
		TimekeepingDayOfficerDetailController testSubject;
		LogTimekeepingOfficer log = new LogTimekeepingOfficer("LOG01","nv1",Date.valueOf("2023-06-01"),Time.valueOf("17:30:00"),Time.valueOf("21:30:00"), true,false);
		int result;

		// default test
		testSubject = createTestSubject();
		result = testSubject.calculateSalary(log);
		Assert.assertTrue(result == 240000);
	}
	
}