package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BaseDeDatos.BaseDeDatos;
import Clases.Sesion;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazActividad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private static Sesion sesion;

	private static String[][] listaActividades = null;
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Numero Actividad", "Duracion", "Tipo", "Subtipo", "Comentarios"};
	public static DefaultTableModel modelo = null;
	private JTable table;
	private JButton btnAtras;
	private JButton btnAdd;
	private JButton btnBorrar;
	
	public InterfazActividad(final Sesion sesion) {
		setTitle("Actividad");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 672, 131);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		table.setEnabled(false);
		
		btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 170, 89, 23);
		contentPane.add(btnAtras);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iAñadirActividad añadirActividad= new iAñadirActividad(sesion);
				añadirActividad.setVisible(true);
			}
		});
		btnAdd.setBounds(494, 170, 89, 23);
		contentPane.add(btnAdd);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(593, 170, 89, 23);
		contentPane.add(btnBorrar);
		
		InterfazActividad.sesion = sesion;
		
		addListado();
	}

	public static void addListado() {
		listaActividades = BaseDeDatos.consultarActividades(sesion);
		if(listaActividades.length > 0){
			for(int i = 0; i < listaActividades.length; i++)
				modelo.addRow(listaActividades[i]);
		}
	}
}
