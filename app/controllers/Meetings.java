/**
 * 
 */
package controllers;

import models.Meeting;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Meetings extends Controller {

	public static Result list() {
		return ok(views.html.meeting.list.render(Meeting.find.all()));
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
	
	public static Result edit() {
		return TODO;
	}

	public static Result createNew() {
		return TODO;
	}
}
