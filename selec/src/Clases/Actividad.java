package Clases;

public class Actividad {

	private int numActividad;
	private int duracionMinutos;
	private int tipoClase;
	private int subTipoClase;
	private String comentarios;
	
	public Actividad(int numActividad, int duracionMinutos, int tipoClase,
			int subTipoClase, String comentarios) {
		this.numActividad = numActividad;
		this.duracionMinutos = duracionMinutos;
		this.tipoClase = tipoClase;
		this.subTipoClase = subTipoClase;
		this.comentarios = comentarios;
	}

	public int getNumActividad() {
		return numActividad;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public int getTipoClase() {
		return tipoClase;
	}

	public int getSubTipoClase() {
		return subTipoClase;
	}

	public String getComentarios() {
		return comentarios;
	}

	public String toString() {
		return numActividad + "-" + duracionMinutos + "-" + tipoClase + "-" + subTipoClase + "-" + comentarios;
	}
	
	
}
