package org.mentoring.unit;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mentoring.employee.Employee;

@Entity
@Table(name="UNIT")
public class Unit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;
	
	public Unit() {
		super();
	}

	
	public Unit(String name, Collection<Employee> employees) {
		super();
		this.name = name;
		this.employees = employees;
	}

	@OneToMany(targetEntity = Employee.class, mappedBy = "unit", orphanRemoval=true)
	private Collection<Employee> employees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Employee> getEmployees() {
		if (employees == null)
		{
			employees = new HashSet<Employee>();
		}
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}
	
	@Override
	public String toString() {
		return "id " + this.getId() + "; name " + this.getName();
	}
}
