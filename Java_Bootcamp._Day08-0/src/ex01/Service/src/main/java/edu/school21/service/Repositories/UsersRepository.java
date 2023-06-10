package edu.school21.service.Repositories;

import edu.school21.service.Models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}
