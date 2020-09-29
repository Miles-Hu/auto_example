package com.github.z.auto.example.exception;

/**
 * @author hujun
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
