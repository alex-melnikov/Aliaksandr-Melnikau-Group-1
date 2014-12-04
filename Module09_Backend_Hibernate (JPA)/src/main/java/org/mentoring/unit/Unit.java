package org.mentoring.unit;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.mentoring.employee.Employee;

@Entity
public class Unit {
	@Id
	private Long id;
	private String name;
	//@org.hibernate.annotations.Fetch(FetchMode.SELECT)
	@OneToMany(targetEntity = Employee.class, mappedBy = "unit")
	private Collection<Employee> employees;
}
