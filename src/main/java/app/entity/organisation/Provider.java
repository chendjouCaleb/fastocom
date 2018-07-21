package app.entity.organisation;

import app.entity.market.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Provider extends Organisation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "provider")
    List<Product> products = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
