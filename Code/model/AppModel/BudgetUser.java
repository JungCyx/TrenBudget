package model.AppModel;

import jakarta.persistence.*;

@Entity
@Table
public class BudgetUser{
    @Id
    @SequenceGenerator(
            name = "BudgetUser_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BudgetUser_sequence"
    )
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private float savingGoal;
    private float totalIncome;
    private float totalSpending;

    public BudgetUser(int id, String firstName, String lastName, String email, String password, float savingGoal, float totalIncome, float totalSpending) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.savingGoal = savingGoal;
        this.totalIncome = totalIncome;
        this.totalSpending = totalSpending;
    }

    public BudgetUser() {

    }


    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public float getSavingGoal() {
        return savingGoal;
    }

    public float getTotalIncome() {
        return totalIncome;
    }

    public float getTotalSpending() {
        return totalSpending;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSavingGoal(float savingGoal) {
        this.savingGoal = savingGoal;
    }

    public void setTotalIncome(float totalIncome) {
        this.totalIncome = totalIncome;
    }

    public void setTotalSpending(float totalSpending) {
        this.totalSpending = totalSpending;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", savingGoal=" + savingGoal +
                ", totalIncome=" + totalIncome +
                ", totalSpending=" + totalSpending +
                '}';
    }
}
