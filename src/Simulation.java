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
    public double m,k,seed, lambda=1;
    
    public double GenerateCongruentialRV(){
        seed = (k*seed)%m;
        double random = seed/m;
        return random;
    }
    
    public double DistributedPseudoRandomNumber()
    {
        double y;
        double rand = GenerateCongruentialRV();
        y = -(1/lambda)*Math.log(rand);
        return y;
    }
}
