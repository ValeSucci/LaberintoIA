package laberinto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Laberinto extends JPanel {

	private int n;
	private int porcObst;

	// Si desea cambiar la direccion en la que se almacena el camino recorrido debe
	// modificar el atributo "dirFile"
	private String dirFile = "C:/Users/Gustavo/Documents/upb/Inteligencia Artificial/caminoLaberinto.txt";

	private int tamanioF = 800;
	private boolean[][] visitados;
	private boolean[][] obstaculos;

	private int w;

	private Persona p = new Persona();

	private boolean trancado = false;

	private boolean pintarCasillas = false;

	public Laberinto(int n, int porcentaje) {
		this.n = n;
		this.porcObst = porcentaje;
		w = tamanioF / n;
		visitados = new boolean[n][n + 2];
		obstaculos = new boolean[n][n + 2];
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (pintarCasillas) {
			g2d.setColor(Color.BLUE);
			g2d.fillRect(p.getPosXAnt(), p.getPosYAnt(), w, w);
			pintarCasillas = false;
		}

		g2d.setColor(Color.BLACK);

		int posY = 15;
		for (int i = 0; i < n; i++) {
			int posX = 15;
			for (int j = 0; j < n + 2; j++) {
				if (j == 0 || j == n + 1) {
					g2d.setColor(Color.BLUE);
					g2d.fillRect(posX, posY, w, w);
				} else {
					g2d.setColor(Color.GRAY);
					g2d.drawRect(posX, posY, w, w);
				}
				posX += w;
			}
			posY += w;
		}

	}

	public void start() {
		dibujarObstaculos();
		Random rnd = new Random();
		dibujarPersona(rnd.nextInt(n));
	}

	public void dibujarObstaculos() {
		int nObst = (n * n * porcObst) / 100;
		// System.out.println("Hay " + nObst + " obstaculos");
		while (nObst > 0) {
			Random rnd = new Random();
			int posObX = rnd.nextInt(n) + 1;
			int posObY = rnd.nextInt(n);
			if (!obstaculos[posObY][posObX]) {
				obstaculos[posObY][posObX] = true;
				// System.out.println("Obs en " + (posObX) + " y " + (posObY) + " ");
				nObst--;
			}
		}
		// System.out.println("Se supone q ya lleno");

		int posY = 15;
		for (int i = 0; i < n; i++) {
			int posX = 15 + w;
			for (int j = 1; j < n + 1; j++) {
				if (obstaculos[i][j]) {
					// getGraphics().setColor(Color.RED);
					getGraphics().fillRect(posX, posY, w, w);
				}
				posX += w;
			}
			posY += w;
		}
	}

	private void dibujarPersona(int y) {
		int coordX = 15;
		int coordY = 15 + y * w;
		getGraphics().fillOval(coordX, coordY, (w - 5), (w - 5));
		p.setPosX(coordX);
		p.setPosY(coordY);
		p.setCasY(y);
	}

	public void iniciar() {

		File f = new File(dirFile);
		f.delete();

		FileWriter file = null;
		PrintWriter pw = null;

		System.out.println("Posicion: (x,y) = ");
		while (!trancado) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Escribir en documento
			try {
				file = new FileWriter(dirFile, true);
				pw = new PrintWriter(file);

				pw.println("	(" + p.getCasX() + "," + p.getCasY() + ")");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (file != null) {
						file.close();
					}
				} catch (Exception ex2) {
					ex2.printStackTrace();
				}
			}
			System.out.println("	(" + p.getCasX() + "," + p.getCasY() + ")");
			hermanito();
		}
		System.out.println();
	}

	public void hermanito() {
		int x = p.getPosX();
		int y = p.getPosY();
		int xAnt = x;
		int yAnt = y;
		int xCas = p.getCasX();
		int yCas = p.getCasY();

		p.setPosXAnt(xAnt);
		p.setPosYAnt(yAnt);

		// boolean backTrack = true;
		// while (backTrack) {
		if (xCas != n + 1) {
			/*
			 * Mover derecha si: no es la ultima casilla en x, no hay obstaculo y no se ha
			 * visitado ya, o
			 * 
			 */
			if (xCas < n + 1 && !obstaculos[yCas][xCas + 1] && !visitados[yCas][xCas + 1]) {
				// || yCas>0 && yCas < n-1 && obstaculos[yCas][xCas-1] &&
				// obstaculos[yCas+1][xCas] && obstaculos[yCas-1][xCas]) {
				x += w;
				p.setPosX(x);
				p.setCasX(xCas + 1);
				visitados[yCas][xCas + 1] = true;

				/*
				 * Mover arriba si: 
				 * no es la primera casilla en y, no hay obstaculo y: 
				 * -no ha sido visitado, o 
				 * -tiene visitados u obstaculos a la derecha, (no es la primera casilla en x y tiene obstaculos a la izquierda 
				 * o es la primera casilla en x) y (no es la ultima casilla en y y tiene (visitados u obstaculos) abajo 
				 * o es la ultima casilla en y)
				 */
			} else if (yCas > 0 && !obstaculos[yCas - 1][xCas]
					&& (!visitados[yCas - 1][xCas] || 
							(visitados[yCas][xCas + 1] || obstaculos[yCas][xCas + 1])
							&& (xCas > 0 && obstaculos[yCas][xCas - 1] || xCas == 0)
							&& (yCas < n - 1 && (visitados[yCas + 1][xCas] || obstaculos[yCas + 1][xCas])
									|| yCas == n - 1))) {

				if (yCas == n - 1 || visitados[yCas][xCas + 1] || obstaculos[yCas][xCas + 1]) {
					for (int k = 0; k < n; k++) {
						visitados[k][xCas] = false;
					}
					visitados[yCas][xCas] = true;
				}
				y -= w;
				p.setPosY(y);
				p.setCasY(yCas - 1);
				visitados[yCas - 1][xCas] = true;

				/*
				 * Mover abajo si: 
				 * no es la ultima casilla en y, no hay obstaculo y: 
				 * -no ha sido visitado, o 
				 * -tiene visitados u obstaculos a la derecha, (no es la primera casilla en x y tiene obstaculos a la izquierda 
				 * o es la primera casilla en x) y (no es la primera casilla en y y tiene (visitados u obstaculos) arriba 
				 * o es la primera casilla en y)
				 */
			} else if (yCas < n - 1 && !obstaculos[yCas + 1][xCas] 
					&& (!visitados[yCas + 1][xCas] 
							|| (visitados[yCas][xCas + 1] || obstaculos[yCas][xCas + 1])
							&& (xCas > 0 && obstaculos[yCas][xCas - 1] || xCas == 0)
							&& (yCas > 0 && (visitados[yCas - 1][xCas] || obstaculos[yCas - 1][xCas]) || yCas == 0))) {
				if (yCas == 0 || visitados[yCas][xCas + 1] || obstaculos[yCas][xCas + 1]) {
					for (int k = 0; k < n; k++) {
						visitados[k][xCas] = false;
					}
					visitados[yCas][xCas] = true;
				}
				y += w;
				p.setPosY(y);
				p.setCasY(yCas + 1);
				visitados[yCas + 1][xCas] = true;

				/*
				 * Mover izquierda si: no es la primera casilla en x y no hay obstaculo
				 */
			} else if (xCas > 0 && !obstaculos[yCas][xCas - 1]) {
				x -= w;
				p.setPosX(x);
				p.setCasX(xCas - 1);
				visitados[yCas][xCas - 1] = true;
			} else {
				trancado = true;
				System.out.println("ESTA TRANCADO D:");
			}
			if (!trancado) {
				getGraphics().fillOval(x, y, w, w);
				getGraphics().clearRect(xAnt, yAnt, w, w);
				getGraphics().drawRect(xAnt, yAnt, w, w);
			}
		} else {
			trancado = true;
			System.out.println("LLEGO AL FINAL! c:");
		}
		// }
	}

	public boolean DFS(boolean[][] mat, int o) {
		Set<Integer> visit = new HashSet<Integer>();
		visit.add(o);
		Stack<Integer> q = new Stack<>();
		q.push(o); // para aniadir un elemento a la cola
		while (!q.isEmpty()) {
			int v = q.pop();
			if ((n - 1) / v == 0) {
				return true;
			}
			for (int vpos = 0; vpos < mat.length; vpos++) {
				if (mat[v][vpos] && !visit.contains(vpos)) {
					q.push(vpos);
					visit.add(vpos);
				}
			}
		}
		return false;
	}

}
