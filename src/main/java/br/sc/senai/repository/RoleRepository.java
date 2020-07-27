package br.sc.senai.repository;

import br.sc.senai.model.ERole;
import br.sc.senai.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
