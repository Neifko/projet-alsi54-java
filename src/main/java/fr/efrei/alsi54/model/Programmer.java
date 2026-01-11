package fr.efrei.alsi54.model;

import fr.efrei.alsi54.Database;

import java.util.Date;

public class Programmer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private Programmer manager;
    private String hobby;
    private Date birthdate;
    private float salary;
    private float bonus;

    public Programmer(int id, String firstName,String lastName, String address, String username, Programmer manager, String hobby, Date birthdate, float salary, float bonus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.username = username;
        this.manager = manager;
        this.hobby = hobby;
        this.birthdate = birthdate;
        this.salary = salary;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return lastName;
    }

    public void setName(String name) {
        this.lastName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Programmer getManager() {
        return manager;
    }

    public void setManager(Programmer manager) {
        this.manager = manager;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getBonus() {
        return bonus;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "id=" + id +
                ", name='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", manager=" + manager +
                ", hobby='" + hobby + '\'' +
                ", birthdate=" + birthdate +
                ", salary=" + salary +
                ", bonus=" + bonus +
                '}';
    }
}
