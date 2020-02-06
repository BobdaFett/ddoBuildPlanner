package application;

/**
 * This class seems to simply load all of the functions from other classes.
 * It seems to be that each and every window is set in a different class.
 */

import classes.Build;
import classes.Enchantments;
import classes.Images;
import classes.Inventory;
import classes.SetBonuses;
import classes.Settings;
import debug.Debug;
import interfaces.fxMain;
import util.system;

public class Main {

	public static final String version = "0.0.1"; // version of the Build Planner being used.

	public static Build loadedBuild; // creates the build that will be worked on.

	public static void main(String[] args) {
		loadedBuild = new Build(); // uses the constructor of the Build.

		Debug.setCrashReporting(); // Makes sure that the debug class is functional.
		system.loadData(); // This allows the program to start initialization?
		Settings.loadSettings(); // That's... self-explanitory.
		Images.load(); // Loads all needed images.
		Inventory.load(); // Loads the inventory of the planned build.
		Enchantments.load(); // Loads the created enchantments (from gson?)
		SetBonuses.load(); // Loads all of the previously selected bonuses.

		fxMain.open(args); // Initializes the window and allows for all information to be displayed.
	}
}
