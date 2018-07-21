package app.model;

import app.entity.identity.Account;
import app.validator.decorator.AvailableEmail;
import app.validator.decorator.AvailablePhone;
import app.validator.decorator.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class OrganisationModel {
    @NotNull @NotEmpty
    @Pattern(regexp = "pharmacy|provider", message = "Type d'organisation non reconnue")
    private String type;

    private String displayType;

    @NotNull
    private Integer adminAccountId;



    private Account adminAccount;

    @NotNull @NotEmpty @ValidEmail @AvailableEmail
    private String email;

    @NotNull @NotEmpty @AvailablePhone
    @Pattern(regexp = "^[0-9]{6,9}$", message = "Renseignez un numéro de téléphone correct")
    private String phone;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 àâäèéêëîïôœùûüÿçÀÂÄÈÉÊËÎÏÔŒÙÛÜŸÇ]+$", message = "Ce nom ontient de caractères non autorisés")
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Integer getAdminAccountId() {
        return adminAccountId;
    }

    public void setAdminAccountId(Integer adminAccountId) {
        this.adminAccountId = adminAccountId;
    }

    public Account getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(Account adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
