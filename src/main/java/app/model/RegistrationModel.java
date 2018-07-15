package app.model;

import app.validator.decorator.PasswordMatches;
import app.validator.decorator.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordMatches
public class RegistrationModel {
    @NotNull @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 àâäèéêëîïôœùûüÿçÀÂÄÈÉÊËÎÏÔŒÙÛÜŸÇ]+$", message = "Contient de caractères non autorisés")
    private String name;

    @NotNull @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 àâäèéêëîïôœùûüÿçÀÂÄÈÉÊËÎÏÔŒÙÛÜŸÇ]+$", message = "Contient de caractères non autorisés")
    private String surname;

    @NotNull @NotEmpty @ValidEmail
    private String email;

    @NotNull @NotEmpty
    @Pattern(regexp = "^[0-9]{6,9}$", message = "Renseignez un numéro de téléphone correct")
    private String phone;

    @NotNull @NotEmpty
    @Size(min = 6, message = "Le mot de passe doit avoir au moins 6 caractères")
    private String password;

    @NotNull @NotEmpty
    private String passwordMatcher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordMatcher() {
        return passwordMatcher;
    }

    public void setPasswordMatcher(String passwordMatcher) {
        this.passwordMatcher = passwordMatcher;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
