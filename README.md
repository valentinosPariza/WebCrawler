# WebCrawler
The program that I have made is  about making a web Crawler. This program splits in three basic main Functionsoperations which use some data-Structures(lists of Queues ,Queues).This three operations are the prioritiser ,the queue Selector and Router and the Queue selector for recover URLs. 
 
At the beginning of the program the number of cycles is given by user. After this the hole structures of the program are created and initialized by default, by reading a text file with the top N domain URLs .After this we have a list of front Queues, a list of back Queues and a queue (for selecting from a back Queue, -recovering an URL).Continuing, then the program  starts to executing the simulation with the three basic functions mention above for K cycles(defined in the beginning).In every cycle it starts calling the prioritiser. The prioritiser first looks for previous analyzed URLs read from a file and if there are ,it places them in the appropriate front Queues, .Next if there is a recovered URL ( indicated from the recovery Queue) it  also places it in a front Queue  and last it reads from the file, T URLS(T is defined from a constant,how many URLS are read each cycle) and analyze them to objects URLs and places them in an array in order ,in the next cycle to put them in front queues. 
 
After that a remaining time is set in order to wait for, taking a back Queue id from the recover Queue .That back Queue id indicates from what Back Queue the next URL will be recovered.For the remaining time that we have to wait in order to take an URL from the recover Queue is defined by the back Queue that the URL that we will take belong to. When the time runs out we take the URL from the back Queue(dequeue) and send it at the next  Cycle to the priorityzer. If the back Queue that we want to take an URL doesn’t have any URLs we call the second function queue selector and Router 
Εργαστήρια ΕΠΛ 231 Χειμερινό 2018                                                      Αριθμός Ταυτότητας: 909759 
Ημερομηνία Παράδοσης: 3/10/2018   Σελίδα 3 από 33 
which refills based in an algorithm the back Queues with URLs from front Queues. The same thing happens for all the cycles.
