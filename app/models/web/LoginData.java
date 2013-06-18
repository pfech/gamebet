/**
 * 
 */
package models.web;

import play.i18n.Messages;

/**
 * @author pfechner
 *
 */
public class LoginData {

	public String login;
	public String password;
	
	public String validate() {
		System.out.println("validate");
		if(login == null || login.length() == 0 || password == null || password.length() == 0)
			return Messages.get("views.login.form.notValid");
		return null;
	}
}
