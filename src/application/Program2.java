package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;


public class Program2 {

	public static void main(String[] args) {

		DepartmentDao departmentDao = DaoFactory.createDepartmentDao(); //Dessa forma eu crio um department sem colocar o construtor
		
		/*
		System.out.println("\n***TEST 1: department Insert***");
		Department newDepartment = new Department(null, "Sports"); 
		departmentDao.insert(newDepartment);
		System.out.println("Inserted! New Id = " + newDepartment.getId());
		*/

		System.out.println("***TEST 2: seller findById***");
		Department dep = departmentDao.findById(3);
		System.out.println(dep);
		
		System.out.println("\n***TEST 3: department Update***");
		dep = departmentDao.findById(5); //Reaproveitando o objeto department ja criado acima
		dep.setName("Adventure");
		departmentDao.update(dep); //Atualizando o nome do Id 1
		System.out.println("Update completed!");
		
		System.out.println("\n***TEST 4: seller Delete***");
		departmentDao.deleteById(6);
		System.out.println("Delete completed!");
		
		System.out.println("\n***TEST 5: department findAll***");
		List <Department> list = departmentDao.findAll();
		for(Department obj : list) {
		System.out.println(obj);
		}
	}

}
