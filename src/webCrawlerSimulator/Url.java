package webCrawlerSimulator;

/**
 * This class represents a URL.This class is used for creating objects
 * type of this class which can represent a URL.The objects of this class
 * have three attributes :
 * a) The String name of the URL which represents
 * b) The priorityLevel which is stored inside it 
*     as an integer
 * c) And the BackQueue id which is an ID which indicates
 * 	  in what BackQueue this URL will be stored
 * 
 * @author Valentinos Pariza
 *
 */
public class Url {
	
	// the name of the URL
	private String nameOfURL;
	
	private int priorityLevel;			 // priority level to specify the frontQueue to be 
										 // placed(takes numbers from 1-F where F 
										 // is the number of frontQueues)
	private int backQueueDomainID;       // a number from 0 to B-1 where B is 
										 // the number of backQueues
	

	/**
	 * 
	 * @param nameOfUrl the name of this URL
	 * @param priorityLevel	 the priority level of this URL
	 * @param backQueueDomainID		the backQueue id where this URL belongs to
	 * @throws NegativeNumberException if the priorityLevel
	 * 		  or the BackQueueDomainId are negative numbers
	 * @throws NullPointerException if the name of the URL is null
	 */
	public Url(String nameOfUrl,int priorityLevel,int backQueueDomainID) 
			throws NegativeNumberException,NullPointerException
	{
		if(nameOfUrl==null || nameOfUrl.equals(""))
			throw new NullPointerException("Null name of the URL");
		
		if(priorityLevel<0)
			throw new NegativeNumberException("Priority level is less than zero.");
		
		if(backQueueDomainID<0)
			throw new NegativeNumberException("Identification code for specifying the"
						+" backQueueDomain is less than zero.");
		
		this.nameOfURL=nameOfUrl;
		this.priorityLevel=priorityLevel;
		this.backQueueDomainID=backQueueDomainID;
		
	}
	
	
	/**
	 * This method is a getter method.Returns the
	 * priority level of this URL.
	 * 
	 * @return the priority level of this URL
	 */
	public int getPriorityLevel()
	{
		return this.priorityLevel;
	}
	
	
	/**
	 * This method is a getter method.Returns the
	 * back queue id of this URL.
	 * 
	 * @return the back queue id of this URL
	 */
	public int getBackQueueDomainID()
	{
		return this.backQueueDomainID;
	}
	
	
	/**
	 * This method is a getter method.Returns the
	 * the name of this URL.
	 * 
	 * @return the name of the URL
	 */
	public String getNameOfURL()
	{
		return this.nameOfURL;
	}
	
	
	/**
	 * This method tries to set the name of the URL to the
	 * one that indicated by parameter and returns whether
	 * it was changed or not.
	 * 
	 * @param newName the new name of the URL to be changed to
	 * @return true if it has been changed or false otherwise
	 */
	public boolean setNameOfURL(String newName)
	{
		if(newName==null)
			return false;
		
		this.nameOfURL=newName;
		return true;
	}
	
	
	/**
	 * This method tries to set the priority level of the 
	 * URL to the one that indicated by parameter and
	 *  returns whether it was changed or not.
	 * 
	 * @param newPriorityLevel the new priority level of the URL to be changed to
	 * @return true if it has been changed or false otherwise
	 */
	public boolean setPriorityLevel(int newPriorityLevel)
	{
		if(newPriorityLevel<0)
			return false;
		
		this.priorityLevel=newPriorityLevel;
		
		return true;
	}
	
	
	/**
	 * This method tries to set the back Queue id 
	 * where this URL belongs to of the URL to the
	 *  one that indicated by parameter and
	 *  returns whether it was changed or not.
	 * 
	 * @param newBackQueueDomainID the new back queue 
	 * id of the object to be changed to
	 * @return true if it has been changed or false otherwise
	 */
	public boolean setBackQueueDomainID(int newBackQueueDomainID)
	{
		if(newBackQueueDomainID<0)
			return false;
			
		this.backQueueDomainID=newBackQueueDomainID;
		
		return true;
	}
	
	
	/**
	 * This method checks  if the URL name of the 
	 * @see Url object(which invokes the method) 
	 * is an extension(hyperlink) of the object type of  
	 * @see Url given as a parameter
	 * 
	 * @param anUrl an object of type @see Url
	 * @return true if the object (type of Url) 
	 * which invokes the method comes from the URL 
	 * given as a parameter
	 */
	public boolean comesFromThisURL(Url anUrl)   // Checks whether the URL which invokes the method is a  
	{												   // hyperlink of the the URL which is taken as a parameter
		if(anUrl==null)
			return false;
		
		return this.getNameOfURL().indexOf(anUrl.getNameOfURL())==0;
		
			
	}
	
	/**
	 * Returns a String representation of the 
	 * object invoking this method
	 * 
	 * @return a String representation of the
	 *  object invoking this method
	 */
	public String toString()
	{
		return "URL name : "+this.nameOfURL+",  PriorityLevel : "
	    +this.priorityLevel+",   BackQueueDomain ID : "
		+this.backQueueDomainID;
	}
	
	
	/**
	 * Compares with equality the object that invokes
	 * the method and the object given as a parameter
	 * 
	 * @param otherObject an object which this method 
	 * tries to find out if actually is of the same 
	 * type and has the same attributes with the 
	 * object calling this method
	 * 
	 * returns true if they are equal or false otherwise
	 */
	public boolean equals(Object otherObject)
	{
		if(otherObject==null || otherObject.getClass()!=getClass())
			return false;
		
					
		Url aUrl=(Url)otherObject;
		
		return nameOfURL.equals(aUrl.nameOfURL) &&
			   priorityLevel==aUrl.priorityLevel &&
			   backQueueDomainID==aUrl.backQueueDomainID;	
					
	}
	
}
