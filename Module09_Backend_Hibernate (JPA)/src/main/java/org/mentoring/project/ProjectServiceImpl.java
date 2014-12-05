package org.mentoring.project;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProjectServiceImpl implements ProjectService {

	@PersistenceContext(name = "ProjectService")
	private EntityManager em;

	public Project createProject(Project project) {
		em.persist(project);
		return project;
	}

	public void deleteProject(Long id) {
		Project project = em.find(Project.class, id);
		if (project != null) {
		em.remove(project);
		}
	}

	public Project findProject(Long id) {
		return em.find(Project.class, id);
	}

	public void updateProject(Project project, Long id) {
		//merge
	}
}
