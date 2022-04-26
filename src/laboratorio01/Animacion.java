package laboratorio01;

import java.awt.*;
import java.applet.Applet;

public class Animacion extends Applet implements Runnable {

	private static final long serialVersionUID = 1L;

	Image imagenes[];
	MediaTracker tracker;
	int indice = 0;
	Thread animacion;
	int maxAncho, maxAlto;
	Image offScrImage;

	// Componente off-screen para buffering doble
	Graphics offScrGC;

	// Nos indicar� si ya se puede pintar
	boolean cargado = false;

	// Inicializamos el applet, establecemos su tama�o y cargamos las im�genes
	public void init() {
		tracker = new MediaTracker(this);

		// Fijamos el tama�o del applet
		maxAncho = 60;
		maxAlto = 60;
		imagenes = new Image[10];

		// Establecemos el doble buffer, y dimensionamos el applet
		try {
			offScrImage = createImage(maxAncho, maxAlto);
			offScrGC = offScrImage.getGraphics();
			offScrGC.setColor(Color.LIGHT_GRAY);
			offScrGC.fillRect(0, 0, maxAncho, maxAlto);
			resize(maxAncho, maxAlto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Cargamos las im�genes en un array
		for (int i = 0; i < 10; i++) {
			imagenes[i] = getImage(getCodeBase(), "animation_"+i+".png");

			// Registramos las im�genes con el tracker
			tracker.addImage(imagenes[i], i);
		}
		try {

			// Utilizamos el tracker para comprobar que todas las im�genes est�n cargadas
			tracker.waitForAll();
		} catch (InterruptedException e) {
		}
		cargado = true;
	}

	// Pintamos el fotograma que corresponda
	public void paint(Graphics g) {
		if (cargado)
			g.drawImage(offScrImage, 0, 0, this);
	}

	// Arrancamos y establecemos la primera imagen
	public void start() {
		if (tracker.checkID(indice))
			offScrGC.drawImage(imagenes[indice], 0, 0, this);
		animacion = new Thread(this);
		animacion.start();
	}

	// Aqu� hacemos el trabajo de animaci�n
	// Muestra una imagen; para; muestra la siguiente imagen...

	public void run() {

		// Obtiene el identificador del thread
		Thread thActual = Thread.currentThread();

		// Nos aseguramos de que se ejecuta cuando estamos en un thread y adem�s es el
		// actual
		while (animacion != null && animacion == thActual) {
			if (tracker.checkID(indice)) {

				// Obtenemos la siguiente imagen
				offScrGC.drawImage(imagenes[indice], 0, 0, this);
				indice++;

				// Volvemos al principio y seguimos, para el bucle
				if (indice >= imagenes.length)
					indice = 0;
			}

			// Ralentizamos la animaci�n para que parezca normal
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}

			// Pintamos el siguiente fotograma
			repaint();
		}
	}
}