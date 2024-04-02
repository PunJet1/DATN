package com.watch.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.watch.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{
	/* @Query("select o from Orders o where o.account.username=?1") */
	
	@Query(value = "select p from Product p where p.category.categoryId=?1")
	Page<Product> selectProductSpIdCategory(Integer id,Pageable pageable);

	@Query(value = "select p from Product p where p.brand.brandId=?1 and p.category.categoryId=?2 and (p.price BETWEEN ?3 AND ?4)")
	Page<Product> searchBCP(Integer brandId, Integer categoryId, Double price1, Double price2,Pageable page);
	
	@Query(value = "select p from Product p where p.price BETWEEN ?1 AND ?2")
	Page<Product> searchBCP1(double price1, double price2,Pageable page);
	
	@Query(value = "select p from Product p where p.category.categoryId=?1 and p.price BETWEEN ?2 AND ?3")
	Page<Product> searchBCP2(int categoryId, double price1, double price2,Pageable page);
	
	@Query(value = "select p from Product p where p.brand.brandId=?1")
	Page<Product> searchBCP6(int bran,Pageable page);
	
	@Query(value = "select p from Product p where p.category.categoryId=?1")
	Page<Product> searchBCP3(int categoryId,Pageable page);
	
	@Query(value = "select p from Product p where p.brand.brandId=?1 and p.category.categoryId=?2")
	Page<Product> searchBCP4(int brandId, int categoryId,Pageable page);
	
	@Query(value = "select p from Product p where p.brand.brandId=?1 and p.price BETWEEN ?2 AND ?3")
	Page<Product> searchBCP5(int brandId, double price1, double price2,Pageable page);
	
	
	@Query(value = "SELECT brand_id,product.category_id,product.create_date,description,image,product.name,\r\n"
			+ "origin,product.price,product.product_id,product.gender,product.glass_id,product.shell_id,product.size_id,product.water_id,product.strap_id,product.update_date,product.quantity,product.status\r\n"
			+ "FROM product, discount, category\r\n"
			+ "WHERE product.category_id = category.category_id and category.discount_id = discount.discount_id \r\n"
			+ "order by percent_discount desc",nativeQuery = true)
	Page<Product> selectRandom1(Pageable page);
	
	@Query(value = "select * from product order by create_date desc",nativeQuery = true)
	Page<Product> selectDateNew(Pageable page);

	@Query(value = "SELECT brand_id,category_id,product.create_date,description,image,name,\r\n"
			+ "origin,product.price,product.product_id,product.gender,product.glass_id,product.shell_id,product.size_id,product.water_id,product.strap_id,product.update_date,product.quantity,product.status, count(order_detail.product_id) as 'tongspmua'\r\n"
			+ "FROM  product ,orders, order_detail where  product.product_id = order_detail.product_id\r\n"
			+ "and  order_detail.order_id = orders.order_id\r\n"
			+ "GROUP BY brand_id,category_id,product.create_date,description,image,name,\r\n"
			+ "origin,product.price,product.product_id,product.gender,product.glass_id,product.shell_id,product.size_id,product.water_id,product.strap_id,product.update_date,product.quantity,product.status\r\n"
			+ "HAVING count(order_detail.product_id) > 0\r\n"
			+ "order by tongspmua desc",nativeQuery = true)
	Page<Product> selectBanChayNhat(Pageable page);
	
	@Query(value = "SELECT top(4) brand_id,category_id,product.create_date,description,image,name,\r\n"
			+ "			origin,product.price,product.product_id,product.gender,product.glass_id,product.shell_id,product.size_id,product.water_id,product.strap_id,product.update_date,product.quantity,product.status, count(order_detail.product_id) as 'tongspmua'\r\n"
			+ "			FROM  product ,orders, order_detail where  product.product_id = order_detail.product_id\r\n"
			+ "			and  order_detail.order_id = orders.order_id\r\n"
			+ "			GROUP BY brand_id,category_id,product.create_date,description,image,name,\r\n"
			+ "			origin,product.price,product.product_id,product.gender,product.glass_id,product.shell_id,product.size_id,product.water_id,product.strap_id,product.update_date,product.quantity,product.status\r\n"
			+ "			HAVING count(order_detail.product_id) > 0\r\n"
			+ "			order by tongspmua desc",nativeQuery = true)
	List<Product> findTop2();

	@Query(value = "select p from Product p where color =?1")
	Page<Product> selectAllColor(String color,Pageable pageable);

	@Query(value = "select p from Product p where water =?1")
	Page<Product> selectAllWaterfoop(String water,Pageable pageable);

	@Query(value = "select p from Product p where  size =?1")
	Page<Product> selectAllthickness(String thickness,Pageable pageable);
	
	@Query(nativeQuery = true, value = "SELECT TOP 6 * FROM Product o ORDER BY o.create_Date DESC")
	List<Product> findTop6Img();
	
	@Query(value = "select p from Product p where p.name like %?1% or p.category.name like %?1% or p.brand.name like %?1%")
	Page<Product> findSearch(String name,Pageable pageable);
	
	@Query(value = "SELECT TOP(5) * FROM Product\r\n"
			+ "order by create_date desc", 
			nativeQuery = true)
	//@Query("select p from Product p where p.category.id=?1")
	List<Product> top10();
	@Query(value = "select p from Product p where p.brand.id=?1")
	Page<Product> selectProductSpThongHieu(Integer id, Pageable pageable);
	@Query(value = "select p from Product p where p.category.name like %?1%")
	Page<Product> selectProductSpLoai2(String id, Pageable pageable);
	
	@Query(value = "SELECT TOP(10) * FROM Product\r\n"
			+ "order by create_date desc", 
			nativeQuery = true)
	List<Product> top10a();

	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1 and status = ?2")
	List<Product> findByName(String name,Boolean status);
	
	@Query("SELECT o FROM Product o WHERE o.status = ?1")
	List<Product> findByStatus(Boolean status);

	@Query("SELECT o FROM Product o WHERE o.name LIKE ?1")
	List<Product> findByName1(String name);
	
	@Query(value="select * from product order by create_date desc",nativeQuery = true)
	List<Product> getAllProduct();
	
	@Query(value="select * from product where gender = ?1",nativeQuery = true)
	Page<Product> getProductByGender(Long gender, Pageable pageable);
	
	@Query(value="select * from product where status = 1 and strap_id = ?1", nativeQuery = true)
	Page<Product> getProductByStrap(Long gender, Pageable pageable);
	
	@Query(value="select * from product where status = 1 and size_id = ?1",nativeQuery = true)
	Page<Product> getProductBySize (Long size, Pageable pageable);
	@Query(value="select * from product join order_detail on product.product_id = order_detail.product_id\n"
			+ "where order_detail.order_id = ?1",nativeQuery = true)
	List<Product> getProductByOrders(Integer id);
	
	@Query(value="select * from product\r\n"
			+ "inner join order_detail on product.product_id = order_detail.product_id\r\n"
			+ "where order_detail_id = ?1",nativeQuery = true)
	Product getProductByOrderDetail(Integer id);

	@Query(value="select * from product where name = ?1",nativeQuery = true)
	Product checkName(String name);
}
