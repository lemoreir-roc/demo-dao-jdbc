package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// métodos estáticos auxiliares
// o Obter e fechar uma conexão com o banco
public class DB {

	//em connection apertar ctrl + espaço - ele  vai mostrar os tipos de connection para selecionar
	// selecionar o java.sql, ele vai importar a biblioteca
	private static Connection conn = null;
	
	//Conexão com o banco de dados
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = loadProperties();
				String url = props.getProperty("dburl"); //dburl é uma url e esta no arquivo db.properties
				conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;		
	}
	//fim do metodo para gerar conexão
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	//Esse metodo vai pegar as informações contias no arquivo db.properties
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.Properties")){
			Properties props = new Properties();
			props.load(fs); //Ele pega os dados do arquivo db.properties e joga no objeto props
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage()); //Caso de erro, ele vai jogar o erro que esta em DbException que foi criado
		}
	}
	
	//Para fechar o rs, st e ***, precisa de try catch
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
