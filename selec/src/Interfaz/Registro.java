package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtNombre;
	private JTextField txtPasswd;
	
	private String usuario;
	private String nombre;
	private String password;

	public Registro() {
		setTitle("Registro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(51, 48, 93, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblNombre = new JLabel("Nombre: ");
		lblNombre.setBounds(51, 99, 93, 14);
		contentPane.add(lblNombre);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a: ");
		lblContrasea.setBounds(51, 165, 93, 14);
		contentPane.add(lblContrasea);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(184, 45, 108, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(184, 96, 108, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPasswd = new JTextField();
		txtPasswd.setBounds(184, 162, 108, 20);
		contentPane.add(txtPasswd);
		txtPasswd.setColumns(10);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuario = txtUsuario.getText();
				nombre = txtNombre.getText();
				password = txtPasswd.getText();
				
				if(!usuario.equals("") && !nombre.equals("") && !password.equals(""))
				{
					Usuario user = new Usuario(usuario, nombre, password);
					if(BaseDeDatos.registrar(user)){
						JOptionPane.showMessageDialog(contentPane, "Registro realizado con éxito");
						dispose();
					}
				}
			}
		});
		btnRegistrarse.setBounds(222, 214, 120, 23);
		contentPane.add(btnRegistrarse);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 214, 89, 23);
		contentPane.add(btnAtras);
	}
}
