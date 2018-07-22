package app.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderModel {

    @NotNull
    private Integer stockId;

    @NotNull
    private Integer pharmacyId;

    @NotNull
    @Min(value = 0, message = "Renseignez une quantité supérieure à 0")
    private Integer quantity;

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Integer pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
