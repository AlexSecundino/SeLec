package Clases;

public class Usuario {

	private String usuario;
	private String nombre;
	private String contrase�a;
	
	public Usuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Usuario(String usuario, String contrase�a) {
		this.usuario = usuario;
		this.contrase�a = contrase�a;
	}
	
	public Usuario(String usuario, String nombre, String contrase�a) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.contrase�a = contrase�a;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public String toString() {
		return usuario;
	}
	
}
