/**
 * 
 */
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author pascal
 *
 */
@Entity
public class Tip extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7271884112894102756L;

	
	@Id
	@GeneratedValue
	public Long id;
	
	@OneToOne
	@Required
	public GamebetUser owner;
	
	@OneToOne
	@Required
	public Game game;
	
	@Required
	public Integer home;
	
	@Required
	public Integer away;
	
	@Required
	public Date lastChange;
	
	public String getTipString() {
		StringBuilder sb = new StringBuilder();
		if(home != null)
			sb.append(home.toString());
		else
			sb.append("-");
		sb.append(" : ");
		if(away != null)
			sb.append(away.toString());
		else
			sb.append("-");
		return sb.toString();
	}
	
	public static Finder<Long, Tip> find = 
			new Finder<Long, Tip>(Long.class, Tip.class);
	
	public static Tip findTip(Long gameId, Long userId) {
		return find.where().eq("game.id", gameId).eq("owner.id", userId).findUnique();
	}
	
	public static List<Tip> findByGame(Long gameId) {
		return find.where().eq("game.id", gameId).findList();
	}

	public int getPoints(models.Result result) {
		if(result == null)
			return 0;
		if(result.home.equals(home) && result.away.equals(away))
			return 3;
		int tendenz = result.home.intValue() - result.away.intValue();
		int tipTendenz = home.intValue() - away.intValue();
		if(tendenz * tipTendenz > 0)
			return 1;
		return 0;
	}
	
}
