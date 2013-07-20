/**
 * 
 */
package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Result;
import models.GroupRole;
import models.Password;
import models.GamebetUser;
import models.web.NewUser;

/**
 * @author pasca_000
 *
 */
public class Admin extends AbstractAuthorizedController {

	@Restrict(@Group({"admins"}))
	public static Result showCreateUser() {
		GamebetUser user = getLoggedInUser();
		
		Form<NewUser> userForm = Form.form(NewUser.class);
		
		return ok(views.html.admin.createUser.render(user, userForm));
	}
	
	public static Result createUser() {
		
		Form<NewUser> userForm = Form.form(NewUser.class).bindFromRequest();
		NewUser newUser = userForm.get();
		if(newUser.username == null || newUser.username.length() == 0) {
			play.Logger.info("need to add username to create a new user");
			flash("Error", Messages.get("views.admin.createUser.error.noUsername"));
			return redirect(routes.Admin.showCreateUser());
		}
		if(newUser.email == null || newUser.email.length() == 0) {
			play.Logger.info("need to add email to create a new user");
			flash("Error", Messages.get("views.admin.createUser.error.noEmail"));
			return redirect(routes.Admin.showCreateUser());
		}
		if(newUser.password == null || newUser.password.length() == 0) {
			play.Logger.info("need to add password to create a new user");
			flash("Error", Messages.get("views.admin.createUser.error.noPassword"));
			return redirect(routes.Admin.showCreateUser());
		}
		if(GamebetUser.findByLogin(newUser.email) != null) {
			play.Logger.info("a user with email '" + newUser.email + "' already exists, cannot add new user");
			flash("Error", Messages.get("views.admin.createUser.error.userAlreadyExists"));
			return redirect(routes.Admin.showCreateUser());
		}
		
		GamebetUser user = new GamebetUser();
		user.email = newUser.email;
		user.username = newUser.username;
		user.roles.add(GroupRole.users());
		
		user.save();
		user.refresh();
		
		Password pwd = new Password();
		pwd.password = newUser.password;
		pwd.user = user;
		
		pwd.save();
		return redirect(routes.Application.index());
	}
}
