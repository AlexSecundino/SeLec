package Clases;

public class Grupo {

	private int codigoGrupo;
	private String curso;
	private String descripcion;
	
	public Grupo(int codigoGrupo, String curso, String descripcion)
	{
		this.codigoGrupo = codigoGrupo;
		this.curso = curso;
		this.descripcion = descripcion;
	}

	public int getCodigoGrupo() {
		return codigoGrupo;
	}

	public String getCurso() {
		return curso;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public String toString() {
		return codigoGrupo + "-" + curso + "-" + descripcion;
	}
}
