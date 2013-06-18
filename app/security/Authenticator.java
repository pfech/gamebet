/**
 * 
 */
package security;

import play.mvc.Http.Context;
import models.Password;
import models.User;

/**
 * @author pfechner
 *
 */
public class Authenticator {

	
	public static boolean authenticate(String login, String password) {
		
		if(login == null || login.length() == 0 || password == null || password.length() == 0)
			return false;
		
		User user = User.findByLogin(login); 
		if(user == null)
			return false;
		
		Password pwd = Password.findByUserId(user.id);
		if(pwd == null)
			return false;
		
		return (pwd.password.equals(password) && login.equals(user.getLogin()));
	}
	
	public static boolean login(Context context, String login, String password) {
		boolean isAuthenticated = authenticate(login, password);
		context.session().clear();
		if(isAuthenticated) {
			context.session().put("user", login);
		}
		
		return isAuthenticated;
	}
	
	public static boolean logout(Context context) {
		context.session().clear();
		return true;
	}
}
