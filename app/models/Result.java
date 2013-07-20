/**
 * 
 */
package models;

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
public class Result extends Model {

	/**
	 * Generated serial version field
	 */
	private static final long serialVersionUID = 7086935298781868262L;

	@Id
	@GeneratedValue
	public Long id;
	@Required
	public Integer home;
	@Required
	public Integer away;
	
	@OneToOne
	@Required
	public Game game;
	
	/**
	 * Finder object to search database for result
	 * objects
	 */
	public static Finder<Long, Result> find = 
			new Finder<Long, Result>(Long.class, Result.class);

	@Override
	public String toString() {
		return "Result [id=" + id + ", home=" + home + ", away=" + away + "]";
	}


}
