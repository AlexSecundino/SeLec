package Clases;

public class Instituto {

	private static int contador = 1;
	
	private int codigo;
	private String nombreInstituto;
	
	public Instituto(int codigo, String nombre)
	{
		this.codigo = codigo;
		this.nombreInstituto = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNombreInstituto() {
		return nombreInstituto;
	}
	
	public static int getContador() {
		return contador;
	}

	public static void setContador(int contador) {
		Instituto.contador = contador;
	}
	
	public static void aumentarContador() {
		contador++;
	}
	public static void reducirContador() {
		contador--;
	}

	public String toString() {
		return codigo + "-" + nombreInstituto;
	}
}
