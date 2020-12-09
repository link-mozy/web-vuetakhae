package com.web.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//파일 못찾을때
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException{

  public MyFileNotFoundException(String message) {
      super(message);
  }

  public MyFileNotFoundException(String message, Throwable cause) {
      super(message, cause);
  }
}