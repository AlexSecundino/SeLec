package BaseDeDatos;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Clases.Actividad;
import Clases.Grupo;
import Clases.Instituto;
import Clases.Modulo;
import Clases.Rol;
import Clases.Sesion;
import Clases.Temario;
import Clases.Usuario;

public class BaseDeDatos {
	
	private static Connection connection = null;
	
	public BaseDeDatos(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/seguimientolectivo","root","admin");
			connection.setAutoCommit(true);
		}
		catch (SQLException e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "No se ha podido establecer la conexion", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static ArrayList<Usuario> consultarUsuarios()
	{
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select usuario, nombre from usuario";
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Usuario u = new Usuario(rs.getString("usuario"), rs.getString("nombre"));
				usuarios.add(u);
			}
		} catch (SQLException e) {}
		finally{
			if (rs!= null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps!= null)
				try {
					ps.close();
				} catch (SQLException e) {}
				
		}
		return usuarios;
	}
	
	private static ArrayList<Integer> consultarFunciones(Usuario usuario){
		ArrayList<Integer> funciones = new ArrayList<Integer>();
		
		String sql = "SELECT codigoFuncion from funcionesDelUsuario where usuario = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			rs = ps.executeQuery();

			while(rs.next())
			{
				funciones.add(rs.getInt("codigoFuncion"));
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return funciones;
	}
	
	public static ArrayList<Rol> consultarRoles(Usuario usuario)
	{
		ArrayList<Rol> roles = new ArrayList<Rol>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select r.codigoRol, r.descripcion from rol r where r.codigoRol not in (select codigoRol from rolesdelusuario where usuario = ?)";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			rs = ps.executeQuery();
			while(rs.next()){
				Rol r = new Rol(rs.getInt("codigoRol"), rs.getString("descripcion"));
				roles.add(r);
			}
		} catch (SQLException e) {}
		finally{
			if (rs!= null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps!= null)
				try {
					ps.close();
				} catch (SQLException e) {}
				
		}
		return roles;
	}
	
	public static ArrayList<Rol> consultarRolesParaBorrar(Usuario usuario)
	{
		ArrayList<Rol> roles = new ArrayList<Rol>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "select r.codigoRol, descripcion from rol r INNER JOIN rolesdelusuario ru on r.codigoRol = ru.codigoRol where usuario = ?";
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			rs = ps.executeQuery();
			while(rs.next()){
				Rol r = new Rol(rs.getInt("codigoRol"), rs.getString("descripcion"));
				roles.add(r);
			}
		} catch (SQLException e) {}
		finally{
			if (rs!= null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps!= null)
				try {
					ps.close();
				} catch (SQLException e) {}
				
		}
		return roles;
	}

	public static void insertarRol(Rol rol) {
		String sql = "INSERT into rol values(?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, rol.getCodigoRol());
			ps.setString(2, rol.getDescripcion());
			if(rol.getContieneRol() != 0)
				ps.setInt(3, rol.getContieneRol());
			else
				ps.setNull(3, Types.INTEGER);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar el rol. Compruebe que no existe.", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static boolean existeRol(int codigoRol){
		boolean existe = false;
		
		PreparedStatement psExiste = null;
		ResultSet rs = null;
		
		String sql = "SELECT count(*) from rol where codigoRol = ?";
		
		try {
			psExiste = connection.prepareStatement(sql);
			psExiste.setInt(1, codigoRol);
			rs = psExiste.executeQuery();
			
			if(rs.next())
				if(rs.getInt(1) > 0)
					existe = true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al comprobar si existe el rol", "Error", 3);
		}
		finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(psExiste != null)
				try {
					psExiste.close();
				} catch (SQLException e) {}
		}
		
		return existe;
	}
	
	public static void administrarFunciones(Usuario usuario, int[] funciones){
		
		ArrayList<Integer> funcionesYaTieneElUsuario = BaseDeDatos.consultarFunciones(usuario);
		
		for(int i = 0; i <= funciones.length - 1; i++){
			if(funciones[i] != 0){
				if(funcionesYaTieneElUsuario.size() > 0){
					if(!BaseDeDatos.encontradaFuncion(funciones[i], funcionesYaTieneElUsuario)){
						int codF = funciones[i];
						insertarFuncionUsuario(usuario, codF);
					}
				}
				else{
					int codF = funciones[i];
					insertarFuncionUsuario(usuario, codF);
				}
			}
			else{
				if(funcionesYaTieneElUsuario.size() > 0){
					int codF = i + 1;
					if(BaseDeDatos.encontradaFuncion(codF, funcionesYaTieneElUsuario)){
						deleteFuncionUsuario(usuario, codF);
					}
				}
			}
		}
	}

	private static boolean encontradaFuncion(int i,
			ArrayList<Integer> funcionesYaTieneElUsuario) {
			boolean encontrado = false;
			
			for(int h = 0; h <= funcionesYaTieneElUsuario.size() - 1; h++){
				if(funcionesYaTieneElUsuario.get(h) == i){
					encontrado = true;
					break;
				}
			}
		return encontrado;
	}


	private static void insertarFuncionUsuario(Usuario usuario, int codF) {
		
		String sql = "INSERT into funcionesdelusuario values(?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setInt(2, codF);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han administrado correctamente la funcion: " + codF, "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private static void deleteFuncionUsuario(Usuario usuario, int codF) {
		
		String sql = "delete from funcionesdelusuario where usuario = ? AND codigoFuncion = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setInt(2, codF);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han administrado correctamente la funcion: " + codF, "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}

	public static void administrarRoles(Usuario usuarioSeleccionado, Rol r) {
		
		String sql = "INSERT into rolesdelusuario values(?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuarioSeleccionado.getUsuario());
			ps.setInt(2, r.getCodigoRol());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han administrado correctamente el rol: " + r.getDescripcion(), "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void administrarRolesParaBorrar(Usuario usuarioSeleccionado, Rol r) {
		
		String sql = "delete from rolesdelusuario where usuario = ? AND codigoRol = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuarioSeleccionado.getUsuario());
			ps.setInt(2, r.getCodigoRol());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha eliminado correctamente el rol: " + r.getDescripcion(), "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void añadirFuncionARol(int codigoRol, int codigoFuncion){
		
		String sql = "INSERT into funcionesdelrol values(?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, codigoRol);
			ps.setInt(2, codigoFuncion);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se han podido añadir la funcion: " + codigoFuncion + ". Compruebe si ya contiene la funcion", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	//Institutos
	public static ArrayList<Instituto> consultarInstitutos()
	{
		ArrayList<Instituto> listaInstitutos = new ArrayList<Instituto>();
		
		String sql = "SELECT * from instituto";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				Instituto instituto = new Instituto(rs.getInt("codigoInstituto"), rs.getString("descripcion"));
				listaInstitutos.add(instituto);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaInstitutos;		
	}
	
	public static void actualizarContador()
	{
		String sql = "SELECT codigoInstituto from instituto order by codigoInstituto desc limit 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				Instituto.setContador(Integer.parseInt(rs.getString("codigoInstituto")));
			}
			else
				Instituto.setContador(1);
		}
		catch (SQLException e) {}	
		finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
	}
	
	public static void insertarInstituto(Instituto instituto)
	{
		String sql = "INSERT into instituto values(?, ?)";
		
		if(!existeInstituto(instituto))
		{
		
			PreparedStatement ps = null;
		
			try {
					ps = connection.prepareStatement(sql);
					ps.setInt(1, Instituto.getContador());
					ps.setString(2, instituto.getNombreInstituto());
					ps.executeUpdate();
			} 
			catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "No se ha podido insertar el instituto", "Error", 3);
			}
			finally{
				if(ps != null){
					try {
						ps.close();
					} catch (SQLException e) {}
				}
			}
		}
		else{
			Instituto.reducirContador();
			JOptionPane.showMessageDialog(null, "El instituto " + instituto.getNombreInstituto() + " ya existe");
		}
	}
	
	public static void deleteInstituto(Instituto instituto)
	{
		String sql = "delete from instituto where codigoInstituto = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, instituto.getCodigo());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el instituto", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	private static boolean existeInstituto(Instituto instituto) {
		
		boolean existe = false;
		PreparedStatement psExiste = null;
		ResultSet rs = null;
		
		String sql = "SELECT count(*) from instituto where descripcion = ?";
		
		try {
			psExiste = connection.prepareStatement(sql);
			psExiste.setString(1, instituto.getNombreInstituto());
			rs = psExiste.executeQuery();
			
			if(rs.next())
				if(rs.getInt(1) > 0)
					existe = true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al acceder al comprobar si existe el instituto", "Error", 3);
		}
		finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(psExiste != null)
				try {
					psExiste.close();
				} catch (SQLException e) {}
		}
		return existe;
	}

	//Grupos
	
	public static ArrayList<Grupo> consultarGrupos(Instituto institutoSeleccionado)
	{
		ArrayList<Grupo> listaGrupos = new ArrayList<Grupo>();
		
		String sql = "SELECT * from grupo where codigoInstituto = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			rs = ps.executeQuery();

			while(rs.next())
			{
				Grupo grupo = new Grupo(rs.getInt("codigoGrupo"), rs.getString("curso"), rs.getString("descripcion"));
				listaGrupos.add(grupo);
			}
		}
		catch (SQLException e) {}
		finally{
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaGrupos;		
	}
	
	public static void insertarGrupo(Instituto institutoSeleccionado, Grupo grupo)
	{
		String sql = "INSERT into grupo values(?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupo.getCodigoGrupo());
			ps.setString(3, grupo.getCurso());
			ps.setString(4, grupo.getDescripcion());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar el instituto. Compruebe que no existe.", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void deleteGrupo(Instituto institutoSeleccionado, Grupo grupo)
	{
		String sql = "delete from grupo where codigoInstituto = ? AND codigoGrupo = ? AND curso = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupo.getCodigoGrupo());
			ps.setString(3, grupo.getCurso());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el grupo", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	//Modulos
	
	public static ArrayList<Modulo> consultarModulos(Instituto institutoSeleccionado, Grupo grupoSeleccionado)
	{
		ArrayList<Modulo> listaModulos = new ArrayList<Modulo>();
		
		String sql = "SELECT * from modulo where codigoInstituto = ? AND codigoGrupo = ? AND curso = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				Modulo modulo = new Modulo(rs.getInt("codigoModulo"), rs.getString("descripcion"), rs.getInt("totalHoras"));
				listaModulos.add(modulo);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaModulos;		
	}
	
	public static void insertarModulo(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo modulo)
	{
		String sql = "INSERT into modulo values(?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, modulo.getCodigoModulo());
			ps.setString(5, modulo.getDescripcion());
			ps.setInt(6, modulo.getTotalHoras());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar el modulo. Compruebe que no existe.", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void deleteModulo(Instituto institutoSeleccionado, Grupo grupo, Modulo modulo)
	{
		String sql = "delete from modulo where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupo.getCodigoGrupo());
			ps.setString(3, grupo.getCurso());
			ps.setInt(4, modulo.getCodigoModulo());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el modulo", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	//Temario
	
	public static String[][] consultarTemarios(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado)
	{
		String[][] listaTemario = null;
		int i = 0;
		
		int numeroFilas = BaseDeDatos.numeroFilasTemario(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado);
		
		String sql = "SELECT * from temario where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, moduloSeleccionado.getCodigoModulo());
			rs = ps.executeQuery();
			
			listaTemario = new String[numeroFilas][14];
			
			while(rs.next())
			{
				listaTemario[i][0] = rs.getString("unidad");
				listaTemario[i][1] = rs.getString("evaluacion");
				listaTemario[i][2] = rs.getString("horasPrevistas");
				listaTemario[i][3] = rs.getString("horasReales");
				listaTemario[i][4] = rs.getString("acumuladasPrograma");
				listaTemario[i][5] = rs.getString("acumuladasReales");
				listaTemario[i][6] = rs.getString("diferencia");
				listaTemario[i][7] = rs.getString("horasLectivas");
				listaTemario[i][8] = rs.getString("horasPractica");
				listaTemario[i][9] = rs.getString("horasTeoria");
				listaTemario[i][10] = rs.getString("horasHuelga");
				listaTemario[i][11] = rs.getString("horasFiesta");
				listaTemario[i][12] = rs.getString("horasEnfermo");
				listaTemario[i][13] = rs.getString("horasOtros");
				i++;
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaTemario;		
	}
	
	public static void insertarTemario(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo modulo, Temario temario)
	{
		String sql = "INSERT into temario(codigoInstituto, codigoGrupo, curso, codigoModulo, unidad, evaluacion, horasPrevistas) values(?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, modulo.getCodigoModulo());
			ps.setInt(5, temario.getUnidad());
			ps.setInt(6, temario.getEvaluacion());
			ps.setInt(7, temario.getHorasPrevistas());

			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar el tema. Compruebe que no existe.", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static void eliminarTemario(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado, int unidad)
	{
		String sql = "delete from temario where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ? AND unidad = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, moduloSeleccionado.getCodigoModulo());
			ps.setInt(5, unidad);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido borrar el modulo", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public static boolean existeTemario(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado, int unidad)
	{
		boolean existe = false;
		String sql = "SELECT count(*) from temario where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ? AND unidad = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
			
			try {
				ps = connection.prepareStatement(sql);
				ps.setInt(1, institutoSeleccionado.getCodigo());
				ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
				ps.setString(3, grupoSeleccionado.getCurso());
				ps.setInt(4, moduloSeleccionado.getCodigoModulo());
				ps.setInt(5, unidad);
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					if (rs.getInt(1) > 0)
						existe = true;
					
				}
			}
			catch (SQLException e) {}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {}
				if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {}
			}
		return existe;
	}
	
	private static int numeroFilasTemario(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado) {
		
		int nFilas = 0;

		String sql = "SELECT count(*) from temario where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
			
			try {
				ps = connection.prepareStatement(sql);
				ps.setInt(1, institutoSeleccionado.getCodigo());
				ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
				ps.setString(3, grupoSeleccionado.getCurso());
				ps.setInt(4, moduloSeleccionado.getCodigoModulo());
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					nFilas = rs.getInt(1);
					
				}
			}
			catch (SQLException e) {}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {}
				if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {}
			}
		return nFilas;
	}
	
	
	//Sesiones
	
	public static String[][] consultarSesiones(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado, int unidad)
	{
		String[][] listaSesiones = null;
		int i = 0;
		
		int numeroFilas = BaseDeDatos.numeroFilasSesion(institutoSeleccionado, grupoSeleccionado, moduloSeleccionado, unidad);
		
		String sql = "SELECT * from sesion where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ? AND unidad = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, moduloSeleccionado.getCodigoModulo());
			ps.setInt(5, unidad);
			rs = ps.executeQuery();
			
			listaSesiones = new String[numeroFilas][6];
			
			while(rs.next())
			{
				listaSesiones[i][0] = rs.getString("numSesion");
				listaSesiones[i][1] = rs.getString("fecha");
				listaSesiones[i][2] = rs.getString("diaSemana");
				listaSesiones[i][3] = rs.getString("tipo");
				listaSesiones[i][4] = rs.getString("numeroMinutos");
				listaSesiones[i][5] = rs.getString("comentarios");
				i++;
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaSesiones;	
	}
	
	public static boolean insertarSesion(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado, int unidad, Usuario usuario, Sesion sesion)
	{
		boolean correct = true;
		String sql = "INSERT into sesion values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, moduloSeleccionado.getCodigoModulo());
			ps.setInt(5, unidad);
			ps.setInt(6, sesion.getNumSesion());
			ps.setDate(7, f);
			ps.setString(8, usuario.getUsuario());
			ps.setString(9, sesion.getDiaSemana());
			ps.setInt(10, sesion.getTipo());
			ps.setInt(11, sesion.getNumeroMinutos());
			ps.setString(12, sesion.getComentarios());

			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar la sesión. Compruebe que no existe.", "Error", 3);
			correct = false;
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correct;
	}

	public static boolean eliminarSesion(Sesion sesion)
	{
		boolean correcto = true;
		String sql = "delete from sesion where numSesion = ? AND fecha = ?";
		
		PreparedStatement ps = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sesion.getNumSesion());
			ps.setDate(2, f);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			correcto = false;
			JOptionPane.showMessageDialog(null, "No se ha podido borrar la sesion", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correcto;
	}
	
	private static int numeroFilasSesion(Instituto institutoSeleccionado, Grupo grupoSeleccionado, Modulo moduloSeleccionado, int unidad) {
		int nFilas = 0;
		
		String sql = "SELECT count(*) from sesion where codigoInstituto = ? AND codigoGrupo = ? AND curso = ? AND codigoModulo = ? AND unidad = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, institutoSeleccionado.getCodigo());
			ps.setInt(2, grupoSeleccionado.getCodigoGrupo());
			ps.setString(3, grupoSeleccionado.getCurso());
			ps.setInt(4, moduloSeleccionado.getCodigoModulo());
			ps.setInt(5, unidad);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				nFilas = rs.getInt(1);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return nFilas;
	}

	public static boolean existeSesion(Sesion sesion) {
		boolean existe = false;
		String sql = "SELECT count(*) from sesion where numSesion = ? AND fecha = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
			
			try {
				Date f = new Date(sesion.getFecha().getTime());
				ps = connection.prepareStatement(sql);
				ps.setInt(1, sesion.getNumSesion());
				ps.setDate(2, f);
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					if (rs.getInt(1) > 0)
						existe =  true;
					
				}
			}
			catch (SQLException e) {}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {}
				if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {}
			}
		return existe;
	}
	
	//Tipo
	
	public static ArrayList<String> consultarTipo()
	{
		ArrayList<String> listaTipos = new ArrayList<>();
		String sql = "SELECT descripcion from tipo";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				listaTipos.add(rs.getString("descripcion"));
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaTipos;
	}
	
	public static int consultarTipo(String t)
	{
		int tipo = 0;
		String sql = "SELECT tipo from tipo where descripcion = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, t);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				tipo = rs.getInt(1);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return tipo;
	}
	
	//Dia Semana
	
	public static ArrayList<String> consultarDiaSemana()
	{
		ArrayList<String> listaDias = new ArrayList<String>();
		String sql = "SELECT diaSemana from DiaSemana";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				listaDias.add(rs.getString("diaSemana"));
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaDias;
	}
	
	//Actividades

	public static String[][] consultarActividades(Sesion sesion) {
		
		String[][] listaActividades = null;
		int i = 0;
		
		int numeroFilas = BaseDeDatos.numeroFilasActividad(sesion);
		
		String sql = "SELECT * from actividad where numSesion = ? AND fecha = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sesion.getNumSesion());
			ps.setDate(2, f);
			rs = ps.executeQuery();
			
			listaActividades = new String[numeroFilas][5];
			
			while(rs.next())
			{
				listaActividades[i][0] = rs.getString("numActividad");
				listaActividades[i][1] = rs.getString("duracionMinutos");
				listaActividades[i][2] = rs.getString("tipoClase");
				listaActividades[i][3] = rs.getString("subTipoClase");
				listaActividades[i][4] = rs.getString("comentarios");
				i++;
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaActividades;	
	}
	
	public static boolean insertarActividad(Sesion sesion, Actividad actividad)
	{
		boolean correct = true;
		String sql = "INSERT into actividad values(?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sesion.getNumSesion());
			ps.setDate(2, f);
			ps.setInt(3, actividad.getNumActividad());
			ps.setInt(4, actividad.getDuracionMinutos());
			ps.setInt(5, actividad.getTipoClase());
			ps.setInt(6, actividad.getSubTipoClase());
			ps.setString(7, actividad.getComentarios());

			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido insertar la actividad. Compruebe que no existe.", "Error", 3);
			correct = false;
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correct;
	}
	
	public static boolean eliminarActividad(Sesion sesion, Actividad actividad)
	{
		boolean correcto = true;
		String sql = "delete from actividad where numSesion = ? AND fecha = ? AND numActividad = ?";
		
		PreparedStatement ps = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sesion.getNumSesion());
			ps.setDate(2, f);
			ps.setInt(3, actividad.getNumActividad());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			correcto = false;
			JOptionPane.showMessageDialog(null, "No se ha podido borrar la actividad", "Error", 3);
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correcto;
	}
	
	private static int numeroFilasActividad(Sesion sesion) {

		int nFilas = 0;
		
		String sql = "SELECT count(*) from actividad where numSesion = ? AND fecha = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			Date f = new Date(sesion.getFecha().getTime());
			ps = connection.prepareStatement(sql);
			ps.setInt(1, sesion.getNumSesion());
			ps.setDate(2, f);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				nFilas = rs.getInt(1);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return nFilas;
	}
	
	public static boolean existeActividad(Sesion sesion, Actividad actividad) {
		boolean existe = false;
		String sql = "SELECT count(*) from actividad where numSesion = ? AND fecha = ? AND numActividad = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
			
			try {
				Date f = new Date(sesion.getFecha().getTime());
				ps = connection.prepareStatement(sql);
				ps.setInt(1, sesion.getNumSesion());
				ps.setDate(2, f);
				ps.setInt(3, actividad.getNumActividad());
				rs = ps.executeQuery();
				
				if(rs.next())
				{
					if (rs.getInt(1) > 0)
						existe =  true;
					
				}
			}
			catch (SQLException e) {}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {}
				if (ps != null)
					try {
						ps.close();
					} catch (SQLException e) {}
			}
		return existe;
	}
	
	public static ArrayList<String> consultarTipoClase()
	{
		ArrayList<String> listaTipos = new ArrayList<>();
		String sql = "SELECT descripcion from tipoclase";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				listaTipos.add(rs.getString("descripcion"));
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaTipos;
	}
	
	public static int consultarTipoClase(String t)
	{
		int tipoClase = 0;
		String sql = "SELECT tipoClase from tipoclase where descripcion = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, t);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				tipoClase = rs.getInt(1);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return tipoClase;
	}
	
	public static ArrayList<String> consultarSubtipoClase()
	{
		ArrayList<String> listaTipos = new ArrayList<>();
		String sql = "SELECT descripcion from subtipoclase";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				listaTipos.add(rs.getString("descripcion"));
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return listaTipos;
	}
	
	public static int consultarSubtipoClase(String t)
	{
		int subtipoClase = 0;
		String sql = "SELECT subTipoClase from subtipoclase where descripcion = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, t);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				subtipoClase = rs.getInt(1);
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return subtipoClase;
	}
	
	//Login y registro

	public static boolean login(String usuario, String password) {
		boolean correcto = false;
		
		String sql = "SELECT count(*) from usuario where usuario = ? AND contraseña = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario);
			ps.setString(2, password);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				if (rs.getInt(1) > 0)
					correcto = true;
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return correcto;
	}

	public static boolean registrar(Usuario user) {
		boolean correcto = true;
		String sql = "INSERT into usuario values(?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUsuario());
			ps.setString(2, user.getContraseña());
			ps.setString(3, user.getNombre());
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al registrarse.", "Error", 3);
			correcto = false;
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correcto;
	}

	public static boolean actualizarAcumuladas() {
		boolean correcto = true;
		
		String sql = "call acumuladas()";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.executeUpdate();
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al actualizar las acumuladas.", "Error", 3);
			correcto = false;
		}
		finally{
			if(ps != null){
				try {
					ps.close();
				} catch (SQLException e) {}
			}
		}
		return correcto;
	}
	
	public static boolean isAutorizado(Usuario usuario, int codigoFuncion){
		boolean autorizado = false;
		String sql = "select isAutorizado(?, ?)";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getUsuario());
			ps.setInt(2, codigoFuncion);
			rs = ps.executeQuery();
			
			if(rs.next())
			{
				if(rs.getBoolean(1)){
					autorizado = true;
				}
			}
		}
		catch (SQLException e) {}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {}
		}
		return autorizado;
	}
	
	public static void exit(){
		try {
			connection.close();
		} catch (SQLException e) {}
	}
}
