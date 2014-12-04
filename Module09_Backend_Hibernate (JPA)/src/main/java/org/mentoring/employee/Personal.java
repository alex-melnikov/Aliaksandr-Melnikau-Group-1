package org.mentoring.employee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Personal {
	@Id
	private Long id;
	private String characteristics;
	@OneToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
}
