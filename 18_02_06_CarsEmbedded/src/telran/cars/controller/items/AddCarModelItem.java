package telran.cars.controller.items;

import telran.cars.dto.CarsReturnCode;
import telran.cars.dto.Model;
import telran.cars.model.IRentCompany;
import telran.view.InputOutput;

public class AddCarModelItem extends CarsItem {

	public AddCarModelItem(InputOutput inputOutput, IRentCompany company) {
		super(inputOutput, company);
	}

	@Override
	public String displayedName() {
		return "Adding new car model";
	}

	@Override
	public void action() {
		Model model=getCarModel();
		CarsReturnCode code=company.addModel(model);
		inputOutput.displayLine(code);

	}

	private Model getCarModel() {
		String modelName=inputOutput.getString("Enter car model name");
		int gasTank=
		inputOutput.getInteger("Enter gas tank volume",
				40, 70);
		String companyName=inputOutput.getString("Enter company name");
		String country=inputOutput.getString("Enter country");
		int priceDay=inputOutput.getInteger
				("Enter price per day", 80, 700);
		return new Model
		(modelName, gasTank, companyName, country, priceDay);
	}

}
