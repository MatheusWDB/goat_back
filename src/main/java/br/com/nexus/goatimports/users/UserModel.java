package br.com.nexus.goatimports.users;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    private String surname;
    
    private String email;

    private String phone;

    private String password;

    private LocalDate date_for_birth;
    
    @CreationTimestamp
    private LocalDateTime createAt;

    private LocalDateTime deletedAt;

    private Boolean deleted = false;

}
