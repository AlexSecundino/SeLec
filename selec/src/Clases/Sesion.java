package Clases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sesion {
	
	private int numSesion;
	private Date fecha;
	private String diaSemana;
	private int tipo;
	private int numeroMinutos;
	private String comentarios;
	
	public Sesion(int numSesion, Date fecha, String diaSemana, int tipo,
			int numeroMinutos, String comentarios) {
		this.numSesion = numSesion;
		this.fecha = fecha;
		this.diaSemana = diaSemana;
		this.tipo = tipo;
		this.numeroMinutos = numeroMinutos;
		this.comentarios = comentarios;
	}
	
	public Sesion(int numSesion, Date fecha) {
		this.numSesion = numSesion;
		this.fecha = fecha;
	}

	public int getNumSesion() {
		return numSesion;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getDiaSemana() {
		return String.valueOf(diaSemana);
	}

	public int getTipo() {
		return tipo;
	}

	public int getNumeroMinutos() {
		return numeroMinutos;
	}

	public String getComentarios() {
		return comentarios;
	}

	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(fecha);
		return numSesion + "-" + date + "-" + diaSemana + "-" + tipo
				+ "-" + numeroMinutos + "-" + comentarios;
	}
}
