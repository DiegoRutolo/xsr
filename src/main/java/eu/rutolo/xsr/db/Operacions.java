package eu.rutolo.xsr.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import eu.rutolo.xsr.Main;
import eu.rutolo.xsr.data.Log;

public class Operacions {

	private Connection con;
	private Random rnd;

	/**
	 * Inicia la conexión con la base de datos.
	 */
	public Operacions() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		final String url = "jdbc:mysql://" + Main.conf.getDBHost() + ":" + Main.conf.getDBPort() + "/"
				+ Main.conf.getDBName();
		try {
			con = DriverManager.getConnection(url, Main.conf.getDBUser(), Main.conf.getDBPasswd());
			Log.i("Conexión a DB correcta");
		} catch (final SQLException e) {
			e.printStackTrace();
			Log.e("Error SQL: " + e.getMessage());
			System.exit(2);
		}

		rnd = new Random();
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void close() {
		try {
			con.close();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// #region Cliente
	/**
	 * Devuelve una lista de todos los clientes.
	 * @return Lista de objetos Cliente
	 */
	public ArrayList<Cliente> listClientes() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Cliente");

			while (rs.next()) {
				Cliente c = new Cliente(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("tlf"),
						rs.getString("email"),
						rs.getString("notas")
					);
				clientes.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clientes;
	}

	/**
	 * Devuelve el cliente con id especificada
	 * @param id	Id del cliente
	 * @return		Objeto Cliente
	 */
	public Cliente getCliente(int id) {
		Cliente c = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Cliente WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				c = new Cliente(
						rs.getInt("id"),
						rs.getString("nome"),
						rs.getString("tlf"),
						rs.getString("email"),
						rs.getString("notas")
					);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Crea un nuevo cliente con id aleatoria
	 * @param nome
	 * @param tlf
	 * @param email
	 * @param notas
	 * @return	Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean addCliente(String nome, String tlf, String email, String notas) {
		int id = 0;
		do {
			id = rnd.nextInt();
		} while (getCliente(id) != null);
		
		return addCliente(new Cliente(id, nome, tlf, email, notas));
	}

	/**
	 * Crea un nuevo cliente con la id proporcionada
	 * 
	 * @param c Objeto cliente
	 * @return	Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean addCliente(Cliente c) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Cliente (id, nome, tlf, email, notas) " + 
					"VALUES (?, ?, ?, ?, ?)"
				);
			ps.setInt(1, c.getId());
			ps.setString(2, c.getNome());
			ps.setString(3, c.getTlf());
			ps.setString(4, c.getEmail());
			ps.setString(5, c.getNotas());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Actualiza los datos de un Cliente
	 * @param c	Objeto cliente con los datos nuevos
	 * @return	Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean updateCliente(Cliente c) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"UPDATE Cliente " +
					"SET nome = ?, tlf = ?, email = ?, notas = ? " +
					"WHERE id = ?"
				);
			
			ps.setString(1, c.getNome());
			ps.setString(2, c.getTlf());
			ps.setString(3, c.getEmail());
			ps.setString(4, c.getNotas());
			ps.setInt(5, c.getId());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina un cliente de la base de datis
	 * @param id	Id del cliente a eliminar
	 * @return		Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean deleteCliente(int id) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"DELETE FROM Cliente WHERE id = ?"
			);
			ps.setInt(1, id);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//#endregion

	//#region Peza
	public ArrayList<Peza> listPezas() {
		ArrayList<Peza> pezas = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Cliente");

			while (rs.next()) {
				Peza p = new Peza(
						rs.getInt("id"),
						rs.getString("codigo"),
						rs.getString("prov"),
						rs.getString("nome"),
						rs.getString("foto"),
						rs.getInt("cantidade"),
						rs.getString("notas")
					);
				pezas.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pezas;
	}

	public Peza getPeza(int id) {
		Peza p = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Peza WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				p = new Peza(
					rs.getInt("id"),
					rs.getString("codigo"),
					rs.getString("prov"),
					rs.getString("nome"),
					rs.getString("foto"),
					rs.getInt("cantidade"),
					rs.getString("notas")
				);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	public boolean addPeza(String codigo, String prov, String nome, String foto, int cantidade, String notas) {
		int id = 0;
		do {
			id = rnd.nextInt();
		} while (getCliente(id) != null);
		
		return addPeza(new Peza(id, codigo, prov, nome, foto, cantidade, notas));
	}

	public boolean addPeza(Peza p) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Peza (id, codigo, prov, nome, foto, cantidade, notas) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?)"
				);
			ps.setInt(1, p.getId());
			ps.setString(2, p.getCodigo());
			ps.setString(3, p.getProv());
			ps.setString(4, p.getNome());
			ps.setString(5, p.getFoto());
			ps.setInt(6, p.getCantidade());
			ps.setString(7, p.getNotas());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePeza(Peza p) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"UPDATE Peza " +
					"SET codigo = ?, prov = ?, nome = ?, foto = ?, cantidade = ?, notas = ? " +
					"WHERE id = ?"
				);
			ps.setString(1, p.getCodigo());
			ps.setString(2, p.getProv());
			ps.setString(3, p.getNome());
			ps.setString(4, p.getFoto());
			ps.setInt(5, p.getCantidade());
			ps.setString(6, p.getNotas());
			ps.setInt(7, p.getId());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePeza(int id) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"DELETE FROM Peza WHERE id = ?"
			);
			ps.setInt(1, id);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//#endregion
}
