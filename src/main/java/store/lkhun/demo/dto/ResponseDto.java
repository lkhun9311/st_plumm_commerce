package store.lkhun.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import store.lkhun.demo.global.ErrorCode;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class ResponseDto<T> implements Serializable {
    private boolean success;
    private T data;
    private ErrorCode errorCode;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, ErrorCode.SUCCESS);
    }

    public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
        return new ResponseDto<>(false, null, errorCode);
    }

}
