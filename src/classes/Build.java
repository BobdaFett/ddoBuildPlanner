package classes;

/**
 * Allows for the creation of the Build object.
 */

import java.util.ArrayList;
import java.util.List;

import classes.Gearset.GearsetExport;
import classes.Item.ItemExport;

public class Build {

	private List<Gearset> gearsets; // List of all the gearsets in the Build.
	private Gearset currentGearset; // Gearset the Build is currently using.

	/**
	 * Creates a Build object with a blank Gearset.
	 */
	public Build() {
		gearsets = new ArrayList<Gearset>();
	}

	/**
	 * Returns the list of gearsets useable.
	 * 
	 * @return
	 */
	public List<Gearset> getGearsets() {
		this.gearsets.remove(null); // Cleanse gearsets
		return this.gearsets;
	}

	/**
	 * Sets the gearsets of the Build.
	 * 
	 * @param gearsets
	 */
	public void setGearsets(List<Gearset> gearsets) {
		this.gearsets = gearsets;
		this.gearsets.remove(null); // Cleanse gearsets
	}

	/**
	 * Adds to the existing list of Gearsets.
	 * 
	 * @param gearset
	 */
	public void addGearset(Gearset gearset) {
		this.gearsets.remove(null); // Cleanse gearsets
		if(!this.gearsets.contains(gearset)) this.gearsets.add(gearset);
	}

	/**
	 * Removes the indicated Gearset.
	 * 
	 * @param gearset
	 */
	public void removeGearset(Gearset gearset) {
		this.gearsets.remove(null); // Cleanse gearsets
		this.gearsets.remove(gearset);
	}

	/**
	 * Returns the current Gearset.
	 * 
	 * @return
	 */
	public Gearset getCurrentGearset() {
		return (this.currentGearset != null) ? this.currentGearset : (getGearsets().size() > 0 ? this.currentGearset = getGearsets().get(0) : null);
	}

	/**
	 * Sets the current Gearset.
	 * 
	 * @param currentGearset
	 */
	public void setCurrentGearset(Gearset currentGearset) {
		addGearset(currentGearset);
		this.currentGearset = currentGearset;
	}

	/**
	 * Allows for exporting the Build.
	 * 
	 * @author LittleTealeaf
	 */
	public static class BuildExport {

		private Build build; // Stored Build.
		private List<ItemExport> items; // list of items in Build.
		private List<Enchantment> enchantments; // list of enchantments in Build.

		/**
		 * Create a BuildExport object made from a Build object.
		 * 
		 * @param build
		 */
		public BuildExport(Build build) {
			this.build = build;
			items = new ArrayList<ItemExport>();
			enchantments = new ArrayList<Enchantment>();

			for(Gearset g : build.gearsets) {
				GearsetExport ge = g.export(); // Creates a GearExport object.
				enchantments.addAll(ge.getEnchantments()); // Adds all enchantments into the list of enchantments.
				items.addAll(ge.getItems()); // Adds all items inot the list of items.
			}

		}

		/**
		 * Returns a Build object.
		 * Basically the reverse of the BuildExport method.
		 * 
		 * @return
		 */
		public Build importBuild() {
			Enchantments.addEnchantments(enchantments); // Adds all enchantments into the Enchantments class.
			for(ItemExport i : items) i.importItem(); // Adds all items into the ItemExport class using the ItemExport.importItem() method.
			return build;
		}
	}
}
