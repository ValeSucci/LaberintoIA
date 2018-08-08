package laberinto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Main {

	/*
	 * "Laberinto" es un programa que simula el movimiento de un agente dentro de un
	 * laberinto representado por una matriz de tamanio nxn, el cual contiene un
	 * determinado porcentaje de obstaculos (casillas) que deben ser esquivados por
	 * el agente. 
	 * El metodo de resolucion es de caracter heuristico, por lo que, en ocasiones, el 
	 * agente no logra completar el recorrido a pesar de la existencia de un camino 
	 * posible. 
	 * El recorrido se puede observar de manera grafica presionando el boton "Establecer" 
	 * para formar el laberinto y la posicion inicial, para luego iniciar la simulacion 
	 * al presionar el boton "Iniciar".
	 * Ademas, el recorrido se puede observar en la consola en forma de pares (x,y) que 
	 * representan posiciones y dicho recorrido es almacenado en un documento de texto, 
	 * cuya direccion se especifica en la variable "dirFile" perteneciente a la clase 
	 * "Laberinto".
	 */

	public static void main(String[] args) {

		JFrame frame = new JFrame("Laberinto");

		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese un entero \"n\" que delimita el tamanio de la matriz (nxn)");
		int n = sc.nextInt();
		System.out.println("Ingrese el porcentaje de elementos de la matriz que seran obstaculos");
		int porc = sc.nextInt();
		Laberinto l = new Laberinto(n, porc);

		// texto N con EditText (no se posiciona correctamente, no implementado)
		// JLabel lblN = new JLabel("Ingrese un entero \"n\" que delimita el tamanio de
		// la matriz (nxn)");
		// lblN.setLocation(1300, 400);
		// JTextField txtN = new JTextField();
		// txtN.setToolTipText("n = 1, ..., 100");
		// txtN.setSize(60, 40);
		// txtN.setLocation(1300,450);
		// txtN.setBackground(Color.DARK_GRAY);
		// String ntxt = txtN.getText();
		// System.out.println("N: " + ntxt);
		// frame.getContentPane().add(lblN);
		// frame.getContentPane().add(txtN);

		// BOTONES
		JButton btnStart = new JButton("Establecer");

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.setEnabled(false);

		// Boton start
		btnStart.addActionListener((ActionEvent event) -> {
			l.start();
			btnStart.setEnabled(false);
			btnIniciar.setEnabled(true);
		});
		btnStart.setSize(100, 50);
		btnStart.setLocation(1300, 50);
		frame.getContentPane().add(btnStart);

		// Boton iniciar
		btnIniciar.addActionListener((ActionEvent event) -> {
			l.iniciar();
			btnIniciar.setEnabled(false);
		});
		btnIniciar.setSize(120, 50);
		btnIniciar.setLocation(1300, 150);
		frame.getContentPane().add(btnIniciar);

		// frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 850);
		frame.add(l);
		frame.setVisible(true);
		frame.setResizable(false);

	}

}
