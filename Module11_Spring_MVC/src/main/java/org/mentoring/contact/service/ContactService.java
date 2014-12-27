package org.mentoring.contact.service;

import java.util.List;

import org.mentoring.contact.form.Contact;

public interface ContactService {

	void addContact(Contact contact);

	List<Contact> listContact();

	void removeContact(Integer id);
}