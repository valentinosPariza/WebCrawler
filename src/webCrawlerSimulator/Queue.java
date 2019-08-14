package webCrawlerSimulator;


/**
 * This interface is an interface for implementing the 
 * functions-operations of a Queue, on a Class(Type 
 * representing a Queue and implements this interface Queue<E>).
 * 
 * @author vpariz01
 * @version 1.0
 * @param <E> a generic type which is specified in the creation of variables
 * 			  of type Queue<E> or by objects which implement this interface 
 * 			  Queue<E> 
 */
public interface Queue<E> 
{
		/**
		 * Unimplemented method which returns (when implemented)
		 *  the size of a Queue.
		 * 
		 * @return the size of the Queue
		 */
		public int size(); 	
		
		
		/**
		 * Unimplemented method which checks(when implemented)
		 *  whether the implemented Queue is empty or not.
		 * 
		 * @return true if is empty or false otherwise
		 */
		public boolean isEmpty(); 
		
		
		/**
		 * Unimplemented method which returns(when implemented)
		 * the first element in the queue that got in and
		 * hasn't been removed yet(This method shouldn't delete
		 * that element from the queue) .
		 * 
		 * @throws QueueIsEmptyException if the Queue is empty
		 * 			and don't have inside any elements
		 * 
		 * @return an object type of E which is the front object
		 * 		of the queue(the next element to be removed
		 * 		when indicated)
		 */
		public   E front() throws QueueIsEmptyException; 	
		
		
		
		/**
		 * Unimplemented method which puts(when implemented)
		 * the object given as parameter in the queue(probably
		 *  in a field "data" of a Node object)
		 * 
		 * @param obj an object type of E(parameterised Type) which is 
		 * 			going to be put before last node o the queue
		 * 
		 * @return void
		 */
		public void enqueue (E  obj);
		
		
		
		/**
		 * Unimplemented method which returns(when implemented)
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
		public   E dequeue() throws QueueIsEmptyException; 	
		
		
		/**
		 * Unimplemented method which (when implemented) clears a Queue
		 * 
		 * 
		 * @return void
		 */
		public void makeEmpty();
		
	
}
