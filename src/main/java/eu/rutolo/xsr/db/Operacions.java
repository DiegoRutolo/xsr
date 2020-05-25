package eu.rutolo.xsr.db;

import java.math.BigDecimal;
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
			ResultSet rs = st.executeQuery("SELECT * FROM Peza");

			while (rs.next()) {
				Peza p = new Peza(
						rs.getInt("id"),
						rs.getString("codigo"),
						rs.getString("prov"),
						rs.getString("nome"),
						rs.getString("foto"),
						rs.getBigDecimal("precio"),
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
					rs.getBigDecimal("precio"),
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

	public boolean addPeza(String codigo, String prov, String nome, String foto, BigDecimal precio, int cantidade, String notas) {
		int id = 0;
		do {
			id = rnd.nextInt();
		} while (getCliente(id) != null);
		
		return addPeza(new Peza(id, codigo, prov, nome, foto, precio, cantidade, notas));
	}

	public boolean addPeza(Peza p) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Peza (id, codigo, prov, nome, foto, precio, cantidade, notas) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
				);
			ps.setInt(1, p.getId());
			ps.setString(2, p.getCodigo());
			ps.setString(3, p.getProv());
			ps.setString(4, p.getNome());
			ps.setString(5, p.getFoto());
			ps.setString(6, p.getPrecio().toString());
			ps.setInt(7, p.getCantidade());
			ps.setString(8, p.getNotas());

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
					"SET codigo = ?, prov = ?, nome = ?, foto = ?, precio = ?, cantidade = ?, notas = ? " +
					"WHERE id = ?"
				);
			ps.setString(1, p.getCodigo());
			ps.setString(2, p.getProv());
			ps.setString(3, p.getNome());
			ps.setString(4, p.getFoto());
			ps.setString(5, p.getPrecio().toString());
			ps.setInt(6, p.getCantidade());
			ps.setString(7, p.getNotas());
			ps.setInt(8, p.getId());

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

	//#region Pedido
	public ArrayList<Pedido> listPedidos() {
		ArrayList<Pedido> pedidos = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Pedido");

			while (rs.next()) {
				Pedido p = new Pedido(
						rs.getInt("client_id"),
						rs.getInt("peza_id"),
						rs.getBigDecimal("pvp"),
						rs.getString("estado")
					);
				pedidos.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pedidos;
	}

	public Pedido getPedido(int idCliente, int idPeza) {
		Pedido pedido = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * "+
				"FROM Pedido "+
				"WHERE client_id = ? AND peza_id = ?"
			);
			ps.setInt(1, idCliente);
			ps.setInt(2, idPeza);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				pedido = new Pedido(
					rs.getInt("client_id"),
					rs.getInt("peza_id"),
					rs.getBigDecimal("pvp"),
					rs.getString("estado")
				);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pedido;
	}

	public boolean addPedido(int idCliente, int idPeza, String pvp, String estado) {
		return addPedido(new Pedido(idCliente, idPeza, pvp, estado));
	}

	public boolean addPedido(Pedido pedido) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Pedido (client_id, peza_id, pvp, estado) " + 
					"VALUES (?, ?, ?, ?)"
				);
			ps.setInt(1, pedido.getIdCliente());
			ps.setInt(2, pedido.getIdPeza());
			ps.setBigDecimal(3, pedido.getPvp());
			ps.setString(4, pedido.getEstado());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean updatePedido(Pedido pedido) {
		try {
			PreparedStatement ps = con.prepareStatement(
					"UPDATE Pedido " +
					"SET pvp = ?, estado = ? " +
					"WHERE client_id = ? AND peza_id = ?"
				);
			ps.setBigDecimal(1, pedido.getPvp());
			ps.setString(2, pedido.getEstado());
			ps.setInt(3, pedido.getIdCliente());
			ps.setInt(4, pedido.getIdPeza());

			return ps.executeUpdate() == 1 ? true : false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean deletePedido(int idCliente, int idPeza) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"DELETE FROM Pedido WHERE client_id = ? AND peza_id = ?"
			);
			ps.setInt(1, idCliente);
			ps.setInt(2, idPeza);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//#endregion

	// #region Reparacion
	/**
	 * Devuelve una lista de todas las reparaciones.
	 * @return Lista de objetos Reparacion
	 */
	public ArrayList<Reparacion> listReparaciones() {
		ArrayList<Reparacion> reparaciones = new ArrayList<>();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM Reparacion");

			while (rs.next()) {
				Reparacion rep = new Reparacion(
						rs.getInt("id"),
						rs.getDate("ini"),
						rs.getDate("fin"),
						rs.getInt("n_horas"),
						rs.getBoolean("completa"),
						rs.getString("causa"),
						rs.getString("solucion"),
						rs.getBigDecimal("pvp"),
						rs.getString("notas"),
						rs.getInt("client_id")
					);
				PreparedStatement ps = con.prepareStatement(
					"SELECT * " + 
					"FROM PezasReparacion " + 
					"WHERE reparacion_id = ?"
				);
				ps.setInt(1, rep.getId());
				ResultSet rs0 = ps.executeQuery();
				while (rs0.next()) {
					rep.addIdPeza(rs0.getInt("peza_id"));
				}
				reparaciones.add(rep);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reparaciones;
	}

	/**
	 * Devuelve la reparacion con id especificada
	 * @param id	Id de la reparacion
	 * @return		Objeto Reparacion
	 */
	public Reparacion getReparacion(int id) {
		Reparacion rep = null;
		try {
			PreparedStatement ps = con.prepareStatement(
				"SELECT * FROM Reparacion WHERE id = ?"
			);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				rep = new Reparacion(
						rs.getInt("id"),
						rs.getDate("ini"),
						rs.getDate("fin"),
						rs.getInt("n_horas"),
						rs.getBoolean("completa"),
						rs.getString("causa"),
						rs.getString("solucion"),
						rs.getBigDecimal("pvp"),
						rs.getString("notas"),
						rs.getInt("client_id")
					);
				PreparedStatement ps0 = con.prepareStatement(
					"SELECT * " + 
					"FROM PezasReparacion " + 
					"WHERE reparacion_id = ?"
				);
				ps0.setInt(1, rep.getId());
				ResultSet rs0 = ps0.executeQuery();
				while (rs0.next()) {
					rep.addIdPeza(rs0.getInt("peza_id"));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rep;
	}

	/**
	 * Crea una nueva reparacion
	 * 
	 * @param rep	Objeto Reparacion
	 * @return		Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean addReparacion(Reparacion rep) {
		// si ID == 0, ponemos uno aleatorio
		if (rep.getId() == 0) {
			int id = 0;
			do {
				id = rnd.nextInt();
			} while (getReparacion(id) != null);
			rep.setId(id);
		}

		try {
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO Reparacion (" +
						"id, ini, fin, n_horas, completa," +
						"causa, solucion, pvp, notas, client_id) " + 
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				);
			ps.setInt(1, rep.getId());
			ps.setDate(2, new java.sql.Date(rep.getIni().getTime()));
			ps.setDate(3, new java.sql.Date(rep.getFin().getTime()));
			ps.setInt(4, rep.getNhoras());
			ps.setBoolean(5, rep.isCompleta());
			ps.setString(6, rep.getCausa());
			ps.setString(7, rep.getSolucion());
			ps.setBigDecimal(8, rep.getPvp());
			ps.setString(9, rep.getNotas());
			ps.setInt(10, rep.getIdCliente());

			boolean f1 = ps.executeUpdate() == 1 ? true : false;

			for (int idPeza : rep.getIdsPezas()) {
				f1 = f1 && addReparacionPeza(rep.getId(), idPeza);
			}

			return f1;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addReparacionPeza(Reparacion rep, Peza peza) {
		return addReparacionPeza(rep.getId(), peza.getId());
	}

	public boolean addReparacionPeza(int idReparacion, int idPeza) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"INSERT INTO PezasReparacion (reparacion_id, peza_id)" +
				"VALUES (?, ?)"
				);
			ps.setInt(1, idReparacion);
			ps.setInt(2, idPeza);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Actualiza los datos de una Reparacion
	 * @param rep	Objeto Reparacion con los datos nuevos
	 * @return		Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean updateReparacion(Reparacion rep) {
		try {
			// Actualizar datos de Reparacion
			PreparedStatement ps = con.prepareStatement(
				"UPDATE Reparacion " +
				"SET ini = ?, fin = ?, n_horas = ?, " +
				"completa = ?, causa = ?, solucion = ?, " +
				"pvp = ?, notas = ?, client_id = ? " +
				"WHERE id = ?"
			);
			
			ps.setDate(1, new java.sql.Date(rep.getIni().getTime()));
			ps.setDate(2, new java.sql.Date(rep.getFin().getTime()));
			ps.setInt(3, rep.getNhoras());
			ps.setBoolean(4, rep.isCompleta());
			ps.setString(5, rep.getCausa());
			ps.setString(6, rep.getSolucion());
			ps.setBigDecimal(7, rep.getPvp());
			ps.setString(8, rep.getNotas());
			ps.setInt(9, rep.getIdCliente());
			ps.setInt(10, rep.getId());

			boolean f1 = ps.executeUpdate() == 1 ? true : false;
			
			// Eliminar todas las piezas anteriores
			PreparedStatement ps0 = con.prepareStatement(
				"DELETE FROM PezasReparacion " + 
				"WHERE reparacion_id = ?"
			);
			ps0.setInt(1, rep.getId());
			ps0.executeUpdate();
			
			// Guardar las piezas nuevas
			for (int idPeza : rep.getIdsPezas()) {
				f1 = f1 && addReparacionPeza(rep.getId(), idPeza);
			}

			return f1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina una reparacion de la base de datos
	 * @param id	Id de la reparacion a eliminar
	 * @return		Verdadero en caso de éxito, falso en caso de fallo.
	 */
	public boolean deleteReparacion(int id) {
		try {
			PreparedStatement ps = con.prepareStatement(
				"DELETE FROM Reparacion WHERE id = ?"
			);
			ps.setInt(1, id);

			return ps.executeUpdate() == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	//#endregion

}
