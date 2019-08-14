package webCrawlerSimulator;

/**
 * This class is an Exception class for representing the Exceptions 
 * caused using or trying to operate faulty on an empty Queue in a 
 * specific situation in the program.This  Exception type is a derived
 * class from {@link Exception}because this Exception (checked 
 * Exception) must be caught explicitly.
 * 
 * 
 * @author vpariz01
 * @version 1.0
 */
public class QueueIsEmptyException extends Exception{
	
	public static final String DEFAULT_MESSAGE="QueueIsEmptyException was thrown";
	
	/**
	 * This constructor is for creating an Exception object type of  
	 * @see QueueIsEmptyException using the constructor of the 
	 * ancestor class {@link Exception} and using a default message
	 * {@link QueueIsEmptyException#DEFAULT_MESSAGE} for error message.
	 * 
	 * @param void
	 * @return void
	 */
	public QueueIsEmptyException()
	{
		super(DEFAULT_MESSAGE);
		
	}
	
	
	/**
	 * This constructor is for creating an Exception object type of by 
	 * @see QueueIsEmptyException using the constructor of the 
	 * ancestor class {@link Exception}.It takes a String 
	 * as an error message.If that message is null then the default
	 * message is used for an error message
	 * {@link QueueIsEmptyException#DEFAULT_MESSAGE}.
	 * 
	 * @param message a String representing an error message
	 * @return void
	 */
	public QueueIsEmptyException(String message)
	{
		super( (message==null) ? DEFAULT_MESSAGE : message );	
	}	
	
}
