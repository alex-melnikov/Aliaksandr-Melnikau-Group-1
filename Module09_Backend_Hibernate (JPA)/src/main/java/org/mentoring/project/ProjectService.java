package org.mentoring.project;


public interface ProjectService {
	Project createProject(Project project);
	void deleteProject(Long id);
	Project findProject(Long id);
	void updateProject(Project project, Long id);
}
