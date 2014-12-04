package org.mentoring.employee;

import javax.persistence.Embeddable;

@Embeddable
//@Access(AccessType.FIELD)
public class Address {
	private String city;
	private String street;
}
