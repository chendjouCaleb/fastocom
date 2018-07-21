package app.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class StockModel {
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 àâäèéêëîïôœùûüÿçÀÂÄÈÉÊËÎÏÔŒÙÛÜŸÇ]+$", message = "Contient de caractères non autorisés")
    private String name;
    private String description = "Lorem ipsum dolor sit amet, consectetur adipisicing elit." +
            " Accusamus ad aliquam aperiam at beatae corporis debitis dolore doloribus eos esse fugiat, " +
            "incidunt ipsum modi necessitatibus nesciunt pariatur placeat vitae voluptas.";

    private Integer providerId;

    @NotNull
    @Min(value = 10, message = "Ce prix doit etre supérieur à 10 FCFA")
    private Double wholeSalePrice;

    @NotNull
    @Min(value = 10, message = "Ce prix doit etre supérieur à 10 FCFA")
    private Double retailPrice;

    @NotNull
    @Min(value = 10, message = "La quantité doit etre supérieur à 0")
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Double getWholeSalePrice() {
        return wholeSalePrice;
    }

    public void setWholeSalePrice(Double wholeSalePrice) {
        this.wholeSalePrice = wholeSalePrice;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
