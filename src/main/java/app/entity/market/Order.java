package app.entity.market;

import app.configuration.JsonDateTimeSerializer;
import app.entity.organisation.Pharmacy;
import app.enumeration.OrderState;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table( name = "StockOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantity;


    private OrderState state = OrderState.WAITING;

    @ManyToOne
    @JoinColumn(name = "PharmacyID", foreignKey = @ForeignKey(name = "FK_Order_OrderPharmacy_PharmacyID"))
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "StockID", foreignKey = @ForeignKey(name = "FK_Order_OrderStock_StockID"))
    private Stock stock;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime registrationDate = new DateTime();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime responseDate;

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

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public DateTime getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(DateTime responseDate) {
        this.responseDate = responseDate;
    }

    public Double getTotalPrice(){
        return stock.getProduct().getWholeSalePrice() * quantity;
    }
}
