///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Person.java
// Semester:         Fall 2019
// Author:           A Team 38
// Instructor:       Debra Deppler
//////////////////////////// 80 columns wide //////////////////////////////////

package application;

/**
 * This class is a representation of a single person. This is used as a node
 * within the SocialNetwork class.
 */
public class Person {

	private String name;
	
	/**
	 * Creates a new Person with the specified name.
	 */
	public Person(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name of this Person.
	 */
	public String getName() {
		return this.name;
	}
}