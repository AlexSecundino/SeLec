package Interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import BaseDeDatos.BaseDeDatos;
import Clases.Usuario;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtUser;
	private JPasswordField passF;
	
	private Usuario user;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseDeDatos bd = new BaseDeDatos();
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(65, 51, 101, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasenya = new JLabel("Contrase\u00F1a:");
		lblContrasenya.setBounds(65, 141, 101, 14);
		contentPane.add(lblContrasenya);
		
		txtUser = new JTextField();
		txtUser.setBounds(176, 48, 123, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		passF = new JPasswordField();
		passF.setBounds(176, 132, 123, 23);
		contentPane.add(passF);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuario = txtUser.getText();
				char[] passwd = passF.getPassword();
				String password = "";
				
				for(int i = 0; i <= passwd.length - 1; i++){
					password += passwd[i];
				}
				if(!usuario.equals("") && !password.equals("")){
					user = new Usuario(usuario, password);
					if(BaseDeDatos.login(usuario, password)){
						JOptionPane.showMessageDialog(contentPane, "Correcto");
					}
					else
						JOptionPane.showMessageDialog(contentPane, "Usuario y/o contraseña incorrectos");
				}
			}
		});
		btnLogin.setBounds(174, 190, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegistro = new JButton("Registro");
		btnRegistro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registro iRegistro = new Registro();
				iRegistro.setVisible(true);
			}
		});
		btnRegistro.setBounds(327, 190, 89, 23);
		contentPane.add(btnRegistro);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnExit.setBounds(327, 224, 89, 23);
		contentPane.add(btnExit);
	}
}
