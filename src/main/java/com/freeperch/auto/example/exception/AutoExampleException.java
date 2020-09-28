package com.freeperch.auto.example.exception;

/**
 * @author hujun1
 * @date 2019-08-16 19:05
 */
public class AutoExampleException extends RuntimeException {

  public AutoExampleException(String message) {
    super(message);
  }

  public AutoExampleException(Throwable cause) {
    super(cause);
  }
}
