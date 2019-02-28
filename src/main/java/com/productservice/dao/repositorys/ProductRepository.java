
package com.productservice.dao.repositorys;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.productservice.dao.entity.Product;

/**
 * The ProductRepository class used for CURD operations.
 *
 * @author Tippireddy
 * @since 26-Feb-2019
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
