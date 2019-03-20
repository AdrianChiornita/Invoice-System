package factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import system.Company;
import system.Invoice;
import system.OrderedProduct;

public class MediumMarket extends Company{
	
	public MediumMarket(String name) {
		super(name, CompanyType.MEDIUM_MARKET);
	}
	
	public MediumMarket() {
		this(null);
	}

	@Override
	public Double exempt() {
		Map<String, Double> map = new HashMap<>();
		
		for(Invoice invoice : getInvoices())
			for(OrderedProduct product : invoice.getOrder())
				if(map.get(product.getCategory()) == null)
					map.put(product.getCategory(), totalWithTaxes(new StringBuilder(product.getCategory())));
		
		Double max = Collections.max(map.values());
		
		if (max > 0.5 * totalWithTaxes()) return 5.0;
		return 0.0;
	}

}
