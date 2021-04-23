package com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[a-zA-Z](.[a-zA-Z0-9_-]*)$")
    @NotEmpty()
    @NotNull()
    private String login;
    @Size(min = 6, max = 50)
    @Pattern(message = "Введи 6 цыфр кожаный!",regexp = "[0-9a-zA-Z]{6,}")
    @NotNull
    @NotEmpty
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
