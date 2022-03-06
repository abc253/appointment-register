package lw.hospital.common.exception;

import lw.hospital.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        return Result.fail();
    }

    @ExceptionHandler(HospitalException.class)
    @ResponseBody
    public Result error(HospitalException e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
}
