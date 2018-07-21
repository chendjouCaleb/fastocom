package app.entity.identity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Role(){}
    public Role(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "role")
    private Set<AccountRole> accountRoles = new HashSet<>();

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

    public Set<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(Set<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }
}
