package Clases;

public class Rol {

	private int codigoRol;
	private String descripcion;
	private int contieneRol = 0;
	
	public Rol(int codigoRol, String descripcion) {
		this.codigoRol = codigoRol;
		this.descripcion = descripcion;
	}

	public Rol(int codigoRol, String descripcion, int contieneRol) {
		this.codigoRol = codigoRol;
		this.descripcion = descripcion;
		this.contieneRol = contieneRol;
	}
	
	public int getCodigoRol() {
		return codigoRol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public int getContieneRol() {
		return contieneRol;
	}

	public String toString() {
		return codigoRol + "-" + descripcion;
	}
	
}
