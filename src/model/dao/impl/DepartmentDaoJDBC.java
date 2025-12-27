package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class DepartmentDaoJDBC implements DepartmentDao {

	//Esse objeto conn fica disponivel para a toda a classe
		private Connection conn;
		
		public DepartmentDaoJDBC(Connection conn){
			this.conn = conn;
		}
	
	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO department "
					+ "(Name) "
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getName());
			
			
			int rowsAffected = st.executeUpdate();
			
			//Maior que 0 significa que uma linha ou mais foram alteradas, inseriu dados.
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);//Inserindo no obj o id
					
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}
	

	@Override
	public void update(Department obj) {

		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"UPDATE department "
					+ "SET Name = ? "
					+ "WHERE Id = ?"
					);
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId()); //Id do WHERE
			
			st.executeUpdate();
						
		}
		catch(SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department WHERE Id = ?"
					);
			
			st.setInt(1, id); //O primeiro interrogação vai receber o numero em id
			rs = st.executeQuery(); //O rs esta apontando para a posição zero se não vier nada
			
			if(rs.next()) {
				//Instanciando um departament e setando suas variaveis Id e Name
				Department obj = instantiateDepartment(rs); //Função que instancia o department
								
				//return dep;
				return obj;
			}
			return null; //Caso não retorne nada na consulta, retorna null para o Seller
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department obj = new Department();
		obj.setId(rs.getInt("Id")); //Nome da coluna no BD
		obj.setName(rs.getString("Name")); //Nome da coluna com o nome do departamento
		return obj;
	}
	
	
	
	
}
