package store.lkhun.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.lkhun.demo.domain.Product;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long id;
    private String productName;
    private String productPrice;
    private String imageFileUrl;

    @Builder
    public ProductResponseDto(Product product, String imageFileUrl) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productPrice = product.getProductPrice();
        this.imageFileUrl = imageFileUrl;
    }
}
