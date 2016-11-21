public class Person {

	//Public and private key variables
	protected long m;
	protected long e;
	private long d;
	private long n;

	/**
	    * @author Stephen Rivera-Lau
	    * Person constructor; calculate the public and private keys
	    */
	public Person()
	{
		//randomPrime to be replaced by RSA method that generates random prime nums
		long p = RSA.randomPrime();
		long q = RSA.randomPrime();
		//Make sure q and p are not the same primes
		while(q == p)
			q = RSA.randomPrime();
		//NOTE M MUST BE BIGGER THAN VALUES WE ARE ENCRYPTING
		m = p*q;
		n = (p-1) * (q-1);
		//relativelyPrime to be replaced by RSA method that returns a relative prime
		e = RSA.relativelyPrime(n);
		d = RSA.inverse(e, n);
	}

	/**
	    * @author Stephen Rivera-Lau
	    * Encrypt the message with the other person's public key
	    * @param Message message: The message to be encrypted
	    * @param Person p: The person to encrypt the message for (use their public key)
	    * @return long[]: The encrypted message
	    */
	public long[] encryptTo(String message, Person p)
	{
		long[] cipher = new long[message.length()];

		//Get other peron's public key
		long pm = p.getModulus();
		long pe = p.getExponent();

		for(int i=0; i<message.length(); i++)
		{
			cipher[i] = RSA.modPower((int)message.charAt(i), pe, pm);
		}

		return cipher;
	}

	/**
	    * @author Stephen Rivera-Lau
	    * Decrypt a message
	    * @param long[] message: The message to decrypt
	    * @return String: The plain text of the message
	    */
	public String decrypt(long[] message)
	{
		String plain = "";

		for(int i =0; i<message.length; i++)
		{
			plain += Character.toString((char)RSA.modPower(message[i], d, m));
		}

		return plain;
	}

	/**
	    * @author Stephen Rivera-Lau
	    * Get to return the public modulus
	    * @return The public modulus
	    */
	public long getModulus()
	{
		return m;
	}

	/**
	    * @author Stephen Rivera-Lau
	    * Get to return the public exponent
	    * @return The public exponent
	    */
	public long getExponent()
	{
		return e;
	}
}
