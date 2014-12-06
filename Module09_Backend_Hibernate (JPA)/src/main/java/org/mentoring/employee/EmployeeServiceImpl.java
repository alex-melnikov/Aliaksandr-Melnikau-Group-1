package org.mentoring.employee;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.mentoring.project.Project;
import org.mentoring.unit.Unit;

public class EmployeeServiceImpl implements EmployeeService {

	static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	public EmployeeServiceImpl() {
		super();
	}

	public EmployeeServiceImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private EntityManager em;

	public Employee createEmployee(Employee employee) {
		em.persist(employee);
		return employee;
	}

	public void deleteEmployee(Long id) {
		Employee employee = em.find(Employee.class, id);
		if (employee != null) {
			for (Project project : employee.getProjects()) {
				project.getEmployees().remove(employee);
			}
			employee.getUnit().getEmployees().remove(employee);
			em.remove(employee);
		}
	}

	public Employee findEmployee(Long id) {
		return em.find(Employee.class, id);
	}

	public void updateEmployee(Employee emploeey, Long id) {
		Employee employeeToUpdate = em.find(Employee.class, id);
		if (employeeToUpdate != null) {
			emploeey.setId(id);
			em.merge(emploeey);
		} else {
			logger.info("unable to update employee with id " + id
					+ "as it does not excists");
		}
	}

	public void assignEmployeeToProject(Long empId, Long projectId) {
		Project project = em.find(Project.class, projectId);
		Employee employee = em.find(Employee.class, empId);
		project.getEmployees().add(employee);
		employee.getProjects().add(project);
	}

	public void addEmployeeToUnit(Long empId, Long unitId) {
		Unit unit = em.find(Unit.class, unitId);
		Employee employee = em.find(Employee.class, empId);
		employee.setUnit(unit);
		unit.getEmployees().add(employee);
	}
}
