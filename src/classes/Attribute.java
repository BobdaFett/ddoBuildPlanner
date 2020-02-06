package classes;

/**
 * Attribute class. Allows for the creation of the Attribute object.
 */

import java.util.List;

import util.string;

public class Attribute {

	private String name; // ID
	private String type;
	private double value;

	public List<String> checks; // List of checks required before ability is given
	// TODO Requirement Class

	/**
	 * Blank constructor.
	 */
	public Attribute() {
		name = "";
		type = "";
	}

	/**
	 * Create an Attribute with a name, type, and value.
	 * 
	 * @param name
	 * @param type
	 * @param value
	 */
	public Attribute(String name, String type, double value) {
		this.name = name;
		this.type = type;
		this.value = value;
	}

	/**
	 * Copy an existing Attribute.
	 * 
	 * @param a
	 */
	public Attribute(Attribute a) { // copy an existing Attribute.
		this.name = a.getName();
		this.type = a.getType();
		this.value = a.getValue();
	}

	/**
	 * Returns the name of the Attribute.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the Attribute.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the type of the Attribute.
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of the Attribute.
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the value of the Attribute.
	 * 
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value of the Attribute.
	 * 
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Returns the list of checks to aquire the Attribute.
	 * 
	 * @return
	 */
	public List<String> getChecks() {
		return checks;
	}

	/**
	 * Sets the checks of the Attribute.
	 * 
	 * @param checks
	 */
	public void setChecks(List<String> checks) {
		this.checks = checks;
	}

	/**
	 * Adds a check to the existing list of checks.
	 * 
	 * @param check
	 */
	public void addCheck(String check) {
		if(!this.checks.contains(check)) this.checks.add(check);
	}

	/**
	 * Removes the indicated check of the Attribute.
	 * 
	 * @param check
	 */
	public void removeCheck(String check) {
		if(this.checks.contains(check)) this.checks.remove(check);
	}

	/**
	 * Returns the variables of the Attribute in string form.
	 */
	public String toString() {
		return string.properTitle(value + " " + type + " " + name);
	}
}
