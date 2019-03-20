package system;

import java.util.Objects;

public class OrderedProduct extends Product{
	private Integer quantity;
	private Double taxes;
	
	public OrderedProduct(String name, String category,
			String country, Double price, 
			Integer quantity, Double taxes) {
		super(name, category, country, price);
		this.quantity = quantity;
		this.taxes = taxes;
	}
	
	public OrderedProduct() {
		this(null, null, null, 0.0, 0, 0.0);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + quantity 
				+ " " + taxes; 
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof OrderedProduct) {
			OrderedProduct product = (OrderedProduct) object;
			return this.name.equals(product.getName()) &&
				this.category.equals(product.getCategory()) &&
				this.country.equals(product.getOrigin()) &&
				this.price.equals(product.getPrice()) &&
				this.quantity.equals(product.getQuantity()) &&
				this.taxes.equals(product.getTaxes()) ;
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
				^ (Double.doubleToLongBits(this.price) >>> 32)
				^ (Double.doubleToLongBits(this.taxes) >>> 64)
				^ (this.quantity >>> 128));
		return hash;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
}
