package app.entity.identity;

import javax.persistence.*;

@Entity
public class AccountRole {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "RoleAccountID", foreignKey = @ForeignKey(name = "FK_RoleAccount_RoleAccountID"))
    private Account account;

    @ManyToOne
    @JoinColumn(name = "AccountRoleID", foreignKey = @ForeignKey(name = "FK_AccountRole_AccountRoleID"))
    private Role role;

    public AccountRole(){}
    public AccountRole(Account account, Role role) {
        this.account = account;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
