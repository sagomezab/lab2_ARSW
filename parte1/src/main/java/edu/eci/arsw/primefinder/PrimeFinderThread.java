package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;


public class PrimeFinderThread extends Thread{

	
	int a,b, threadNumber;
	
	private List<Integer> primes=new LinkedList<Integer>();
	private Boolean execute = true;

	public PrimeFinderThread(int a, int b, int threadNumber) {
		super();
		this.a = a;
		this.b = b;
		this.threadNumber = threadNumber;
	}

	public synchronized void run(){
		for (int i=a;i<=b;i++){						
			if (isPrime(i)){
				primes.add(i);
				System.out.println("Thread number: " + threadNumber + " prime " + i);
				while (!execute){
					try{	
						wait();
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
		notifyAll();
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
