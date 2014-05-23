package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import BaseDeDatos.BaseDeDatos;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;
import Clases.Sesion;
import Clases.Usuario;

import javax.swing.JComboBox;

public class iAñadirSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtNSesion;
	private JTextField txtDate;
	private JTextField txtDuracion;
	private JTextArea txtComentarios;
	private JComboBox<String> comboBoxTipo;
	private JComboBox<String> comboBoxDia;
	
	private ArrayList<String> listaDias = new ArrayList<String>();
	private ArrayList<String> listaTipos = new ArrayList<String>();

	public iAñadirSesion(final Instituto institutoSeleccionado, final Grupo grupoSeleccionado, final Modulo moduloSeleccionado, final int unidadSeleccionada) {
		setTitle("Nueva Sesion");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumeroSesion = new JLabel("Numero sesion:");
		lblNumeroSesion.setBounds(10, 11, 103, 14);
		contentPane.add(lblNumeroSesion);
		
		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(178, 11, 41, 14);
		contentPane.add(lblFecha);
		
		JLabel lblDia = new JLabel("Dia:");
		lblDia.setBounds(300, 11, 35, 14);
		contentPane.add(lblDia);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(31, 45, 35, 14);
		contentPane.add(lblTipo);
		
		JLabel lblDuracion = new JLabel("Duracion:");
		lblDuracion.setBounds(188, 45, 61, 14);
		contentPane.add(lblDuracion);
		
		JLabel lblComentarios = new JLabel("Comentarios:");
		lblComentarios.setBounds(10, 98, 89, 14);
		contentPane.add(lblComentarios);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(55, 196, 89, 23);
		contentPane.add(btnAtras);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtNSesion.getText().isEmpty() && !txtDate.getText().isEmpty()
						&& !txtDuracion.getText().isEmpty()){
					int numSesion = Integer.valueOf(txtNSesion.getText());
					Date fecha = null;
					String diaSemana = comboBoxDia.getSelectedItem().toString();
					int tipo = BaseDeDatos.consultarTipo(comboBoxTipo.getSelectedItem().toString());
					int duracion = Integer.valueOf(txtDuracion.getText());
					String comentarios = txtComentarios.getText();
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						fecha = sdf.parse(txtDate.getText());
						Sesion sesion =
								new Sesion(numSesion,
										fecha,
										diaSemana,
										tipo,
										duracion,
										comentarios);
						/*OJO*/Usuario usuario = new Usuario("root", "a", "a");
						if(BaseDeDatos.insertarSesion(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidadSeleccionada, usuario, sesion))
						{
							JOptionPane.showMessageDialog(contentPane, "Se ha insertado correctamente");
							while(InterfazSesion.modelo.getRowCount() > 0){
								InterfazSesion.modelo.removeRow(0);
							}
							InterfazSesion.addListado();
							dispose();
						}
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(contentPane, "Fecha incorrecta. Siga el formato: dd/mm/aaaa");
					}
				}
			}
		});
		btnGuardar.setBounds(266, 196, 89, 23);
		contentPane.add(btnGuardar);
		
		txtNSesion = new JTextField();
		txtNSesion.setBounds(107, 8, 61, 20);
		contentPane.add(txtNSesion);
		txtNSesion.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(225, 8, 61, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		txtDuracion = new JTextField();
		txtDuracion.setBounds(269, 42, 86, 20);
		contentPane.add(txtDuracion);
		txtDuracion.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(119, 94, 276, 69);
		contentPane.add(scrollPane);
		
		txtComentarios = new JTextArea();
		scrollPane.setViewportView(txtComentarios);
		
		comboBoxTipo = new JComboBox<String>();
		comboBoxTipo.setBounds(76, 42, 92, 20);
		contentPane.add(comboBoxTipo);
		
		comboBoxDia = new JComboBox<String>();
		comboBoxDia.setBounds(339, 8, 56, 20);
		contentPane.add(comboBoxDia);
		
		addListadoDias();
		addListadoTipo();
	}
	private void addListadoTipo() {
		listaTipos = BaseDeDatos.consultarTipo();
			if(listaTipos.size() > 0){
				for(int i = 0; i < listaTipos.size(); i++)
					comboBoxTipo.addItem(listaTipos.get(i));
			}
	}
	private void addListadoDias() {
		listaDias = BaseDeDatos.consultarDiaSemana();
		if(listaDias.size() > 0){
			for(int i = 0; i < listaDias.size(); i++)
				comboBoxDia.addItem(listaDias.get(i));
		}
	}
	public JTextArea getTxtComentarios() {
		return txtComentarios;
	}
	public JComboBox<String> getComboBoxTipo() {
		return comboBoxTipo;
	}
	public JComboBox<String> getComboBoxDia() {
		return comboBoxDia;
	}
}
