package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.Objects;

import static com.util.CredentialsRegExp.REGEXP_FOR_LOGIN;
import static com.util.CredentialsRegExp.REGEXP_FOR_PASSWORD;
import static com.util.CredentialsWarnings.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @Size(message = LOGIN_SIZE, min = 3, max = 20)
    @Pattern(message = INVALID_LOGIN_DATA, regexp = REGEXP_FOR_LOGIN)
    @NotEmpty(message = LOGIN_IS_EMPTY)
    @NotNull(message = LOGIN_IS_NULL)
    private String login;
    @Size(message = PASSWORD_SIZE, min = 6, max = 20)
    @Pattern(message = INVALID_PASSWORD_DATA, regexp = REGEXP_FOR_PASSWORD)
    @NotNull(message = PASSWORD_IS_NULL)
    @NotEmpty(message = PASSWORD_IS_EMPTY)
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(login, personDTO.login) && Objects.equals(password, personDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
