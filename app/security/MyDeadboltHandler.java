/**
 * 
 */
package security;

import models.User;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subject getSubject(Context context) {
		return User.findByLogin(context.session().get("user"));
	}

	@Override
	public Result onAuthFailure(Context context, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DynamicResourceHandler getDynamicResourceHandler(Context context) {
		// TODO Auto-generated method stub
		return null;
	}

}
