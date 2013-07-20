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
import javax.persistence.OneToMany;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;

/**
 * @author pfechner
 *
 */
@Entity
public class GamebetUser extends Model implements Subject {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = 7770800324302224529L;
	
	@Id
	@GeneratedValue
	public Long id;

	public String username;
	
	public String email;
	
	@ManyToMany
	public List<GroupRole> roles = new ArrayList<GroupRole>();
	
	@OneToMany(mappedBy="manager")
	public List<Meeting> managerOfMeetings = new ArrayList<Meeting>();
	
	@ManyToMany(mappedBy="members")
	public List<Meeting> meetings = new ArrayList<Meeting>();
	
	public String getLogin() {
		return this.email;
	}
	
	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return new ArrayList<Permission>();
	}

	@Override
	public String getIdentifier() {
		return email;
	}

	
	public static Finder<Long, GamebetUser> find = 
			new Finder<Long, GamebetUser>(Long.class, GamebetUser.class);
	
	public static GamebetUser findByLogin(String login) {
		return find.where().eq("email", login).findUnique();
	}

}
