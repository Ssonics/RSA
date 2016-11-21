import java.util.ArrayList;
import java.util.Arrays;

public class RSA {

    public static void main(String args[]) {
        long e = 2983475;
        long m = 999999999;
        long eInverse = inverse(e, m);
        System.out.println("Inverse of "+ e + " mod " + m + ": " + eInverse );
        System.out.println("Inverse of "+ eInverse + " mod " + m + ": " + inverse(eInverse, m));
        
        long b = 3965;
        e = 724347;
        m = 3545234;
        System.out.println("" + b + "^" + e + " (mod " + m + ") = " + modPower(b, e, m));
        b = 7;
        e = 43;
        m = 77;
        System.out.println("" + b + "^" + e + " (mod " + m + ") = " + modPower(b, e, m));

    }
    
    public RSA() 
    {
        //empty constructor
    }
    /**
    * @author Steven Snyder
    * Find the multiplicative inverse of a long int, mod m using the Euclidean method
    * @param e The number to find the multiplicative inverse of
    * @param m The number to mod by
    * @return The positive value inverse of e (mod m)
    */
    public static long inverse(long e, long m) {
        //Columns for the Euclidean algorithm
        ArrayList<Long>r = new ArrayList<Long>(Arrays.asList(e,m));
        ArrayList<Long>q = new ArrayList<Long>(Arrays.asList((long)0,(long)0));
        ArrayList<Long>u = new ArrayList<Long>(Arrays.asList((long)0,(long)1));
        ArrayList<Long>v = new ArrayList<Long>(Arrays.asList((long)1,(long)0));

        long dividend = m;
        long divisor = e;
        int index = 2; //Start index at 2 because index 0 and 1 are already occupied.
        while(divisor > 1) {
            r.add(dividend % divisor );
            q.add(dividend / divisor );
            u.add( u.get(index - 2) - ( u.get(index - 1) * q.get(index)) );
            v.add( v.get(index - 2) - ( v.get(index - 1) * q.get(index)) );
            dividend = divisor;
            divisor = r.get(index);
            index++;
        }
        
        return (long)(m + u.get(index -1) ) % m;
    }
    
    /**
    * @author Steven Snyder
    * Raise a number, b, to a power, p, modulo m
    * @param b The number to be raised to a power
    * @param p The power to raise a number to
    * @param m The modulus 
    * @return b^p (mod m)
    */
    public static long modPower(long b, long p, long m) {
        // a list of b^(2^x) mod m where x is the index as well
        ArrayList<Long> powers = new ArrayList<Long>();
        powers.add(b); //begin the list with b^1;
        
        //the value of p to be shifted to the right to check for '1' value bits
        long exponent = p;
        
        //If a '1' bit in the 2^0 position of exponent assign value b otherwise assign 1 (accounts for b^0)
        long result = ( (exponent & 1) == 1 ) ? b : 1;         
        exponent = exponent >> 1;
        
        int index = 1;
        //While exponent has any bits with a value of 1...
        while (exponent > 0) {
            //square the previous value of [b^(2^(x-1)) (mod m)] and then (mod m) it
            //add it to the list of b^(2^x) (mod m) powers
            powers.add( (long)Math.pow(powers.get(index - 1),2) % m);
            
            //If a '1' bit in the 2^0 position of exponent...
            //     result = ([b^x (mod m)] * result) (mod m)
            if( (exponent & 1) == 1) 
                result = (powers.get(index) * result) % m;
            
            exponent = exponent >> 1;
            index++;
        }        
        
        return result;
    }
}
