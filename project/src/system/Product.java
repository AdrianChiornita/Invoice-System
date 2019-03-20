package system;

import java.util.Objects;

public class Product {
	protected String name;
	protected String category;
	protected String country;
	protected Double price;
	
	public Product(String name, String category,
			String country, Double price) {
		this.name = name;
		this.category = category;
		this.country = country;
		this.price = price;
	}
	
	public Product() {
		this(null, null, null, 0.0);
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Product) {
			Product product = (Product) object;
			return this.name.equals(product.getName()) &&
				this.category.equals(product.getCategory()) &&
				this.country.equals(product.getOrigin()) &&
				this.price.equals(product.getPrice());
		}
		return false;
	}
	
	@Override
    public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.name);
		hash = 97 * hash + Objects.hashCode(this.category);
		hash = 97 * hash + Objects.hashCode(this.country);
		hash = 97 * hash + (int) (Double.doubleToLongBits(this.price) 
				^ (Double.doubleToLongBits(this.price) >>> 32));
		return hash;
	}
	
	@Override
	public String toString() {
		return name + " " + category 
				+ " " + country + " " + price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOrigin() {
		return country;
	}
	public void setOrigin(String country) {
		this.country = country;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
