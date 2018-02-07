package telran.cars.model;

import telran.cars.dto.RentCompanyData;

public abstract class AbstractRentCompany implements IRentCompany {
protected RentCompanyData companyData=new RentCompanyData();
@Override
public void setCompanyData(RentCompanyData companyData){
	this.companyData=companyData;
}
}
