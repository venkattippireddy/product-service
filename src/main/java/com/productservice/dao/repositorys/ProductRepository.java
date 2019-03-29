
package com.productservice.dao.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.productservice.dao.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
