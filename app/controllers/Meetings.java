/**
 * 
 */
package controllers;

import java.util.List;

import models.Meeting;
import models.User;
import play.data.Form;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Meetings extends AbstractAuthorizedController {

	public static Result list() {
		User user = getLoggedInUser();
		return ok(views.html.meeting.list.render(user));
	}
	
	public static Result show(Long id) {
		Meeting item = Meeting.find.byId(id);
		return ok(views.html.meeting.show.render(item));
	}
	
	public static Result update(Long id) {
		return TODO;
	}
		
	public static Result create() {
		return TODO;
	}
	
	public static Result delete(Long id) {
		return TODO;
	}
	
	public static Result edit(Long id) {
		Meeting item = Meeting.find.byId(id);
		Form<Meeting> form = Form.form(Meeting.class).fill(item);
		return ok(views.html.meeting.edit.render(true, item, form));
	}

	public static Result createNew() {
		Form<Meeting> form = Form.form(Meeting.class);
		return ok(views.html.meeting.edit.render(false, null, form));
	}
}
 