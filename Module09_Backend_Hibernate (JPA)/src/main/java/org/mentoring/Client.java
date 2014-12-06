package org.mentoring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.mentoring.employee.Address;
import org.mentoring.employee.Employee;
import org.mentoring.employee.EmployeeService;
import org.mentoring.employee.EmployeeServiceImpl;
import org.mentoring.employee.EmployeeStatus;
import org.mentoring.employee.PersonalInfo;
import org.mentoring.project.Project;
import org.mentoring.project.ProjectService;
import org.mentoring.project.ProjectServiceImpl;
import org.mentoring.unit.Unit;
import org.mentoring.unit.UnitService;
import org.mentoring.unit.UnitServiceImpl;

public class Client {
	
	static final Logger logger = Logger.getLogger(Client.class);
	public static void main(String[] args) {
		EntityManager em = null;
		EntityManagerFactory emf = null;
		try {
			emf = Persistence.createEntityManagerFactory("db-manager");
			em = emf.createEntityManager();
			logger.info("Creating unit");
			UnitService us = new UnitServiceImpl(em);
			Unit unit = new Unit("TestUnit", null);
			unit = us.createUnit(unit);
			logger.info("created unit: " + unit);
			logger.info("Creating  First Project");
			ProjectService ps = new ProjectServiceImpl(em);
			Project firstProject = new Project("First Project", null);
			firstProject = ps.createProject(firstProject);
			logger.info("created project: " + firstProject);
			logger.info("Creating  Second Project");
			Project secondProject = new Project("Second Project", null);
			Project createdProject = ps.createProject(secondProject);
			logger.info("created project: " + createdProject);
			logger.info("Creating  First Employee");
			EmployeeService es = new EmployeeServiceImpl(em);
			Employee firstEmployee = new Employee("John", "Smith", EmployeeStatus.FULL_TIME_EMPLOYEE , new Address("City1", "Street1"), new PersonalInfo("Personal info", null));
			firstEmployee = es.createEmployee(firstEmployee);
			logger.info("created employee: " + firstEmployee);
			logger.info("Creating  Second Employee");
			Employee secondEmployee = new Employee("Bill", "Brown", EmployeeStatus.PART_TIME_EMPLOYEE , new Address("City2", "Street2"), new PersonalInfo("Second Personal info", null));
			secondEmployee = es.createEmployee(secondEmployee);
			logger.info("created employee: " + secondEmployee);
			logger.info("Assign employees to projects");
			es.assignEmployeeToProject(firstEmployee.getId(), firstProject.getId());
			logger.info("first employee: " + es.findEmployee(firstEmployee.getId()));
			es.assignEmployeeToProject(secondEmployee.getId(), secondProject.getId());
			logger.info("second employee: " + es.findEmployee(secondEmployee.getId()));
			logger.info("Assign employees to unit");
			es.addEmployeeToUnit(firstEmployee.getId(), unit.getId());
			logger.info("first employee: " +  es.findEmployee(firstEmployee.getId()));
			es.addEmployeeToUnit(secondEmployee.getId(), unit.getId());
			logger.info("second employee: " + es.findEmployee(secondEmployee.getId()));
			logger.info("Update unit name");
			unit.setName("Updated name");
			us.updateUnit(unit, unit.getId());
			logger.info("updated unit: " + us.findUnit(unit.getId()));
			logger.info("Update project");
			firstProject.setName("Updated name");
			ps.updateProject(firstProject, firstProject.getId());
			logger.info("updated project: " + ps.findProject(firstProject.getId()));
			logger.info("Update emploeey first name");
			firstEmployee.setFirstName("Updated name");
			es.updateEmployee(firstEmployee, firstEmployee.getId());
			logger.info("updated employee: " + es.findEmployee(firstEmployee.getId()));
			logger.info("Delete first project: " + ps.findProject(firstProject.getId()));
			ps.deleteProject(firstProject.getId());
			logger.info("First project: " + ps.findProject(firstProject.getId()));
			logger.info("First employee: " + es.findEmployee(firstEmployee.getId()));
			logger.info("Deelete second employee:");
			es.deleteEmployee(secondEmployee.getId());
			logger.info("Second employee: " + es.findEmployee(secondEmployee.getId()));
			logger.info("Delete unit");
			us.deleteUnit(unit.getId());
			logger.info("Unit: " + us.findUnit(unit.getId()));
			logger.info("First employee: " + es.findEmployee(firstEmployee.getId()));
		} finally {
			if (em != null) {
				em.close();
			}
			if (emf != null) {
				emf.close();
			}
		}
	}
}
