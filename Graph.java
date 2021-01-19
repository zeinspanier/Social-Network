///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Graph.java
// Semester:         Fall 2019
// Author:           A Team 38
// Instructor:       Debra Deppler
//////////////////////////// 80 columns wide //////////////////////////////////

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is the implementation of GraphADT. This allows the user to 
 * add nodes and add edges between nodes. This also can give the user information
 * about the graph.
 * 
 * @author A Team 38
 *
 */
public class Graph implements GraphADT {

	private HashMap<Person, List<Person>> graph; // Graph of all Node
	private int order; // Number of nodes
	private int size;  // Number of Edges

	/**
	 * Graph constructor that initializes the graph.
	 */
	public Graph() {
		graph = new HashMap<Person, List<Person>>();
	}

	/**
	 * This method adds an edge between two objects of type Person
	 * 
	 * 
	 *@Param: one, first person
	 *@Param: two, second person  
	 *@returns: true if edge was added and false otherwise
	 */
	@Override
	public boolean addEdge(Person one, Person two) {

		if (one == null || two == null) {
			return false;
		} else {
			
			List<Person> person1List = graph.get(one); // Gets list of edges
			person1List.add(two);// adds person
			graph.put(one, person1List); // puts list of edges back

			List<Person> person2List = graph.get(two); // Get List of edges
			person2List.add(one); // adds person
			graph.put(two, person2List);// puts list of edges back

			++size;

			return true;
		}
	}

	/**
	 *This method removes an edge between two objects of type Person.
	 *
	 *
	 *@Param: one, first person
	 *@Param: two, second person  
	 *@returns: true if edge was added and false otherwise
	 */
	@Override
	public boolean removeEdge(Person one, Person two) {
		if (one == null || two == null) {
			return false;
		} else {
			graph.get(one).remove(two); // Gets list of edges and removes one
			graph.get(two).remove(one); // Gets list of edges and removes two

			--size;

			return true;
		}
	}

	/**
	 * This method adds a person to the graph with an empty list of edges 
	 * 
	 *@Param: person, person to add
	 *@returns: true if edge was added and false otherwise
	 */
	@Override
	public boolean addNode(Person person) {
		if (person == null) {
			return false;
		} else {
			List<Person> people = new ArrayList<Person>();
			graph.put(person, people); // adds person with no edges

			++order;

			return true;
		}
	}

	/**
	 * This method removes a person to the graph 
	 * 
	 *@Param: person, person to remove
	 *@returns: true if edge was added and false otherwise
	 */
	@Override
	public boolean removeNode(Person person) {
		// Returned boolean
		boolean removed = false;

		if (person == null) {
			return false;
		} else {

			graph.remove(person); // removes person

			// Find all references of person and removes it

			Set<Person> graphKeys = graph.keySet();

			for (Person currPerson : graphKeys) {
				List<Person> temp = graph.get(currPerson);
				for (int i = 0; i < temp.size(); ++i) {
					if (temp.get(i).equals(person)) {
						temp.remove(i);
						removed = true;
					}
				}
			}

			--order;

			return removed;
		}
	}

	/**
	 *This method finds a set of neighbors for a given person
	 *
	 *@param person, person to find neighbors of
	 *@return: a set of objects of type Person
	 */
	@Override
	public Set<Person> getNeighbors(Person person) {
		if (person == null) {
			return null;
		}

		else {
			List<Person> edges = graph.get(person);//Gets edges
			
			Set<Person> returnSet = new HashSet(edges);

			return returnSet;
		}
	}

	/**
	 * This method finds a person with a given name
	 * 
	 * @param: the name of the person to find
	 * @Return: the Person with the given name, null if cannot find person
	 */
	@Override
	public Person getNode(String name) {

		Set<Person> graphKeys = graph.keySet(); // gets all nodes in graph
		// Looks at each node in graph to find the person
		for (Person currPerson : graphKeys) {
			if (currPerson.getName().equals(name)) {
				return currPerson;
			}
		}
		return null;
	}

	/**
	 *This method returns a set of all people in the graph
	 *
	 *@return: a set of all nodes in the graph
	 */
	@Override
	public Set<Person> getAllNodes() {
		if (graph == null) {
			return null;
		} else {
			return graph.keySet();
		}
	}
}