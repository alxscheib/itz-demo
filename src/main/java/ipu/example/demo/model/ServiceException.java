package ipu.example.demo.model;

/**
 * Custom exception class used to represent service layer errors.
 * <p>
 * This exception is typically thrown when an error occurs during business logic
 * processing or interaction with the data layer.
 */
public class ServiceException extends Exception {

  /**
   * Constructs a new {@code ServiceException} with the specified detail message and cause.
   *
   * @param message the detail message explaining the reason for the exception
   * @param cause   the underlying cause of the exception
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
