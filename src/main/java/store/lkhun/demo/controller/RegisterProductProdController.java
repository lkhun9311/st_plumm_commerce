package store.lkhun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.Service.ProductProdService;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ResponseDto;
import store.lkhun.demo.global.CacheKey;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class RegisterProductProdController {
    private final ProductProdService productProdService;

    @CachePut(value = CacheKey.PRODUCT)
    @GetMapping("/readAllProd")
    public ResponseDto<?> readProdProducts() { return productProdService.readProdProducts(); }

    @CachePut(value = CacheKey.PRODUCT, key = "#requestDto.productName", unless = "#result == null")
    @PostMapping(value = "/uploadProd", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createProdProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productProdService.createProdProduct(requestDto, imageFile);
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
        return productProdService.updateProdProduct(productName, productRequestDto, imageFile);
    }

    @CacheEvict(value = CacheKey.PRODUCT, key = "#productName")
    @DeleteMapping("/deleteProd/{productName}")
    public ResponseDto<?> deleteProdProduct(@PathVariable (name = "productName") String productName) {
        return productProdService.deleteProdProduct(productName);
    }

}
