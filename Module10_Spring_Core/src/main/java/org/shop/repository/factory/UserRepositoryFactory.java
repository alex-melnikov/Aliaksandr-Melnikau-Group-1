package org.shop.repository.factory;

import java.util.ArrayList;
import java.util.List;

import org.shop.data.User;
import org.shop.repository.UserRepository;
import org.shop.repository.map.AbstractMapRepository;

/**
 * A factory for creating UserRepository objects.
 * 
 * @author Dzmitry_Naskou
 * 
 * @see UserRepository
 * @see User
 */
public final class UserRepositoryFactory {

	/**
	 * Creates a new UserRepository instance.
	 * 
	 * @return the user repository
	 */
	public UserRepository createUserRepository() {
		return new UserMapRepository();
	}

	/**
	 * 
	 * @author Dzmitry_Naskou
	 */
	private class UserMapRepository extends AbstractMapRepository<User>
			implements UserRepository {

		public User getUserById(Long id) {
			return get(id);
		}

		public Long createUser(User user) {
			return create(user);
		}

		public void updateUser(User user) {
			update(user);
		}

		public List<User> getUsers() {
			return new ArrayList<User>(register.values());
		}
	}
}
