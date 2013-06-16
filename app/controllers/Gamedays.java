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
}
