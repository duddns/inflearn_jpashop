package jpabook.jpashop.execption;

public class NotEnoughStockException extends RuntimeException {
  
  /**
  * 
  */
  private static final long serialVersionUID = 1L;
  
  
  public NotEnoughStockException() {
    super();
  }
  
  public NotEnoughStockException(String message) {
    super(message);
  }
  
  public NotEnoughStockException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public NotEnoughStockException(Throwable cause) {
    super(cause);
  }
  
  protected NotEnoughStockException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
