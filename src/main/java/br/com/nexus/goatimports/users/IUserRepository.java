package br.com.nexus.goatimports.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;



public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    UserModel findByEmail(String email);
}
