///////////////////////////////////////////////////////////////////////////////
//
//                   ALL STUDENTS COMPLETE THESE SECTIONS
//
// Title:            SocialNetwork.java
//
// Semester:         Fall 2019
//
// Author:           A Team 38
//
// Instructor:       Debra Deppler
//
//////////////////////////// 80 columns wide //////////////////////////////////
package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * This class uses the SocialNetworkADT to implement a data structure that
 * acts as a social network for users
 *
 * @author Ateam 38
 */
public class SocialNetwork implements SocialNetworkADT {

	private Graph socialNetwork;
	private String saveActions = "";
	private int numPeople;
	private Person centralUser = null;

	/**
	 * This method returns the number of people currently in the social network
	 *
	 * @return int number of people
	 */
	public int numPeople() {
		return numPeople;
	}

	/**
	 * This method returns the current central user in the social network
	 *
	 * @return Person current central user in the social network
	 */
	public Person getCentralUser() {
		return centralUser;
	}

	/**
	 * Default constructor to create a new empty social network
	 *
	 */
	public SocialNetwork() {
		socialNetwork = new Graph();
		numPeople = 0;
	}

	/**
	 * This method creates a friendship between two users in the social
	 * network. If either user is not present, they are added to the social network
	 *
	 * @param name1 name of the first user in the friendship
	 * @param name2 name of the second user in the friendship
	 * @return boolean true if friendship is added successfully, false if not
	 */
	@Override
	public boolean addFriends(String name1, String name2) {
		if (name1 == null || name2 == null) {
			return false;
		}

		boolean returnedValue = false;

		//Check if users are already in the network
		Person person1 = socialNetwork.getNode(name1);
		Person person2 = socialNetwork.getNode(name2);

		if (person1 != null && person2 != null) { //Both users already present
			returnedValue = socialNetwork.addEdge(person1, person2);
		} else if ((person1 != null && person2 == null)) {//User 2 not present
			if(addUser(name2)) {
				returnedValue = socialNetwork.addEdge(socialNetwork.getNode(name2), person1);
			}
		} else if (person1 == null && person2 != null) {//User 1 not present
			if(addUser(name1)) {
				returnedValue = socialNetwork.addEdge(socialNetwork.getNode(name1), person2);
			}
		}else if( person1 == null && person2 == null) {//Both users not in network
			if(addUser(name1) && addUser(name2)) {
				returnedValue = socialNetwork.addEdge(socialNetwork.getNode(name1),
						socialNetwork.getNode(name2));
			}
		}

		if(returnedValue) { //Update log file
			String addFriend = "a " + name1 + " " + name2 + "\n";
			saveActions = saveActions + addFriend;
		}

		return returnedValue;
	}

	/**
	 * This method removes a friendship between two users in the social
	 * network.
	 *
	 * @param name1 name of the first user in the friendship
	 * @param name2 name of the second user in the friendship
	 * @return boolean true if friendship is removed successfully, false if not
	 */
	@Override
	public boolean removeFriends(String name1, String name2) {

		//Check if users are already in the network
		Person person1 = socialNetwork.getNode(name1);
		Person person2 = socialNetwork.getNode(name2);

		boolean removedFriends = socialNetwork.removeEdge(person1, person2);

		if(removedFriends) {
			String removeFriend = "r " + name1 + " " + name2 + "\n";
			saveActions = saveActions + removeFriend;
		}

		return removedFriends;
	}

	/**
	 * This method sets the new central of the user of the graph
	 *
	 * @param newCentralUser name of the new central user in the graph
	 */
	public void setCentralUser(String newCentralUser) {
		if(socialNetwork.getNode(newCentralUser) != null) {
			System.out.println("New central user is " + newCentralUser);
			centralUser = socialNetwork.getNode(newCentralUser);
			String sUser = "s " + newCentralUser + "\n";
			saveActions = saveActions + sUser;
		}
	}

	/**
	 * This method adds a new user to the social network
	 *
	 * @param name of the new user to add to the network
	 * @return boolean true if successfully added, false if not
	 */
	@Override
	public boolean addUser(String name) {
		Person person = new Person(name);

		boolean addedUser = socialNetwork.addNode(person);

		if(addedUser) {
			String aUser = "a " + name + "\n";
			saveActions = saveActions + aUser;
			System.out.println(name);
			if(numPeople == 0) {
				centralUser = person;
			}
			numPeople++;
		}
		return addedUser;
	}

	/**
	 * This method removes a user from the social network
	 *
	 * @param name of the user to remove
	 * @return boolean true if successfully removed, false if not
	 */
	@Override
	public boolean removeUser(String name) {
		Person person = socialNetwork.getNode(name);

		if(person.equals(centralUser)) { //Set central user to null if removed
			centralUser = null;
		}

		boolean removedUser =  socialNetwork.removeNode(person);

		if(removedUser) {
			String rUser = "r " + name + "\n";
			saveActions = saveActions + rUser;
			numPeople--;
		}

		return removedUser;
	}

	/**
	 * This method gets a user's friends list
	 *
	 * @param name the user to get the friends list of
	 * @return Set<Person> the user's friends list
	 */
	@Override
	public Set<Person> getFriends(String name) {
		Person person = socialNetwork.getNode(name);
		return socialNetwork.getNeighbors(person);
	}

	/**
	 * This method gets the mutual users between two friends
	 *
	 * @param name1 first user to get mutual friends of
	 * @param name2 second user to get mutual friends of
	 * @return Set<Person> a set of mutual friends between the users
	 */
	@Override
	public Set<Person> getMutualFriends(String name1, String name2) {
		Person person1 = socialNetwork.getNode(name1);
		Person person2 = socialNetwork.getNode(name2);

		//Get the friends lists of each user
		Set<Person> person1Friends = socialNetwork.getNeighbors(person1);
		Set<Person> person2Friends = socialNetwork.getNeighbors(person2);

		//Store mutual friends in a set
		Set<Person> mutualFriends = new HashSet<Person>();

		boolean person1FriendsLonger = false;
		Set<Person> longerFriendsList;
		Set<Person> shorterFriendsList;

		//Determine which friend has a longer friends list
		if (person1Friends.size() >= person2Friends.size()) {
			person1FriendsLonger = true;
		}

		if (person1FriendsLonger) {
			longerFriendsList = person1Friends;
			shorterFriendsList = person2Friends;
		} else {
			longerFriendsList = person2Friends;
			shorterFriendsList = person1Friends;
		}

		//Compare all friends in longer list to the friends in the shorter
		for (Person currPerson : longerFriendsList) {
			if (shorterFriendsList.contains(currPerson)) {
				mutualFriends.add(currPerson);
			}
		}

		return mutualFriends;
	}

	/**
	 * This method gets the shortest path between two users in the network
	 *
	 * @param name1 first user to get shortest path between
	 * @param name2 second user to get shortest path between
	 * @return List<Person> shortest path between the two users
	 */
	@Override
	public List<Person> getShortestPath(String name1, String name2) {

		Person person1 = socialNetwork.getNode(name1);
		Person person2 = socialNetwork.getNode(name2);

		Set<Person> shortestPath = new HashSet<Person>();

		//Hashmap for storing each person with a reference to the person
		//that came before them in the path
		HashMap<Person, Person> allPeople = new HashMap<Person, Person>();
		Set<Person> allNodes = socialNetwork.getAllNodes();
		Set<Person> visitedNodes = new HashSet<Person>();

		allPeople.put(person1, null);
		visitedNodes.add(person1);

		//Use a queue to implement a BFS
		Queue bfsQueue = new LinkedList<Person>();
		bfsQueue.add(person1);

		Person friend;

		while(bfsQueue.size() != 0) {
			friend = (Person) bfsQueue.poll(); //Get first person in queue
			//Add all of the person's friends to the queue if not visited yet
			for(Person currentPerson : socialNetwork.getNeighbors(friend)) {
				if(!visitedNodes.contains(currentPerson)) {
					visitedNodes.add(currentPerson); //Visit current friend
					//Keep reference of the previous person in the path in hashmap
					allPeople.put(currentPerson, friend);
					bfsQueue.add(currentPerson);
				}
			}
		}

		//Back track from the end person to the starting person in order
		//to create a path back
		Person current = person2;
		ArrayList<Person> reverseSet = new ArrayList<Person>();

		while(current != null) {
			reverseSet.add(current);
			current = allPeople.get(current); //Get the previous person from hashmap
		}
		Collections.reverse(reverseSet);

		return reverseSet;
	}

	/**
	 * This method gets the number of connected components in the network
	 *
	 * @return int number of connected components in social network
	 */
	@Override
	public int getConnectedComponents() {

		int count = 0;

		Set<Person> visitedSet = new HashSet<Person>(); //Keep track of visited
		Set<Person> allPeople = socialNetwork.getAllNodes();

		//Loop through all people in the network and do a depth first search
		//at each unvisited person
		for(Person currPerson: allPeople) {
			if(!visitedSet.contains(currPerson)) {
				//Visit all nodes connected to the current node
				dfs(currPerson, visitedSet);
				count++; //Each time a dfs must be called, another component was found
			}
		}
		return count;

	}

	/**
	 * This method implements a Depth First Search, used when finding the number
	 * of connected components in the network
	 *
	 * @param currPerson current person in depth first search
	 * @param visitedSet set of people who have been visited so far
	 */
	private void dfs(Person currPerson, Set<Person> visitedSet) {
		visitedSet.add(currPerson);//Visit current person
		for(Person friend: getFriends(currPerson.getName())) {
			if(!visitedSet.contains(friend)) { //DFS all unvisited friends
				dfs(friend, visitedSet);
			}
		}

	}

	/**
	 * This method loads a text file and reads through it to create a network
	 *
	 * @param file file to load from
	 * @param boolean true if file successfully read, false if not
	 */

	@Override
	public boolean loadFromFile(File file) {
		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) { //Read file line by line
				String command = sc.nextLine();
				command = command.trim();
				String[] command2 = command.split(" ");

				if(command2[0].equals("a")) { //Add 1 user
					if(command2.length == 2) {
						addUser(command2[1]);
					}
					if(command2.length == 3) {//Add a friendship
						addFriends(command2[1], command2[2]);
					}
				}
				else if(command2[0].equals("r")) {//Remove a user
					if(command2.length == 2) {
						removeUser(command2[1]);
					}
					if(command2.length == 3) {//Remove a friendship
						removeFriends(command2[1], command2[2]);
					}
				}
				else if(command2[0].equals("s")) {//Set central user
					setCentralUser(new Person(command2[1]).getName());
				}
				else {
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * This method saves a log file of all previous actions to a text file
	 *
	 * @param file file to save to
	 * @param boolean true if file successfully saved, false if not
	 */

	@Override
	public boolean saveToFile(File file) {

		try {
			FileWriter fw = new FileWriter(file);
			fw.write(saveActions); //Write the saveActions string to a file
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This method is used to check if a duplicate user exists in the network
	 *
	 * @param name name of the user to check
	 * @param boolean true if duplicate user exists, false if not
	 */
	public boolean duplicate(String name) {

		Set<Person> allPeople = new HashSet<Person>();
		allPeople = socialNetwork.getAllNodes();

		for(Person currPerson : allPeople) {
			if(currPerson.getName().equals(name)) {
				return true;
			}

		}

		return false;

	}

}
