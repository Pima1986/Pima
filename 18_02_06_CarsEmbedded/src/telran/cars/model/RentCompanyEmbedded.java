package telran.cars.model;
import java.util.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import telran.cars.dto.*;

public class RentCompanyEmbedded extends AbstractRentCompany {
private HashMap<String,Car> cars=new HashMap<>();
private HashMap<Long,Driver> drivers=new HashMap<>();
private HashMap<String,List<RentRecord>> carRecords=new HashMap<>();
private HashMap<Long,List<RentRecord>> driverRecords=new HashMap<>();
private HashMap<String,Model> models=new HashMap<>();
private TreeMap<LocalDate,List<RentRecord>> returnedRecords=new TreeMap<>();
	@Override
	public CarsReturnCode addModel(Model model) {
		
		return models.putIfAbsent(model.getModelName(), model)==null?
				CarsReturnCode.OK:CarsReturnCode.MODEL_EXISTS;
	}

	@Override
	public CarsReturnCode addCar(Car car) {
		
		return cars.putIfAbsent(car.getRegNumber(),
				car)==null?CarsReturnCode.OK:CarsReturnCode.CAR_EXISTS;
	}

	@Override
	public CarsReturnCode addDriver(Driver driver) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model getModel(String modelName) {
		
		return models.get(modelName);
	}

	@Override
	public Car getCar(String carNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver getDriver(long licenseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarsReturnCode rentCar
	(String carNumber, long licenseId, LocalDate rentDate, int rentDays) {
		Car car=getCar(carNumber);
		if(car==null||car.isFlRemoved())
			return CarsReturnCode.NO_CAR;
		if(car.isInUse())
			return CarsReturnCode.CAR_IN_USE;
		if(getDriver(licenseId)==null)
			return CarsReturnCode.NO_DRIVER;
		RentRecord record=new RentRecord(licenseId, carNumber, rentDate, rentDays);
		addCarRecords(record);
		addDriverRecords(record);
		return CarsReturnCode.OK;
	}

	private void addDriverRecords(RentRecord record) {
		// TODO Auto-generated method stub
		
	}

	private void addCarRecords(RentRecord record) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CarsReturnCode returnCar(String carNumber, long licenseId, LocalDate returnDate, int gasTankPercent,
			int damages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CarsReturnCode removeCar(String carNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Car> clear(LocalDate currentDate, int days) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Driver> getCarDrivers(String carNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Car> getDriverCars(long licenseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Car> getAllCars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Driver> getAllDrivers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<RentRecord> getAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

}
