/**
 * 
 */
package controllers;

import models.Game;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Games extends AbstractAuthorizedController {

	public static Result list() {
		return TODO;
	}
	
	public static Result show(Long id) {
		User user = getLoggedInUser();
		Game item = Game.find.byId(id);
		
		return ok(views.html.game.show.render(user, item));
	}
	
	public static Result update(Long id) {
		Form<Game> gameForm = Form.form(Game.class).bindFromRequest();
		if(gameForm.hasErrors()) {
			play.Logger.info(gameForm.toString());
			return redirect(routes.Games.edit(id));
		}
		Game game = gameForm.get();
		
		game.update(id);
		
		return redirect(routes.Games.show(id));
	}
		
	public static Result create() {
		return TODO;
	}
	
	public static Result delete(Long id) {
		
		
		return TODO;
	}
	
	public static Result edit(Long id) {
		User user = getLoggedInUser();
		Game item = Game.find.byId(id);
		Form<Game> gameForm = Form.form(Game.class).fill(item);
		models.Result result = item.result;
		if(result == null) {
			result = new models.Result();
			result.game = item;
		}
		Form<models.Result> resultForm = Form.form(models.Result.class);
		if(result != null)
			resultForm.fill(result);
		
		return ok(views.html.game.edit.render(user, item, gameForm, resultForm));
	}

	public static Result createNew() {
		return TODO;
	}	
	
	public static Result createResult() {
		Form<models.Result> resultForm = Form.form(models.Result.class).bindFromRequest();
		if(resultForm.hasErrors()) {
			return redirect(routes.Games.edit(Long.parseLong(resultForm.data().get("game.id"))));
		}
		models.Result result = resultForm.get();
		result.save();
		
		return redirect(routes.Games.edit(result.game.id));
	}
	
	public static Result editResult(Long resultId) {
		Form<models.Result> resultForm = Form.form(models.Result.class).bindFromRequest();
		if(resultForm.hasErrors()) {
			return redirect(routes.Games.edit(Long.parseLong(resultForm.data().get("game.id"))));
		}
		models.Result result = resultForm.get();
		result.update(resultId);
		
		return redirect(routes.Games.edit(result.game.id));
	}
	
}
