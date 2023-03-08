package view;

import java.awt.image.BufferedImage;

import controller.ImageControllerFeatures;

/**
 * A GUI for an image processor. The GUI serves an accessory to the ImageView interface.
 */
public interface ImageGUIView extends ImageView {

  /**
   * Add callbacks for components via the provided controller features.
   *
   * @param controller
   *     An object of the ControllerFeatures interface
   * @throws IllegalArgumentException
   *     If the given controller features is null
   */
  void addFeatures(ImageControllerFeatures controller) throws IllegalArgumentException;

  /**
   * Display the given image. The controller tells the view when to update its image.
   *
   * @param img
   *     The image to display
   * @throws IllegalArgumentException
   *     If the provided image is null
   */
  void refreshImage(BufferedImage img) throws IllegalArgumentException;

  /**
   * Optional actions to perform after some loading sequence or a successful first mount.
   * This allows the controller to dictate to the view via certain requests.
   */
  void firstMount();
}
