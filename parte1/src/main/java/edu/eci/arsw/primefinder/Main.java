package edu.eci.arsw.primefinder;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Integer i = 1;
		Boolean executing = true;
		PrimeFinderThread pft1 = new PrimeFinderThread(0, 10000000, 1, i);
		PrimeFinderThread pft2 = new PrimeFinderThread(10000001, 20000000, 2, i);
		PrimeFinderThread pft3 = new PrimeFinderThread(20000001, 30000000, 3, i);
		
		pft1.start();
		pft2.start();
		pft3.start();


		while (executing){

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Scanner scanner = new Scanner(System.in);
			System.out.println("Press enter to activate the threads...");
			String enter = scanner.nextLine();

			if (enter.trim().isEmpty()) {
				System.out.println("Resuming threads.");
				pft1.stateContinue();
				pft2.stateContinue();
				pft3.stateContinue();
				synchronized(i){
					i.notifyAll();
				}
			}

			if (Thread.activeCount() <= 1){
				executing = false;
			}
			
		}


		/*
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
		
		if (enter.trim().isEmpty()){
			System.out.println("Aca se reanuda.");
			pft1.stateContinue();
			pft2.stateContinue();
			pft3.stateContinue();
		}
		*/



		
	}
	
}
