package store.lkhun.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import store.lkhun.demo.dto.ProductRequestDto;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productPrice;

    @Column(nullable = false)
    private String imageFileUrl;

    private Product(String productName, String productPrice, String imageFileUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageFileUrl = imageFileUrl;
    }

    @Builder
    public Product(ProductRequestDto requestDto, String imageFileUrl) {
        this.productName = requestDto.getProductName();
        this.productPrice = requestDto.getProductPrice();
        this.imageFileUrl = imageFileUrl;
    }

    public void update(ProductRequestDto requestDto) {
        this.productName = requestDto.getProductName();
        this.productPrice = requestDto.getProductPrice();
        this.imageFileUrl = requestDto.getImageFileUrl();
    }

}
