package cz.fi.muni.pa165.tasks;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	private Category electro;
	private Category kitchen;

	private Product flashlight;
	private Product kitchenRobot;
	private Product plate;


	@BeforeClass
	public void setUp(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		electro = new Category();
		electro.setName("Electro");
		em.persist(electro);

		kitchen = new Category();
		kitchen.setName("Kitchen");
		em.persist(kitchen);

		flashlight = new Product();
		flashlight.setName("Flashlight");
		electro.addProduct(flashlight);
		em.persist(flashlight);

		kitchenRobot = new Product();
		kitchenRobot.setName("Kitchen robot");
		kitchen.addProduct(kitchenRobot);
		em.persist(kitchenRobot);

		plate = new Product();
		plate.setName("Plate");
		kitchen.addProduct(plate);
		em.persist(plate);

		em.getTransaction().commit();
		em.close();
	}

	
	private void assertContainsCategoryWithName(Set<Category> categories,
			String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category "+ expectedCategoryName+ " in collection "+categories);
	}
	private void assertContainsProductWithName(Set<Product> products,
			String expectedProductName) {
		
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product "+ expectedProductName+ " in collection "+products);
	}


	@Test
	public void testKitchenRobot(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Product foundedProduct = em.find(Product.class, kitchenRobot.getId());

		assertContainsCategoryWithName(foundedProduct.getCategories(), "Kitchen");

		em.getTransaction().commit();
		em.close();
	}


	@Test
	public void testFlashlight(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Product foundedProduct = em.find(Product.class, flashlight.getId());

		assertContainsCategoryWithName(foundedProduct.getCategories(), "Electro");

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testPlate(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Product foundedProduct = em.find(Product.class, plate.getId());

		assertContainsCategoryWithName(foundedProduct.getCategories(), "Kitchen");

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testElectro(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Category foundedCategory = em.find(Category.class, electro.getId());

		assertContainsProductWithName(foundedCategory.getProducts(), "Flashlight");

		em.getTransaction().commit();
		em.close();

	}

	@Test
	public void testKitchen(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Category foundedCategory = em.find(Category.class, kitchen.getId());

		assertContainsProductWithName(foundedCategory.getProducts(), "Kitchen robot");
		assertContainsProductWithName(foundedCategory.getProducts(), "Plate");

		em.getTransaction().commit();
		em.close();
	}

	@Test(expectedExceptions= ConstraintViolationException.class)
	public void testDoesntSaveNullName(){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		Product nullNameProduct = new Product();
		nullNameProduct.setName(null);

		em.persist(nullNameProduct);

		em.getTransaction().commit();
		em.close();
	}

	
}
