package laboratorio01;

public class Main {
	public static void main(String[] args) {
		CubbyHole ch = new CubbyHole();
		Thread consumidor  = new Consumidor(ch, 1);
		Thread consumidor_2  = new Consumidor(ch, 2);
		Thread productor = new Productor(ch, 0);
		productor.start(); 
		consumidor.start();
		consumidor_2.start(); 
	}
}
