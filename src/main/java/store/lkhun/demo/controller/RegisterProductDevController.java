package store.lkhun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.Service.ProductDevService;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ResponseDto;
import store.lkhun.demo.global.CacheKey;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class RegisterProductDevController {
    private final ProductDevService productDevService;

    @CachePut(value = CacheKey.PRODUCT)
    @GetMapping("/readAllDev")
    public ResponseDto<?> readDevProducts() { return productDevService.readDevProducts(); }

    @CachePut(value = CacheKey.PRODUCT, key = "#requestDto.productName", unless = "#result == null")
    @PostMapping(value = "/uploadDev", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createDevProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                           @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return productDevService.createDevProduct(requestDto, imageFile);
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
        return productDevService.updateDevProduct(productName, productRequestDto, imageFile);
    }

    @CacheEvict(value = CacheKey.PRODUCT, key = "#productName")
    @DeleteMapping("/deleteDev/{productName}")
    public ResponseDto<?> deleteDevProduct(@PathVariable (name = "productName") String productName) {
        return productDevService.deleteDevProduct(productName);
    }

}
