package factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import system.Company;
import system.Invoice;
import system.OrderedProduct;

public class MiniMarket extends Company{

	public MiniMarket(String name) {
		super(name, CompanyType.MINI_MARKET);
	}
	
	public MiniMarket() {
		this(null);
	}
	
	@Override
	public Double exempt() {
		Map<String, Double> map = new HashMap<>();
		
		for(Invoice invoice : getInvoices())
			for(OrderedProduct product : invoice.getOrder())
				if(map.get(product.getOrigin()) == null)
					map.put(product.getOrigin(), totalWithTaxes(product.getOrigin()));
		
		Double max = Collections.max(map.values());
		
		if (max > 0.5 * totalWithTaxes()) return 10.0;
		return 0.0;
	}
}
