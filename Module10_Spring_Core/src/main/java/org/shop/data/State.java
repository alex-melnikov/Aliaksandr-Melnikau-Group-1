package org.shop.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entity of <code>State</code> class represents the state object.
 * 
 * @author Dzmitry_Naskou
 */
public final class State implements Entity {

	private static final long serialVersionUID = -4707978480914424319L;

	public static final State ACTIVE_PROPOSAL = new State((long) 0);

	public static final State NOT_ACTIVE_PROPOSAL = new State((long) 1);

	public static final State CANCELED_PROPOSAL = new State((long) 3);

	private static final List<State> PROPOSAL_STATES;

	static {
		List<State> states = new ArrayList<State>(3);
		states.add(ACTIVE_PROPOSAL);
		states.add(NOT_ACTIVE_PROPOSAL);

		PROPOSAL_STATES = Collections.unmodifiableList(states);
	}

	private Long id;

	private String name;

	private State(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString() {
		return "State [id=" + id + ", name=" + name + "]";
	}

	public static List<State> getProposalStates() {
		return PROPOSAL_STATES;
	}
}
