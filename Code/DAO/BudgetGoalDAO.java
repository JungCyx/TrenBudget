package DAO;
import Model.BudgetGoal;

//TODO: Update to match the savings DAO

 public class BudgetGoalDAO{

    public static BudgetGoal currentGoal; // this is instance of a budget goal which will be updating in the dashboard

    //get the current budget goal
    public static BudgetGoal getCurrentGoal(){
        return currentGoal;
    }

    //set the current budget goal
    public static void setCurrentSavingsGoal(BudgetGoal goal) {
         currentGoal = goal;
        
    }
   
}