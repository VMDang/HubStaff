package genneralinfomation;

import org.junit.Test;

import controller.report.hrmanager.generalinformation.GeneralInformationUnit;
import junit.framework.TestCase;

public class GeneralInfomationTest extends TestCase {

	@Test
	public void JunitMethod() {
		System.out.println("abc");
	}
	
	GeneralInformationUnit generalInformationUnit;
	
	public void testCountNumberWorker() {
		assertTrue(GeneralInformationUnit.countNumberWorkerUnit("123")==2);
	}
	
	public void testCountNumberOfficer() {
		assertTrue(GeneralInformationUnit.countNumberOfficerUnit("443")==1);
	}
}
