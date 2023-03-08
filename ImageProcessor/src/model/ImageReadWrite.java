package model;

import java.io.OutputStream;

/**
 * An application that needs to read and/or write to an image file. This interface defines
 * the available color components for a loaded image.
 */
public interface ImageReadWrite {

  /**
   * Represents RGB color channels for a pixel.
   */
  enum Channel {
    Red,
    Green,
    Blue
  }

  /**
   * Load an image with the given file path and save it under the given name.
   * Image names can contain characters from the platform's default character set,
   * however, image names cannot be empty or contain spaces.
   *
   * @param imagePath
   *     The image file to load
   * @param name
   *     The name to save the loaded image under
   * @throws IllegalArgumentException
   *     If the file at the provided path (which should include the file name) cannot be found
   *     (encountered a FileNotFoundException)
   */
  void loadImage(String imagePath, String name) throws IllegalArgumentException;

  /**
   * Saves an image to the provided OutputStream. If the specified OutputStream is
   * a FileOutputStream, this will save the image to the specified path
   * (which should include the filename) in the user's local environment.
   *
   * @param imageData
   *     The output format for the image
   * @param name
   *     The name of the image to be saved
   * @param extension
   *     The filename extension
   * @throws IllegalArgumentException
   *     If the provided image name is invalid (i.e. has not been saved in this implementation),
   *     or if the file transmission fails (encountered an IOException).
   *     NOTE: this method does NOT handle exceptions related to OutputStream instantiation;
   *     such exceptions must be handled by the caller of this method.
   */
  void saveImage(OutputStream imageData, String name, String extension)
          throws IllegalArgumentException;

  /**
   * Does the image processor already have an image loaded with the given name.
   *
   * @param name
   *     The placeholder name of the image
   * @return
   *     True if the image processor already has a loaded image under the given
   *     name, false otherwise
   */
  boolean hasLoadedImage(String name);

  /**
   * Returns an array of strings listing the supported file formats for this application.
   *
   * @return
   *     Array of file extensions as strings
   */
  String[] getSupportedFormats();
}
