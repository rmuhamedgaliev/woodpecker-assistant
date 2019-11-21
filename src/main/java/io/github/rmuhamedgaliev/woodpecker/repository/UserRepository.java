package io.github.rmuhamedgaliev.woodpecker.repository;

import io.github.rmuhamedgaliev.woodpecker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
