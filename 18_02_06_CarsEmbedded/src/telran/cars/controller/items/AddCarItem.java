package telran.cars.controller.items;

import java.util.Arrays;
import java.util.List;

import telran.cars.dto.Car;
import telran.cars.dto.CarsReturnCode;
import telran.cars.model.IRentCompany;
import telran.view.InputOutput;

public class AddCarItem extends CarsItem {

	public AddCarItem
	(InputOutput inputOutput, IRentCompany company) {
		super(inputOutput, company);
	}

	@Override
	public String displayedName() {
		
		return "Adding new car";
	}

	@Override
	public void action() {
		Car car=getCar();
		CarsReturnCode code=company.addCar(car);
		inputOutput.displayLine(code);

	}

	private Car getCar() {
		
		String regNumber=inputOutput.getString("Enter reg number");
		String color=inputOutput.getString("Enter car's color", getPossibleColors());
		String modelName=inputOutput.getString("Enter model name",getExistingModels());
		return new Car(regNumber, color, modelName);
	}

	private List<String> getExistingModels() {
		
		return company.getAllModels();
	}

	private List<String> getPossibleColors() {
		
		return Arrays.asList("red","green","black","white","yellow","blue","gray","pink");
	}
	
	
	
	
	
	

}
