package edu.csusm.Observer;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import edu.csusm.Model.UserModel;
import edu.csusm.Model.emaptyIF;
import io.github.cdimascio.dotenv.Dotenv;

/*
 * the class is reponsible for sending notification when funciton update is called by Observable transaction/budgetGoal/savingGoal
 * the function upadate also has generic paramater that is becuase we the function might be called by different observable which will 
 * each pass a different dataObject/model(Transaction, BudgetGoal, SavingGoal)
 */

public class ObserverEmailNotification implements ObserverIF{

    Dotenv dotenv = Dotenv.load(); // Automatically loads the .env file

    // takes a generic dataObject which can be (Transaction, BudgetGoal, SavingGoal)
    @Override
    public <T> void update(T dataObject){
        
        // all types of models implement interfaceIF
        emaptyIF obj = (emaptyIF) dataObject;

        // interface defin a function for returning the user model 
        UserModel userModel = obj.getUser();
        // interface defin a function for returning this or itself 
        emaptyIF serviceModel = obj.getInstance();

        if(!serviceModel.getNotificationsEnabled()){
            return;
        }

        // build email contant
        String emailContant = String.format("Hi %s,<br><strong>%s</strong>", 
        userModel.getUserName(),
        serviceModel.emailContant());
        
        String apiKey = dotenv.get("API_KEY");

        Resend resend = new Resend(apiKey);

        CreateEmailOptions params = CreateEmailOptions.builder()
            .from("TrenBuget <csusmSE471@tariqelamin.live>")
            .to(userModel.getEmail())
            .subject("Changes has been made on your TrenBuget app")
            .html(emailContant)
            .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
        e.printStackTrace();
        }

    }
    
}
