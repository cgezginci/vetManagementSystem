package dev.patika.vetManagementSystem.core.result;

import lombok.Getter;

@Getter
public class ResultData<T> extends Result  {

    private T data;

    public ResultData(boolean status, String message, String code, T data) {
        super(status, message, code);
        this.data = data;
    }

    // hata için kullanılacak mesaj
    public static <T> ResultData<T> error(String message, String code) {
        return new ResultData<>(false, message, code, null);

    }

    public static ResultData<Object> notFoundError(String message, String code) {
        return new ResultData<>(false, message, code, null);
    }

}
