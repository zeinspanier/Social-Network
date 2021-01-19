package application;

import java.util.Set;

/**
 * This class is the implementation of GraphADT. This allows the user to 
 * add nodes and add edges between nodes. This also can give the user information
 * about the graph.
 * 
 * @author A Team 38
 *
 */
public interface GraphADT {

	/**
	 * This method adds an edge between two objects of type Person
	 * 
	 * 
	 *@Param: one, first person
	 *@Param: two, second person  
	 *@returns: true if edge was added and false otherwise
	 */
	public boolean addEdge(Person one,  Person two);
	
	/**
	 *This method removes an edge between two objects of type Person.
	 *
	 *
	 *@Param: one, first person
	 *@Param: two, second person  
	 *@returns: true if edge was added and false otherwise
	 */
	public boolean removeEdge(Person one,  Person two);
	
	/**
	 * This method adds a person to the graph with an empty list of edges 
	 * 
	 *@Param: person, person to add
	 *@returns: true if edge was added and false otherwise
	 */
	public boolean addNode(Person person);
	
	/**
	 * This method removes a person to the graph 
	 * 
	 *@Param: person, person to remove
	 *@returns: true if edge was added and false otherwise
	 */
	public boolean removeNode(Person person);
	
	/**
	 *This method finds a set of neighbors for a given person
	 *
	 *@param person, person to find neighbors of
	 *@return: a set of objects of type Person
	 */
	public Set<Person> getNeighbors(Person person);
	
	/**
	 * This method finds a person with a given name
	 * 
	 * @param: the name of the person to find
	 * @Return: the Person with the given name, null if cannot find person
	 */
	public Person getNode(String name);
	
	/**
	 *This method returns a set of all people in the graph
	 *
	 *@return: a set of all nodes in the graph
	 */
	public Set<Person> getAllNodes();
}
