package factory;

import system.Company;

public class Factory {
	
	private Factory() {
	}
	
	public static Company create(String name, CompanyType type) {
		Company company = null;
		
		switch(type) {
		case MINI_MARKET:
			company = new MiniMarket(name);
			break;
		case MEDIUM_MARKET:
			company = new MediumMarket(name);
			break;
		case HYPER_MARKET:
			company = new HyperMarket(name);
			break;
		default:
			break;
		}
		return company;
	}
	
	public static Company create(CompanyType type) {
		return create(null, type);
	}
}
