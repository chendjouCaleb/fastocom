package app.entity;

import app.configuration.JsonDateTimeSerializer;
import app.enumeration.Activity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrganisationID")
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


    private String image;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime registrationDate = new DateTime();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModificationDate = new DateTime();
}
