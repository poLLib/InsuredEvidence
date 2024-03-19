package bb.example;

public record Person(int id, String name, String surname, String phone, int age) {

    @Override
    public String toString() {
        return "Insured person: " + name + " " + surname + ", phone: " + phone + ", age: " + age + ", ID-" + id;
    }
}
