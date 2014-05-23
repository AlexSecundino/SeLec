package Clases;

public class Modulo {

	private int codigoModulo = 0;
	private String descripcion;
	private int totalHoras;
	
	public Modulo(int codigoModulo, String descripcion, int totalHoras)
	{
		this.codigoModulo = codigoModulo;
		this.descripcion = descripcion;
		this.totalHoras = totalHoras;
	}

	public int getCodigoModulo() {
		return codigoModulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public String toString() {
		return codigoModulo + "-" + descripcion + "-" + totalHoras + " horas";
	}
}
