package telran.cars.controller;

import telran.cars.model.IRentCompany;
import telran.cars.model.RentCompanyEmbedded;

public class CarsRandomAppl {

	public static void main(String[] args) {
		IRentCompany rentCompany=new RentCompanyEmbedded();
		RandomRentCompanyController controller=
		new RandomRentCompanyController(rentCompany);
		controller.createRentCompany(2);
		rentCompany.getAllRecords().limit(200).forEach(System.out::println);

	}

}
