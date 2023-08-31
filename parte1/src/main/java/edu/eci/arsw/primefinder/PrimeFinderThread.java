package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;


public class PrimeFinderThread extends Thread{

	
	int a,b, threadNumber;
	
	private List<Integer> primes=new LinkedList<Integer>();
	private Boolean execute = true;
	private Integer key;

	public PrimeFinderThread(int a, int b, int threadNumber, Integer key) {
		super();
		this.a = a;
		this.b = b;
		this.threadNumber = threadNumber;
		this.key = key;
	}


	public void run(){
		long start = System.currentTimeMillis();
		for (int i=a;i<=b;i++){
			long end = System.currentTimeMillis();
			long elapsedTime = end - start;
			if (elapsedTime < 5000){
				if (isPrime(i)){
					primes.add(i);
					System.out.println("Thread Number: " + threadNumber + " prime " + i);
				}	
			}
			else {
				statePause();
				synchronized (key){
					try{	
						key.wait();
						start = System.currentTimeMillis();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}				

		}
	}

	public void statePause(){
		execute = false;
	}

	public void stateContinue(){
		execute = true;
	}

	public boolean isRunning(){
		return execute;
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}
	
	
	
	
}
