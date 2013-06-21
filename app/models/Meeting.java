/**
 * 
 */
package models;

import java.util.ArrayList;
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

	@ManyToMany(mappedBy="meetings")
	public List<Team> teams = new ArrayList<Team>();
	
	@ManyToOne
	public User manager;
	
	@ManyToMany
	public List<User> members = new ArrayList<User>();
	
	/**
	 * Finder object to search database for game
	 * objects
	 */
	public static Finder<Long, Meeting> find = 
			new Finder<Long, Meeting>(Long.class, Meeting.class);

	public static Meeting fndById(Long meetingId) {
		return find.byId(meetingId);
	}

}
