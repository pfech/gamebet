/**
 * 
 */
package controllers;

import java.util.List;

import models.Game;
import models.Gameday;
import models.Meeting;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.Util;

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
		Form<Gameday> form = Form.form(Gameday.class).bindFromRequest();
		if(form.hasErrors()) {
			play.Logger.info("could not update gameday " + form.toString());
			return redirect(routes.Gamedays.edit(id));
		}
		Gameday day = form.get();
		day.update(id);
		
		return redirect(routes.Gamedays.show(id));
	}
		
	public static Result create() {
		return TODO;
	}
	
	public static Result delete(Long id) {
		
		
		return TODO;
	}
	
	public static Result deleteWithRedirect(Long meetingId, Long gamedayId) {
		Gameday toDelete = Gameday.find.byId(gamedayId);
		toDelete.delete();

		return redirect(routes.Meetings.edit(meetingId));
	}
	
	public static Result edit(Long id) {
		User user = getLoggedInUser();
		Gameday item = Gameday.find.byId(id);
		Form<Gameday> form = Form.form(Gameday.class).fill(item);
		Game g = new Game();
		g.gameday = item;
		g.startDate = Util.getNow();
		Form<Game> gForm = Form.form(Game.class).fill(g);
		return ok(views.html.gameday.edit.render(user, item, form, gForm));
	}

	public static Result createNew() {
//		Form<Gameday> form = Form.form(Gameday.class);
//		return ok(views.html.gameday.edit.render(false, null, form));
		return TODO;
	}
	
	public static Result createFromMeeting(Long meetingId) {
		Form<Gameday> gamedayForm = Form.form(Gameday.class).bindFromRequest();
		if(gamedayForm.hasErrors()) {
			play.Logger.of(Gamedays.class).info("Could not create new gameday, " + gamedayForm.errors().toString());
			return Meetings.edit(meetingId);
		}
		
		Gameday gameday = gamedayForm.get();
		gameday.save();
		play.Logger.of(Gamedays.class).info("Saved gameday " + gameday.toString());
		Meeting meeting = Meeting.fndById(meetingId);
		meeting.gamedays.add(gameday);
		meeting.update();
		play.Logger.of(Gamedays.class).info("Updated meeting " + meeting.toString());
		return redirect(routes.Meetings.edit(meetingId));
	}
	
	public static class GameCreate {
		public Game game;
		public models.Result result;
		
		@Override
		public String toString() {
			return game + ", " + result;
		}
	}
	
	public static Result createGame(Long gamedayId) {
		Form<Game> form = Form.form(Game.class).bindFromRequest();
		play.Logger.info(form.toString());
		if(form.hasErrors()) {
			play.Logger.of(Gamedays.class).info("Could not create new game");
			return badRequest();
		}
		Game game = form.get();
		if(game != null) {
			if(game.home != null && game.away != null) {
				game.gameday = Gameday.find.byId(gamedayId);
				game.startDate = Util.getNow();
				game.save();
			}
		}
		
		play.Logger.info("created new game");
		return redirect(routes.Gamedays.edit(gamedayId));
	}
	
	public static Result deleteGame(Long dayId, Long gameId) {
		Game toDelete = Game.find.byId(gameId);
		if(toDelete.result != null)
			toDelete.result.delete();
		toDelete.delete();
		return redirect(routes.Gamedays.edit(dayId));
	}
}
