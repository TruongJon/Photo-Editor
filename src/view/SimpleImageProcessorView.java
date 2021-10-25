package view;

import java.io.IOException;

/**
 * Class that represents the view of the MVC design for an image processor.
 */
public class SimpleImageProcessorView implements IImageProcessorView {

  private Appendable ap;

  public SimpleImageProcessorView(Appendable ap) {
    this.ap = ap;
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void renderMessage(String message) throws IOException {
    try {
      ap.append("\n" + message);
    } catch (IOException e) {
      throw new IOException("Could not output message.");
    }
  }

}
