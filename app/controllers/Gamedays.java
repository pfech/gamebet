/**
 * 
 */
package controllers;

import java.util.List;

import models.Gameday;
import models.Meeting;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Gamedays extends AbstractAuthorizedController {

	public static Result list() {
		List<Gameday> list = Gameday.find.all();
		return ok(views.html.gameday.list.render(list));
	}
	
	public static Result show(Long id) {
		Gameday item = Gameday.find.byId(id);
		User user = getLoggedInUser();
		return ok(views.html.gameday.show.render(user, item));
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
		Gameday item = Gameday.find.byId(id);
		Form<Gameday> form = Form.form(Gameday.class).fill(item);
		return ok(views.html.gameday.edit.render(true, item, form));
	}

	public static Result createNew() {
		Form<Gameday> form = Form.form(Gameday.class);
		return ok(views.html.gameday.edit.render(false, null, form));
	}
	
	public static Result createFromMeeting(Long meetingId) {
		Form<Gameday> gamedayForm = Form.form(Gameday.class).bindFromRequest();
		if(gamedayForm.hasErrors()) {
			return badRequest(routes.Meetings.edit(meetingId).url());
		}
		
		Gameday gameday = gamedayForm.get();
		gameday.save();
		Meeting meeting = Meeting.fndById(meetingId);
		meeting.gamedays.add(gameday);
		meeting.update();
		
		return Meetings.edit(meetingId);
	}
}
