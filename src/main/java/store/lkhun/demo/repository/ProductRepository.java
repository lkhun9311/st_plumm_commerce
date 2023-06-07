package store.lkhun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.lkhun.demo.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByProductName(String productName);
    void deleteByProductName(String productName);
}
