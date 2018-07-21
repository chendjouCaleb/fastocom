package app.entity.organisation;

import app.entity.market.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pharmacy extends Organisation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "pharmacy")
    private List<Order> orders = new ArrayList<>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
