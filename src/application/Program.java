package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
		SellerDao sellerDao = DaoFactory.createSellerDao(); //Dessa forma eu crio um seller sem colocar o construtor
		
		System.out.println("***TEST 1: seller findById***");
		Seller seller = sellerDao.findById(3);
		
		System.out.println(seller);
		
		System.out.println("\n***TEST 2: seller findByDepartment***");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n***TEST 3: seller findAll***");
		list = sellerDao.findAll();
		for(Seller obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n***TEST 4: seller Insert***");
		Seller newSeller = new Seller(null, "Greg", "greg@greg.com.br", new Date(), 4000.00, department); //aproveitando o department do TEST2
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New Id = " + newSeller.getId());
		
		System.out.println("\n***TEST 5: seller Update***");
		seller = sellerDao.findById(1); //Reaproveitando o objeto seller ja criado acima
		seller.setName("Martha Waine");
		sellerDao.update(seller); //Atualizando o nome do Id 1
		System.out.println("Update completed!");
		
		
				
	}

}
