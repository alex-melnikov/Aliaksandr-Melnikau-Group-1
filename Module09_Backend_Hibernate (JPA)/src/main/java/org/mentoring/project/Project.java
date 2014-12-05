package org.mentoring.project;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.mentoring.employee.Employee;

@Entity
@Table(name="PROJECT")
public class Project {
	@Id
	private Long id;

	private String name;

	@ManyToMany
	@JoinTable(
	name = "employee_projects",
	joinColumns = @JoinColumn(name = "project_id"),
	inverseJoinColumns = @JoinColumn(name = "employee_id"))
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
