package edu.csusm.Observer;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import edu.csusm.Model.UserModel;
import edu.csusm.Model.emaptyIF;

public class ObserverEmailNotification implements Observer{

    @Override
    public <T> void update(T dataObject){

    // 
    emaptyIF obj = (emaptyIF) dataObject;

    UserModel currentUser = obj.getUser();
    emaptyIF service = obj.getInstance();
    
    
    Resend resend = new Resend("re_bkBFiBPk_5v18BSjoMUMs3P7SyKdsXQQn");

    CreateEmailOptions params = CreateEmailOptions.builder()
        .from("Acme <csusmSE471@resend.dev>")
        .to("delivered@resend.dev")
        .subject("it works!")
        .html("<strong>hello world</strong>")
        .build();

    try {
        CreateEmailResponse data = resend.emails().send(params);
        System.out.println(data.getId());
    } catch (ResendException e) {
    e.printStackTrace();
    }

    }
    
}
