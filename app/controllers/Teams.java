/**
 * 
 */
package controllers;

import java.util.List;

import models.Gameday;
import models.Meeting;
import models.Team;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author pascal
 *
 */
public class Teams extends AbstractAuthorizedController {

	public static Result list() {
		List<Team> list = Team.find.all();
		return ok(views.html.team.list.render(list));
	}
	
	public static Result show(Long id) {
		Team item = Team.find.byId(id);
		return ok(views.html.team.show.render(item));
	}
	
	public static Result update(Long id) {
		return TODO;
	}
		
	public static Result create() {
		
		return TODO;
	}
	
	public static Result delete(Long id) {
		Team item = Team.find.byId(id);
		Team.delete(item);
		return redirect(routes.Application.index());
	}
	
	public static Result edit(Long id) {
		Team item = Team.find.byId(id);
		Form<Team> form = Form.form(Team.class).fill(item);
		return ok(views.html.team.edit.render(true, item, form));
	}

	public static Result createNew() {
		Form<Team> form = Form.form(Team.class);
		return ok(views.html.team.edit.render(false, null, form));
	}
	
	public static Result createFromMeeting(Long meetingId) {
		Form<Team> teamForm = Form.form(Team.class).bindFromRequest();
		if(teamForm.hasErrors()) {
			play.Logger.of(Teams.class).info("Could not create new team, " + teamForm.errors().toString());
			return Meetings.edit(meetingId);
		}
		
		Team team = teamForm.get();
		team.save();
		play.Logger.of(Teams.class).info("Saved team " + team.toString());
		Meeting meeting = Meeting.fndById(meetingId);
		meeting.teams.add(team);
		meeting.update();
		play.Logger.of(Gamedays.class).info("Updated meeting " + meeting.toString());
		return redirect(routes.Meetings.edit(meetingId));
	}
}
