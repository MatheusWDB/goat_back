package br.com.nexus.goatimports.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.nexus.goatimports.utils.Utils;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody UserModel userModel) {
        
        var user = this.userRepository.findByEmail(userModel.getEmail());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("E-mail já cadastrado");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @PostMapping("/login")
    public ResponseEntity<?> enter(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByEmail(userModel.getEmail());

        if (user == null || user.getDeleted() == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não cadastrado!");
        }

        var password = userModel.getPassword();

        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if (passwordVerify.verified) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> perfil(@PathVariable UUID id) {
        var perfil = this.userRepository.findById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(perfil.get());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UserUpdateDTO userUpdateDTO) {
        var user = this.userRepository.findById(id).orElse(null);

        if (userUpdateDTO.getNewPassword() != null) {

            var newPassword = userUpdateDTO.getNewPassword();
            var currentPassword = userUpdateDTO.getPassword();

            if (newPassword.equals(currentPassword)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A nova senha não pode ser igual a atual!");
            }

            var passwordVerify = BCrypt.verifyer().verify(currentPassword.toCharArray(), user.getPassword());

            if (passwordVerify.verified) {

                user.setPassword(newPassword);
                userUpdateDTO.setPassword(newPassword);

                Utils.copyNonNullProperties(userUpdateDTO, user);

                user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Senha incorreta!");
            }

        } else {
            Utils.copyNonNullProperties(userUpdateDTO, user);
        }

        var userUpdated = this.userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
