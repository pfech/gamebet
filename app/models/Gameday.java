/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author pascal
 *
 */
@Entity
public class Gameday extends Model {

	/**
	 * Generated serial version field
	 */
	private static final long serialVersionUID = -5225734147417680155L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	public String name;
	
	@OneToMany(mappedBy="gameday")
	public List<Game> games = new ArrayList<Game>();
	
	@Required
	@ManyToOne
	public Meeting meeting;
	
	/**
	 * Finder object to search database for game
	 * objects
	 */
	public static Finder<Long, Gameday> find = 
			new Finder<Long, Gameday>(Long.class, Gameday.class);

}
