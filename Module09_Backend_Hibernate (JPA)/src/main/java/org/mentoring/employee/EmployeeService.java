package org.mentoring.employee;

public interface EmployeeService {
	Employee createEmployee(Employee employee);
	void deleteEmployee(Long id);
	Employee findEmployee(Long id);
	void updateEmployee(Employee emploeey, Long id);
	void assignEmployeeToProject(int empId, int projectId);
	void addEmployeeToUnit(int empId, int uniId);
}
