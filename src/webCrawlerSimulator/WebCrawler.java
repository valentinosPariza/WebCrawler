package webCrawlerSimulator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * This class simulates a WebCrawler and its functions-operations.
 * As indicated by the instructions of the exercise this class
 * follows all the proper steps explained in the exercise-text
 * and simulates N simulations of the WebCrawler and its functions.
 * The number N(how many simulations will be made) is given 
 * by keyboard by the user at the beginning of the program.
 * Also this program uses streams in order to read URLs from
 * files,place them into data-structures,do some operations
 * on the data in the data-structures (like analyzing the
 * URLs and putting them in front Queues,transferring URLs 
 * from the front Queues to back Queues and selecting a 
 * URL from a Back Queue indicated by a queue for recovery
 * selection) and at the end direct the output of the program
 * to some files(Creating in this way two files - one for 
 * analytical simulation details - and - one for synoptical
 * simulation details)
 * 
 * 
 * @author Valentinos Pariza
 * @version 1.0
 *
 */
public class WebCrawler {
	
	
	public static final int NUMBER_OF_URLS_READ_FROM_FILE=5;
	
	public static final int PRIORITY_LEVELS=10;
	
	private static final int DELAY_TIME=5;
	
	public static final String OUTPUT_FILE_FOR_ANALYTICAL_PROCESSES="analyticalProcessesOfWebCrawlerSimulation.txt";
	
	public static final String OUTPUT_FILE_FOR_SYNOPTICAL_PROCESSES="synopticalProcessesOfWebCrawlerSimulation.txt";
	
	// How many queues will be printed in each number of rows
	// in the file which is the analytical version of the
	// simulation output
	private static final int QUEUES_PER_LINES_TO_PRINT=3;
	
	private  ArrayList<QueueList<Url>> frontQueues; 
	
	private  ArrayList<BackQueueCell> backQueues;
	
	private QueueList<Integer> recoverQueue;
	
	private int numberOfTopDomains;
	
	// the  URLS which will be written every time in front queues
	// because of analyziation of them from prioritizer during the read
	// from input from file stream
	private Url[] readyUrlsToWritten;

	
	/**
	 * A class which represents a backQueue.
	 * A backQueue is defined in this way
	 * because we want a backQueue not only
	 * have a Queue,but also a domainName
	 * (if it belongs to a top domain URL 
	 * at all) which all the URLs that are
	 * hyperlinks of that domain name URL, 
	 * will be placed inside it and also
	 * the delay time in cycles which 
	 * the recover queue will need in 
	 * order to estimate in how many cycles
	 * an element will be removed from a 
	 * backQueue. 
	 * 
	 * @author Valentinos Pariza
	 *
	 */
	private class  BackQueueCell {		// inner class

		
		 QueueList<Url> backQueue;
		
		 String domainName;
		
		 int delayTimeCycles;     		 	// The delay time which the backQueue
										  	// will need in order to give an element
		
		public BackQueueCell()
		{
			this.backQueue=new QueueList<Url>();
			this.domainName="";		// "" is indicated any URL domain name because 
									// it can be matched in any String .So any  
									// hyperlink can come of this domain name
									// (Hypothetically).
			
			delayTimeCycles=0;	
		}
		
		
	}
	
	
	
	/**
	 * A constructor of an object of type this class {@link WebCrawler}.
	 *  
	 */
	public WebCrawler() 
	{
		
	}
	
	
	/**
	 * This method reads all the top domain file URLs' names from a file indicated by the parameter 
	 * and set the WebCrwaler to an initialized state with these top domain URLs which are read. 
	 * 
	 * @param topDomainsFileName a name of a file where the names of the top domain URLs exist
	 * @throws FileNotFoundException if the file with name indicated by topDomainsFileName (parameter)
	 * 			cannot be opened or found for reading
	 */
	private void readTopDomainsAndBuildTheQueues(String topDomainsFileName) throws FileNotFoundException
	{
			
			
		Scanner inputStream=new Scanner(new FileInputStream(topDomainsFileName));
		
		
		while(inputStream.hasNextLine())
		{
			
			numberOfTopDomains++;
			
			inputStream.nextInt();			// Suppose that the file has a number
											// and a String (the name of the URL)
			
			
			// create the BackQueue for the current domain URL
			BackQueueCell aBackQueue=new BackQueueCell();
			
			aBackQueue.domainName=inputStream.next();
			
			// in order all the BackQueues to have delay time 
			 // for dequeue (delayTime>=2 & delayTime<=2)
			 aBackQueue.delayTimeCycles=(int)(Math.random()*(DELAY_TIME-1)+2);		
			   
			 // All the top domain URLs have priority level 1															
			Url anUrl=new Url(aBackQueue.domainName, 1 ,numberOfTopDomains);
			
			
			// add the backQueueID to the recover queue 
			this.recoverQueue.enqueue(numberOfTopDomains);			
			
			
			// At front Queue 0 we place all the top 
			// domains and the URLS with priority 1
			this.frontQueues.get(0).enqueue(anUrl);		
			
			//add the backQueue to the list of backQueues
			backQueues.add(numberOfTopDomains-1, aBackQueue);
			
			
		}
		
		
		
		inputStream.close();
		
		// the last backQueue for the rest URLs that
		// don't come from top domains
		backQueues.add(new BackQueueCell());
		
		
		// assign a random delay time in cycles,which will be used as a 
		// sign for how long we have to wait to recover an URL from
		// a backQueue
		backQueues.get(numberOfTopDomains).delayTimeCycles=(int)(Math.random()*(DELAY_TIME-1)+2);
		
																
		recoverQueue.enqueue(numberOfTopDomains+1);				
		// a register in recoverQueue for 
		// URLs that don't come from top domains										
		
	}
	
	
	
	/**
	 * This method clear all the data Structures and data attributes of this object
	 * and initializes it to default for a creation of a new webCrawler.
	 * 
	 * @return void
	 */
	private void clearWebCrawler()
	{
	
		// clear the WebCrawler's data and Data Structures
			
		// creation of list with front queues
			this.frontQueues=new ArrayList<QueueList<Url>>(PRIORITY_LEVELS);		
				
				
		// Create all the front Queues
		  for(int i=0;i<PRIORITY_LEVELS;i++)								
			{
				this.frontQueues.add(i,new QueueList<Url>());
			}
					
				
			this.backQueues=new ArrayList<BackQueueCell>(21);	// suppose we need 21 back Queues
				
			recoverQueue=new QueueList();	//	create the recover queue
				
			numberOfTopDomains=0;	//number of top domains
			
			// the URLS which will be written every time in front queues
			// because of analyziation of them from prioritizer during the read
			// from input from file stream
			readyUrlsToWritten=new Url[NUMBER_OF_URLS_READ_FROM_FILE];
			
		
	}
	
	
	
	/**
	 * This method reads N(N={@link WebCrawler#NUMBER_OF_URLS_READ_FROM_FILE}) 
	 * or less URL names from an input Stream ,places them in an array of Strings
	 * and returns the number of URL names that it have read
	 * 
	 * @param inputStream
	 * @param urlsNames an array of Strings in which the URLs' names (which will be read)
	 * 		  will be placed
	 * @return the number of URLs which have been successfully read from 
	 * 			a file input stream ,given as a parameter
	 */
	private int readURLs(Scanner inputStream,String[] urlsNames)
	{
		if(inputStream==null)
			return -1;
		
		int counter=0;
		
		while(inputStream.hasNext() && counter<NUMBER_OF_URLS_READ_FROM_FILE)
		{
			
			urlsNames[counter]=inputStream.next();
			counter++;
			
		}
		
		return counter;
	}
	
	
	
	/**
	 * Returns the backQueue ID of an URL name.The ID of the back queue 
	 * returned denotes that the name of this URL is a hyperlink of that
	 * (belongs to that) domain URL that exists in the backQueue with 
	 * ID as returned.
	 * 
	 * @param urlName the name of the URL
	 * @return the id of the back queue that this URL belongs to
	 */
	private int getBackQueueID(String urlName)
	{
		for(int i=0;i<this.numberOfTopDomains;i++)			// we ignore the last queue which is used only for 
		{													// the URLs ,not having top domains
									
			if(urlName.indexOf(this.backQueues.get(i).domainName)==0)
				return i+1;											// return its backQueueID
		}
		
		return numberOfTopDomains+1;		// if it hasn't been found its domain, then we will place it
	}										// in the queue with the URLs which aren't hyperlinks of the
											// top domains 
	
	
	
	

	/**
	 * This method simulates the function-operation of a prioritiser in
	 * a Web-Crawler.This function takes an input stream from where it reads
	 * (from a file) N URLs' names (N={@link WebCrawler#NUMBER_OF_URLS_READ_FROM_FILE})
	 * after it puts a URL object(if takes one as a parameter) in the front Queues and after
	 * it puts the previous read URLs to front Queues.The URLs' names that are read are 
	 * analysed and put in an array(as attribute of the object) so in the next cycle it 
	 * can use this array in order to write these URLs to the front Queues.     
	 * 
	 * @param inputStream an input stream which conects the program with a file for input
	 * 
	 * @param recoveredURL an object type of {@link Url} or a null.This parameter indicates
	 * 		  the recovered from a Back queue URL that has to be placed in a front queue.
	 * 
	 * @param outputStreamAnalytical an output stream for writing in the analytical details
	 * 		  of the prioritiser function in the cuurent cycle of the simulation
	 * 
	 * @param synopticalOutputStream an output stream for writing in, the synoptical details
	 * 		  of the prioritiser function in the cuurent cycle of the simulation
	 */
	private void prioritizer(Scanner inputStream,Url recoveredURL
	,PrintStream outputStreamAnalytical,PrintStream synopticalOutputStream)
	{
		outputStreamAnalytical.println();
		outputStreamAnalytical.println("+++++++++++++++++ Prioritizer Process +++++++++++++++++");
		outputStreamAnalytical.println();
		
		outputStreamAnalytical.println("Prioritizer's ready URLS to be written in front Queues :");
		boolean haveAnalizedURLs=false;
		
		// First priority in this process is the  the URLS to be written to front Queues
			
		int count=0;
		
		for(int i=0;i<readyUrlsToWritten.length;i++)				// the URLs that have been read	
		{															// in the previous cycle	
			if(readyUrlsToWritten[i]!=null)								
			{
				haveAnalizedURLs=true;
				frontQueues.get(readyUrlsToWritten[i].getPriorityLevel()-1).enqueue(readyUrlsToWritten[i]);
				outputStreamAnalytical.println(i+") "+readyUrlsToWritten[i]);
				readyUrlsToWritten[i]=null;		// Clear it in order if the next 
				count++;						// time there isn't an update of this
												// list to understand it and not analyze them again
				
			}
						// calculate where to put the next URL which is ready from previous 
		}			    // cycle to be written in front queues and place it there
		
		
		synopticalOutputStream.printf("%29d ",count);
		
		if(haveAnalizedURLs)
		outputStreamAnalytical.println("These URLS have been written to front Queues.");
		else outputStreamAnalytical.println("No URLs are analized and ready to be placed in front Queues.");
		
		outputStreamAnalytical.println();
		
		
		
		
		if(recoveredURL!=null)		// if actually there was a recovery of an URL from a backQueue
		{
			outputStreamAnalytical.println();
			outputStreamAnalytical.println("There is an URL to be written in frontQueue "
		    +recoveredURL.getPriorityLevel());
			outputStreamAnalytical.println("This URL is :"+recoveredURL);
			
			frontQueues.get(recoveredURL.getPriorityLevel()-1).enqueue(recoveredURL);	
			// add again the recovered (from a backQueue) URL to its appropriate frontQueue
			
			
			synopticalOutputStream.printf("%-25s | ","+ (1)");							
		}																				
		else 
		{
			outputStreamAnalytical.println("No available recovered URL to be written in front Queue.");
			
			synopticalOutputStream.printf("%-25s | ","+ (0)");
		}
		
		
		
		outputStreamAnalytical.println();
		
		String urlsNames[]=new String[NUMBER_OF_URLS_READ_FROM_FILE];	
		
											// read the next NUMBER_OF_URLS_READ_FROM_FILE
											// URLs from the file
		
		int urlsReadFromFile=readURLs(inputStream, urlsNames);
		
		synopticalOutputStream.printf("%-33d | ",urlsReadFromFile);
		
		if(urlsReadFromFile>0)			// if there are any URLs read from file
		{
			int i=0;
			
			outputStreamAnalytical.println("The URLS which have been read from the file and given to prioritizer : ");
			
			while(i<readyUrlsToWritten.length)
			{
				
				if(urlsNames[i]!=null)				// Analyze all the URLs in order in the next cycle to
				{									// place them in the front Queues
					
					readyUrlsToWritten[i]=new Url(urlsNames[i] ,		// create the new URL
					(int)(Math.random()*PRIORITY_LEVELS+1) ,
					getBackQueueID(urlsNames[i]));
					outputStreamAnalytical.println(i+") "+readyUrlsToWritten[i]);
					
				}
				
				i++;
				
			}
			
			outputStreamAnalytical.println("These URLS are analized "
			+"and at the next cycle will be written to front Queues.");
				
		}
		else outputStreamAnalytical.println("No URLs have been read from file.");
		
		
		outputStreamAnalytical.println();
		outputStreamAnalytical.println("+++++++++++++++++ End of Prioritizer Process +++++++++++++++++");
		outputStreamAnalytical.println();
		
		
		
	}
	
	
	
	
	/**
	 * This method simulates the operation of the queue selector & router function
	 * of a web-Crawler.It tries to place an amount of URLs from front Queues to back 
	 * queues with an algorithm which gives more priority in taking more URLs from 
	 * bigger priority level front queues.The idea of the algorithm is that if we 
	 * suppose that we have N priority levels(N front queues more simpler),and every
	 *  time that we need an URL from a back queue that is empty,we invoke this method ,
	 * that starts from the front queue with biggest priority and ends at the one with
	 *  the lowest  priority level( [1-(N-1)] ) and takes from each of them N-i URLs(where
	 *  i is the index of each front Queue starting from 0 until N-1).
	 * 
	 * 
	 * @param outputStreamAnalytical an output stream for writing in, the analytical details
	 * 		  of the queueSelectorAndRouter function in the current cycle of the simulation
	 */
	private void queueSelectorAndRouter(PrintStream outputStreamAnalytical)		
	{											// selects via a priority algorithm some 
												// URLs from front queues and places
												// them in the appropriate back queues
		outputStreamAnalytical.println();
		outputStreamAnalytical.println("******************** Queue"
		+ " Selector and Router Process ********************");
		outputStreamAnalytical.println();
		
		for(int i=0;i<PRIORITY_LEVELS;i++)
		{
			int k=1;
			
			// showing from what front queue the URLs will be take
			if(!frontQueues.get(i).isEmpty())
			{
				outputStreamAnalytical.println();
				outputStreamAnalytical.println();
				outputStreamAnalytical.println("<<<<<<<<<<<<<<<<<<<< "
				+ "From frontQueue "+(i+1)+" >>>>>>>>>>>>>>>>>>>>");
				outputStreamAnalytical.println();
			}
			
			while( (k <= PRIORITY_LEVELS-i) && !frontQueues.get(i).isEmpty())	
			{
				// the frontQueue with priority level 1 will give PRIORITY_LEVELS-i where i=0
				// the frontQueue with priority level 2 will give PRIORITY_LEVELS-i where i=1
				// the same stands for i=[0,PRIORITY_LEVELS-1] for the priority levels 1-PRIORITY_LEVELS
				
				
				
				try						
				{
					Url anURL=frontQueues.get(i).dequeue();
					
					
					backQueues.get(anURL.getBackQueueDomainID()-1).backQueue.enqueue(anURL);
					
					outputStreamAnalytical.println("URL "+anURL);
					
					outputStreamAnalytical.println(" \n was taken from front queue "
					+ "with priority level "+(i+1)+" and was placed at backQueue with id "
					+anURL.getBackQueueDomainID());
					outputStreamAnalytical.println();
					
				} 
				catch (QueueIsEmptyException e) // if the front Queue don't have any 
				{									  // further elements inside, then skip
					k++;							  // this back Queue
					continue;
				}
				
				this.backQueues.get(0);
				
				k++;
				
			}
			
			
			
		}
		
	
		outputStreamAnalytical.println("\n******************** End of"
				+ " Queue Selector and Router Process ********************");
		outputStreamAnalytical.println();
		
	}
	
	
	/**
	 * 
	 * This method prints the contents of front Queues(to a file,screen,...) through a stream ,which is given as
	 * a parameter starting from index as indicated by parameter fromIndex and until the toIndex-th
	 * front queue of the list of front Queues.
	 * 
	 * @param analyticalOuputStream an output stream which is used to print to a file or to the screen 
	 * 		  the content and information about the front queues
	 * 
	 * @param fromIndex	from what index of the list with front Queues it will start 
	 * 		  printing(The indexes start from 0 - size Of front Queue-1) the front queues
	 * @param toIndex until what index of the list with front Queues it will
	 * 		  print(The indexes start from 0 - size Of front Queue-1) the front queues
	 */
	private void printFrontQueuesAnalytical(PrintStream analyticalOuputStream,int fromIndex,int toIndex)
	{
		
		// We suppose that fromIndex and toIndex
		// are indexes for the arrayList of Queues(frontQueues).
		
		if(analyticalOuputStream==null)
			return ;
		
		int size=this.frontQueues.size();
		
		if( (fromIndex<0 ||fromIndex>=size) || fromIndex>toIndex || toIndex>=size)
			return ;
		
		
		
		analyticalOuputStream.printf("%6s","");
		
		// print general inforamtion for each front queue(size,priority level,...)
		for(int i=fromIndex;i<=toIndex;i++)
		{
			analyticalOuputStream.printf("%-15s %-2d  %-6s %-3d  %-16s %-2d%4s | ",
		"FrontQueue",(i+1),"Size =",frontQueues.get(i).size(),"Priority Queue =",(i+1),"");
		}
		
		analyticalOuputStream.println();
		
		
		analyticalOuputStream.print("   N) ");
		
		// print the sections-parts titles which each URL have 
		for(int i=fromIndex;i<=toIndex;i++)
		{
			analyticalOuputStream.printf("%-35s | %-7s | %-7s | ","Name of URL","Pr.-Lvl","Domn-ID" );
		}
		
		analyticalOuputStream.println();
		
		// create an array of Iterators for iterating through each back Queue

		Iterator queueIterators[]=new Iterator[size];
		
		// get each iterator for each back Queue
		for(int i=fromIndex;i<=toIndex;i++)
		{
			queueIterators[i]=frontQueues.get(i).iterator();
			
		}
		
		
		boolean haveElements=true;
		
		int row=1;
		
		while(haveElements)
		{
			analyticalOuputStream.printf("%4d) ",row);
			
			haveElements=false;
			
			for(int i=fromIndex;i<=toIndex;i++)
			{
				// check for every corresponding line in the backQueues
				// which of the back queues have an element inside or not
				// in order to print the URL inside that line or a blank line 
				// if there is not any element
				
				if(queueIterators[i].hasNext())
				{
					haveElements=true;
					
					Url url=(Url) queueIterators[i].next();
					
					analyticalOuputStream.printf("%-35s | %-7d | %-7d | ",
					url.getNameOfURL(),url.getPriorityLevel(),url.getBackQueueDomainID());
				}
				else analyticalOuputStream.printf("%55s | ","");
				
				
			}
			
			analyticalOuputStream.println();
			
			if(haveElements)		// in order to print the next line,
			row++;					// of the front queue at least one  
									// front queue must have elements 
		}							// more elements
		
	}
	
	
	
	
	
	/**
	 * 
	 * This method prints the contents of back Queues(to a file,screen,...) through a stream ,which is given as
	 * a parameter starting from index as indicated by parameter fromIndex and until the toIndex-th
	 * back queueu of the list of back Queues.
	 * 
	 * @param analyticalOuputStream an output stream which is used to print to a file or to the screen 
	 * 		  the content and information about the Back queues
	 * 
	 * @param fromIndex	from what index of the list with back Queues it will start 
	 * 		  printing(The indexes start from 0 - size Of Back Queue-1) the back queues
	 * @param toIndex until what index of the list with back Queues it will
	 * 		  print(The indexes start from 0 - size Of Back Queue-1) the back queues
	 */
	private void printBackQueuesAnalytical(PrintStream analyticalOuputStream,int fromIndex,int toIndex)
	{
		// We suppose that fromBackQueue and UntilBackQueue
		// are indexes for the arrayList of Queues(backQueues).
		
		if(analyticalOuputStream==null)
			return ;
		
		int size=this.backQueues.size();
		
		if(fromIndex>toIndex || (fromIndex<0 || fromIndex>=size) || toIndex>=size)
		return ;
		
		
		analyticalOuputStream.printf("%6s","");
		
		
		// print general inforamtion for each back queue(delay time,domain 
		// name which hyperlinks belongs to back queue,...)
		for(int i=fromIndex;i<=toIndex;i++)
		{
			
			analyticalOuputStream.printf("%-9s %-2d %-13s %-15s %-12s %-2d | ",
			"BackQueue",(i+1),"Domain Name =",backQueues.get(i).domainName,
			"Delay Time =",backQueues.get(i).delayTimeCycles,"");
		}
		
		
		analyticalOuputStream.println();
		
		
		analyticalOuputStream.print("   N) ");
		
		// print the sections-parts titles which each URL have 
		for(int i=fromIndex;i<=toIndex;i++)
		{
			analyticalOuputStream.printf("%-38s | %-7s | %-7s | ","Name of URL","Pr.-Lvl","Domn-ID" );
		}
		
		analyticalOuputStream.println();
		
		// create an array of Iterators for iterating through each back Queue

		Iterator<Url> queueIterators[]=(Iterator<Url> [])new Iterator[size];
		
		// get each iterator for each back Queue

		for(int i=fromIndex;i<=toIndex;i++)
		{
			queueIterators[i]=this.backQueues.get(i).backQueue.iterator();
			
		}
		
		
		boolean haveElements=true;
		
		int row=1;
		
		
		// print each back queue if it actually has any URLS inside,else print empty spaces in order to  keep
		// well formatted the printing of the queues
		while(haveElements)
		{
			
			// check for every corresponding line in the backQueues
			// which of the back queues have an element inside or not
			// in order to print the URL inside that line or a blank line 
			// if there is not any element
			
			analyticalOuputStream.printf("%4d) ",row);
			
			haveElements=false;
			
			for(int i=fromIndex;i<=toIndex;i++)
			{
				
				
				if(queueIterators[i].hasNext())
				{
					haveElements=true;
					
					Url url=(Url) queueIterators[i].next();
					
					analyticalOuputStream.printf("%-38s | %-7d | %-7d | ",
					url.getNameOfURL(),url.getPriorityLevel(),url.getBackQueueDomainID());
				}
				else analyticalOuputStream.printf("%58s | ","");
				
				
			}
			
			analyticalOuputStream.println();
			
			if(haveElements)		// in order to print the next line
				row++;				// of the back queue ,at least one  
									// back queue must have elements 
									// more elements
		}
		
		analyticalOuputStream.printf("%6s","");
		
		// at the end print the size of each back queue
		for(int i=fromIndex;i<=toIndex;i++)
		{
			analyticalOuputStream.printf("%38s%-20d | ",
			"Size of Back Queue = ",backQueues.get(i).backQueue.size());
		}
		
		analyticalOuputStream.println();
		analyticalOuputStream.println();
		
	}
	
	
	
	
	/**
	 * This method takes an output stream and prints the analytical information
	 * of every frontQueue in the current cycle in simulation. 
	 * 
	 * @param analyticalOuputStream the output stream which uses to print
	 * through it the contents and the information of the front Queues.
	 * 
	 * @return void
	 */
	public void printFrontQueuesAnalytical(PrintStream analyticalOuputStream)
	{
		if(analyticalOuputStream==null)
			return ;
		
		analyticalOuputStream.println("\n======= Front Queues =======\n");
		
		int lines=frontQueues.size()/QUEUES_PER_LINES_TO_PRINT;
		
		for(int i=0;i<lines;i++)
		{	//	we print N(N=QUEUES_PER_LINES_TO_PRINT)	
			// front queues per line for S(S=lines) lines
		    // so that S*N<=size
			printFrontQueuesAnalytical(analyticalOuputStream,
			i*QUEUES_PER_LINES_TO_PRINT, (i+1)*QUEUES_PER_LINES_TO_PRINT-1);
			analyticalOuputStream.println();
		}
		
		printFrontQueuesAnalytical(analyticalOuputStream, 
		lines*QUEUES_PER_LINES_TO_PRINT,frontQueues.size()-1);
		
	}
	
	
	/**
	 * This method takes an output stream and prints the analytical information
	 * of every backQueue in the current cycle in simulation. 
	 * 
	 * @param analyticalOuputStream the output stream which uses to print
	 * through it the contents and the information of the back Queues.
	 * 
	 * @return void
	 */
	public void printBackQueuesAnalytical(PrintStream analyticalOuputStream)
	{
		if(analyticalOuputStream==null)
			return ;
	
		analyticalOuputStream.println("\n======= Back Queues =======\n");
		
		int lines=this.backQueues.size()/QUEUES_PER_LINES_TO_PRINT;
		
		for(int i=0;i<lines;i++)
		{									//	we print N(N=QUEUES_PER_LINES_TO_PRINT)
											// back queues per line for S(S=lines) lines
											// so that S*N<=size
			printBackQueuesAnalytical(analyticalOuputStream,
			i*QUEUES_PER_LINES_TO_PRINT, (i+1)*QUEUES_PER_LINES_TO_PRINT-1);
			analyticalOuputStream.println();
		}
		
		printBackQueuesAnalytical(analyticalOuputStream,
		lines*QUEUES_PER_LINES_TO_PRINT,backQueues.size()-1);
		
	}
	
	
	
	
	/**
	 * This method is used for running the simulation of the Web Crawler.It coordinates and
	 * controls the executions and operations of the different functions of the Web Crawler
	 * such as the prioritiser({@link WebCrawler#prioritiser(Scanner, Url, PrintStream, PrintStream)})
	 * ,the queue-Selector and Router({@link WebCrawler#queueSelectorAndRouter(PrintStream)}),
	 * reader of URLs every cycle ({@link WebCrawler#readURLs(Scanner, String[])})  and much 
	 * more.
	 * 
	 * @param topDomainsFileName  the name of the file which will is used in 
	 * 		  every cycle for reading from it all the top domains URLs'names
	 * 
	 * 
	 * @param urlsFileName the name of the file which will is used in 
	 * 		  every cycle for reading from it all the URLs'names
	 * 
	 * @param numberOfCyclesOfTheSimulation the number of the cycles that the
	 * 		  simulation will run
	 * 
	 * @param analyticalOutputStream the output stream which uses to print
	 * through it the analytical details of the simulation of every cycle
	 * 
	 * @param synopticalOutputStream the output stream which uses to print
	 * through it the synoptical details of the simulation of every cycle
	 * 
	 * @throws FileNotFoundException an Exception object type of {@link #FileNotFoundException} 
	 * 			if there are any problems in reading from any from the fileNames that are given 
	 * 			as parameters
	 */
	public void runWebCrawlerSimulation(String topDomainsFileName,String urlsFileName,
			int numberOfCyclesOfTheSimulation,PrintStream analyticalOutputStream,
			PrintStream synopticalOutputStream) throws FileNotFoundException
	{
		
		
		
		if(urlsFileName==null || synopticalOutputStream==null || topDomainsFileName==null)
			throw new NullPointerException("The file which it was given was null.");
		
		clearWebCrawler();	// initialise to default the web crawler
		
		// read the top domain URLs'names
		readTopDomainsAndBuildTheQueues(topDomainsFileName);
		
		Scanner inputStream=null;
		
		
		try
		{
			inputStream=new Scanner(new FileInputStream(urlsFileName));
		}
		catch(FileNotFoundException e)
		{
			throw new FileNotFoundException("The file "+urlsFileName+" could not be opened.");
		}
		// create an input stream for the URLs that will be read in every cycle

		
		int backQueueIDToRecoverURL=0;				// The current back Queue to be dequeued when 
													// it's time indicated by the recover Queue 
		
		
		try
		{
			backQueueIDToRecoverURL = recoverQueue.front();			// we have the backQueue ID 
		} 															// of the first URl to be
		catch (QueueIsEmptyException e1) 						// recovered(indicated by
		{															//  recover Queue)
			e1.printStackTrace();
		} 
			
		
		Url recoveredURL=null;		// the recovered URL					   
		
		int currentCycle=0;
		
		int cyclesRemainingToRecoverURL=0;
		
		// the titles of the synoptical details of the simulation
		synopticalOutputStream.printf("| %-13s | %-35s | ","Current Cycle","Remaining Time to Recover URL");
		synopticalOutputStream.printf("%-50s | ","Recovered backQueue URL to go to prioritizer");
		synopticalOutputStream.printf("%-40s | ","Next backQueueID to deque when ready");
		synopticalOutputStream.printf("%-55s | ","URLs written to frontQueues/ fromFile +(fromRecover)");
		synopticalOutputStream.printf("%-33s | ","URLs read from file and analized");
		synopticalOutputStream.printf("%-22s | ","Queue Selector Status");
		synopticalOutputStream.printf("%-35s |","Queue Selector and Router status");
		synopticalOutputStream.printf("%-35s | ","Recovered backQueue at this cycle");
		
		
		
		while(currentCycle<numberOfCyclesOfTheSimulation ) 
		{					// number of simulations to do -> until maxNumberOfCycles>=currentCycle				   
							// numberOfCyclesOfTheSimulation cycles will do the simulation
			
			analyticalOutputStream.println();
			analyticalOutputStream.println();
			analyticalOutputStream.println("---------------------------------- Current Cycle : "
			+currentCycle+" ----------------------------------");
			analyticalOutputStream.println();
			
			synopticalOutputStream.println();
			
			synopticalOutputStream.printf("| %-13d | ",currentCycle);
			synopticalOutputStream.printf("%-35d | ",cyclesRemainingToRecoverURL);
			synopticalOutputStream.printf("%-50s | ",(recoveredURL!=null) ?
			recoveredURL.getNameOfURL() : "No available recovered URL yet ." );
			
			synopticalOutputStream.printf("%-40d | ",backQueueIDToRecoverURL);
			
			// print the front Queues
			printFrontQueuesAnalytical(analyticalOutputStream);
			
			// print the back Queues
			printBackQueuesAnalytical(analyticalOutputStream);
			
			
			analyticalOutputStream.println();
			
				if(recoveredURL==null)
				{
					analyticalOutputStream.println("No available recovered URL yet .");
					
				}
				
				else 
					analyticalOutputStream.println("Recovered URL : "+recoveredURL);
				
				
				prioritizer( inputStream, recoveredURL,analyticalOutputStream,synopticalOutputStream);
				
				if(recoveredURL!=null)
					recoveredURL=null;	// it means that we place it in the front  
										// queues we lose its track in the queue
					
													// we  suppose that 
													// the front Queues
													// won't be empty
													//(indicated by instructions 
													// of the exercise)
			
				
				boolean hasBackQueuesBeenRefilled=false;
				
			if(cyclesRemainingToRecoverURL<=0)
			{
				
				synopticalOutputStream.printf("%5s", "");
				synopticalOutputStream.printf("%-17s | ","was invoked");
				
				// execution of the operation queue selector,which takes the next back Queue
				// id from the recover queue
				analyticalOutputStream.println();
				analyticalOutputStream.println("############ Queue Selector From Recover Queue ############");
				analyticalOutputStream.println();
				
				analyticalOutputStream.println("Time to recover URL");
				
				boolean hasTakenURL=false;
				
				
				while(!hasTakenURL)
				{
					try 
					{
						recoveredURL=backQueues.get(backQueueIDToRecoverURL-1).backQueue.dequeue();
						hasTakenURL=true;
						
						analyticalOutputStream.println("URL recovered : "+recoveredURL);
						
					} 
					catch (QueueIsEmptyException e) 
					{
						queueSelectorAndRouter(analyticalOutputStream);   
						hasBackQueuesBeenRefilled=true;
															// if the backQueue, we try to take 
					}								  		 // an URL is empty then an exception
													  		 // is thrown and the program invokes
				}									         // a function to refill the backQueues
				
				
				try 
				{													 // we  suppose that
					recoverQueue.enqueue(recoverQueue.dequeue());    // the recover Queue
					backQueueIDToRecoverURL=recoverQueue.front();	 // won't be empty	

					// the next backQueue id that the program will use for taking a URL in a specific time of cycles
					analyticalOutputStream.println("Next backQueue ID to dequeue : "+backQueueIDToRecoverURL);
					
					cyclesRemainingToRecoverURL=backQueues.get(backQueueIDToRecoverURL-1).delayTimeCycles;  
					
																				 
				} 							
				catch (QueueIsEmptyException e) 
				{
					e.printStackTrace();
				}
				
				analyticalOutputStream.println();
				analyticalOutputStream.println();
				analyticalOutputStream.println("############ End of"
				+ " Queue Selector From Recover Queue ############");
				analyticalOutputStream.println();
									
			}
			else
			{
				// if it isn't time to recover an URL from recover queue
				analyticalOutputStream.println("Time remaining to recover URL from back Queue "
				+backQueueIDToRecoverURL+" in cycles : "+cyclesRemainingToRecoverURL);
				
				synopticalOutputStream.printf("%5s", "");
				synopticalOutputStream.printf("%-17s | ","was not invoked");
			}
				
			
			// check if the queuSelector and Router function has been invoked then 
			// print the appropriate message for the synoptical simulation description
			if(hasBackQueuesBeenRefilled)
				{
					synopticalOutputStream.printf("%-35s |","Back Queues were refilled");
				}
			else synopticalOutputStream.printf("%-35s |","Back Queues weren't refilled");;
			
			synopticalOutputStream.printf("%-35s | ",
			(recoveredURL!=null) ? recoveredURL.getNameOfURL() : "No recovered URL" );
			
			cyclesRemainingToRecoverURL--;
			
			
			analyticalOutputStream.println();
			analyticalOutputStream.println("---------------------------------- End of Current Cycle : "
					+currentCycle+" ----------------------------------");
			analyticalOutputStream.println();
			analyticalOutputStream.println();
			currentCycle++;
		}
		

		
		inputStream.close();
	}
	
	
	
	
	/**
	 * This method is the main method of the program.From here all the program is coordinated
	 * and executed.In this method the number of the cycles of the simulation to run is asked
	 * and read from keyboard ,and then an instance of the class {@link WebCrawler} is 
	 * created which is used for running the simulation of the WebCrawler by invoking the method 
	 * {@link WebCrawler#runWebCrawlerSimulation(String, String, int, PrintStream, 
	 * PrintStream)} on the instance-object created.
	 * 
	 * @param args An array of Strings which are the arguments which are passed in the program from
	 * 		  command line(These are the command line arguments).
	 * @return void
	 */
	public static void main(String[] args)
	{
		
		// The files should be in the current directory 
		String topDomainsFile="src/webCrawlerSimulator/Top20Domains.txt";	
		String someURLSFile="src/webCrawlerSimulator/manyURLs.txt";	
	
		PrintStream analyticalOutputStream=null;
		
		PrintStream synopticalOutputStream=null;
		
		
		Scanner keyboard=new Scanner(System.in);
		
		
		System.out.println("Give the name of the file or the path for the file with the top domains file.");
		topDomainsFile=keyboard.next().trim();
		keyboard.nextLine();
		
		System.out.println("\nGive the file name or the path for the file with the URLs to be read.");
		someURLSFile=keyboard.next();
		keyboard.nextLine();
		
		boolean isLegalNmberOfSimulationsGiven=false;
		
		System.out.println("Give the number for simulations : ");
		int numberOfSimulations=0;
		while(!isLegalNmberOfSimulationsGiven)
		{
			try 
			{
				numberOfSimulations=keyboard.nextInt();		// read the number of the cycles 
				if(numberOfSimulations<=0)				    // of the webCrawler simulation
				{
					System.out.println("You have to give a number greater than zero.\n");
					// ensure that the user has typed a valid number of cycles
				}
				else isLegalNmberOfSimulationsGiven=true;
			}
			catch(InputMismatchException inputTypeException)
			{
				// ensure that the user typed a number
				System.out.println("Give a correct format of a number.");
				keyboard.nextLine();
			}
		}
		
		
		
			try
			{
				analyticalOutputStream=new PrintStream(new FileOutputStream(OUTPUT_FILE_FOR_ANALYTICAL_PROCESSES));
				
				synopticalOutputStream=new PrintStream(new FileOutputStream(OUTPUT_FILE_FOR_SYNOPTICAL_PROCESSES));
				// we need two output-streams one for the analytical and the other for the
				// synoptical simulation's information output
				
				WebCrawler webCrawler=new WebCrawler();
				
				System.out.println("Start of Simulation...");
				
				// run the simulation
				webCrawler.runWebCrawlerSimulation(topDomainsFile,someURLSFile,
				numberOfSimulations,analyticalOutputStream,synopticalOutputStream);
				
				
	 		} 
			catch (FileNotFoundException e) 
			{
				// if there is any problem with reading from the file,then inform the user
				// about it
				System.out.println("Files couldn't be opened to write on them.");;
			}
			
			
			System.out.println("End of Simulation...");
				analyticalOutputStream.close();
				synopticalOutputStream.close();
		
		
	}
	
	
}
