package com.barrage.webcontroller.advice;


import com.barrage.webcontroller.exception.BarrageException;
import com.barrage.api.dao.ExceptionDao;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ExceptionDao handlerRuntimeException(Exception exception) {
        BarrageException barrageException;
        ExceptionDao exceptionDao = new ExceptionDao();
        barrageException = exception instanceof BarrageException ? (BarrageException) exception : new BarrageException(500, exception.getMessage());
        exceptionDao.setCode(barrageException.getCode());
        exceptionDao.setMessage(exception.getMessage());
        return exceptionDao;
    }
}
