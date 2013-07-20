/**
 * 
 */
package controllers;

import models.User;
import models.web.Password;
import play.data.Form;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Users extends AbstractAuthorizedController {

	public static Result show(Long id) {
		User user = getLoggedInUser();
		return ok(views.html.user.show.render(user));
	}
	
	public static Result settings(Long id) {
		User user = getLoggedInUser();
		return ok(views.html.user.settings.render(user));
	}
	
	public static Result showChangePassword(Long id) {
		User user = getLoggedInUser();
		Password pwd = new Password();
		Form<Password> form = Form.form(Password.class).fill(pwd);
		return ok(views.html.user.changePassword.render(user, form));
	}
	
	public static Result changePassword(Long id) {
		Form<Password> form = Form.form(Password.class).bindFromRequest();
		if(form.hasErrors()) {
			play.Logger.info(form.toString());
			return redirect(routes.Users.showChangePassword(id));
		}
		Password newPwd = form.get();
		models.Password password = models.Password.findByUserId(id);
		if(newPwd.old.equals(password.password)) {
			if(!newPwd.new1.equals(newPwd.old) && 
					newPwd.new1 != null && 
					newPwd.new1.length() > 0 
					&& newPwd.new1.equals(newPwd.new2)) {
				
				password.password = newPwd.new1;
				password.update();
			} else {
				play.Logger.info("New Passwords do not match");
				flash("Error", "New passwords do not match");
				return redirect(routes.Users.showChangePassword(id));
			}
			
		} else {
			play.Logger.info("Old Passwords do not match");
			flash("Error", "Old password is not correct");
			return redirect(routes.Users.showChangePassword(id));
		}
		play.Logger.info("Success to change password");
		return settings(id);
	}
}
