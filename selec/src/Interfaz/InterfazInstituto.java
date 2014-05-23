package Interfaz;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Instituto;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazInstituto extends JFrame {


	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnBorrar;
	private JButton btnExit;
	
	private JComboBox<Instituto> comboBox;

	private ArrayList<Instituto> listaInstitutos = new ArrayList<Instituto>();
	
	public InterfazInstituto() {
		setTitle("Instituto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<Instituto>();
		comboBox.setBounds(47, 53, 212, 20);
		contentPane.add(comboBox);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()< 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un instituto");
				else{
					Instituto institutoSeleccionado = (Instituto) comboBox.getSelectedItem();
					InterfazGrupo iGrupo = new InterfazGrupo(institutoSeleccionado);
					iGrupo.setVisible(true);
				}
			}
		});
		btnSeleccionar.setBounds(297, 52, 89, 23);
		contentPane.add(btnSeleccionar);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				añadirInstituto();
			}
		});
		btnAdd.setBounds(297, 86, 89, 20);
		contentPane.add(btnAdd);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()< 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un instituto");
				else{
					Instituto instituto = (Instituto) comboBox.getSelectedItem();
					BaseDeDatos.deleteInstituto(instituto);
					comboBox.removeAllItems();
					addListado();
				}
			}
		});
		btnBorrar.setBounds(297, 117, 89, 23);
		contentPane.add(btnBorrar);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(297, 151, 89, 23);
		contentPane.add(btnExit);
		
		addListado();
	}
	protected void añadirInstituto() {

		String nombre = JOptionPane.showInputDialog(contentPane, "Introduce el nombre del instituto", "Añadir instituto", JOptionPane.QUESTION_MESSAGE);
		
		if(nombre != null)
			if(!nombre.equals("")){
				Instituto instituto = new Instituto(Instituto.getContador() + 1, nombre);
				BaseDeDatos.insertarInstituto(instituto);
				Instituto.aumentarContador();
				comboBox.removeAllItems();
				addListado();
		}
	}
	private void addListado() {
		listaInstitutos = BaseDeDatos.consultarInstitutos();
		if(listaInstitutos.size() > 0){
			for(int i = 0; i < listaInstitutos.size(); i++)
				comboBox.addItem(listaInstitutos.get(i));
		}
	}

	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
	public JButton getBtnAadir() {
		return btnAdd;
	}
	public JButton getBtnBorrar() {
		return btnBorrar;
	}
	public JComboBox<Instituto> getComboBox() {
		return comboBox;
	}
}
