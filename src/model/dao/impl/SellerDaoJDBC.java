package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		PreparedStatement st = null;
		try {
			
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
					);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthdate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name"
					); //Não tem o WHERE
						
			rs = st.executeQuery(); //O rs esta apontando para a posição zero se não vier nada
			
			//Como retorna uma lista, criar uma
			List<Seller> list = new ArrayList<>();
			
			//Map criado para não repetir a criação de um novo Department pelo while abaixo. Map esta vazio
			Map<Integer, Department> map = new HashMap<>();
			
			//O resultado pode ter 0 ou mais valores. Aqui usa o while
			while(rs.next()) {
				
				//Todo o DepartmentId sera salvo em um map. Toda vez que passa no while ele vai ver se o DepartmentId ja existe
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se o DepartmentId não estiver salvo na list do map, retorna null. Então crio uma nova instancia de department
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //Salva na list map o DepartmentId
				}
								
				//Instanciando um Seller e setando as variaveis
				Seller obj = instantiateSeller(rs, dep);
				
				list.add(obj);
				
			}
			return list; //Ele sempre retorna a lista
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
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name"
					);
			
			st.setInt(1, department.getId()); //O primeiro interrogação vai receber o numero em id
			rs = st.executeQuery(); //O rs esta apontando para a posição zero se não vier nada
			
			//Como retorna uma lista, criar uma
			List<Seller> list = new ArrayList<>();
			
			//Map criado para não repetir a criação de um novo Department pelo while abaixo. Map esta vazio
			Map<Integer, Department> map = new HashMap<>();
			
			//O resultado pode ter 0 ou mais valores. Aqui usa o while
			while(rs.next()) {
				
				//Todo o DepartmentId sera salvo em um map. Toda vez que passa no while ele vai ver se o DepartmentId ja existe
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				//Se o DepartmentId não estiver salvo na list do map, retorna null. Então crio uma nova instancia de department
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep); //Salva na list map o DepartmentId
				}
								
				//Instanciando um Seller e setando as variaveis
				Seller obj = instantiateSeller(rs, dep);
				
				list.add(obj);
				
			}
			return list; //Ele sempre retorna a lista
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
