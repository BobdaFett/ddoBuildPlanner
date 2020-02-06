package fxTabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;

/**
 * This compiles all of the Tabs into one object.
 * 
 * @author LittleTealeaf
 */

public class Tabs {

	public static ObservableList<Tab> getMainTabs() {
		ObservableList<Tab> ret = FXCollections.observableArrayList();
		ret.addAll(Tomes.getTab(), Leveling.getTab(), Enhancements.getTab(), Gearsets.getTab(), Stats.getTab(), debug.debugTab.getTab());
		return ret;
	}
}
