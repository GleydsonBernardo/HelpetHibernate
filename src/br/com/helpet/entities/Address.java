package br.com.helpet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address extends BaseEntity implements Cloneable{

	private String street;
	private String city;
	private String state;
	private String complement;
	
	public Address() {
	}
	
	public Address(String street, String city, String state, String complement) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.complement = complement;
	}

	@Column(name="street", nullable=false)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name="city", nullable=false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="state", nullable=false)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name="complement", nullable=false)
	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}
	
	@Override
	public Address clone() {
		try {
			return (Address) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
}
