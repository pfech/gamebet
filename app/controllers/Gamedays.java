/**
 * 
 */
package controllers;

import java.util.List;

import models.Gameday;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Gamedays extends Controller {

	public static Result list() {
		List<Gameday> list = Gameday.find.all();
		return ok(views.html.gameday.list.render(list));
	}
	
	public static Result show(Long id) {
		Gameday item = Gameday.find.byId(id);
		return ok(views.html.gameday.show.render(item));
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
