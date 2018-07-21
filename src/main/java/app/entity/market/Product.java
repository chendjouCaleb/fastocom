package app.entity.market;

import app.configuration.JsonDateTimeSerializer;
import app.entity.organisation.Provider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double wholeSalePrice;

    private Double retailPrice;

    private String image;

    @ManyToOne
    @JoinColumn(name = "ProviderID", foreignKey = @ForeignKey(name = "FK_Product_ProductProvider_ProductID"))
    private Provider provider;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks = new ArrayList<>();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime registrationDate = new DateTime();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public DateTime getRegistrationDate() {
        return registrationDate;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
