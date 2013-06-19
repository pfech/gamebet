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
		User user = getLoggedInUser();
		return ok(views.html.meeting.show.render(user, item));
	}
	
	public static Result update(Long id) {
		Meeting item = Meeting.find.byId(id);
		User user = getLoggedInUser();
		
		Form<Meeting> form = Form.form(Meeting.class).bindFromRequest();
		if(form.hasErrors()) {
			return badRequest(views.html.meeting.edit.render(user, item, form));
		}
		Meeting toUpdate = form.get();
		toUpdate.update(id);
		
		return show(toUpdate.id);
	}
		
	public static Result create() {
		return TODO;
	}
	
	public static Result delete(Long id) {
		return TODO;
	}
	
	public static Result edit(Long id) {
		Meeting item = Meeting.find.byId(id);
		User user = getLoggedInUser();
		
		Form<Meeting> form = Form.form(Meeting.class).fill(item);
		return ok(views.html.meeting.edit.render(user, item, form));
	}

	public static Result createNew() {
//		Form<Meeting> form = Form.form(Meeting.class);
//		return ok(views.html.meeting.edit.render(false, null, form));
		return TODO;
	}
}
 