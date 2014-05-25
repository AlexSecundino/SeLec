package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;
import Clases.Sesion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InterfazSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private static String[][] listaSesiones = null;
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Numero Sesion", "Fecha", "Dia", "Tipo", "Duracion", "Comentarios"};
	public static DefaultTableModel modelo = null;
	private JTable table;
	
	private static Instituto institutoSeleccionado;
	private static Grupo grupoSeleccionado;
	private static Modulo moduloSeleccionado;
	private static int unidadSeleccionada;

	public InterfazSesion(final Instituto institutoSeleccionado, final Grupo grupoSeleccionado, final Modulo moduloSeleccionado, final int unidadSeleccionada) {
		setTitle("Sesion");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 702, 154);
		contentPane.add(scrollPane);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 188, 89, 23);
		contentPane.add(btnAtras);
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		table.setEnabled(false);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(BaseDeDatos.isAutorizado(Login.user, 3)){
					String nSesion = JOptionPane.showInputDialog(contentPane, "Seleccione el numSesion", "Borrar sesion", JOptionPane.QUESTION_MESSAGE);
					
					if(nSesion != null){
						if(nSesion.matches("[0-9]+")){
							int numSesion = Integer.valueOf(nSesion);
							String fecha = JOptionPane.showInputDialog(contentPane, "Seleccione la fecha", "Borrar sesion", JOptionPane.QUESTION_MESSAGE);
							if(fecha != null){
								if(fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
									SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									Date f = null;
									try {
										f = sdf.parse(fecha);
										Sesion sesion = new Sesion(numSesion, f);
										if(BaseDeDatos.existeSesion(sesion))
										{	
											if(BaseDeDatos.eliminarSesion(sesion)){
												while(InterfazSesion.modelo.getRowCount() > 0){
													InterfazSesion.modelo.removeRow(0);
												}
												InterfazSesion.addListado();
												JOptionPane.showMessageDialog(contentPane, "La sesion se ha eliminado correctamente");
											}
										}
										else{
											JOptionPane.showMessageDialog(contentPane, "La sesion seleccionada no existe");
										}
									} catch (ParseException e1) {
										JOptionPane.showMessageDialog(contentPane, "La fecha debe seguir el formato dd/mm/aaaa");
									}
								}
								else{
									JOptionPane.showMessageDialog(contentPane, "La fecha debe seguir el formato dd/mm/aaaa");
								}
							}
						}
					}
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
		btnBorrar.setBounds(623, 188, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BaseDeDatos.isAutorizado(Login.user, 1)){
					iAñadirSesion añadirSesion= new iAñadirSesion(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidadSeleccionada);
					añadirSesion.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
		btnAdd.setBounds(524, 188, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnVerActividades = new JButton("Ver actividades");
		btnVerActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nSesion = JOptionPane.showInputDialog(contentPane, "Seleccione el numSesion", "Elegir sesion", JOptionPane.QUESTION_MESSAGE);
				
				if(nSesion != null){
					if(nSesion.matches("[0-9]+")){
						int numSesion = Integer.valueOf(nSesion);
						String fecha = JOptionPane.showInputDialog(contentPane, "Seleccione la fecha", "Elegir sesion", JOptionPane.QUESTION_MESSAGE);
						if(fecha != null){
							if(fecha.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}")){
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								Date f = null;
								try {
									f = sdf.parse(fecha);
									Sesion sesion = new Sesion(numSesion, f);
									if(BaseDeDatos.existeSesion(sesion))
									{	
										InterfazActividad iActividad = new InterfazActividad(sesion);
										iActividad.setVisible(true);
									}
									else{
										JOptionPane.showMessageDialog(contentPane, "La sesion seleccionada no existe");
									}
								} catch (ParseException e1) {
									JOptionPane.showMessageDialog(contentPane, "La fecha debe seguir el formato dd/mm/aaaa");
								}
							}
							else{
								JOptionPane.showMessageDialog(contentPane, "La fecha debe seguir el formato dd/mm/aaaa");
							}
						}
					}
				}
			}
		});
		btnVerActividades.setBounds(391, 188, 123, 23);
		contentPane.add(btnVerActividades);
		
		InterfazSesion.institutoSeleccionado = institutoSeleccionado;
		InterfazSesion.grupoSeleccionado = grupoSeleccionado;
		InterfazSesion.moduloSeleccionado = moduloSeleccionado;
		InterfazSesion.unidadSeleccionada = unidadSeleccionada;
		
		addListado();
	}

	public static void addListado() {
		listaSesiones = BaseDeDatos.consultarSesiones(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidadSeleccionada);
		if(listaSesiones.length > 0){
			for(int i = 0; i < listaSesiones.length; i++)
				modelo.addRow(listaSesiones[i]);
		}
	}
}
