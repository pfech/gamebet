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

import play.db.ebean.Model;

/**
 * @author pascal
 *
 */
@Entity
public class Team extends Model {

	/**
	 * Generated serial version field
	 */
	private static final long serialVersionUID = -1346623339379020510L;

	@Id
	@GeneratedValue
	public Long id;
	
	public String name;

	@ManyToMany
	public List<Meeting> meetings = new ArrayList<Meeting>();
	
	/**
	 * Finder object to search database for Team
	 * objects
	 */
	public static Finder<Long, Team> find = 
			new Finder<Long, Team>(Long.class, Team.class);

}
