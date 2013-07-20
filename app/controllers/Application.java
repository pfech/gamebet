package controllers;

import models.GamebetUser;
import models.web.LoginData;
import play.*;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.*;
import play.mvc.Http.Context;
import security.Authenticator;
import views.html.*;

public class Application extends Controller {
  
	/**
	 * 
	 * @return
	 */
    public static Result index() {
    	GamebetUser user = getLoggedInUser();
        return ok(index.render(user, "Your new application is ready."));
    }
  
    
    /**
     * 
     * @return
     */
    public static Result login() {
    	Form<LoginData> form = Form.form(LoginData.class);
    	
    	return ok(views.html.login.render(form));
    }
    
    /**
     * 
     * @return
     */
    public static Result logout() {
    	Authenticator.logout(Context.current());
    	return redirect(routes.Application.index());
    }
    
    /**
     * 
     * @return
     */
    public static Result authenticate() {
    	Form<LoginData> form = Form.form(LoginData.class).bindFromRequest();
    	if(form.hasErrors())
    		return badRequest(views.html.login.render(form));
    	
    	LoginData data = form.get();
    	
    	if(Authenticator.login(Context.current(), data.login, data.password)) {
    		return redirect(routes.Application.index());	
    	} else {
    		data.password = null;
    		Form<LoginData> newForm = Form.form(LoginData.class).fill(data);
    		newForm.reject(Messages.get("views.login.badLogin"));
    		return badRequest(views.html.login.render(newForm)); 
    	}
    }
    
    public static GamebetUser getLoggedInUser() {
		Context context = Context.current();
		if(context.session().containsKey("user")) {
			GamebetUser user = GamebetUser.findByLogin(context.session().get("user"));
			if(user == null) return null; //throw new UserNotFoundException("Could not find user " + context.session().get("user"));
			return user;
		}
		return null;
//		throw new UserNotFoundException("No user logged in in session or session is empty");
	}
}
