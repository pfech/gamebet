/**
 * 
 */
package models;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author pascal
 *
 */
@Entity
public class Game extends Model {

	/**
	 * Generated serial version field
	 */
	private static final long serialVersionUID = -2404712939784986362L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	@ManyToOne
	public Team home;
	
	@Required
	@ManyToOne
	public Team away;
	
	@Nullable
	@OneToOne(mappedBy="game")
	public Result result = null;

	@Required
	@ManyToOne
	public Gameday gameday;
	
	
	
	public String getEncounterString() {
		StringBuilder sb = new StringBuilder();
		sb.append(home.name);
		sb.append(" : ");
		sb.append(away.name);
		sb.append("\t");
		if(result == null) {
			sb.append("- : -");
		} else {
			sb.append(result.home);
			sb.append(" : ");
			sb.append(result.away);
		}		
		return sb.toString();
	}
	
	/**
	 * Finder object to search database for game
	 * objects
	 */
	public static Finder<Long, Game> find = 
			new Finder<Long, Game>(Long.class, Game.class);
}
