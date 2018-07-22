package app.entity.organisation;

import app.configuration.JsonDateTimeSerializer;
import app.entity.Address;
import app.entity.market.Stock;
import app.enumeration.Activity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Activity active = Activity.ACTIVE;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "OrganisationInfoID",
            foreignKey = @ForeignKey(name = "FK_Organisation_OrganisationInfo_OrganisationInfoID"))
    private OrganisationInfo info = new OrganisationInfo();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AddressID", foreignKey = @ForeignKey(name = "FK_Organisation_OrganisationAddress_AddressID"))
    private Address address = new Address();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AdminID", foreignKey = @ForeignKey(name = "FK_Organisation_OrganisationAdmin_AdminID"))
    private Admin admin = new Admin();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ContactID", foreignKey = @ForeignKey(name = "FK_Organisation_OrganisationContact_ContactID"))
    private Contact contact = new Contact();

    @OneToMany(mappedBy = "organisation", fetch = FetchType.EAGER)
    private List<Stock> stocks = new ArrayList<>();


    private String image;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime registrationDate = new DateTime();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModificationDate = new DateTime();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActive() {
        return active;
    }

    public void setActive(Activity active) {
        this.active = active;
    }

    public OrganisationInfo getInfo() {
        return info;
    }

    public void setInfo(OrganisationInfo info) {
        this.info = info;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(DateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }
}
