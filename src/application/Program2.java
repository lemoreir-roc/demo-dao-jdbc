package application;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;


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
		
	}

}
