package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private int ubicacionGalgo;
	private boolean detenerse = false; 

	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);

			synchronized(this){
				while(detenerse){
					this.wait();
				}
			}
			
			if (paso == carril.size()) {						
				carril.finish();
				synchronized (regl){
					int ubicacion=regl.getUltimaPosicionAlcanzada();
					this.ubicacionGalgo = ubicacion;
					regl.setUltimaPosicionAlcanzada(ubicacion+1);
					System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
				}
				
				if (ubicacionGalgo == 1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}

	public void detener(){
		detenerse = true;
	}

	public void siga(){
		synchronized(this){
			detenerse = false;
			notifyAll();
		}
	}

	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
