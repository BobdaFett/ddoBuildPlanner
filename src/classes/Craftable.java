package classes;

import java.util.ArrayList;
import java.util.List;

public class Craftable {

	private String name;
	
	private List<Attribute> choices;

	public Craftable() {
		choices = new ArrayList<Attribute>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public List<Attribute> getChoices() {
		return choices;
	}

	public Attribute getChoice(int index) {
		if(index < choices.size()) return choices.get(index);
		else return null;
	}

	public void setChoices(List<Attribute> choices) {
		this.choices = choices;
	}

	public void addChoice(Attribute choice) {
		this.choices.add(choice);
	}

	public void removeChoice(Attribute choice) {
		this.choices.remove(choice);
	}
}
