package classes;

import java.util.ArrayList;
import java.util.List;

public class ItemExport {

	private Item item;
	private List<Enchantment> enchantments;
	private String[] icon;
	private String[] image;

	public ItemExport(Item item) {
		this.item = item;
		
		enchantments = new ArrayList<Enchantment>();
		for(Enchref ench : item.getEnchantments()) {
			enchantments.add(ench.getEnchantment());
		}
		
		if(Settings.porting.exporting.includeImages) {
			icon = Images.getImageFileContents(item.getIconUUID());
			image = Images.getImageFileContents(item.getImageUUID()); 
		}
	}

	public void importItem() {
		
		if(icon != null) {
			Images.saveImageFromContents(item.getIconUUID(), icon);
		}
	}
}
