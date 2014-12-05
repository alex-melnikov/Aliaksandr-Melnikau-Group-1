package org.mentoring.unit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class UnitServiceImpl implements UnitService {
	@PersistenceContext(name = "EmployeeService")
	private EntityManager em;

	public Unit createUnit(Unit unit) {
		em.persist(unit);
		return unit;
	}

	public void deleteUnit(Long id) {
		Unit unit = em.find(Unit.class, id);
		if (unit != null) {
		em.remove(unit);
		}
	}

	public Unit findUnit(Long id) {
		return em.find(Unit.class, id);
	}

	public void updateProject(Unit unit, Long id) {
		//merge
	}
}
