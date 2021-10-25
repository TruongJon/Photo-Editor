package view;

import java.io.IOException;

/**
 * Interface for the image processor view. An implementation will output feedback to the user.
 */
public interface IImageProcessorView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  public void renderMessage(String message) throws IOException;
}
