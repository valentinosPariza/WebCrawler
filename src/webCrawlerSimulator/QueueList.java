package webCrawlerSimulator;

import java.util.Iterator;

/**
 * This class is a class for representing a Queue and its
 * hole functionality.This class implements the interface
 * {@link Iterable} in order to give the opportunity
 * for traversing all the elements in the queue
 * and also the interface  {@link Queue} in 
 * order to implement in this class all the operations
 * of a proper queue.
 * 
 * @author Valentinos Pariza
 *
 * @param <E> a generic type of this class which indicates the type of data that
 * 			  will be stored in an instance the queue of type of this class
 */
public class QueueList<E> implements Queue<E>,Iterable<E>{

	private int size=0;		// the size of the queue
	
	private Node first;	    // the node which refers to the first node of
							//  the queue(where elements are dequeued)
	private Node last;      // The node which refers the last node
							//  of the queue(where elements are enqueued)
	
	
	/**
	 * An inner class of the class  {@link QueueList}
	 * which represents the Nodes.
	 * 
	 * @author Valentinos Pariza
	 *
	 */
	private class Node
	{
		 E data;	// the data of the node
		 Node next; // the reference node of the 
		 			// current node
		
		/*A constructor of the inner class Node.
		 * Constructor for nodes of the queue class*/
		public Node()
		{
			this.data=null;
			this.next=null;
		}
		
	}
	
	
	/**
	 * An implementation of the interface {@link Iterator}
	 * in order to make this outer class @see {@link QueueList}
	 *  an iterable class.Which means to give a 
	 * functionality to this class which is associated
	 * with traversing all the elements of this queue.
	 * 
	 * @author Valentinos Pariza
	 *
	 */
	private class IterableListForQueue implements Iterator<E>
	{

		private Node currentNodeToReturn;	
		// the current node which is returned
										
		
		public IterableListForQueue()
		{
			currentNodeToReturn=first;
		}
		
		// checks whether there is a next element
		// in the queue to traverse
		@Override
		public boolean hasNext() {
			
			return currentNodeToReturn!=null;
		}

		// the next element which is being currently 
		// in the currentNodeToReturn attribute
		@Override
		public E next() 
		{
			Node returnNode=currentNodeToReturn;
			
			if(currentNodeToReturn!=null)
				currentNodeToReturn=currentNodeToReturn.next;
			else return null;
			
			return returnNode.data;
		}

	}
	
	
	/**
	 * A constructor of this class which implements
	 * the functionality of a Queue.It creates an
	 * object of this class with all its attributes
	 * to be initialized to default values.
	 * 
	 */
	public QueueList()
	{
		this.size=0;
		this.first=null;
		this.last=null;
	}
	
	
	/**
	 * Implemented method from {@link Queue} interface
	 *  which returns the size of this Queue.
	 * 
	 * @return the size of the Queue
	 */
	@Override
	public int size() {
		
		return this.size;
	}

	
	/**
	 * Implemented method from {@link Queue} which checks
	 *  whether the  Queue is empty or not.
	 * 
	 * @return true if is empty or false otherwise
	 */
	@Override
	public boolean isEmpty() {
		
		return first==null;		// if the first is null then the queue doesn't have elements to dequeue
	}

	
	
	@Override
	public E front() throws QueueIsEmptyException {
		
		if(this.isEmpty()) 
			throw new QueueIsEmptyException("Queue is empty.");
		
		else return this.first.data;
	}

	
	
	/**
	 * Implemented method from {@link Queue} which puts
	 * the object given as parameter in the queue(probably
	 *  in a field "data" of a Node object)
	 * 
	 * @param obj an object type of E(parameterized Type) which is 
	 * 			going to be put before last node o the queue
	 * 
	 * @return void
	 */
	@Override
	public void enqueue(E element) 
	{
		
		Node newNode=new Node();		// Create the new Node to place at the end of the Queue
		newNode.data=element;			// Update the data of that Node
		newNode.next=null;				// Be sure that the newNode doesn't refer to another Node
				
		if(isEmpty())				
		{
			this.last=newNode;			// if the Queue is empty then the first and
			this.first=newNode;			// the last Node(attributes) refer to the same 
										// Node
		}
		else
		{
			last.next=newNode;
			
			last=last.next;
		}
		
		this.size++;
		
	}

	
	/**
	 * Implemented method from {@link Queue} which returns
	 * the first element in the queue that got in earliest and
	 * hasn't been removed yet and also delete that element
	 * from the queue .
	 * 
	 * @throws QueueIsEmptyException   if the Queue is empty
	 * 			and don't have inside any elements
	 * 
	 * @return  an object type of E which is the front object
	 * 			of the queue(the next element to be removed
	 * 			when indicated)
	 */	
	@Override
	public E dequeue() throws QueueIsEmptyException {
		
		if(isEmpty())
			throw new QueueIsEmptyException("Queue is empty.");
		
			Node aNode=this.first;
				
			first=this.first.next;
			
			if(this.first==null)  // It means that there aren't any elements in the queue(queue is Empty)
			{
				this.last=null;   // It means that before removing it there was an element in the queue,
			}					  // where the first and last (Nodes) where referring to the same Node
								  // After removing the first Node ,there is still the reference to first
								   // Node which the variable last refers to.So we clear the last variable.
				
			this.size--;
			
			return aNode.data;
		
	}

	

	/**
	 * Implemented method from {@link Queue} which
	 *  clears a Queue.
	 * 
	 * @return void
	 */
	@Override
	public void makeEmpty()
	{								// Clear the Queue in order 
		this.size=0;				// to have no elements inside
		this.first=null;
		this.last=null;
		
	}


	
	/**
	 * This method returns an object type of {@link Iterator<E>}
	 * which can be used to traverse all the elements of this Queue.
	 * 
	 * @return an object type of {@link Iterator<E>}
	 * 		which cane be used to traverse all the
	 * 		elements of this Queue
	 */
	@Override
	public Iterator<E> iterator() {
		
		return new IterableListForQueue();
	}

	
	
}
