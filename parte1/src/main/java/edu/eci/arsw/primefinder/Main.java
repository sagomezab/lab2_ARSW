package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		long start = System.currentTimeMillis();

		PrimeFinderThread pft1 = new PrimeFinderThread(0, 10000000, 1);
		PrimeFinderThread pft2 = new PrimeFinderThread(10000001, 20000000, 2);
		PrimeFinderThread pft3 = new PrimeFinderThread(20000001, 30000000, 3);
		
		pft1.start();
		pft2.start();
		pft3.start();

		long end = System.currentTimeMillis();
		long elapsedTime = end - start;

		while (elapsedTime < (long) 5000){
			end = System.currentTimeMillis();
			elapsedTime = end - start; 
		}
		
		pft1.statePause();
		pft2.statePause();
		pft3.statePause();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Press enter to activate the threads...");
		String enter = scanner.nextLine();
		
		if (enter == " "){
			pft1.stateContinue();
			pft2.stateContinue();
			pft3.stateContinue();
		}


		
	}
	
}
