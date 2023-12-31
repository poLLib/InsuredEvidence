package bb.example;

/**
 * Represents a specific insured person.
 *
 * @author pollib
 */
public class InsuredPerson {
    private String name;
    private String surname;
    private String phone;
    private int age;
    private int id;

    public InsuredPerson(String name, String surname, String phone, int age) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.age = age;


    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Insured person: " + name + " " + surname + ", phone: " + phone + ", age: " + age + ", ID-" + id;
    }
}

