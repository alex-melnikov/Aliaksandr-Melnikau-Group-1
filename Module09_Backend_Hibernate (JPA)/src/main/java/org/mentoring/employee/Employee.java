package org.mentoring.employee;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.FetchMode;
import org.mentoring.project.Project;
import org.mentoring.unit.Unit;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String firstName;
	private String lastName;
	@Enumerated(EnumType.ORDINAL)
	private EmployeeStatus status;
	@Embedded
	private Address address;
	@OneToOne(mappedBy = "employee")
	@JoinColumn(referencedColumnName = "employee_id")
	@Cascade({CascadeType.ALL})
	private PersonalInfo personalInfo;
	@org.hibernate.annotations.Fetch(FetchMode.SELECT)
	@ManyToOne(cascade = javax.persistence.CascadeType.PERSIST)
	@JoinColumn(name = "unit_id")
	private Unit unit;
	@ManyToMany
	@JoinTable(name = "employee_projects", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
	private Collection<Project> projects;

	public Employee() {
		super();
	}

	public Employee(String firstName, String lastName, EmployeeStatus status,
			Address address, PersonalInfo personalInfo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;
		this.address = address;
		this.personalInfo = personalInfo;
	}

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
		if (projects == null)
		{
			projects = new HashSet<Project>();
		}
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return "Id " + this.getId() + "; first_name " + this.getFirstName()
				+ " ; last_name " + this.getLastName() + " ; status "
				+ this.getStatus() + " ; address " + this.getAddress()
				+ " ; personal_info " + this.getPersonalInfo() + " ; unit "
				+ this.getUnit() + " ; projects: " + this.getProjects();
	}
}
