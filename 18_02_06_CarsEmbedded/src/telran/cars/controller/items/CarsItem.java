package telran.cars.controller.items;

import telran.cars.model.IRentCompany;
import telran.view.AbstractItem;
import telran.view.InputOutput;

public abstract class CarsItem extends AbstractItem {
	protected IRentCompany company;

	public CarsItem(InputOutput inputOutput, IRentCompany company) {
		super(inputOutput);
		this.company = company;
	}
	
}
