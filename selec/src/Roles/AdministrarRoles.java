package Roles;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BaseDeDatos.BaseDeDatos;
import Clases.Rol;
import Clases.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class AdministrarRoles extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private ArrayList<Rol> listaRoles = new ArrayList<Rol>();
	
	private Usuario usuarioSeleccionado;
	
	private Boolean borrar = false;
	
	private JPanel contentPane;
	private JComboBox<Rol> cbRoles;
	private JComboBox<Usuario> cbUsuarios;
	private JButton btnAplicar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseDeDatos bd = new BaseDeDatos();
					AdministrarRoles frame = new AdministrarRoles();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdministrarRoles() {
		setTitle("Administrar Roles");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cbUsuarios = new JComboBox<Usuario>();
		cbUsuarios.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				btnAplicar.setEnabled(false);
			}
		});
		cbUsuarios.setBounds(81, 36, 123, 20);
		contentPane.add(cbUsuarios);
		
		cbRoles = new JComboBox<Rol>();
		cbRoles.setBounds(81, 104, 123, 20);
		contentPane.add(cbRoles);
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!borrar){
					Rol r = (Rol) cbRoles.getSelectedItem();
					BaseDeDatos.administrarRoles(usuarioSeleccionado, r);
					addRoles(usuarioSeleccionado);
				}
				else{
					Rol r = (Rol) cbRoles.getSelectedItem();
					BaseDeDatos.administrarRolesParaBorrar(usuarioSeleccionado, r);
					addRolesParaBorrar(usuarioSeleccionado);
				}
					
			}
		});
		btnAplicar.setBounds(297, 103, 89, 23);
		contentPane.add(btnAplicar);
		btnAplicar.setEnabled(false);
		
		JButton btnAddRol = new JButton("A\u00F1adir nuevo Rol");
		btnAddRol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigoRol = 0;
				String descripcion = null;
				int contieneRol = 0;
				String cod = JOptionPane.showInputDialog(contentPane, "Introduce el codigo del rol", "Añadir rol", JOptionPane.QUESTION_MESSAGE);
				if (cod != null){
					if(cod.matches("[0-9]+")){
						codigoRol = Integer.valueOf(cod);
						descripcion = JOptionPane.showInputDialog(contentPane, "Introduce la descripcion del rol", "Añadir rol", JOptionPane.QUESTION_MESSAGE);
				
						if(descripcion != null){
							String contRol = JOptionPane.showInputDialog(contentPane, "Introduce el código del rol que contiene, en caso de contener alguno", "Añadir rol", JOptionPane.QUESTION_MESSAGE);
							if(contRol != null){
								if(!contRol.equals("")){
									if(contRol.matches("[0-9]+")){
										contieneRol = Integer.valueOf(contRol);
										Rol rol = new Rol(codigoRol, descripcion, contieneRol);
										BaseDeDatos.insertarRol(rol);
										if(usuarioSeleccionado != null)
											addRoles(usuarioSeleccionado);
									}
									else{
										JOptionPane.showMessageDialog(contentPane, "El codigo del rol debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
									}
								}
								else{
									Rol r = new Rol(codigoRol, descripcion);
									BaseDeDatos.insertarRol(r);
									if(usuarioSeleccionado != null)
										addRoles(usuarioSeleccionado);
								}
							}
							else{
								Rol r = new Rol(codigoRol, descripcion);
								BaseDeDatos.insertarRol(r);
								if(usuarioSeleccionado != null)
									addRoles(usuarioSeleccionado);
							}
								
						}
					}
					else
						JOptionPane.showMessageDialog(contentPane, "El codigo del rol debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
				}
			}
		});
		btnAddRol.setBounds(27, 179, 159, 23);
		contentPane.add(btnAddRol);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAtras.setBounds(343, 233, 89, 23);
		contentPane.add(btnAtras);
		
		JLabel lblRol = new JLabel("Rol: ");
		lblRol.setBounds(10, 107, 46, 14);
		contentPane.add(lblRol);
		
		JLabel lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setBounds(10, 39, 46, 14);
		contentPane.add(lblUsuario);
		
		JButton btnSeleccionarParaAdd = new JButton("Seleccionar para a\u00F1adir");
		btnSeleccionarParaAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = cbUsuarios.getSelectedItem().toString();
				usuarioSeleccionado = new Usuario(usuario);
				addRoles(usuarioSeleccionado);
			}
		});
		btnSeleccionarParaAdd.setBounds(234, 11, 198, 23);
		contentPane.add(btnSeleccionarParaAdd);
		
		JButton btnSeleccionarParaBorrar = new JButton("Seleccionar para borrar");
		btnSeleccionarParaBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuario = cbUsuarios.getSelectedItem().toString();
				usuarioSeleccionado = new Usuario(usuario);
				addRolesParaBorrar(usuarioSeleccionado);
			}
		});
		btnSeleccionarParaBorrar.setBounds(234, 55, 198, 23);
		contentPane.add(btnSeleccionarParaBorrar);
		
		JButton btnAddFuncionRol = new JButton("A\u00F1adir funcion a un rol");
		btnAddFuncionRol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigoRol = 0;
				int codigoFuncion = 0;
				String codRol = JOptionPane.showInputDialog(contentPane, "Introduce el codigo del rol", "Añadir funcion a un rol", JOptionPane.QUESTION_MESSAGE);
				if(codRol != null){ 
					if(codRol.matches("[0-9]+")){
						codigoRol = Integer.parseInt(codRol);
						if(BaseDeDatos.existeRol(codigoRol)){
							String codFuncion = JOptionPane.showInputDialog(contentPane, "Introduce el codigo de la funcion\n1-Insert\n2-Update\n3-Delete\n4-Alta\n5-Modificacion\n6-Baja", "Añadir funcion a un rol", JOptionPane.QUESTION_MESSAGE);
							if(codFuncion != null){
								if(codFuncion.matches("[1-6]")){
									codigoFuncion = Integer.parseInt(codFuncion);
									BaseDeDatos.añadirFuncionARol(codigoRol, codigoFuncion);
								}
								else
									JOptionPane.showMessageDialog(contentPane, "La función no existe");
							}
						}
						else
							JOptionPane.showMessageDialog(contentPane, "El rol: " + codigoRol + " no existe");
					}
					else
						JOptionPane.showMessageDialog(contentPane, "El codigo del rol debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
				}
				
			}
		});
		btnAddFuncionRol.setBounds(234, 179, 198, 23);
		contentPane.add(btnAddFuncionRol);
		
		addUsuarios();
	}

	protected void addRoles(Usuario u) {
		cbRoles.removeAllItems();
		listaRoles = null;
		listaRoles = BaseDeDatos.consultarRoles(usuarioSeleccionado);
		if(listaRoles.size() > 0){
			for(int i = 0; i < listaRoles.size(); i++)
				cbRoles.addItem(listaRoles.get(i));
			btnAplicar.setEnabled(true);
		}
		else
		{
			cbRoles.removeAllItems();
			btnAplicar.setEnabled(false);
		}
		borrar = false;
	}

	protected void addRolesParaBorrar(Usuario u) {
		cbRoles.removeAllItems();
		listaRoles = null;
		listaRoles = BaseDeDatos.consultarRolesParaBorrar(usuarioSeleccionado);
		if(listaRoles.size() > 0){
			for(int i = 0; i < listaRoles.size(); i++)
				cbRoles.addItem(listaRoles.get(i));
			btnAplicar.setEnabled(true);
		}
		else
		{
			cbRoles.removeAllItems();
			btnAplicar.setEnabled(false);
		}
		borrar = true;
	}
	
	private void addUsuarios() {
		listaUsuarios = BaseDeDatos.consultarUsuarios();
		if(listaUsuarios.size() > 0){
			for(int i = 0; i < listaUsuarios.size(); i++)
				cbUsuarios.addItem(listaUsuarios.get(i));
		}
	}
	public JComboBox<Rol> getCbRoles() {
		return cbRoles;
	}
	public JComboBox<Usuario> getCbUsuarios() {
		return cbUsuarios;
	}
	public JButton getBtnAplicar() {
		return btnAplicar;
	}
}
