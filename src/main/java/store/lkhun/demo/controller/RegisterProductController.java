package store.lkhun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.Service.ProductService;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ResponseDto;
import store.lkhun.demo.global.CacheKey;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class RegisterProductController {
    private final ProductService productService;

    @CachePut(value = CacheKey.PRODUCT)
    @GetMapping("/readAll")
    public ResponseDto<?> readProducts() { return productService.readProducts(); }

    @CachePut(value = CacheKey.PRODUCT, key = "#requestDto.productName", unless = "#result == null")
    @PostMapping(value = "/uploadDev", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createDevProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                           @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.createDevProduct(requestDto, imageFile);
    }

    @CachePut(value = CacheKey.PRODUCT, key = "#requestDto.productName", unless = "#result == null")
    @PostMapping(value = "/uploadProd", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createProdProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.createProdProduct(requestDto, imageFile);
    }

    @CachePut(value = CacheKey.PRODUCT, key = "#productName", unless = "#result == null")
    @PutMapping(value = "/updateDev/{productName}")
    public ResponseDto<?> updateDevProduct(@PathVariable(name = "productName") String productName,
                                      @Valid @RequestPart(value = "requestDto") ProductRequestDto productRequestDto,
                                      @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException{
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.updateDevProduct(productName, productRequestDto, imageFile);
    }

    @CachePut(value = CacheKey.PRODUCT, key = "#productName", unless = "#result == null")
    @PutMapping(value = "/updateProd/{productName}")
    public ResponseDto<?> updateProdProduct(@PathVariable(name = "productName") String productName,
                                      @Valid @RequestPart(value = "requestDto") ProductRequestDto productRequestDto,
                                      @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException{
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productService.updateProdProduct(productName, productRequestDto, imageFile);
    }

    @CacheEvict(value = CacheKey.PRODUCT, key = "#productName")
    @DeleteMapping("/delete/{productName}")
    public ResponseDto<?> deleteProduct(@PathVariable (name = "productName") String productName) {
        return productService.deleteProduct(productName);
    }

}
