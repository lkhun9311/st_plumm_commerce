package store.lkhun.demo.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.domain.Product;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ProductResponseDto;
import store.lkhun.demo.dto.ResponseDto;
import store.lkhun.demo.repository.ProductRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductProdService {
    private final S3UploadService s3UploadService;
    private final ProductRepository productRepository;

    public ResponseDto<?> readProdProducts() {
        List<Product> product = productRepository.findAll();
        return ResponseDto.success(product);
//        return ResponseDto.success(null);
    }

    @Transactional
    public ResponseDto<?> createProdProduct(ProductRequestDto productRequestDto, MultipartFile imageFile) throws IOException {

        String defaultImageUrl = "https://lkhun.store/img/base_image.jpg";
        String imageFileUrl = "";
        String dirName = "img/product/prod/" + productRequestDto.getProductName();

        s3UploadService.removeImageFolderInS3("img/product/prod/" + productRequestDto.getProductName());
        productRepository.deleteByProductName(productRequestDto.getProductName());

        if (imageFile == null) {
            Product product = productRepository.save(Product.builder()
                    .requestDto(productRequestDto)
                    .imageFileUrl(defaultImageUrl)
                    .build());

            return ResponseDto.success(ProductResponseDto.builder()
                    .product(product)
                    .imageFileUrl(defaultImageUrl)
                    .build());
        }

        s3UploadService.s3UploadFile(imageFile, dirName);
        imageFileUrl = "https://lkhun.store/" + dirName + "/" + imageFile.getOriginalFilename();

        Product product = productRepository.save(Product.builder()
                .requestDto(productRequestDto)
                .imageFileUrl(imageFileUrl)
                .build());

        return ResponseDto.success(ProductResponseDto.builder()
                .product(product)
                .imageFileUrl(imageFileUrl)
                .build());
    }

    @Transactional
    public ResponseDto<?> updateProdProduct(String productName, ProductRequestDto productRequestDto, MultipartFile imageFile) throws IOException {

        String defaultImageUrl = "https://lkhun.store/img/base_image.jpg";
        String imageFileUrl = "";
        String dirName = "img/product/prod/" + productRequestDto.getProductName();

        productRepository.deleteByProductName(productName);
        s3UploadService.removeImageFolderInS3("img/product/prod/" + productName);

        if (imageFile == null) {
            productRepository.deleteByProductName(productName);
            s3UploadService.removeImageFolderInS3("img/product/prod/" + productName);

            Product product = productRepository.save(Product.builder()
                    .requestDto(productRequestDto)
                    .imageFileUrl(defaultImageUrl)
                    .build());

            return ResponseDto.success(ProductResponseDto.builder()
                    .product(product)
                    .imageFileUrl(defaultImageUrl)
                    .build());
        }

        s3UploadService.s3UploadFile(imageFile, dirName);
        imageFileUrl = "https://lkhun.store/" + dirName + "/" + imageFile.getOriginalFilename();

        Product product = productRepository.save(Product.builder()
                .requestDto(productRequestDto)
                .imageFileUrl(imageFileUrl)
                .build());

        return ResponseDto.success(ProductResponseDto.builder()
                .product(product)
                .imageFileUrl(imageFileUrl)
                .build());
    }

    @Transactional
    public ResponseDto<?> deleteProdProduct(String productName) {
        Optional<Product> product = Optional.ofNullable(productRepository.findProductByProductName(productName));
        s3UploadService.removeImageFolderInS3("img/product/prod/" + product.get().getProductName());

        productRepository.deleteByProductName(productName);
        log.info("[Delete] " + product.get().getProductName() + " 상품을 DB에서 삭제했습니다.");

        return ResponseDto.success("상품을 삭제했습니다.");
    }

}
