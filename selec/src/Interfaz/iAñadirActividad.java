package Interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Actividad;
import Clases.Sesion;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class iAñadirActividad extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtNActividad;
	private JTextField txtDuracion;
	
	private ArrayList<String> listaTipoClase = new ArrayList<String>();
	private ArrayList<String> listaSubtipoClase = new ArrayList<String>();
	private JComboBox<String> cbTipoClase;
	private JComboBox<String> cbSubtipoClase;
	private JTextArea txtComentarios;

	public iAñadirActividad(final Sesion sesion) {
		setTitle("Nueva Actividad");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNumeroActividad = new JLabel("Numero Actividad:");
		lblNumeroActividad.setBounds(10, 11, 98, 14);
		contentPane.add(lblNumeroActividad);
		
		JLabel lblDuracion = new JLabel("Duracion: ");
		lblDuracion.setBounds(190, 11, 87, 14);
		contentPane.add(lblDuracion);
		
		JLabel lblTipoClase = new JLabel("Tipo Clase: ");
		lblTipoClase.setBounds(10, 49, 71, 14);
		contentPane.add(lblTipoClase);
		
		JLabel lblSubtipoClase = new JLabel("Subtipo Clase: ");
		lblSubtipoClase.setBounds(190, 49, 87, 14);
		contentPane.add(lblSubtipoClase);
		
		JLabel lblComentarios = new JLabel("Comentarios:");
		lblComentarios.setBounds(10, 129, 87, 14);
		contentPane.add(lblComentarios);
		
		txtNActividad = new JTextField();
		txtNActividad.setBounds(122, 8, 58, 20);
		contentPane.add(txtNActividad);
		txtNActividad.setColumns(10);
		
		txtDuracion = new JTextField();
		txtDuracion.setBounds(287, 8, 65, 20);
		contentPane.add(txtDuracion);
		txtDuracion.setColumns(10);
		
		cbTipoClase = new JComboBox<String>();
		cbTipoClase.setBounds(87, 46, 93, 20);
		contentPane.add(cbTipoClase);
		
		cbSubtipoClase = new JComboBox<String>();
		cbSubtipoClase.setBounds(287, 46, 87, 20);
		contentPane.add(cbSubtipoClase);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(91, 104, 263, 74);
		contentPane.add(scrollPane);
		
		txtComentarios = new JTextArea();
		scrollPane.setViewportView(txtComentarios);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAtras.setBounds(10, 233, 89, 23);
		contentPane.add(btnAtras);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtNActividad.getText().isEmpty() && !txtDuracion.getText().isEmpty()){
					int numActividad = Integer.valueOf(txtNActividad.getText());
					int tipoClase = BaseDeDatos.consultarTipoClase(cbTipoClase.getSelectedItem().toString());
					int subtipoClase = BaseDeDatos.consultarSubtipoClase(cbSubtipoClase.getSelectedItem().toString());
					int duracion = Integer.valueOf(txtDuracion.getText());
					String comentarios = txtComentarios.getText();
					Actividad actividad =
							new Actividad(numActividad,
									duracion,
									tipoClase,
									subtipoClase,
									comentarios);
					if(BaseDeDatos.insertarActividad(sesion, actividad))
					{
						JOptionPane.showMessageDialog(contentPane, "Se ha insertado correctamente");
						while(InterfazActividad.modelo.getRowCount() > 0){
							InterfazActividad.modelo.removeRow(0);
						}
						InterfazActividad.addListado();
						dispose();
					}
				}
			}
		});
		btnGuardar.setBounds(343, 233, 89, 23);
		contentPane.add(btnGuardar);
		
		addListadoTipoClase();
		addListadoSubtipoClase();
	}

	private void addListadoSubtipoClase() {
		listaTipoClase = BaseDeDatos.consultarTipoClase();
		if(listaTipoClase.size() > 0){
			for(int i = 0; i < listaTipoClase.size(); i++)
				cbTipoClase.addItem(listaTipoClase.get(i));
		}
	}

	private void addListadoTipoClase() {
		listaSubtipoClase = BaseDeDatos.consultarSubtipoClase();
		if(listaSubtipoClase.size() > 0){
			for(int i = 0; i < listaSubtipoClase.size(); i++)
				cbSubtipoClase.addItem(listaSubtipoClase.get(i));
		}
	}
	public JComboBox<String> getCbTipoClase() {
		return cbTipoClase;
	}
	public JComboBox<String> getCbSubtipoClase() {
		return cbSubtipoClase;
	}
	public JTextArea getTxtComentarios() {
		return txtComentarios;
	}
	public JTextField getTxtNActividad() {
		return txtNActividad;
	}
	public JTextField getTxtDuracion() {
		return txtDuracion;
	}
}
