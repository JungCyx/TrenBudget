package edu.csusm.Observer;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import edu.csusm.Model.UserModel;
import edu.csusm.Model.emaptyIF;

public class ObserverEmailNotification implements ObserverIF{

    @Override
    public <T> void update(T dataObject){
        
        emaptyIF obj = (emaptyIF) dataObject;

        UserModel userModel = obj.getUser();
        emaptyIF serviceModel = obj.getInstance();

        // build email contant
        String emailContant = String.format("Hi %s,<br><strong>%s</strong>", 
        userModel.getUserName(),
        serviceModel.emailContant());

    
        Resend resend = new Resend("re_bkBFiBPk_5v18BSjoMUMs3P7SyKdsXQQn");

        CreateEmailOptions params = CreateEmailOptions.builder()
            .from("Acme <csusmSE471@tariqelamin.live>")
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
