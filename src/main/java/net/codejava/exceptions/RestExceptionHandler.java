package net.codejava.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.codejava.response.StatusResp;
import net.codejava.utils.DateUtil;

import java.sql.SQLException;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StatusResp> handleException(MyAppException exception) {

        StatusResp statusResp = new StatusResp();
        statusResp.setErrorCode(exception.getErrorCode());
        statusResp.setMessage(exception.getMessage());
        statusResp.setTimeStamp(DateUtil.getCurrentTimeStamp());

        return new ResponseEntity<>(statusResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<StatusResp> handleException(Exception exception) {

        exception.printStackTrace();

        StatusResp statusResp = new StatusResp();
        statusResp.setErrorCode(HttpStatus.BAD_REQUEST.value());
        statusResp.setMessage(exception.getMessage());
        statusResp.setTimeStamp(DateUtil.getCurrentTimeStamp());

        return new ResponseEntity<>(statusResp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<StatusResp> handleSqlException(SQLException exception) {

        exception.printStackTrace();

        StatusResp statusResp = new StatusResp();
        statusResp.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        statusResp.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        statusResp.setTimeStamp(DateUtil.getCurrentTimeStamp());

        return new ResponseEntity<>(statusResp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}