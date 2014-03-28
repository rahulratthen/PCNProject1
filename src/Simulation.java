
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
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
    public int N ,Ndep, Narr,l;
    boolean done ;    
    Random r;
    public Simulation()
    {
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter the value of \"m\"");
//        m = input.nextDouble();
//        System.out.println("Enter the value of \"k\"");
//        k = input.nextDouble();
//        System.out.println("Enter the value of \"lambda1\"");
//        lambda1 = input.nextDouble();
//        System.out.println("Enter the value of \"lambda2\"");
//        lambda2 = input.nextDouble();
//        System.out.println("Enter the value of \"mu\"");
//        mu = input.nextDouble();
        r = new Random(100);

        m=2;k=3;lambda1=0.4;lambda2=0.3;mu=3;
        l=3;
        eventCount = 0;
        eventList = new LinkedList<EventClass>();
        clock = 0;
        Ndep = 0;
        Narr = 0;
        EN = 0;
        done = false;
        
    }
    
    public String getRandomEventType()
    {
        String type=null;
        if(r.nextDouble()%100 > 0.5)
            type = "Admin";
        else
            type = "User";
        return type;
    }
    
    public void Simulate()
    {
        for(double rho=0.1;rho<=1;rho= rho+0.1)
        {
            lambda1 = rho * m * mu;
            double lambda = lambda1 + lambda2;
            done=false;
            Narr=0;
            Ndep=0;
            N=0;
            EN = 0;
            clock = 0;
            //insert first ARR event
            eventList.add(new EventClass("ARR",DistributedPseudoRandomNumber(lambda),getRandomEventType()));

            while(!done)
            {
                EventClass currEvent = (EventClass)eventList.remove();
                double prev = clock;
                clock = currEvent.time;
                System.out.println(currEvent.time+" "+currEvent.jobType+" "+currEvent.eventType);
                switch(currEvent.eventType)
                {
                    case "ARR" :
                        EN += N*(clock-prev);                 
                        N++;
                        Narr++;
                        if(N<l)
                        {
                            //generate any type of event
                            
                            eventList.add(new EventClass("ARR",clock + DistributedPseudoRandomNumber(lambda),getRandomEventType()));
                        }
                        else if(N>=l && N<k)
                        {
                            //generate only Admin event
                            eventList.add(new EventClass("ARR",clock + DistributedPseudoRandomNumber(lambda1),"Admin"));
                        }
                        //eventList.add(new EventClass("ARR",clock + DistributedPseudoRandomNumber(lambda1),getRandomEventType()));
                        if(N<=m && N>0)
                        {
                            eventList.add(new EventClass("DEP",clock + DistributedPseudoRandomNumber(m*mu),currEvent.jobType));
                        }
                        Collections.sort(eventList);
                        break;
                    case "DEP" :
                        EN += N*(clock-prev);  
                        N--;
                        Ndep++;
                        if(N>m-1)
                        {
                            eventList.add(new EventClass("DEP",clock + DistributedPseudoRandomNumber(m*mu),currEvent.jobType));
                        }
                         Collections.sort(eventList);
                        break;
                }
             
                if(Ndep > 1000)
                    done = true;
            }
            
            System.out.println("Current number of customers in the system : "+rho);
            System.out.println("Expected number of customers(sim) : "+EN/clock);
            Scanner in = new Scanner(System.in);
            in.nextLine();
            eventList.clear();
        }
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
