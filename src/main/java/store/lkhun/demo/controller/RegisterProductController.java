package store.lkhun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.Service.ProductService;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ResponseDto;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RegisterProductController {
    private final ProductService productService;

    @GetMapping("/readAll")
    public ResponseDto<?> readProducts() {
        return productService.readProducts();
    }

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.createProduct(requestDto, imageFile);
    }

    @PutMapping(value = "/update/{productName}")
    public ResponseDto<?> updatePosts(@PathVariable(name = "productName") String productName,
                                      @Valid @RequestPart(value = "requestDto") ProductRequestDto productRequestDto,
                                      @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException{
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.updateProduct(productName, productRequestDto, imageFile);
    }

    @DeleteMapping("/delete/{productName}")
    public ResponseDto<?> deleteProduct(@PathVariable (name = "productName") String productName) {
        return productService.deleteProduct(productName);
    }

}
