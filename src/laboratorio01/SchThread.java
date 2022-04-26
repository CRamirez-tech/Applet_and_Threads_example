package laboratorio01;

import java.awt.*;
import java.applet.Applet;

public class SchThread extends Applet {

	private static final long serialVersionUID = 6670389091745865501L;

	Contar alto, bajo;

	public void init() {
		// Creamos un thread en 200, ya adelantado
		bajo = new Contar(200, "bajo");
		// El otro comienza desde cero
		alto = new Contar(0, "alto");
		// Al que comienza en 200 le asignamos prioridad mínima
		bajo.setPriority(Thread.MIN_PRIORITY);
		// Y al otro máxima
		alto.setPriority(Thread.MAX_PRIORITY);
		System.out.println("Prioridad alta es " + alto.getPriority());
		System.out.println("Prioridad baja es " + bajo.getPriority());
	}

	// Arrancamos los dos threads, y vamos repintando hasta que el thread que tiene
	// prioridad más alta alcanza o supera al
	// que tiene prioridad más baja, pero empezó a contar más alto.
	public void start() {
		
		bajo.start();
		alto.start();
		while (alto.getContar() < bajo.getContar()) {
			repaint();
		}
		bajo.interrupt();
		alto.interrupt();
	}

	// Vamos pintando los incrementos que realizan ambos threads
	public void paint(Graphics g) {
		g.drawString("bajo = " + bajo.getContar() + " alto = " + alto.getContar(), 20, 10);
	}
}