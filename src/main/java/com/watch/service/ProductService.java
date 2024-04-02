package com.watch.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.watch.entity.Product;

public interface ProductService {

	Page<Product> selectProductSpIdCategory(Integer id, Pageable pageable);

	Product getById(Integer id);

	void delete(Product entity);

	void deleteById(Integer id);

	Optional<Product> findById(Integer id);

	List<Product> findAll(Sort sort);

	List<Product> findAll();

	<S extends Product> S save(S entity);

	Page<Product> searchBCP(int brandId, int categoryId, double price1, double price2,Pageable page);

	Page<Product> searchBCP1(double price1, double price2,Pageable page);

	Page<Product> searchBCP2(int categoryId, double price1, double price2,Pageable page);

	Page<Product> searchBCP6(int bran,Pageable page);

	Page<Product> searchBCP3(int categoryId,Pageable page);

	Page<Product> searchBCP4(int brandId, int categoryId,Pageable page);

	Page<Product> searchBCP5(int brandId, double price1, double price2,Pageable page);


	Page<Product> selectRandom1(Pageable page);

	Page<Product> selectDateNew(Pageable page);

	Page<Product> selectBanChayNhat(Pageable page);

	Page<Product> findAll(Pageable pageable);

	List<Product> findTop2();

	Page<Product> selectAllColor(String color,Pageable pageable);

	Page<Product> selectAllWaterfoop(String water,Pageable pageable);

	Page<Product> selectAllthickness(String thickness,Pageable pageable);

	List<Product> findTop6Img();

	Page<Product> findSearch(String name,Pageable pageable);

	Page<Product> selectProductSpThongHieu(Integer id, Pageable pageable);

	Page<Product> selectProductSpLoai2(String id, Pageable pageable);

	List<Product> top10a();

	
	List<Product> findByName(String name,Boolean status);

	List<Product> findByStatus(Boolean status);

	List<Product> findByName1(String name);
	//Page<Product> findAll1(Pageable pageable);
	
	Page<Product> findByGender(Long strap,Pageable pageable);
	Page<Product> getProductByStrap(Long strap, Pageable pageable);
	Page<Product> getProductBySize(Long size, Pageable pageable);

	Product checkName(String name);
	

}
