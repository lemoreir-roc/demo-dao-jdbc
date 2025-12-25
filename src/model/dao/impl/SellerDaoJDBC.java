package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	//Esse objeto conn fica disponivel para a toda a classe
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn){
		this.conn = conn;
	}
	
	
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?"
					);
			
			st.setInt(1, id); //O primeiro interrogação vai receber o numero em id
			rs = st.executeQuery(); //O rs esta apontando para a posição zero se não vier nada
			
			if(rs.next()) {
				//Instanciando um departament e setando suas variaveis Id e Name
				Department dep = instantiateDepartment(rs); //Função que instancia o department
				
				//Instanciando um Seller e setando as variaveis
				Seller obj = instantiateSeller(rs, dep);
				
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

	//Função para instanciar seller, ao inves de deixar no if(rs.next)
	//Não se trata a exceção pois no if ja esta tratando, então se propaga aceitando a opçao dada pelo eclipse
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthdate(rs.getDate("BirthDate"));
		obj.setDepartment(dep); //Department ja foi instanciado e salvo na variavel dep
		return obj;
	}



	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); //Nome da coluna no BD
		dep.setName(rs.getString("DepName")); //Nome da coluna com o nome do departamento
		return dep;
	}



	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
