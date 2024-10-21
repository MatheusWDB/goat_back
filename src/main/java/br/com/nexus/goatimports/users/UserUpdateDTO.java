package br.com.nexus.goatimports.users;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserUpdateDTO extends UserModel {
    private String newPassword;
}
