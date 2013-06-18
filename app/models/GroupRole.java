/**
 * 
 */
package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import be.objectify.deadbolt.core.models.Role;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * @author pfechner
 *
 */
@Entity
public class GroupRole extends Model implements Role {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 2346015770443581561L;

	@Id
	@GeneratedValue
	public Long id;
	
	@Required
	public String name;
	
	@Override
	public String getName() {
		return this.name;
	}

}
