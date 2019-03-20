package system;

public interface ICompany {
	public Double totalWithoutTaxes();
	public Double totalWithTaxes();
	public Double totalWithExempt();
	
	public Double totalWithoutTaxes(String country);
	public Double totalWithTaxes(String country);
	public Double totalWithExempt(String country);
	
	public Double totalWithoutTaxes(StringBuilder category);
	public Double totalWithTaxes(StringBuilder category);
	public Double totalWithExempt(StringBuilder category);
	
	public Double exempt();
}
