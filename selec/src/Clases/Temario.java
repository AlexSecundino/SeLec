package Clases;

public class Temario {

	private int unidad;
	private int evaluacion;
	private int horasPrevistas;
	
	public Temario(int unidad, int evaluacion, int horasPrevistas)
	{
		this.unidad = unidad;
		this.evaluacion = evaluacion;
		this.horasPrevistas = horasPrevistas;
	}

	public int getUnidad() {
		return unidad;
	}

	public int getEvaluacion() {
		return evaluacion;
	}

	public int getHorasPrevistas() {
		return horasPrevistas;
	}
	
	public String toString() {
		return unidad + evaluacion + horasPrevistas + "";
	}	
}
