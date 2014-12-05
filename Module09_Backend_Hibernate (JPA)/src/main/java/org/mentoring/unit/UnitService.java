package org.mentoring.unit;

import org.mentoring.project.Project;

public interface UnitService {
	Unit createUnit(Unit unit);
	void deleteUnit(Long id);
	Unit findUnit(Long id);
	void updateProject(Unit unit, Long id);
}
