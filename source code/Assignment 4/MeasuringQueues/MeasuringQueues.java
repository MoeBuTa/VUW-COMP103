// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2018T2, Assignment 4
 * Name:Wenxiao Zhang
 * Username:zhangwenx4
 * ID:300462639
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;


/** 
 *  Measures the performance of different ways of doing a priority queue of Patients
 *  Uses a cutdown version of Patient that has nothing in it but a priority (so it takes
 *   minimal time to construct a new Patient).
 *  The Patient constructor doesn't need any arguments
 *  Remember that small priority values are the highest priority - 1 is higher priority than 10.
 *  Algorithms to test and measure:
 *      Use a built-in PriorityQueue
 *      Use an ArrayList, with queue's head at 0,   sorting when you add an item.
 *      Use an ArrayList, with queue's head at end, sorting when you add an item.
 *  Each method should have an items parameter, which is a collection of Patients
 *    that should be initially added to the queue (eg  new PriorityQueue<Patient>(items); or
 *    new ArrayList<Patient>(items))
 *    It should then repeatedly dequeue a patient from the queue, and enqueue a new Patient(). 
 *    It should do this 100,000 times.
 *    (the number of times can be changed using the textField)
 *  To test your methods, you should have debugging statements such as UI.println(queue)
 *   in the loop to print out the state of the queue. You could comment them out before measuring.
 */

public class MeasuringQueues{

    private static final int TIMES=100000;  //Number of times to repeatedly dequeue and enqueue item 

    /**
    * Construct a new MeasuringQueues object
    */
    public MeasuringQueues(){
       setupGUI();
    }

    /**
    * Setup the GUI
    */
    public void setupGUI(){
       UI.addButton("Test", this::test);
       UI.addButton("Measure", this::measure);
       UI.addButton("Quit", UI::quit);
       UI.setDivider(1.0);
    }


    /**
     * Create a priority queue using a PriorityQueue, 
     * adding all the patients in the collection to the queue.
     * (n will be the size of the the collection in the patients parameter).
     * Then, dequeue and enqueue TIMES times.
     */
    public void useQueuesPQ(Collection<Patient> patients){
        Queue<Patient> queue = new PriorityQueue<Patient>(patients);
        //UI.println(queue);
        for (int i=0; i<TIMES; i++){
            queue.poll();
            queue.offer(new Patient());
            //UI.println(queue);
        }
    }

    /**
     * Create a queue using an ArrayList with the head at the end.
     * Make a new ArrayList using all the patients in the collection,
     * and then sorting the list.
     * Then, dequeue and enqueue TIMES times.
     * (n will be the size of the the collection in the patients parameter).
     * Note: Head of queue is at the end of the list, 
     * so we need to sort in the reverse order of Patients (so the smallest value comes at the end)
     */
    public void useQueuesALEnd(Collection<Patient> patients){
        /*# YOUR CODE HERE */
    	List<Patient> queue = new ArrayList<Patient>(patients);
    	Collections.sort(queue, Collections.reverseOrder());
    	//UI.println(queue);
        for (int i=0; i<TIMES; i++){
            queue.remove(queue.size()-1);
            queue.add(new Patient());
            Collections.sort(queue, Collections.reverseOrder());
            //UI.println(queue);
        }
    }
//    public void useQueuesALEndRev(Collection<Patient> patients){
//        /*# YOUR CODE HERE */
//    	List<Patient> queue = new ArrayList<Patient>(patients);
//    	Collections.sort(queue);
//    	Collections.reverse(queue);
//    	//UI.println(queue);
//        for (int i=0; i<TIMES; i++){
//            queue.remove(queue.size()-1);
//            queue.add(new Patient());
//            Collections.sort(queue);
//        	Collections.reverse(queue);
//            //UI.println(queue);
//        }
//    }

    /**
     * Create a queue using an ArrayList with the head at the start.
     * Head of queue is at the start of the list
     * Make a new ArrayList using all the patients in the collection,
     * and then sorting the list.
     * Then, dequeue and enqueue TIMES times.
     * (n will be the size of the the collection in the patients parameter).
     */
    public void useQueuesALStart(Collection<Patient> patients){
        /*# YOUR CODE HERE */
    	List<Patient> queue = new ArrayList<Patient>(patients);
    	Collections.sort(queue);
    	//UI.println(queue);
        for (int i=0; i<TIMES; i++){
        	queue.remove(0);
            queue.add(new Patient());            
            Collections.sort(queue);
            //UI.println(queue);
        }

    }



    /**
     * For a sequence of values of n, from 1000 to 1024000,
     *  - Construct a collection of n Patients (This step shouldn't be included in the time measurement)
     *  - call each of the methods, passing the collection, and measuring
     *    how long each method takes to dequeue and enqueue a Patient 100,000 times
     */
    public void measure(){
        UI.printf("Measuring methods using %d repeitions, on queues of size 1000 up to 1,024,000\n",TIMES);
        UI.println("       n          PQ         ALEnd      ALStart");
        /*# YOUR CODE HERE */
        int i = 0;
        Collection<Patient> patients = new ArrayList<Patient>();
        for(int n = 1000; n <= 1024000; n*=2) {
        	while(i<n) {
        		patients.add(new Patient());        		
        		i++;
        	}
        	long PQStart = System.currentTimeMillis();
        	useQueuesPQ(patients);        
        	System.gc();
        	long PQTime = System.currentTimeMillis()-PQStart;
        	
        	long EndStart = System.currentTimeMillis();
        	useQueuesALEnd(patients);
        	System.gc();
        	long EndTime = System.currentTimeMillis()-EndStart;
        	
//        	long EndRevStart = System.currentTimeMillis();
//        	useQueuesALEndRev(patients);
//        	System.gc();
//        	long EndRevTime = System.currentTimeMillis()-EndRevStart;
        	
        	long StartStart = System.currentTimeMillis();
        	useQueuesALStart(patients);
        	System.gc();
        	long StartTime = System.currentTimeMillis()-StartStart;
        	
        	UI.printf("%10d    %8.3f    %8.3f    %8.3f \n", 
        			n, PQTime/1000.0, EndTime/1000.0, StartTime/1000.0);

        	
        }

    }

    /**
     * Method for testing the methods on short queues, to make sure that they work.
     * Make sure you change the value of TIMES to something small, like 10,
     * And include debugging println's in the methods
     */
    public void test(){
        Collection<Patient> items = new ArrayList<Patient>();
        for (int i=0; i<10; i++) { items.add(new Patient()); }

        UI.println("======== Testing useQueuesPQ ============");
        useQueuesPQ(items);
        
        UI.println("======== Testing useQueuesALEnd ============");
        useQueuesALEnd(items);
        
        UI.println("======== Testing useQueuesALStart ============");
        useQueuesALStart(items);
    }
        


    public static void main(String[] arguments){
        new MeasuringQueues();
    }


}
