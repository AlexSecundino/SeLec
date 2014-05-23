package Roles;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import BaseDeDatos.BaseDeDatos;
import Clases.Usuario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AdministrarFunciones extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JCheckBox chckbxModificacion;
	private JButton btnAplicar;
	private JCheckBox chckbxUpdate;
	private JCheckBox chckbxBaja;
	private JComboBox<Usuario> cbUsuarios;
	private JCheckBox chckbxInsertar;
	private JCheckBox chckbxDelete;
	private JCheckBox chckbxAlta;
	

	private ArrayList<Usuario> listaUsuarios = null;
	public static BaseDeDatos bd = null;
	private JButton btnAtras;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					bd = new BaseDeDatos();
					AdministrarFunciones frame = new AdministrarFunciones();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdministrarFunciones() {
		setTitle("Administrar Funciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		chckbxInsertar = new JCheckBox("Insertar");
		chckbxInsertar.setBounds(27, 148, 97, 23);
		contentPane.add(chckbxInsertar);
		
		chckbxUpdate = new JCheckBox("Update");
		chckbxUpdate.setBounds(136, 148, 97, 23);
		contentPane.add(chckbxUpdate);
		
		chckbxDelete = new JCheckBox("Delete");
		chckbxDelete.setBounds(249, 148, 97, 23);
		contentPane.add(chckbxDelete);
		
		chckbxAlta = new JCheckBox("Alta");
		chckbxAlta.setBounds(27, 212, 97, 23);
		contentPane.add(chckbxAlta);
		
		chckbxModificacion = new JCheckBox("Modificaci\u00F3n");
		chckbxModificacion.setBounds(136, 212, 97, 23);
		contentPane.add(chckbxModificacion);
		
		chckbxBaja = new JCheckBox("Baja");
		chckbxBaja.setBounds(249, 212, 97, 23);
		contentPane.add(chckbxBaja);
		
		btnAplicar = new JButton("Aplicar");
		btnAplicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbUsuarios.getSelectedIndex() < 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un usuario", "No ha seleccionado un usuario", 1);
				else{
					Usuario usuario = new Usuario(cbUsuarios.getSelectedItem().toString());
					boolean[] codFunciones = {chckbxInsertar.isSelected(), chckbxUpdate.isSelected(), chckbxDelete.isSelected(), chckbxAlta.isSelected(), chckbxModificacion.isSelected(), chckbxBaja.isSelected()};
					int[] funciones = new int[codFunciones.length];
					for(int i = 0; i <= codFunciones.length - 1; i++){
						if(codFunciones[i]){
							funciones[i] = i + 1;
						}
						else
							funciones[i] = 0;
					}
					BaseDeDatos.administrarFunciones(usuario, funciones);
				}
			}
		});
		btnAplicar.setBounds(343, 182, 89, 23);
		contentPane.add(btnAplicar);
		
		cbUsuarios = new JComboBox<Usuario>();
		cbUsuarios.setBounds(75, 69, 194, 23);
		contentPane.add(cbUsuarios);
		
		btnAtras = new JButton("Atras");
		btnAtras.setBounds(343, 233, 89, 23);
		contentPane.add(btnAtras);
		
		añadirUsuarios();
	}
	
	
	private void añadirUsuarios() {
		listaUsuarios = BaseDeDatos.consultarUsuarios();
		if(listaUsuarios.size() > 0){
			for(int i = 0; i < listaUsuarios.size(); i++)
				cbUsuarios.addItem(listaUsuarios.get(i));
		}
	}
}
