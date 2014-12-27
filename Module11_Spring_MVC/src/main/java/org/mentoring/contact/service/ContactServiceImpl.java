package org.mentoring.contact.service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.mentoring.contact.dao.ContactDAO;
import org.mentoring.contact.form.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class ContactServiceImpl implements ContactService {
 
	   @Autowired
	    private ContactDAO contactDAO;
	     
	    @Transactional
	    public void addContact(Contact contact) {
	        contactDAO.addContact(contact);
	    }
	 
	    @Transactional
	    public List<Contact> listContact() {
	 
	        return contactDAO.listContact();
	    }
	 
	    @Transactional
	    public void removeContact(Integer id) {
	        contactDAO.removeContact(id);
	    }
}