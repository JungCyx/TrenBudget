package Main.Model;
import jakarta.persistence.*;


@Entity
@Table
public class SavingModel {
    @Id
    private Long id;
    private double SavingGoal;
    private double currentGoal;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key referencing UserModel
    private UserModel user;

    public SavingModel(Long id, double savingGoal, double currentGoal) {
        this.id = id;
        this.SavingGoal = savingGoal;
        this.currentGoal = currentGoal;
    }

    public SavingModel() {

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
