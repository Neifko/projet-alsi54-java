package fr.efrei.alsi54;

public class Programmer {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private String manager;
    private String hobby;
    private int birthYear;
    private float salary;
    private float bonus;

    public Programmer(int id, String lastName, String firstName, String address, String username, String manager,
            String hobby, int birthYear, float salary, float bonus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.username = username;
        this.manager = manager;
        this.hobby = hobby;
        this.birthYear = birthYear;
        this.salary = salary;
        this.bonus = bonus;
    }

    public Programmer(String lastName, String firstName, String address, String username, String manager, String hobby,
            int birthYear, float salary, float bonus) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.username = username;
        this.manager = manager;
        this.hobby = hobby;
        this.birthYear = birthYear;
        this.salary = salary;
        this.bonus = bonus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
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
        return "Id           : " + id + "\n" +
                "Nom          : " + lastName + "\n" +
                "Prénom       : " + firstName + "\n" +
                "Adresse      : " + address + "\n" +
                "Pseudo       : " + username + "\n" +
                "Responsable  : " + manager + "\n" +
                "Hobby        : " + hobby + "\n" +
                "Naissance    : " + birthYear + "\n" +
                "Salaire      : " + salary + "\n" +
                "Prime        : " + bonus + "\n" +
                "--------------------------------------------------";
    }
}