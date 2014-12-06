package org.mentoring.project;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.mentoring.employee.Employee;

public class ProjectServiceImpl implements ProjectService {

	static final Logger logger = Logger.getLogger(ProjectServiceImpl.class);

	public ProjectServiceImpl() {
		super();
	}

	public ProjectServiceImpl(EntityManager em) {
		super();
		this.em = em;
	}

	private EntityManager em;

	public Project createProject(Project project) {
		em.persist(project);
		return project;
	}

	public void deleteProject(Long id) {
		Project project = em.find(Project.class, id);
		if (project != null) {
			for (Employee employee : project.getEmployees()) {
				employee.getProjects().remove(project);
			}
			em.remove(project);
		}
	}

	public Project findProject(Long id) {
		return em.find(Project.class, id);
	}

	public void updateProject(Project project, Long id) {
		Project projectToUpdate = em.find(Project.class, id);
		if (projectToUpdate != null) {
			project.setId(id);
			em.merge(project);
		} else {
			logger.info("unable to update project with id " + id
					+ "as it does not excists");
		}
	}
}
