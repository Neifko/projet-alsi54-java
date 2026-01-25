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

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return String
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @return String
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return int
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * @param birthYear
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * @return float
     */
    public float getSalary() {
        return salary;
    }

    /**
     * @param salary
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * @return float
     */
    public float getBonus() {
        return bonus;
    }

    /**
     * @param bonus
     */
    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    /**
     * @return String
     */
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