package app.entity;

import javax.persistence.*;

@Entity
public class Pharmacy extends Organisation{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrganisationID")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
