package br.ufc.quixada.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufc.quixada.dao.jdbc.exception.ErroAbrirConexaoException;

public class ConnectionFactory {

	private static ThreadLocal<Connection> repConnection = new ThreadLocal<Connection>();
	
	public static Connection getConnection() throws ErroAbrirConexaoException{
		
		Connection connection = repConnection.get();
		if (connection == null){
			
			connection = abrirConexao();
			repConnection.set(connection);
			
			return connection;
		}else{
			return connection;
		}
		
	}

	private static final String url = "jdbc:postgresql://localhost:5432/wikihome";
	private static final String usuario= "postgres";
	private static final String senha = "postgres";
	
	private static Connection abrirConexao(){
		
		Connection connection;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url,usuario,senha);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ErroAbrirConexaoException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ErroAbrirConexaoException();

		}
		
	}
	
}
