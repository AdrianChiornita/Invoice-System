package system;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	private String name;
	private List<OrderedProduct> order;
	
	public Invoice(String name) {
		this.name = name;
		this.order = new ArrayList<>();
	}
	
	public Invoice() {
		this(null);
	}
	
	 @Override
	 public String toString () {
		 StringBuilder result = new StringBuilder();
        result.append(name + ":( \n");
        for (OrderedProduct product : order)
            result.append("\t\t\t" + product.toString() + "\n");
        result.append("\t\t\t)\n");
        return result.toString();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrderedProduct> getOrder() {
		return order;
	}
	
	public OrderedProduct getOrderedProduct(int index) {
		return order.get(index);
	}

	public void setOrder(List<OrderedProduct> order) {
		this.order = order;
	}
	
	public static double round(double number) {
		return Math.round(number * 10000.0)/10000.0;
	}
	
	public double totalWithoutTaxes () {
		double result = 0;
		for (OrderedProduct product : order)
			result += product.getQuantity() * product.getPrice();
	    return round(result);
	}
	
	public double totalWithoutTaxes(String country) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getOrigin().equals(country))
				result += product.getQuantity() * product.getPrice();
	    return round(result);
	}
	
	public double totalWithoutTaxes(StringBuilder category) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getCategory().equals(category.toString()))
				result += product.getQuantity() * product.getPrice();
	    return round(result);
	}
	
	public double totalWithTaxes() {
		double result = 0;
		for (OrderedProduct product : order)
			result += product.getQuantity() * 
					(1 + product.getTaxes() / 100) *
					product.getPrice();
		return round(result);
	}
	

	public double totalWithTaxes(String country) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getOrigin().equals(country))
				result += product.getQuantity() * 
					(1 + product.getTaxes() / 100) *
					product.getPrice();
		return round(result);
	}
	
	public double totalWithTaxes(StringBuilder category) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getCategory().equals(category.toString()))
				result += product.getQuantity() * 
					(1 + product.getTaxes() / 100) *
					product.getPrice();
		return round(result);
	}
	

	public double totalTaxes() {
		double result = 0;
		for (OrderedProduct product : order)
			result += product.getTaxes() * product.getPrice() / 100;
		return round(result);
	}
	
	public double totalTaxes(String country) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getOrigin().equals(country))
				result += product.getTaxes() * product.getPrice() / 100;
		return round(result);
	}
	
	public double totalTaxes(StringBuilder category) {
		double result = 0;
		for (OrderedProduct product : order)
			if (product.getCategory().equals(category.toString()))
				result += product.getTaxes() * product.getPrice() / 100;
		return round(result);
	}
}
