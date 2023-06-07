package store.lkhun.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto implements Serializable {
    @NotBlank private String productName;
    @NotBlank private String productPrice;
    private String ImageFileUrl;
}
