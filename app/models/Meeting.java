/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author pascal
 *
 */
@Entity
public class Meeting extends Model {

	/**
	 * Generated serial version field
	 */
	private static final long serialVersionUID = 1588093102741598231L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	public String name;
	
	@OneToMany(mappedBy="meeting")
	public List<Gameday> gamedays = new ArrayList<Gameday>();

	@OneToMany(mappedBy="meeting")
	public List<Team> teams = new ArrayList<Team>();
	
	@ManyToOne
	public GamebetUser manager;
	
	@ManyToMany
	public List<GamebetUser> members = new ArrayList<GamebetUser>();
	
	/**
	 * Finder object to search database for game
	 * objects
	 */
	public static Finder<Long, Meeting> find = 
			new Finder<Long, Meeting>(Long.class, Meeting.class);

	public static Meeting fndById(Long meetingId) {
		return find.byId(meetingId);
	}
	
	public List<Gameday> getSortedGamedays() {
		Collections.sort(gamedays, new Comparator<Gameday>() {

			@Override
			public int compare(Gameday o1, Gameday o2) {
				// TODO Auto-generated method stub
				return o1.startDate.compareTo(o2.startDate);
			}
			
		});
		return gamedays;
	}

}
