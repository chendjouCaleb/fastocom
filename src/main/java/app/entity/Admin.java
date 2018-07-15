package app.entity;

import app.entity.identity.Account;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "AccountID", foreignKey = @ForeignKey(name = "FK_Admin_AdminAccount_AccountID"))
    private Account account;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastModificationDate = new DateTime();

    public Admin() {}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
