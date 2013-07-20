/**
 * 
 */
package security;

import models.GamebetUser;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
/**
 * @author pfechner
 *
 */
public class MyDeadboltHandler implements DeadboltHandler {

	@Override
	public Result beforeAuthCheck(Context context) {
		return null;
	}

	@Override
	public Subject getSubject(Context context) {
		return GamebetUser.findByLogin(context.session().get("user"));
	}

	@Override
	public Result onAuthFailure(Context context, String content) {
		return Controller.redirect(controllers.routes.Application.login());
	}

	@Override
	public DynamicResourceHandler getDynamicResourceHandler(Context context) {
		return null;
	}

}
