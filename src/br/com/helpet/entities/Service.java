package br.com.helpet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SERVICE")
public class Service extends BaseEntity implements Cloneable{

	private double value;
	private String description;
	
	public Service() {
	}

	public Service(double value, String description) {
		this.value = value;
		this.description = description;
	}

	@Column(name="value", nullable = false)
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Column(name="description", nullable=false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString(){
		return "Serviço: "+this.description+" Valor: "+this.value;
	}
	
	@Override
	public Service clone() {
		try {
			return (Service) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
}
