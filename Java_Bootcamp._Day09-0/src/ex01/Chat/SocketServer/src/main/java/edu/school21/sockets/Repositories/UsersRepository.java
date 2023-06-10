package edu.school21.sockets.Repositories;

import edu.school21.sockets.Model.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String email);
}
