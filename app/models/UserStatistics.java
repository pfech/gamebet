/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pasca_000
 *
 */
public class UserStatistics {

	public Integer points = 0;
	
	public Integer onlyCorrectTipsPoints = 0;
	
	public Integer onlyCorrectGoalDifferenceTipsPoints = 0;
	
	public Integer onlyCorrectTendenceTipsPoints = 0;
	
	public Integer correctTips = 0;
	
	public Integer correctGoalDifferenceTips = 0;
	
	public Integer correctTendenceTips = 0;
	
	public Integer homeTeamWins = 0;
	
	public Integer homeTeamWinsTips = 0;
	
	public Integer awayTeamWins = 0;
	
	public Integer awayTeamWinsTips = 0;
	
	public Integer tieWins = 0;
	
	public Integer tieWinsTips = 0;
	
	public List<Integer> pointsPerGameday = new ArrayList<Integer>();
	
	public Map<Long, Integer> pointsForTeam = new HashMap<Long, Integer>();
	
	
	public void addPointsForTeam(Long teamId, Integer points) {
		if(!pointsForTeam.containsKey(teamId)) {
			pointsForTeam.put(teamId, points);
		} else {
			pointsForTeam.put(teamId, pointsForTeam.get(teamId) + points);
		}
	}
	
	public Double averagePointsPerGameday() {
		Integer total = 0;
		Integer totalGamedays = pointsPerGameday.size();
		if(totalGamedays == 0)	return 0.0;
		for(Integer i : pointsPerGameday)
			total += i;
		
		return (double)total / (double)totalGamedays;
	}
	
	public Integer getPointsForTeamId(Long teamId) {
		if(pointsForTeam.containsKey(teamId))
			return pointsForTeam.get(teamId);
		return 0;
	}
}
