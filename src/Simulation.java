
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rahulratthen
 */

public class Simulation {
    public double m,k,seed=1111.0, lambda1, lambda2, mu;
    public int eventCount ;
    LinkedList eventList;
    public double clock, EN ;
    public int N ,Ndep, Narr;
    boolean done ;    
    
    public Simulation()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the value of \"m\"");
        m = input.nextDouble();
        System.out.println("Enter the value of \"k\"");
        k = input.nextDouble();
        System.out.println("Enter the value of \"lambda1\"");
        lambda1 = input.nextDouble();
        System.out.println("Enter the value of \"lambda2\"");
        lambda2 = input.nextDouble();
        System.out.println("Enter the value of \"mu\"");
        mu = input.nextDouble();
        
        eventCount = 0;
        eventList = new LinkedList<EventClass>();
        clock = 0;
        Ndep = 0;
        Narr = 0;
        EN = 0;
        done = false;
        
    }
    
    public void Simulate()
    {
        //insert first ARR event
        eventList.add(new EventClass("ARR",DistributedPseudoRandomNumber(lambda1)));
        
        while(!done)
        {
            EventClass currEvent = (EventClass)eventList.remove();
            double prev = clock;
            clock = currEvent.time;
            
            switch(currEvent.eventType)
            {
                case "ARR" :
                    EN += N*(clock-prev);                 
                    N++;
                    Narr++;
                    eventList.add(new EventClass("ARR",clock + DistributedPseudoRandomNumber(lambda1)));
                    if(N==1)
                    {
                        eventList.add(new EventClass("DEP",clock + DistributedPseudoRandomNumber(mu)));
                    }
                    Collections.sort(eventList);
                    break;
                case "DEP" :
                    EN += N*(clock-prev);  
                    N--;
                    Ndep++;
                    if(N>0)
                    {
                        eventList.add(new EventClass("DEP",clock + DistributedPseudoRandomNumber(mu)));
                    }
                    Collections.sort(eventList);
                    break;
            }
            
            if(Ndep > 100000)
                done = true;
        }
        
        System.out.println("Current number of customers in the system : "+N);
        //System.out.println("Expected number of customers(sim) : "+EN/clock);

    }
    
    public double GenerateCongruentialRV(){
        seed = (k*seed)%m;
        double random = seed/m;
        return random;
    }
    
    public double DistributedPseudoRandomNumber(double lambda)
    {
        double y;
        double rand = GenerateCongruentialRV();
        y = -(1/lambda)*Math.log(rand);
        return y;
    }
    
    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.Simulate();
    }
}
