package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private String city;

    public User(int id, String name, int age, String city) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id",this.id)
                .add("name", this.name)
                .add("age", this.age)
                .add("city", this.city).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !object.getClass().getName().equals(this.getClass().getName())) {
            return false;
        }

        User that = (User)object;

        return  Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name) &&
                Objects.equal(this.age, that.age) &&
                Objects.equal(this.city, that.city);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}