/**
 * 
 */
package controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.Game;
import models.Gameday;
import models.Meeting;
import models.Team;
import models.Tip;
import models.GamebetUser;
import models.UserStatistics;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import util.Util;

/**
 * @author pascal
 *
 */
public class Meetings extends AbstractAuthorizedController {

	public static Result list() {
		GamebetUser user = getLoggedInUser();
		return ok(views.html.meeting.list.render(user));
	}
	
	public static Result show(Long id) {
		Meeting item = Meeting.find.byId(id);
		GamebetUser user = getLoggedInUser();
		return ok(views.html.meeting.show.render(user, item));
	}

	public static Result showStatistics(Long id) {
		Meeting item = Meeting.find.byId(id);
		GamebetUser user = getLoggedInUser();
		return ok(views.html.meeting.statistics.render(user, item, null));
	}

	public static Result update(Long id) {
		Meeting item = Meeting.find.byId(id);
		GamebetUser user = getLoggedInUser();
		
		Form<Meeting> form = Form.form(Meeting.class).bindFromRequest();
		Form<Gameday> gForm = Form.form(Gameday.class);
		Form<GamebetUser> uForm = Form.form(GamebetUser.class);
		if(form.hasErrors()) {
			return badRequest(views.html.meeting.edit.render(user, item, form, gForm, uForm));
		}
		Meeting toUpdate = form.get();
		toUpdate.members.addAll(item.members);
		toUpdate.update(id);
		
		return show(toUpdate.id);
	}
		
	public static Result create() {
		Form<Meeting> form = Form.form(Meeting.class).bindFromRequest();
		if(form.hasErrors()) {
			play.Logger.info(form.toString());
			return redirect(routes.Meetings.createNew());
		}
		Meeting m = form.get();
		GamebetUser user = getLoggedInUser();
		m.manager = user;
		m.members.add(user);
		m.save();
		m.refresh();
		return redirect(routes.Meetings.show(m.id));
	}
	
	public static Result delete(Long id) {
		return TODO;
	}
	
	public static Result edit(Long id) {
		Meeting item = Meeting.find.byId(id);
		GamebetUser user = getLoggedInUser();
		
		Form<Meeting> form = Form.form(Meeting.class).fill(item);
		Gameday g = new Gameday();
		g.meeting = item;
		Form<Gameday> gForm = Form.form(Gameday.class).fill(g);
		Form<GamebetUser> uForm = Form.form(GamebetUser.class);
		return ok(views.html.meeting.edit.render(user, item, form, gForm, uForm));
	}

	public static Result createNew() {
		GamebetUser user = getLoggedInUser();
		Meeting m = new Meeting();
		m.manager = user;
		m.members.add(user);
		Form<Meeting> form = Form.form(Meeting.class);
//		return ok(views.html.meeting.edit.render(false, null, form));
		return ok(views.html.meeting.admin.create.render(user, m, form));
	}
	
	public static Result addMember(Long meetingId) {
		Meeting item = Meeting.fndById(meetingId);
		
		Form<GamebetUser> uForm = Form.form(GamebetUser.class).bindFromRequest();
		if(uForm.hasErrors()) {
			Logger.of(Meeting.class).info("Could not add user");
			return redirect(routes.Meetings.edit(meetingId));
		}
		GamebetUser newMember = uForm.get();
		
		newMember.refresh();
		
		item.members.add(newMember);
		item.update();
		
		return redirect(routes.Meetings.edit(meetingId));
	}
	
	public static Result removeMember(Long meetingId, Long memberId) {
		Meeting item = Meeting.fndById(meetingId);
		
		GamebetUser toRemove = GamebetUser.find.byId(memberId);
		item.members.remove(toRemove);
		item.update();
	
		return redirect(routes.Meetings.edit(meetingId));
	}
	
	public static class Standing {
		public Integer place;
		
		public GamebetUser user;
		
		public int points = 0;
	}
	
	public static List<Standing> getStandings(Long meetingId) {
		Meeting meeting = Meeting.find.byId(meetingId);
		
		Map<Long, Standing> standings = new HashMap<Long, Standing>();
		for(GamebetUser user : meeting.members) {
			Standing tmp = new Standing();
			tmp.user = user;
			standings.put(user.id, tmp);
		}
		List<GamebetUser> members = meeting.members;
		for(Gameday day : meeting.gamedays) {
			for(Game game : day.games) {
				for(GamebetUser user : members) {
					Tip tip = Tip.findTip(game.id, user.id);
					if(tip != null) {
						standings.get(user.id).points += tip.getPoints(game.result);
					}
				}
			}
		}
		
		List<Standing> list = new LinkedList<Standing>();
		list.addAll(standings.values());
		Collections.sort(list, new Comparator<Standing>() {

			@Override
			public int compare(Standing arg0, Standing arg1) {
				return -1 * (new Integer(arg0.points).compareTo(new Integer(arg1.points)));
//				return Integer.compare(arg1.points, arg0.points);
			}
		});
		
		return list;
	}
	
	public static Result showStatisticsForTeam(Long meetingId) {
		Form<Team> teamForm = Form.form(Team.class).bindFromRequest();
		Team t = teamForm.get();
		Team team = Team.find.byId(t.id);
		System.out.println("t.id=" + t.id);
		System.out.println("t.name=" + t.name);
		System.out.println("team=" + team.toString());
		System.out.println("team.id=" + team.id);
		System.out.println("team.name=" + team.name);
		
		Meeting item = Meeting.find.byId(meetingId);
		GamebetUser user = getLoggedInUser();
		return ok(views.html.meeting.statistics.render(user, item, team));
	}
	
	public static Map<GamebetUser, UserStatistics> getStatistics(Long meetingId) {
		Meeting meeting = Meeting.find.byId(meetingId);
		
		Map<GamebetUser, UserStatistics> statistics = new HashMap<GamebetUser, UserStatistics>();
		for(GamebetUser u : meeting.members) {
			statistics.put(u, new UserStatistics());
		}
		
		for(GamebetUser user : meeting.members) {
			UserStatistics stat = statistics.get(user); 
			for(Gameday day : meeting.getSortedGamedays()) {
				Integer gamedayPoints = 0;
				
				for(Game game : day.games) {
					models.Result gameResult = game.result;
					if(gameResult != null) {
						Tip gameTip = Tip.findTip(game.id, user.id);
						if(gameTip != null) {
							Integer winner = gameResult.home - gameResult.away; 
							Integer tipWinner = gameTip.home - gameTip.away;
							
							Integer pointsForGame = gameTip.getPoints(gameResult);
							stat.points += pointsForGame;
							switch(pointsForGame) {
							case 3:
									stat.correctTips += 1;
									stat.correctGoalDifferenceTips += 1;
									stat.correctTendenceTips += 1;
									stat.onlyCorrectTipsPoints += 1;
									stat.onlyCorrectGoalDifferenceTipsPoints += 1;
									stat.onlyCorrectTendenceTipsPoints += 1;
								break;
							case 2:
									stat.correctGoalDifferenceTips += 1;
									stat.correctTendenceTips += 1;
									stat.onlyCorrectGoalDifferenceTipsPoints += 1;
									stat.onlyCorrectTendenceTipsPoints += 1;
								break;
							case 1:
									stat.correctTendenceTips += 1;
									stat.onlyCorrectTendenceTipsPoints += 1;
								break;
							default:
								break;
							}
							if(pointsForGame > 0) {
								if(winner > 0 && tipWinner > 0) {
									stat.homeTeamWins += 1;
									stat.addPointsForTeam(game.home.id, pointsForGame);
								} else {
									if(winner < 0 && tipWinner < 0) {
										stat.awayTeamWins += 1;
										stat.addPointsForTeam(game.away.id, pointsForGame);
									} else {
										stat.tieWins += 1;
										stat.addPointsForTeam(game.home.id, pointsForGame);
										stat.addPointsForTeam(game.away.id, pointsForGame);
									}
								}
							}
							gamedayPoints += pointsForGame;
						}
					}
					
				}
				stat.pointsPerGameday.add(gamedayPoints);
			}
			statistics.put(user, stat);
		}
		return statistics;
	}
}
 