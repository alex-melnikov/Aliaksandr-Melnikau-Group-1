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
import javax.persistence.Table;

import org.mentoring.project.Project;
import org.mentoring.unit.Unit;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
	@Id
	private Long id;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.ORDINAL)
	private EmployeeStatus status;
	@Embedded
	private Address address;
	@OneToOne(mappedBy = "employee")
	@JoinColumn(referencedColumnName = "employee_id")
	private PersonalInfo personalInfo;
	@ManyToOne
	@JoinColumn(name = "unit_id")
	private Unit unit;
	@ManyToMany
	@JoinTable(
	name = "employee_projects",
	joinColumns = @JoinColumn(name = "employee_id"),
	inverseJoinColumns = @JoinColumn(name = "project_id"))
	private Collection<Project> projects;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public EmployeeStatus getStatus() {
		return status;
	}
	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public PersonalInfo getPersonalInfo() {
		return personalInfo;
	}
	public void setPersonalInfo(PersonalInfo personalInfo) {
		this.personalInfo = personalInfo;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Collection<Project> getProjects() {
		return projects;
	}
	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}
}
