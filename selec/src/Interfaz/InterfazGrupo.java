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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class InterfazGrupo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JComboBox<Grupo> comboBox;
	private JButton btnSeleccionar;
	private JButton btnAdd;
	private JButton btnBorrar;
	
	private Instituto institutoSeleccionado;
	private ArrayList<Grupo> listaGrupos = new ArrayList<Grupo>();

	public InterfazGrupo(final Instituto institutoSeleccionado) {
		setTitle("Grupo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox = new JComboBox<Grupo>();
		comboBox.setBounds(35, 56, 211, 20);
		contentPane.add(comboBox);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()< 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un grupo");
				else{
					Grupo grupoSeleccionado = (Grupo) comboBox.getSelectedItem();
					InterfazModulo iModulo = new InterfazModulo(institutoSeleccionado, grupoSeleccionado);
					iModulo.setVisible(true);
				}
			}
		});
		btnSeleccionar.setBounds(291, 55, 89, 23);
		contentPane.add(btnSeleccionar);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(291, 211, 89, 23);
		contentPane.add(btnExit);
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtras.setBounds(31, 211, 89, 23);
		contentPane.add(btnAtras);
		
		btnAdd = new JButton("A\u00F1adir");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int codigoGrupo = 0;
				String curso = null;
				String descripcion = null;
				
				String cod = JOptionPane.showInputDialog(contentPane, "Introduce el codigo del grupo", "Añadir grupo", JOptionPane.QUESTION_MESSAGE);
				if (cod != null){
					if(cod.matches("[0-9]+")){
						codigoGrupo = Integer.valueOf(cod);
						curso = JOptionPane.showInputDialog(contentPane, "Introduce el curso del grupo", "Añadir grupo", JOptionPane.QUESTION_MESSAGE);
				
						if(curso != null){
							if(curso.matches("[0-9]{4}/[0-9]{4}")){
								descripcion = JOptionPane.showInputDialog(contentPane, "Introduce la descripción del grupo", "Añadir grupo", JOptionPane.QUESTION_MESSAGE);
								Grupo grupo = new Grupo(codigoGrupo, curso, descripcion);
								BaseDeDatos.insertarGrupo(institutoSeleccionado, grupo);
								comboBox.removeAllItems();
								addListado();
							}
							else
								JOptionPane.showMessageDialog(contentPane, "El curso debe seguir el formato: yyyy/yyyy");
						}
					}
					else
						JOptionPane.showMessageDialog(contentPane, "El codigo del grupo debe ser un número mayor que 0 y menor que: " + Integer.MAX_VALUE);
				}
			}
		});
		btnAdd.setBounds(291, 101, 89, 23);
		contentPane.add(btnAdd);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBox.getSelectedIndex()< 0)
					JOptionPane.showMessageDialog(contentPane, "Selecciona un grupo");
				else{
					Grupo grupo = (Grupo) comboBox.getSelectedItem();
					BaseDeDatos.deleteGrupo(institutoSeleccionado, grupo);
					comboBox.removeAllItems();
					addListado();
				}
			}
		});
		btnBorrar.setBounds(291, 148, 89, 23);
		contentPane.add(btnBorrar);
		
		this.institutoSeleccionado = institutoSeleccionado;
		addListado();
	}

	private void addListado() {
		listaGrupos = BaseDeDatos.consultarGrupos(institutoSeleccionado);
		if(listaGrupos.size() > 0){
			for(int i = 0; i < listaGrupos.size(); i++)
				comboBox.addItem(listaGrupos.get(i));
		}
	}
	public JComboBox<Grupo> getComboBox() {
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
