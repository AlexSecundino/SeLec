package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import BaseDeDatos.BaseDeDatos;
import Roles.AdministrarFunciones;
import Roles.AdministrarRoles;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private static JMenuItem mntmLogin;
	private static JMenuItem mntmLogout;
	private static JMenu mnAdministrar;
	private static JMenu mnVer;
	private static JTextField txtWelcome;

	public Menu() {
		setTitle("Seguimiento Lectivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 145, 23);
		contentPane.add(menuBar);
		
		JMenu mnInicio = new JMenu("Inicio");
		menuBar.add(mnInicio);
		
		mntmLogin = new JMenuItem("Login");
		mntmLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				login.setVisible(true);
			}
		});
		mnInicio.add(mntmLogin);
		
		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.user = null;
				Menu.actualizarEstado();
			}
		});
		mnInicio.add(mntmLogout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BaseDeDatos.exit();
				System.exit(-1);
			}
		});
		mnInicio.add(mntmExit);
		
		mnAdministrar = new JMenu("Administrar");
		menuBar.add(mnAdministrar);
		
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registro registro = new Registro();
				registro.setVisible(true);
			}
		});
		mnAdministrar.add(mntmUsuarios);
		
		JMenuItem mntmFunciones = new JMenuItem("Funciones");
		mntmFunciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarFunciones iAdministrarFunciones = new AdministrarFunciones();
				iAdministrarFunciones.setVisible(true);
			}
		});
		mnAdministrar.add(mntmFunciones);
		
		JMenuItem mntmRoles = new JMenuItem("Roles");
		mntmRoles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministrarRoles iAdministrarRoles = new AdministrarRoles();
				iAdministrarRoles.setVisible(true);
			}
		});
		mnAdministrar.add(mntmRoles);
		
		mnVer = new JMenu("Ver");
		menuBar.add(mnVer);
		
		JMenuItem mntmSesiones = new JMenuItem("Sesiones");
		mntmSesiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InterfazInstituto iInterfazInstituto = new InterfazInstituto();
				iInterfazInstituto.setVisible(true);
			}
		});
		mnVer.add(mntmSesiones);
		
		txtWelcome = new JTextField();
		txtWelcome.setBounds(78, 54, 241, 23);
		contentPane.add(txtWelcome);
		txtWelcome.setColumns(10);
		txtWelcome.setVisible(false);
		txtWelcome.setEditable(false);
		
		Menu.actualizarEstado();
	}

	public static void actualizarEstado() {

		if(Login.user == null)
		{
			mntmLogout.setEnabled(false);
			mnAdministrar.setEnabled(false);
			mntmLogin.setEnabled(true);
			mnVer.setEnabled(false);
			txtWelcome.setVisible(false);
		}
		else{
			mntmLogin.setEnabled(false);
			mntmLogout.setEnabled(true);
			mnAdministrar.setEnabled(true);
			mnVer.setEnabled(true);
			txtWelcome.setText("¡Bienvenido " + Login.user.getUsuario() + "!");
			txtWelcome.setVisible(true);
		}
	}
	public JMenuItem getMntmLogin() {
		return mntmLogin;
	}
	public JMenuItem getMntmLogout() {
		return mntmLogout;
	}
	public JMenu getMnAdministrar() {
		return mnAdministrar;
	}
	public JMenu getMnVer() {
		return mnVer;
	}
	public JTextField getTxtWelcome() {
		return txtWelcome;
	}
}
