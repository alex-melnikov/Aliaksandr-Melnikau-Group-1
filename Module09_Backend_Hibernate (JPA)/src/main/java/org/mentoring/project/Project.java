package org.mentoring.project;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.mentoring.employee.Employee;

@Entity
public class Project {
	@Id
	private Long id;
	@ManyToMany
	@JoinTable(
	name = "employee_projects",
	joinColumns = @JoinColumn(name = "project_id"),
	inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Collection<Employee> employees;
}
