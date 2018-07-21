package app.entity.organisation;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "OrganisationInfo")
public class OrganisationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrganisationInfoID")
    private Integer id;

    //@Size(min = 3, message = "Le nom doit contenir au moins 3 caractères")
    //@LetterAndDigit
    //@NotEmpty(message = "Veuillez renseignez un nom")
    private String name;

    //@Size(min = 12, message = "La doit contenir au moins 12 caractères")
    //@NotEmpty(message = "Veuillez faire un brève votre établissement")
    //@Comment
    private String description;

    @Size(min = 3, max = 5, message = "Le sigle doit contenir entre 3 et 5 caractères")
    //@LetterAndDigit
    private String acronym;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @NotNull(message = "Veuillez renseignez une date")
    //@Past(message = "Veuillez renseignez une date passé")
    private DateTime creationDate = new DateTime();

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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }
}
