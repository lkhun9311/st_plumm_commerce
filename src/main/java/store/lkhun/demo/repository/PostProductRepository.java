package store.lkhun.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.lkhun.demo.domain.Product;

public interface PostProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}
