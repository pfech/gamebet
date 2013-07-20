/**
 * 
 */
package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import models.GamebetUser;
import models.GroupRole;
import models.Meeting;
import models.Tip;
import models.web.Password;
import play.data.Form;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Users extends AbstractAuthorizedController {

	public static Result show(Long id) {
		GamebetUser user = getLoggedInUser();
		return ok(views.html.user.show.render(user));
	}
	
	public static Result settings(Long id) {
		GamebetUser user = getLoggedInUser();
		return ok(views.html.user.settings.render(user));
	}
	
	public static Result showChangePassword(Long id) {
		GamebetUser user = getLoggedInUser();
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
	
	@Restrict(@Group({"admins"}))
	public static Result delete(Long id) {
		GamebetUser user = GamebetUser.find.byId(id);
		models.Password.findByUserId(id).delete();
		
		user.roles.clear();
		user.update();
		user.refresh();
		
		for(Meeting m : user.managerOfMeetings) {
			m.manager = null;
			m.members.remove(user);
			m.update();
		}
		
		for(Meeting m : user.meetings) {
			m.members.remove(user);
			m.update();
		}
		
		user.meetings.clear();
		
		user.managerOfMeetings.clear();
		
		for(Tip tip : Tip.findTipByUserId(user.id)) {
			tip.delete();
		}
		
		user.delete();
		
		return redirect(routes.Admin.listUsers());
	}
	
	public static Result resetPassword(Long id) {
		GamebetUser user = GamebetUser.find.byId(id);
		if(user.id.equals(getLoggedInUser().id) || getLoggedInUser().hasRole("admins")) {
			models.Password password = models.Password.findByUserId(id);
			password.password = "1234";
			password.update();
			play.Logger.info("Password changed for User [" + user.username + "(" + user.id + ")]");
		} else {
			play.Logger.info("Password could not be changed for User [" + user.username + "(" + user.id + ")]");
		}
		return redirect(routes.Application.index());
	}
}
