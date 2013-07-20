/**
 * 
 */
package controllers;

import models.Game;
import models.Tip;
import models.GamebetUser;
import play.data.Form;
import play.mvc.Result;
import util.Util;

/**
 * @author pascal
 *
 */
public class Tips extends AbstractAuthorizedController {

	public static Result edit(Long tipId) {
		GamebetUser user = getLoggedInUser();
		Tip item = Tip.find.byId(tipId);
		item.game.refresh();
		Form<Tip> tipForm = Form.form(Tip.class).fill(item);
		
		return ok(views.html.tip.edit.render(user, item, tipForm));
	}
	
	public static Result create(Long gameId) {
		GamebetUser user = getLoggedInUser();
		Game game = Game.find.byId(gameId);
		Tip item = new Tip();
		item.owner = user;
		item.game = game;
		item.lastChange = Util.getNow();
		
		Form<Tip> tipForm = Form.form(Tip.class).fill(item);
		
		return ok(views.html.tip.create.render(user, item, tipForm));
	}
	
	public static Result createNew() {
		Form<Tip> form = Form.form(Tip.class).bindFromRequest();
		if(form.hasErrors()) {
			play.Logger.info(form.toString());
			return redirect(routes.Tips.create(Long.parseLong(form.data().get("game.id"))));
		}
		Tip tip = form.get();
		tip.lastChange = Util.getNow();
		tip.save();
		tip.game.refresh();
		return redirect(routes.Gamedays.show(tip.game.gameday.id));
	}
	
	public static Result update(Long id) {
		Form<Tip> form = Form.form(Tip.class).bindFromRequest();
		if(form.hasErrors()) {
			play.Logger.info(form.toString());
			return redirect(routes.Tips.edit(id));
		}
		Tip tip = form.get();
		tip.lastChange = Util.getNow();
		tip.update(id);
		tip.game.refresh();
		return redirect(routes.Gamedays.show(tip.game.gameday.id));
	}
}
