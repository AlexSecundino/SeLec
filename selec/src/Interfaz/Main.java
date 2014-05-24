package Interfaz;

import java.awt.EventQueue;

import BaseDeDatos.BaseDeDatos;

public class Main {

	public static BaseDeDatos bd;
	
	public static void main(String[] args) {
		bd = new BaseDeDatos();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu menu = new Menu();
					menu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
