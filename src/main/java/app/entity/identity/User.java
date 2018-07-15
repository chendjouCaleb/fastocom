package app.entity.identity;

import app.configuration.JsonDateTimeSerializer;
import app.enumeration.UserType;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class User implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountUserID")
    private Integer id;


    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column
    private DateTime lastModificationDate = new DateTime();

    public User(){}
    public User(UserType userType){
        setUserType(userType);
    }

    private UserType userType;
    private String name;
    private String surname;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private DateTime birthDate;

    private String gender;

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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }
}
