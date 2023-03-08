package controller;

/**
 * This represents the controller for the program. It is responsible
 * for the interaction between a player and the program.
 */

public interface ImageController {

  /**
   * Based on the player's inputs, will load an image, modify the image,
   * and save it to a destination.
   * @throws IllegalStateException
   *     if there is a transmission error to the view,
   *     or there an attempt to read the Readable object fails.
   */
  void modifyImages() throws IllegalStateException;
}
