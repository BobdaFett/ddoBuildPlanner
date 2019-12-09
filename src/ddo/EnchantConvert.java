package ddo;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classes.Attribute;
import classes.Enchantment;
import util.resource;

/*
 * NOTICE TO ANY REVIEWERS OR FUTURE DEVELOPERS
 * THIS IS ANCIENT CODE AND I DONT' KNOW WHAT I WAS DOING
 * wise words:
 * 
 * if it still works
 * don't touch it
 * I don't know how it works
 * but if you're lucky you won't see this soon
 * cause this probably needs a complete makeover
 */

public class EnchantConvert {

	public static List<converter> data;

	public static void load() {
		// TODO converter reads from a system file that copies the enchantment file if it's needed
		data = new ArrayList<converter>();

		BufferedReader reader = resource.getText("enchantments.txt");

		String line;

		try {

			while((line = reader.readLine()) != null) {
				if(line.toCharArray()[0] != '#') data.add(new converter(line));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static String getName(Enchantment ench) {
		converter conv = getConverter(ench);
		return (conv != null) ? repPlaceHolders(ench, conv.getName()) : "";
	}

	public static String getDescription(Enchantment ench) {
		converter conv = getConverter(ench);
		return (conv != null) ? repPlaceHolders(ench, conv.getDescription()) : "";
	}

	public static List<Attribute> getAttributes(Enchantment ench) {
		converter conv = getConverter(ench);
		return (conv != null) ? conv.getAttributes(ench) : null;
	}

	private static converter getConverter(Enchantment ench) {
		for(converter c : data) if(c.getId().contentEquals(ench.getId())) return c;
		return null;
	}
	
	private static List<String> repPlaceHolders(Enchantment ench, List<String> texts) {
		List<String> r = new ArrayList<String>();
		for(String s : texts) r.add(repPlaceHolders(ench,s));
		return r;
	}

	private static String repPlaceHolders(Enchantment ench, String text) {
		return text.replace("<type>", ench.getType()).replace("<value>", ench.getValue()).replace("<bonus>", ench.getBonus());
	}

	public static class converter {

		private String id;
		private String name;
		private String description;
		private List<conAttribute> attrBonuses;

		public converter(String id, String name, String description) {
			this.id = id;
			this.name = name;
			this.description = description;
		}

		public converter(String line) {
			String[] data = line.split("\\");
			id = data[0];
			name = data[1];
			description = data[2];
			attrBonuses = new ArrayList<conAttribute>();

			// Parse the conAttributes
			int pos = 0;
			String temp = "";

			for(char c : data[3].toCharArray()) {

				switch (pos) {
				case 0:
					if(c == '\"') pos = 1;
					break;
				case 1:
					if(c == '\"') {
						pos = 0;
						String[] parsed = temp.split(":");
						conAttribute add = new conAttribute(parsed[0], parsed[1]);
						add.setType((parsed.length > 2) ? parsed[2] : "<type>");
						
						// Get the checks
						for(int i = 3; i < parsed.length; i++) {
							add.addCheck(parsed[i]);
						}
						attrBonuses.add(add);
						temp = "";
					} else temp += c;
					break;
				}
			}
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public List<Attribute> getAttributes(Enchantment ench) {
			List<Attribute> r = new ArrayList<Attribute>();

			for(conAttribute c : attrBonuses) r.add(c.toAttribute(ench));

			return r;
		}

		private static class conAttribute {

			private String attribute;
			private double multiplier;
			public String type;
			public List<String> checks;

			public conAttribute(String attribute, String multiplier) {
				this.attribute = attribute;
				this.multiplier = Double.parseDouble(multiplier);
				this.checks = new ArrayList<String>();
			}

			public void setType(String type) {
				this.type = type;
			}

			public void addCheck(String check) {
				checks.add(check);
			}

			public Attribute toAttribute(Enchantment ench) {
				Attribute r = new Attribute();
				
				r.setName(repPlaceHolders(ench,attribute));
				r.setType(repPlaceHolders(ench,type));
				r.setChecks(repPlaceHolders(ench,checks));
				r.setValue(ench.getNumericValue() * multiplier);
				
				return r;
			}
		}
	}
}
