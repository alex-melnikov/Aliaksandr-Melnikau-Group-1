package org.mentoring.employee;

import java.util.Collection;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.mentoring.project.Project;
import org.mentoring.unit.Unit;

@Entity
public class Employee {
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.STRING)
	private EmployeeStatus status;
	@Embedded
	private Address address;
	private Personal personal;
	@OneToOne(mappedBy = "employee")
	@JoinColumn(referencedColumnName = "employee_id")
	private Personal personalInfo;
	@ManyToOne
	@JoinColumn(name = "unit_id")
	private Unit unit;
	@ManyToMany
	@JoinTable(
	name = "employee_projects",
	joinColumns = @JoinColumn(name = "employee_id"),
	inverseJoinColumns = @JoinColumn(name = "project_id"))
	private Collection<Project> projects;
}
