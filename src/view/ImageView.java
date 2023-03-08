package view;

import java.io.IOException;

import controller.ImageControllerFeatures;

/**
 * This interface represents functionality for displaying the status
 * of the program.
 */
public interface ImageView {

  /**
   * Render a specific message to the data destination.
   *
   * @param message
   *     the message that we want to transmit
   * @throws IOException
   *     if the transmission of the message does not go through
   */
  void renderMessage(String message) throws IOException;


  void addFeatures(ImageControllerFeatures controller);
}
