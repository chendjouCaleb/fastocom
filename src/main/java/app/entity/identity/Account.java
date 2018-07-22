package app.entity.identity;

import app.configuration.JsonDateTimeSerializer;
import app.entity.Address;
import app.entity.organisation.Admin;
import app.enumeration.Activity;
import app.enumeration.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Account")
public class Account implements Principal{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private Integer id;

    private Activity active = Activity.ACTIVE;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "AccountDetailsID", foreignKey = @ForeignKey(name = "FK_Account_AccountDetails_AccountDetailsID"))
    private Details details = new Details();

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AccountRole> accountRoles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "AccountUserID", foreignKey = @ForeignKey(name = "FK_Account_AccountUser_AccountUserID"))
    private User user = new User(UserType.ACCOUNT);

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "AddressID", foreignKey = @ForeignKey(name = "FK_Account_AccountAddress_AddressID"))
    private Address address = new Address();

    private String image;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime registrationDate = new DateTime();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Column
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModificationDate = new DateTime();

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Connection> connections = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Admin> admins = new ArrayList<>();


    public Activity getActive() {
        return active;
    }

    public void setActive(Activity active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        Account account = (Account) obj;
        if (id == null) {
            return account == this;
        }
        return account.getId().equals(id);
    }

    @Override
    public String getName() {
        return id.toString();
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public void addConnection(Connection connection) {
        connection.setAccount(this);
        this.connections.add(connection);
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Set<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(Set<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }
}
