/**
 * 
 */
package controllers;

import exceptions.UserNotFoundException;
import be.objectify.deadbolt.java.actions.SubjectPresent;
import models.User;
import play.mvc.Controller;
import play.mvc.Http.Context;

/**
 * @author pfechner
 *
 */
@SubjectPresent
public class AbstractAuthorizedController extends Controller {

	//public static User getLoggedInUser() throws UserNotFoundException {
	public static User getLoggedInUser() {
		Context context = Context.current();
		if(context.session().containsKey("user")) {
			User user = User.findByLogin(context.session().get("user"));
			if(user == null) return null; //throw new UserNotFoundException("Could not find user " + context.session().get("user"));
			return user;
		}
		return null;
//		throw new UserNotFoundException("No user logged in in session or session is empty");
	}
}
