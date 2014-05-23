package Interfaz;

import BaseDeDatos.BaseDeDatos;

public class Main {

	public static BaseDeDatos bd;
	
	public static void main(String[] args) {
		
		bd = new BaseDeDatos();
		BaseDeDatos.actualizarContador();
		InterfazInstituto frame = new InterfazInstituto();
		frame.setVisible(true);
	}

}
