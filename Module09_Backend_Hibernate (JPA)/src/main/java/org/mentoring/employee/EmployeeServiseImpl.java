package org.mentoring.employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.mentoring.project.Project;
import org.mentoring.unit.Unit;

public class EmployeeServiseImpl implements EmployeeService {

	@PersistenceContext(name = "EmployeeService")
	private EntityManager em;
	public Employee createEmployee(Employee employee) {
		em.persist(employee);
		return employee;
	}

	public void deleteEmployee(Long id) {
		Employee employee = em.find(Employee.class, id);
		if (employee != null) {
		em.remove(employee);
		}
	}

	public Employee findEmployee(Long id) {
		return em.find(Employee.class, id);
	}

	public void updateEmployee(Employee emploeey, Long id) {
		Employee employeeToUpdate = em.find(Employee.class, id);
		//merge

	}

	public void assignEmployeeToProject(int empId, int projectId) {
		Project project = em.find(Project.class, projectId);
		Employee employee = em.find(Employee.class, empId);
		project.getEmployees().add(employee);
		employee.getProjects().add(project);

	}

	public void addEmployeeToUnit(int empId, int unitId) {
		Unit unit = em.find(Unit.class, unitId);
		Employee employee = em.find(Employee.class, empId);
		employee.setUnit(unit);
		unit.getEmployees().add(employee);
	}
}
