
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;

import models.Game;
import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

/**
 * 
 */

/**
 * @author pascal
 *
 */
public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		if(Game.find.findRowCount() == 0) {
			Map<String, List<Object>> map = (Map<String, List<Object>>)Yaml.load("initial-data.yaml");
			
			Ebean.save(map.get("meetings"));
			Ebean.save(map.get("teams"));
			
			for(Object o : map.get("teams")) {
				Ebean.saveManyToManyAssociations(o, "meetings");
			}
			Ebean.save(map.get("gamedays"));
			Ebean.save(map.get("games"));
			Ebean.save(map.get("results"));
			
		}
	}
}
