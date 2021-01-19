///////////////////////////////////////////////////////////////////////////////
//
//                   ALL STUDENTS COMPLETE THESE SECTIONS
//
// Title:            SocialNetworkADT.java
//
// Semester:         Fall 2019
//
// Author:           Ateam 38
//
// Instructor:       Debra Deppler
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * An interface for a data structure that allows implementation of a social
 * network for users
 *
 * @author Ateam 38
 */
public interface SocialNetworkADT {

	/**
	 * This method adds a friendship between two users. If either user is not
	 * in the social network yet, they will be added
	 *
	 * @param name1 the name of first friend
	 * @param name2 the name of second friend
	 * @return boolean true if friendship successfully added, false if not
	 */
	public boolean addFriends(String name1, String name2);

	/**
	 * This method removes a friendship between two users.
	 *
	 * @param name1 the name of first friend
	 * @param name2 the name of second friend
	 * @return boolean true if friendship successfully removed, false if not
	 */
	public boolean removeFriends(String name1, String name2);

	/**
	 * This method adds a new user to the social network
	 *
	 * @param name the name of the user to add
	 * @return boolean true if successfully added, false if not
	 */
	public boolean addUser(String name);

	/**
	 * This method removes a user from the social network
	 *
	 * @param name the name of the user to remove
	 * @return boolean true if successfully removed, false if not
	 */
	public boolean removeUser(String name);

	/**
	 * This method returns a set made up of all friends of a specific user
	 *
	 * @param name the name of the user to get the friends list of
	 * @return Set<Person> set of user's friends
	 */
	public Set<Person> getFriends(String name);

	/**
	 * This method returns a set made up of all friends that two users share
	 *
	 * @param name1 the name of first friend
	 * @param name2 the name of second friend
	 * @return Set<Person> set of mutual friends between the users
	 */
	public Set<Person> getMutualFriends(String name1, String name2);

	/**
	 * This method returns a list of friends that shows the shortest shortest path
	 * between the start user and end user
	 *
	 * @param name1 the name of first friend
	 * @param name2 the name of second friend
	 * @return Set<Person> set of mutual friends between the users
	 */
	public List<Person> getShortestPath(String name1, String name2);

	/**
	 * This method returns the number of connected components in the social
	 * network
	 *
	 * @return int number of connected components
	 */
	public int getConnectedComponents();

	/**
	 * This method loads a set of actions from a given file
	 *
	 * @return true if file is successfully loaded, false if not
	 */
	public boolean loadFromFile(File file);

	/**
	 * This method saves all past actions to a given file
	 *
	 * @return true if file is successfully saved, false if not
	 */
	public boolean saveToFile(File file);
}
