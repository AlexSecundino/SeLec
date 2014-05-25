package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BaseDeDatos.BaseDeDatos;
import Clases.Actividad;
import Clases.Sesion;

import javax.swing.JOptionPane;
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
		setBounds(100, 100, 700, 250);
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
				if(BaseDeDatos.isAutorizado(Login.user, 1)){
					iAñadirActividad añadirActividad= new iAñadirActividad(sesion);
					añadirActividad.setVisible(true);
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
		btnAdd.setBounds(494, 170, 89, 23);
		contentPane.add(btnAdd);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(BaseDeDatos.isAutorizado(Login.user, 3)){
					String nActividad = JOptionPane.showInputDialog(contentPane, "Seleccione el numero de actividad", "Borrar actividad", JOptionPane.QUESTION_MESSAGE);
					
					if(nActividad != null){
						if(nActividad.matches("[0-9]+")){
							int numActividad = Integer.valueOf(nActividad);
							Actividad actividad = new Actividad(numActividad);
							if(BaseDeDatos.existeActividad(sesion, actividad))
							{	
									if(BaseDeDatos.eliminarActividad(sesion, actividad)){
										while(InterfazActividad.modelo.getRowCount() > 0){
											InterfazActividad.modelo.removeRow(0);
										}
										InterfazActividad.addListado();
										JOptionPane.showMessageDialog(contentPane, "La actividad se ha eliminado correctamente");
									}
							}
							else{
								JOptionPane.showMessageDialog(contentPane, "La actividad seleccionada no existe");
							}
						}
						else
							JOptionPane.showMessageDialog(contentPane, "El numero de actividad debe de ser un numero mayor que 0 y menor que: " + Integer.MAX_VALUE);
					}
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
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
