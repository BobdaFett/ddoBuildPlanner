package wiki;

import classes.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

public class Compendium {
	
	private static final String SITE = "https://ddocompendium.com";
	
	public static Item getItem(String name) {
		Item ret = new Item();
		ret.name = name;
		
		//String displayHTML = util.getContents(SITE + "/w/" + name.replace(' ', '_'));
		String editContents = getEditContents(resUtil.getContents(SITE + "/index.php?title=" + resUtil.toURL(name) + "&action=edit"));
		
		//Set up variables if needed
		if(editContents.contains("Template:Armor") || editContents.contains("Template:Shield")) ret.armor = new Item.Armor();
		if(editContents.contains("Template:Weapon") || editContents.contains("Template:Shield")) ret.weapon = new Item.Weapon();
		
		
		for(String[] a : getItemVariables(editContents)) {
			String i = a[1].replace(" ", "");
			try {
				switch(a[0].toLowerCase()) { //TODO sort these cases
				case "icon": ret.icon = getImageURL(resUtil.toURL(a[1]) + "_Icon.png"); break;
				case "image": ret.cosmetic = getImageURL(resUtil.toURL(a[1]) + ".png"); break;
				case "minlevel": ret.minLevel = Integer.parseInt(i); break;
				case "hardness": ret.hardness = Integer.parseInt(i); break;
				case "durability": ret.durability = Integer.parseInt(i); break;
				case "material": ret.material = a[1]; break;
				case "description": ret.description = a[1]; break;
				case "enchantments": ret.enchantments = parseEnchantments(a[1]); break;
				case "weight": ret.weight = Double.parseDouble(i); break;
				//TODO add icon/image fetchers, might require a separate HTML grabber?
				//Weapon
				case "damage": ret.weapon.damage = new Dice(resUtil.parseTemplate(a[1], false)); break;
				case "damagetype": ret.weapon.damageTypes = resUtil.parseTemplate(a[1],false); break;
				case "critprofile":
					List<String> temp = resUtil.parseTemplate(a[1],false);
					ret.weapon.critRange = Integer.parseInt(temp.get(0));
					ret.weapon.critMultiplier = Integer.parseInt(temp.get(1));
					break;
				case "attackmod": ret.weapon.attackModifiers = clenseAbilities(resUtil.parseTemplate(a[1],false)); break;
				case "damagemod": ret.weapon.damageModifiers = clenseAbilities(resUtil.parseTemplate(a[1],false)); break;
				//Armor
				case "armorcheckpenalty": ret.armor.armorCheckPenalty = Integer.parseInt(i); break;
				case "spellfailure": ret.armor.spellFailure = Integer.parseInt(i.replace("%", "")); break;
				case "maxdex": ret.armor.maxDexBonus = Integer.parseInt(i); break;
				case "armorbonus": ret.armor.armorBonus = Integer.parseInt(i); break;
				case "attackpenalty": ret.armor.attackPenalty = Integer.parseInt(i); break;
				case "dr": ret.armor.damageReduction = Integer.parseInt(i); break;
				case "shiedbonus": ret.armor.armorBonus = Integer.parseInt(i); break;
				//TODO add the rest of the variables
				default: //System.out.println(a[0] + " is empty");
				}
			} catch (Exception e) {}
		}
		if(ret.icon == null) ret.icon = getImageURL(resUtil.toURL(name) + "_Icon.png");
		if(ret.cosmetic == null) ret.cosmetic = getImageURL(resUtil.toURL(name) + ".png");
		
		return ret;
	}
	
	private static String getEditContents(String HTML) { //TODO (also remove anything that's not part of the actual content!!!!)
		final String START = "name=\"wpTextbox1\">", END = "</textarea>";
		return HTML.substring(HTML.indexOf(START) + START.length(), HTML.indexOf(END));
	}
	
	private static List<String[]> getItemVariables(String editContents) {
		List<String[]> ret = new ArrayList<String[]>();
		
		String temp = "";
		int index = 0;
		for(char c : editContents.toCharArray()) {
			if(c == '|' && index == 2) {
				if(temp.contains("=")) {
					String name = temp.substring(0,temp.indexOf('='));
					String data = temp.substring(temp.indexOf('=') + 1);
					if(temp.contains(" =")) name = name.substring(0,name.length() - 1);
					if(temp.contains("= ")) data = data.substring(1);
					System.out.println(name + " " + data);
					ret.add(new String[] {name, data});
				}
				temp = "";
			} else temp+=c;
			if(c == '{') index++;
			if(c == '}') index--;
		}
		return ret;
	}

	private static List<Item.Enchantment> parseEnchantments(String attributes) {
		List<Item.Enchantment> ret = new ArrayList<Item.Enchantment>();
		
		int depth = 0;
		String temp = "";
		for(char c : attributes.toCharArray()) {
			if(c == '{') {
				depth++;
				if(depth > 2) temp+=c;
			}
			else if(c == '}') {
				depth--;
				if(depth == 1) {
					try {
						ret.add(utilComp.parseEnchantment(temp));
						temp = "";
					} catch(Exception e) {}
				} else if(depth > 1) temp+=c;
			} else if(depth >= 2) temp+=c;
		}
		
		return ret;
	}
	
	private static URL getImageURL(String imageName) {
		final String ind = "/images";
		String html = resUtil.getContents(SITE + "/w/File:" + imageName);
		if(html.contentEquals("")) return null;
		html = html.substring(html.indexOf(ind) + 1);
		html = html.substring(html.indexOf(ind));
		try {
			return new URL(SITE + html.substring(0,html.indexOf("\"")));
		} catch(Exception e) {}
		return null;
	}
	
	private static List<String> clenseAbilities(List<String> mods) {
		List<String> ret = new ArrayList<String>();
		for(String s : mods) {
			s = s.toLowerCase();
			for(String a : classes.DDOUtil.abilities) {
				s = s.replace(a.substring(0,3).toLowerCase(),a.toLowerCase());
			}
			s = s.substring(0, 1).toUpperCase() + s.substring(1);
			ret.add(s);
		}
		
		return ret;
	}
}