package test.ivacompany.com.test.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by root on 13.01.17.
 */

public class Employee extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;
    private String surname;
    private String middleName;
    private Date birthday;
    private String city;
    private String position;

    public Employee(){}

    public Employee(long id, String name, String suname, String middleName, Date birthday, String city, String position) {
        this.id = id;
        this.name = name;
        this.surname = suname;
        this.middleName = middleName;
        this.birthday = birthday;
        this.city = city;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setSurname(String suname) {
        this.surname = suname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
