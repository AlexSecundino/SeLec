package Interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;
import Clases.Temario;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;

public class InterfazTemario extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private String[][] listaTemario = null;
	private String[][] matrizDatos = {};
	private String[] nombreColumnas = {"Tema", "Evaluacion", "Previstas", "Reales", "Acum. Programa", "Acum. Reales", "Diferencia", "Lectivas", "Practicas", "Teoria", "Huelga", "Fiesta", "Enfermo", "Otros"};
	private DefaultTableModel modelo = null;
	private JTable table;
	
	private Instituto institutoSeleccionado;
	private Grupo grupoSeleccionado;
	private Modulo moduloSeleccionado;

	public InterfazTemario(final Instituto institutoSeleccionado, final Grupo grupoSeleccionado, final Modulo moduloSeleccionado) {
		setTitle("Estad\u00EDsticas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 187, 89, 23);
		contentPane.add(btnAtras);
		
		JButton btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int unidad = 0;
				int evaluacion = 0;
				int horasPrevistas = 0;
				
				String eva = null;
				String horas = null;
				
				String uni = JOptionPane.showInputDialog(contentPane, "Introduce la unidad", "Añadir temario", JOptionPane.QUESTION_MESSAGE);
				if (uni != null){
					if(uni.matches("[0-9]+")){
						unidad = Integer.valueOf(uni);
						eva = JOptionPane.showInputDialog(contentPane, "Introduce la evaluacion", "Añadir temario", JOptionPane.QUESTION_MESSAGE);
				
						if(eva != null){
							if(eva.matches("[1-3]")){
								evaluacion = Integer.valueOf(eva);
								horas = JOptionPane.showInputDialog(contentPane, "Introduce el numero de horas del modulo", "Añadir temario", JOptionPane.QUESTION_MESSAGE);
								if(horas != null){
									if(horas.matches("[0-9]+")){
										horasPrevistas = Integer.valueOf(horas);
										Temario temario = new Temario(unidad, evaluacion, horasPrevistas);
										BaseDeDatos.insertarTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, temario);
										while(modelo.getRowCount() > 0)
											modelo.removeRow(0);
										addListado();
									}
									else{
										JOptionPane.showMessageDialog(contentPane, "Las horas del tema deben ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
									}
								}
							}
							else{
								JOptionPane.showMessageDialog(contentPane, "La evaluacion debe ser un número comprendido entre [1-3] " + Integer.MAX_VALUE);
							}
						}
					}
					else
						JOptionPane.showMessageDialog(contentPane, "La unidad del tema debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
				}
			}
		});
		btnAdd.setBounds(794, 187, 89, 23);
		contentPane.add(btnAdd);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int unidad = 0;
				String uni = JOptionPane.showInputDialog(contentPane, "Introduce la unidad", "Eliminar tema", JOptionPane.QUESTION_MESSAGE);
				if(uni != null){
					unidad = Integer.valueOf(uni);
					if(BaseDeDatos.existeTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidad))
					{
						BaseDeDatos.eliminarTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidad);
						while(modelo.getRowCount() > 0)
							modelo.removeRow(0);
						addListado();
					}
					else{
						JOptionPane.showMessageDialog(contentPane, "El tema seleccionado no existe");
					}
				}
			}
		});
		btnBorrar.setBounds(893, 187, 89, 23);
		contentPane.add(btnBorrar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 972, 155);
		contentPane.add(scrollPane);
		

		modelo = new DefaultTableModel(matrizDatos, nombreColumnas);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(modelo);
		table.setEnabled(false);
		
		JButton btnCrearSesion = new JButton("Ver Sesiones");
		btnCrearSesion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uni = JOptionPane.showInputDialog(contentPane, "Seleccione la unidad sobre la que desea ver las sesiones", "Elegir unidad", JOptionPane.QUESTION_MESSAGE);
				
				if(uni != null){
					if(uni.matches("[0-9]+")){
						int unidad = Integer.valueOf(uni);
						if(BaseDeDatos.existeTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidad))
						{	
							InterfazSesion iSesion = new InterfazSesion(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidad);
							iSesion.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(contentPane, "El tema seleccionado no existe");
					}
				}
			}
		});
		btnCrearSesion.setBounds(651, 187, 133, 23);
		contentPane.add(btnCrearSesion);
		this.institutoSeleccionado = institutoSeleccionado;
		this.grupoSeleccionado = grupoSeleccionado;
		this.moduloSeleccionado = moduloSeleccionado;
		
		addListado();
	}

	private void addListado() {
		
		listaTemario = BaseDeDatos.consultarTemarios(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado);
		if(listaTemario.length > 0){
			for(int i = 0; i < listaTemario.length; i++)
				modelo.addRow(listaTemario[i]);
		}
		
	}
}
