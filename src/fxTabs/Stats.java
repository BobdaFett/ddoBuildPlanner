package fxTabs;

import javafx.scene.control.Tab;
import javafx.scene.text.Text;

/**
 * This is the window for the Stats Tab.
 * 
 * @author LittleTealeaf
 */

public class Stats {

	private static Text statText;

	public static Tab getTab() {
		Tab ret = new Tab("Stats");

		statText = new Text();

		ret.setContent(statText);
		return ret;
	}
}
