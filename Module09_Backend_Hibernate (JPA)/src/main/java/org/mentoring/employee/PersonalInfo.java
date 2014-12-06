package org.mentoring.employee;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="PERSONALINFO")
public class PersonalInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String characteristics;
	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	public PersonalInfo() {
		super();
	}
	public PersonalInfo(String characteristics, Employee employee) {
		super();
		this.characteristics = characteristics;
		this.employee = employee;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "id " + this.getId() + "; characteristics " + this.getCharacteristics();
	}
}
