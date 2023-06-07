package store.lkhun.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import store.lkhun.demo.Service.CreateProductService;
import store.lkhun.demo.dto.ProductRequestDto;
import store.lkhun.demo.dto.ResponseDto;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RegisterProductController {
    private final CreateProductService createProductService;

    @PostMapping(value = "/upload", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDto<?> createProduct(@Valid @RequestPart(value = "requestDto") ProductRequestDto requestDto,
                                         @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        System.out.println(imageFile);
        //org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@681db269
        System.out.println(imageFile.getOriginalFilename());
        //aws.png
        return createProductService.createProduct(requestDto, imageFile);
    }

}
