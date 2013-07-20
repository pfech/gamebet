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
 * @author pfechner
 *
 */
@Entity
public class Password extends Model {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 1790155822215409826L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	@OneToOne
	public GamebetUser user;
	
	@Required
	public String password;
	
	
	private static Finder<Long, Password> find = 
			new Finder<Long, Password>(Long.class, Password.class);


	public static Password findByUserId(Long id2) {
		return find.fetch("user", "id").where().eq("user.id", id2).findUnique();
	}
}
