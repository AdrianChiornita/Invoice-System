package system;

import java.util.ArrayList;
import java.util.List;

import factory.CompanyType;

public abstract class Company implements ICompany{
	private String name;
	private CompanyType type;
	private List<Invoice> invoices;
	
	public Company(String name, CompanyType type) {
		this.name = name;
		this.type = type;
		this.invoices = new ArrayList<>();
	}
	
	public Company(CompanyType type) {
		this(null, type);
	}
	
	public Company() {
		this(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompanyType getType() {
		return type;
	}

	public void setType(CompanyType type) {
		this.type = type;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public Invoice getInvoice(int index) {
		return invoices.get(index);
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(name + ":(" + type + "):[\n ");
		for(Invoice invoice : invoices)
			result.append("\t\t" + invoice.toString());
		result.append("\t\t]\n");
		
		return result.toString();
	}

	@Override
	public Double totalWithoutTaxes() {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithoutTaxes();
		return Invoice.round(result);
	}

	@Override
	public Double totalWithTaxes() {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithTaxes();
		return Invoice.round(result);
	}

	@Override
	public Double totalWithExempt() {
		return Invoice.round((1 - exempt() / 100) * totalWithTaxes());
	}

	@Override
	public Double totalWithoutTaxes(String country) {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithoutTaxes(country);
		return Invoice.round(result);
	}

	@Override
	public Double totalWithTaxes(String country) {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithTaxes(country);
		return Invoice.round(result);
	}

	@Override
	public Double totalWithExempt(String country) {
		return Invoice.round((1 - exempt() / 100) * totalWithTaxes(country));
	}
	
	public Double totalWithoutTaxes(StringBuilder category) {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithoutTaxes(category);
		return Invoice.round(result);
	}

	@Override
	public Double totalWithTaxes(StringBuilder category) {
		Double result = 0.0;
		for(Invoice invoice : invoices)
			result += invoice.totalWithTaxes(category);
		return Invoice.round(result);
	}

	@Override
	public Double totalWithExempt(StringBuilder category) {
		return Invoice.round((1 - exempt() / 100) * totalWithTaxes(category));
	}
}
