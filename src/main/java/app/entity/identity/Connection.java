package app.entity.identity;
import app.configuration.JsonDateTimeSerializer;
import app.entity.identity.Account;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "Connection")
public class Connection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;

    @Transient
    private Boolean active;

    private Boolean closed = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "ConnectionAccountID", foreignKey = @ForeignKey(name = "FK_ConnectionAccount_ConnectionAccountID"))
    private Account account;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column
    private DateTime beginDate = new DateTime();

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column
    private DateTime endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getClosed() {
        return closed;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public DateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
        this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
