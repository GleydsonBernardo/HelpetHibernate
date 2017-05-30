package br.com.helpet.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ANIMAL")
public class Animal extends BaseEntity implements Cloneable{

	private AnimalEnum specie;
	private String breed;
	private String gender;
	private double weight;
	private int age;
	private String description;
	private Person person;
	private Adoption adoption;
	
	public Animal() {
		
	}

	public Animal(String specie, String breed, String gender, double weight, int age, String description, Person person) {
		this.specie = AnimalEnum.setName(specie);
		this.breed = breed;
		this.gender = gender;
		this.weight = weight;
		this.age = age;
		this.description = description;
		this.person = person;
	}

	@Column(name="specie", nullable = false)
	public String getSpecie() {
		return specie.name();
	}

	public void setSpecie(String specie) {
		this.specie = AnimalEnum.setName(specie);
	}

	@Column(name="breed", nullable = false)
	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	@Column(name="gender", nullable = false)
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name="weight", nullable = false)
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	@Column(name = "age", nullable = true)
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Column(name="description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name="person_id", nullable = true, updatable = true)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public enum AnimalEnum {
		CACHORRO, GATO;	
		
		public static AnimalEnum setName(String name){
			if(name.equalsIgnoreCase(CACHORRO.name())){ 
				return CACHORRO;
			}
			else return GATO;
		}
	}
	
	@Override
	public String toString(){
		return "Espécie: "+this.specie+" - Raça: "+this.breed+" - Gênero: "+this.gender+" - Peso: "+this.weight+" - Idade: "
				+ ""+this.age+" - Descrição: "+this.description+" - Dono: "+this.person;
	}
	
	@Override
	public Animal clone() {
		try {
			return (Animal) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.getMessage());
		}
	}
}
