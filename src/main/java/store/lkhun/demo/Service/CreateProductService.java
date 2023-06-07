package store.lkhun.demo.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.domain.Product;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ProductResponseDto;
import store.lkhun.demo.dto.ResponseDto;
import store.lkhun.demo.repository.PostProductRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreateProductService {
    private final S3UploadService s3UploadService;
    private final PostProductRepository postProductRepository;

    @Transactional
    public ResponseDto<?> createProduct(ProductRequestDto requestDto, MultipartFile imageFile) throws IOException {

        String defaultImageUrl = "https://st-plumm-dev.s3.ap-northeast-2.amazonaws.com/img/base_image.jpg";
        String imageFileUrl = "";

        if (imageFile == null) {
            Product product = postProductRepository.save(Product.builder()
                    .requestDto(requestDto)
                    .imageFileUrl(defaultImageUrl)
                    .build());

            return ResponseDto.success(ProductResponseDto.builder()
                    .product(product)
                    .imageFileUrl(defaultImageUrl)
                    .build());
        }

        imageFileUrl = s3UploadService.s3UploadFile(imageFile, "img/product/" + requestDto.getProductName());

        Product product = postProductRepository.save(Product.builder()
                .requestDto(requestDto)
                .imageFileUrl(imageFileUrl)
                .build());

        return ResponseDto.success(ProductResponseDto.builder()
                .product(product)
                .imageFileUrl(imageFileUrl)
                .build());
    }
}
