package org.mentoring.contact.dao;

import java.util.List;

import org.mentoring.contact.form.Contact;

public interface ContactDAO {

	void addContact(Contact contact);

	List<Contact> listContact();

	void removeContact(Integer id);
}