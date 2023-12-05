package dev.patika.vetManagementSystem.core.config.utilies;

import dev.patika.vetManagementSystem.core.result.ListResult;
import dev.patika.vetManagementSystem.core.result.Result;
import dev.patika.vetManagementSystem.core.result.ResultData;

import java.util.List;

public class ResultHelper {
    public static <T> ResultData<T> created(T data ){
        return new ResultData<>(true , Msg.CREATE , "201" , data);
    }


    public static <T> ResultData<T> validateError(T data ){
        return new ResultData<>(false , Msg.VALIDATE_ERROR , "400" , data);
    }


    public static <T> ResultData<T> success(T data ){
        return new ResultData<>(true , Msg.OK , "200" , data);
    }


    public static Result ok() {
        return new Result(true , Msg.OK , "200");
    }


    public static Result notFoundError(String msg ){
        return new Result (false ,msg ,"404");
    }

    public static <T> ListResult<T> successList(List<T> data) {
        return new ListResult<>(true, Msg.OK, "200", data);
    }
    public static <T> ListResult<T> notFoundErrorList(String msg) {
        return new ListResult<>(false, msg, "404", null);
    }


}
