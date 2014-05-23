package Clases;

public class Usuario {

	private String usuario;
	private String nombre;
	private String contraseña;
	
	public Usuario(String usuario) {
		this.usuario = usuario;
	}
	
	public Usuario(String usuario, String contraseña) {
		this.usuario = usuario;
		this.contraseña = contraseña;
	}
	
	public Usuario(String usuario, String nombre, String contraseña) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.contraseña = contraseña;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public String getContraseña() {
		return contraseña;
	}

	public String toString() {
		return usuario;
	}
	
}
