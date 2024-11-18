package model.AppModel;
import jakarta.persistence.*;


@Entity
@Table
public class SavingUser {
    @Id
    private Long id;
    double SavingGoal;
    double currentGoal;



    public SavingUser(Long id, double savingGoal, double currentGoal) {
        this.id = id;
        this.SavingGoal = savingGoal;
        this.currentGoal = currentGoal;
    }

    public SavingUser() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getSavingGoal() {
        return SavingGoal;
    }

    public void setSavingGoal(double savingGoal) {
        SavingGoal = savingGoal;
    }

    public double getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(double currentGoal) {
        this.currentGoal = currentGoal;
    }
}
