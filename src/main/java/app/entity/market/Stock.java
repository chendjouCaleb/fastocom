package app.entity.market;

import app.entity.organisation.Organisation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;

    @Transient
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "ProductID", foreignKey = @ForeignKey(name = "FK_Stock_StockProduct_ProductID"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "OrganisationID", foreignKey = @ForeignKey(name = "FK_Stock_StockPOrganisation_OrganisationID"))
    private Organisation organisation;

    @OneToMany(mappedBy = "stock")
    private List<Order> orders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
