package classes;

import java.util.ArrayList;
import java.util.List;

import classes.Gearset.GearsetExport;
import classes.Item.ItemExport;

public class Build {

	private List<Gearset> gearsets;
	private Gearset currentGearset;

	public Build() {
		gearsets = new ArrayList<Gearset>();
	}

	public List<Gearset> getGearsets() {
		return this.gearsets;
	}

	public void setGearsets(List<Gearset> gearsets) {
		this.gearsets = gearsets;
	}

	public void addGearset(Gearset gearset) {
		if(!this.gearsets.contains(gearset)) this.gearsets.add(gearset);
	}

	public void removeGearset(Gearset gearset) {
		this.gearsets.remove(gearset);
	}

	public Gearset getCurrentGearset() {
		return this.currentGearset;
	}

	public void setCurrentGearset(Gearset currentGearset) {
		addGearset(currentGearset);
		this.currentGearset = currentGearset;
	}
	
	
	public static class BuildExport {
		private Build build;
		private List<ItemExport> items;
		private List<Enchantment> enchantments;
		
		public BuildExport(Build build) {
			this.build = build;
			items = new ArrayList<ItemExport>();
			enchantments = new ArrayList<Enchantment>();
			
			for(Gearset g : build.gearsets) {
				GearsetExport ge = g.export();
				enchantments.addAll(ge.getEnchantments());
				items.addAll(ge.getItems());
			}

		}
		
		public Build importBuild() {
			Enchantments.addEnchantments(enchantments);
			for(ItemExport i : items) i.importItem();
			return build;
		}
	}
}
