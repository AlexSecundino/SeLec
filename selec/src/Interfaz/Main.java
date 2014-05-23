package Interfaz;

import java.awt.EventQueue;

import BaseDeDatos.BaseDeDatos;

public class Main {

	public static BaseDeDatos bd;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bd = new BaseDeDatos();
					Menu menu = new Menu();
					menu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
