package Interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InterfazModulo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	private ArrayList<Modulo> listaModulos = new ArrayList<Modulo>();
	private JComboBox<Modulo> comboBox;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnBorrar;
	
	private Instituto institutoSeleccionado;
	private Grupo grupoSeleccionado;
	
	public InterfazModulo(final Instituto institutoSeleccionado, final Grupo grupoSeleccionado) {
		setTitle("Modulo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 425, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<Modulo>();
		comboBox.setBounds(10, 40, 255, 20);
		contentPane.add(comboBox);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()< 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un modulo");
				else{
					Modulo moduloSeleccionado = (Modulo) comboBox.getSelectedItem();
					InterfazTemario iTemario = new InterfazTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado);
					iTemario.setVisible(true);
					dispose();
				}
			}
		});
		btnSeleccionar.setBounds(312, 39, 89, 23);
		contentPane.add(btnSeleccionar);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BaseDeDatos.isAutorizado(Login.user, 1)){
					int codigoModulo = 0;
					String descripcion = null;
					int totalHoras = 0;
					String horas = null;
					
					String cod = JOptionPane.showInputDialog(contentPane, "Introduce el codigo del modulo", "Añadir modulo", JOptionPane.QUESTION_MESSAGE);
					if (cod != null){
						if(cod.matches("[0-9]+")){
							codigoModulo = Integer.valueOf(cod);
							descripcion = JOptionPane.showInputDialog(contentPane, "Introduce la descripcion del modulo", "Añadir modulo", JOptionPane.QUESTION_MESSAGE);
					
							if(descripcion != null){
								horas = JOptionPane.showInputDialog(contentPane, "Introduce el numero de horas del modulo", "Añadir modulo", JOptionPane.QUESTION_MESSAGE);
								if(horas != null){
									if(horas.matches("[0-9]+")){
											totalHoras = Integer.valueOf(horas);
											Modulo modulo = new Modulo(codigoModulo, descripcion, totalHoras);
											BaseDeDatos.insertarModulo(institutoSeleccionado, grupoSeleccionado, modulo);
											comboBox.removeAllItems();
											addListado();
									}
									else{
										JOptionPane.showMessageDialog(contentPane, "Las horas del modulo deben ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
									}
								}
							}
						}
						else
							JOptionPane.showMessageDialog(contentPane, "El codigo del modulo debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
					}
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
		btnAdd.setBounds(10, 85, 89, 23);
		contentPane.add(btnAdd);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(BaseDeDatos.isAutorizado(Login.user, 3)){
					if(comboBox.getSelectedIndex()< 0)
						JOptionPane.showMessageDialog(contentPane, "Selecciona un modulo");
					else{
						Modulo modulo = (Modulo) comboBox.getSelectedItem();
						BaseDeDatos.deleteModulo(institutoSeleccionado, grupoSeleccionado, modulo);
						comboBox.removeAllItems();
						addListado();
					}
				}
				else
					JOptionPane.showMessageDialog(contentPane, "No tiene permisos");
			}
		});
		btnBorrar.setBounds(160, 85, 89, 23);
		contentPane.add(btnBorrar);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(312, 85, 89, 23);
		contentPane.add(btnAtras);
		
		this.grupoSeleccionado = grupoSeleccionado;
		this.institutoSeleccionado = institutoSeleccionado;
		addListado();
	}

	private void addListado() {
		
		listaModulos = BaseDeDatos.consultarModulos(institutoSeleccionado, grupoSeleccionado);
		if(listaModulos.size() > 0){
			for(int i = 0; i < listaModulos.size(); i++)
				comboBox.addItem(listaModulos.get(i));
		}
	}
	public JComboBox<Modulo> getComboBox() {
		return comboBox;
	}
	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
	public JButton getBtnAdd() {
		return btnAdd;
	}
	public JButton getBtnBorrar() {
		return btnBorrar;
	}
}

