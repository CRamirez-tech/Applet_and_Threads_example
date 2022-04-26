package laboratorio01;

public class Contar extends Thread {

	private int contar = 0;

	public Contar(int i, String name) {
		super(name);
		contar = i;
	}

	public synchronized int getContar() {
		return contar;
	}

	@Override
	public void run() {
		while(contar<1000 && !Thread.currentThread().isInterrupted()) {
			System.out.println("Hilo "+this.getName()+" :"+ ++contar);
		}
		System.out.println("Hilo "+this.getName()+" terminado");
	}

}
