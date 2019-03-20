package factory;

import system.Company;
import system.Invoice;

public class HyperMarket extends Company{
	
	public HyperMarket(String name) {
		super(name, CompanyType.HYPER_MARKET);
	}
	
	public HyperMarket() {
		this(null);
	}

	@Override
	public Double exempt() {
		for(Invoice invoice : getInvoices())
			if(invoice.totalWithTaxes() > 0.1 * this.totalWithTaxes())
				return 1.0;
		return 0.0;
	}
}
