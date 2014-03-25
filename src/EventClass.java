
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahulratthen
 */
public class EventClass  implements Comparable<EventClass>{
    public String eventType, jobType;
    public double time;
    
    public EventClass(String type, double t, String jtype)
    {
        eventType = type;
        jobType = jtype;
        time = t;
    }

    @Override
    public int compareTo(EventClass o) {
        if(this.time > o.time)
            return 1;
        else if(this.time == o.time)
            return 0;
        else
            return -1;
    }
    
//    public static void main(String args[])
//    {
//        LinkedList list = new LinkedList<EventClass>();
//        
//        list.add(new EventClass("ARR",1));
//        list.add(new EventClass("DEP",2));
//        list.add(new EventClass("ARR",5));
//        list.add(new EventClass("ARR",3));
//        EventClass.printList(list);
//        
//        Collections.sort(list);
//        EventClass.printList(list);
//    }
//    
    
}
