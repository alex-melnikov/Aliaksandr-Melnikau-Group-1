package org.mentoring.unit;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class UnitServiceImpl implements UnitService {

	static final Logger logger = Logger.getLogger(UnitServiceImpl.class);

	private EntityManager em;

	public UnitServiceImpl() {
		super();
	}

	public UnitServiceImpl(EntityManager em) {
		super();
		this.em = em;
	}

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

	public void updateUnit(Unit unit, Long id) {
		Unit unitToUpdate = em.find(Unit.class, id);
		if (unitToUpdate != null) {
			unit.setId(id);
			em.merge(unit);
		} else {
			logger.info("unable to update unit with id " + id
					+ "as it does not excists");
		}
	}
}
