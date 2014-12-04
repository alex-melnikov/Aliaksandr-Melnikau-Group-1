package org.mentoring.unit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mentoring.employee.Employee;

@Entity
@Table(name="UNIT")
public class Unit {
	@Id
	private Long id;
	
	private String name;
	
	//@org.hibernate.annotations.Fetch(FetchMode.SELECT)
	@OneToMany(targetEntity = Employee.class, mappedBy = "unit")
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
		return employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}
}
