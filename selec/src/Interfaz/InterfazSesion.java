package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		setBounds(100, 100, 730, 300);
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
		btnAtras.setBounds(10, 233, 89, 23);
		contentPane.add(btnAtras);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(623, 233, 89, 23);
		contentPane.add(btnExit);
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		table.setEnabled(false);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(623, 188, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iAñadirSesion añadirSesion= new iAñadirSesion(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidadSeleccionada);
				añadirSesion.setVisible(true);
			}
		});
		btnAdd.setBounds(503, 188, 89, 23);
		contentPane.add(btnAdd);
		
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
