package edu.csusm.Factory;
import edu.csusm.Model.Transaction;
import edu.csusm.Model.UserSession;
import edu.csusm.Model.emaptyIF;

//Concrete Factory: TransactionFactory
public class TransactionFactory implements SavingsFactory {
    private String type;
    private String category;
    private double amount;
    private boolean notificationsEnabled;

    public TransactionFactory(String type, String category, double amount, boolean notificationsEnabled) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.notificationsEnabled = notificationsEnabled;
    }

    @Override
    public emaptyIF create() {
        return new Transaction(type, category, amount, notificationsEnabled, UserSession.getInstance().getCurrentUser());
    }
}