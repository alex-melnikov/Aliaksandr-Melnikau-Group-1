package org.mentoring.unit;


public interface UnitService {
	Unit createUnit(Unit unit);
	void deleteUnit(Long id);
	Unit findUnit(Long id);
	void updateUnit(Unit unit, Long id);
}
