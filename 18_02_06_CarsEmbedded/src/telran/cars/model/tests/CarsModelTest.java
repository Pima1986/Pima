package telran.cars.model.tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import telran.cars.dto.Car;
import telran.cars.dto.CarsReturnCode;
import telran.cars.dto.Driver;
import telran.cars.dto.Model;
import telran.cars.dto.RentCompanyData;
import telran.cars.dto.RentRecord;
import telran.cars.model.IRentCompany;
import telran.cars.model.RentCompanyEmbedded;

public class CarsModelTest {
private static final int FINE_PER_DAY = 10;
private static final int GAS_LITER_PRICE = 10;
private static final String REG_NUMBER1 = "123ILS";
private static final String COLOR1 = "red";
private static final String MODEL_NAME1 = "Mersedes600";
private static final String REG_NUMBER2 = "124ILS";
private static final String COLOR2 = "green";
private static final long LICENSE1 = 123;
private static final String NAME1 = "Vasya";
private static final int BIRTH_YEAR1 = 1970;
private static final String PHONE1 = "054-1234567";
private static final long LICENSE2  = 124;
private static final String NAME2 = "Olya";
private static final int BIRTH_YEAR2 = 1980;
private static final String PHONE2 = "053-5674321";
private static final int GAS_TANK = 60;
private static final String COMPANY = "Mersedes";
private static final String COUNTRY = "Germany";
private static final int PRICE_DAY = 350;
private static final String DATE1 = "2018-02-10";
private static final String DATE2 = "2018-02-12";
private static final String DATE3 = "2018-02-20";
private static final String DATE4 = "2018-02-24";
private static final int DELAY_DAYS=2;//12+10=22, but returned 24
private static final LocalDate RENT_DATE1 = LocalDate.parse(DATE1);
private static final int RENT_DAYS = 10;
private static final LocalDate RENT_DATE2 = LocalDate.parse(DATE2);
private static final LocalDate RETURN_DATE1 = LocalDate.parse(DATE3);
private static final LocalDate RETURN_DATE2 = LocalDate.parse(DATE4);
private static final int GAS_TANK_PERCENT1 = 100;
private static final int GAS_TANK_PERCENT2 = 50;
private static final int DAMAGES1 = 0;
private static final int DAMAGES2 = 50;
Car car1=new Car(REG_NUMBER1, COLOR1, MODEL_NAME1);
Car car2=new Car(REG_NUMBER2, COLOR2, MODEL_NAME1);
Driver driver1=new Driver(LICENSE1, NAME1, BIRTH_YEAR1, PHONE1);
Driver driver2=new Driver(LICENSE2, NAME2, BIRTH_YEAR2, PHONE2);
Model model=new Model(MODEL_NAME1, GAS_TANK, COMPANY, COUNTRY, PRICE_DAY);
IRentCompany company;
	@Before
	public void setUp() throws Exception {
		company=new RentCompanyEmbedded();
		company.setCompanyData(new RentCompanyData
				(FINE_PER_DAY, GAS_LITER_PRICE));
		company.addModel(model);
		company.addDriver(driver1);
		company.addDriver(driver2);
		company.addCar(car1);
		company.addCar(car2);
		
		company.rentCar(REG_NUMBER1, LICENSE1, RENT_DATE1, RENT_DAYS);
		company.rentCar(REG_NUMBER2, LICENSE2, RENT_DATE2, RENT_DAYS);
		company.returnCar
		(REG_NUMBER1, LICENSE1, RETURN_DATE1, GAS_TANK_PERCENT1, DAMAGES1);
//		company.returnCar
//		(REG_NUMBER2, LICENSE2, RETURN_DATE2, GAS_TANK_PERCENT2, DAMAGES2);
		
	}

	@Test
	public void testGetModel() {
		Model actualModel=company.getModel(MODEL_NAME1);
		assertEquals(MODEL_NAME1,actualModel.getModelName());
	}
	@Test 
	public void testGetDriver() {
		Driver actualDriver=company.getDriver(LICENSE1);
		assertEquals(LICENSE1,actualDriver.getLicenseId());
	}
	@Test
	public void testGetCar() {
		Car actualCar=company.getCar(REG_NUMBER1);
		assertEquals(REG_NUMBER1,actualCar.getRegNumber());
	}
	@Test
	public void testRentCar(){
		
		assertEquals(CarsReturnCode.CAR_IN_USE,company.rentCar
				(REG_NUMBER2, LICENSE2, RENT_DATE2, RENT_DAYS));
		assertEquals(CarsReturnCode.OK,company.rentCar
				(REG_NUMBER1, LICENSE2, RENT_DATE2, RENT_DAYS));
		//getting record for car with REG_NUMBER2 
		RentRecord record = getRecord(REG_NUMBER2);
		assertEquals(null,record.getReturnDate());
		assertEquals(LICENSE2,record.getLicenseId());
		assertEquals(RENT_DATE2,record.getRentDate());
		assertEquals(RENT_DAYS,record.getRentDays());
		
	}

	private RentRecord getRecord(String regNumber) {
		RentRecord record=company.getAllRecords()
				.filter(r->r.getCarNumber()==regNumber).findFirst()
				.get();
		return record;
	}
	@Test 
	public void testReturnCar(){
		assertEquals(CarsReturnCode.CAR_NOT_RENTED,company.returnCar
			(REG_NUMBER1, LICENSE1, RETURN_DATE1, 100, 0));
		assertEquals(CarsReturnCode.RETURN_DATE_WRONG,company.returnCar
			(REG_NUMBER2, LICENSE2, RENT_DATE1, GAS_TANK_PERCENT2, DAMAGES2));
		assertEquals(CarsReturnCode.OK,company.returnCar
				(REG_NUMBER2, LICENSE2, RETURN_DATE2, GAS_TANK_PERCENT2, DAMAGES2));
		RentRecord record1=getRecord(REG_NUMBER1);
		RentRecord record2=getRecord(REG_NUMBER2);
		assertEquals(RETURN_DATE1,record1.getReturnDate());
		assertEquals(RETURN_DATE2,record2.getReturnDate());
		assertFalse(car1.isFlRemoved());
		assertTrue(car2.isFlRemoved());
		assertFalse(car1.isInUse());
		assertFalse(car2.isInUse());
		assertEquals(getCost1(),record1.getCost(),0.1F);
		assertEquals(getCost2(),record2.getCost(),0.1F);
		
	}

	private float getCost2() {
		float res=getCost1()+
		(GAS_TANK-GAS_TANK_PERCENT2*GAS_TANK/100)*GAS_LITER_PRICE+
		DELAY_DAYS*(PRICE_DAY+FINE_PER_DAY*PRICE_DAY/100);
		return res;
	}
	
	private float getCost1() {
		return PRICE_DAY*RENT_DAYS;
	}
	@Test
	public void testGetCarDrivers(){
		List<Driver> drivers=company.getCarDrivers(REG_NUMBER1);
		assertEquals(1,drivers.size());
		Driver driver=drivers.get(0);
		assertEquals(LICENSE1,driver.getLicenseId());
	}
	@Test
	public void testGetDriverCars(){
		List<Car> cars=company.getDriverCars(LICENSE2);
		assertEquals(1,cars.size());
		Car car=cars.get(0);
		assertEquals(REG_NUMBER2,car.getRegNumber());
	}
	@Test
	public void testGetAllRecords(){
		assertEquals(2,company.getAllRecords().count());
	}
	@Test
	public void testGetAllDrivers(){
		assertEquals(2,company.getAllDrivers().count());
	}
	@Test
	public void testGetAllCars(){
		assertEquals(2,company.getAllCars().count());
	}
	@Test
	public void testRemoveCar(){
		assertEquals(CarsReturnCode.CAR_IN_USE,
				company.removeCar(REG_NUMBER2));
		assertEquals(CarsReturnCode.OK,
				company.removeCar(REG_NUMBER1));
		assertTrue(car1.isFlRemoved());
		
	}
	@Test
	public void testClear(){
		LocalDate currentDate=LocalDate.parse("2021-10-10");
		int days=1000; 
		List<Car> deletedCars=company.clear(currentDate, days);
		assertTrue(deletedCars.isEmpty());
		company.removeCar(REG_NUMBER1);
		assertEquals(CarsReturnCode.OK,company.returnCar
				(REG_NUMBER2, LICENSE2, RETURN_DATE2, GAS_TANK_PERCENT2, DAMAGES2));
		deletedCars=company.clear(currentDate, days);
		assertEquals(2,deletedCars.size());
		assertTrue(company.getCarDrivers(REG_NUMBER1).isEmpty());
		assertTrue(company.getCarDrivers(REG_NUMBER2).isEmpty());
		assertTrue(company.getDriverCars(LICENSE1).isEmpty());
		assertTrue(company.getDriverCars(LICENSE2).isEmpty());
		assertEquals(0,company.getAllCars().count());
		assertEquals(0,company.getAllRecords().count());
	}
}
