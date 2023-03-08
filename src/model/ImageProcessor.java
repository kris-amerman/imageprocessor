package model;

import java.awt.image.BufferedImage;

/**
 * An image processing application that supports a variety of image manipulations.
 * Multiple images can be loaded and edited at the same time. Loading and saving is
 * offered by the ImageReadWrite interface.
 */
public interface ImageProcessor extends ImageReadWrite {

  /**
   * Visualize the red channel of an image. Assigns the RGB values for each
   * pixel in the image to the red channel.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void redChannel(String name, String destName) throws IllegalArgumentException;

  /**
   * Visualize the green channel of an image. Assigns the RGB values for each
   * pixel in the image to the green channel.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void greenChannel(String name, String destName) throws IllegalArgumentException;

  /**
   * Visualize the blue channel of an image. Assigns the RGB values for each
   * pixel in the image to the blue channel.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void blueChannel(String name, String destName) throws IllegalArgumentException;

  /**
   * Visualizes the luma component of an image. Assigns the RBG values for each
   * pixel in the image to the weighted sum: 0.2126r + 0.7152g + 0.0722b.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void luma(String name, String destName) throws IllegalArgumentException;

  /**
   * Visualizes the maximum value for each pixel in the image. Assigns
   * the RGB values for each pixel in the image to the maximum of the three
   * RGB values.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void maxVal(String name, String destName) throws IllegalArgumentException;

  /**
   * Visualizes the intensity component of an image. Intensity can be computed
   * by taking the average of the RGB values for each pixel. Each RGB value is
   * assigned this average in the new image.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void intensity(String name, String destName) throws IllegalArgumentException;

  /**
   * Converts the specified image to a sepia tone.
   *
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void sepia(String name, String destName) throws IllegalArgumentException;

  /**
   * Convert the original image to greyscale using the given RGB coefficients.
   * A greyscale image is composed of pixels whose RGB values are the same. The coefficients
   * rC, gC, and bC will specify how to scale an assign the RGB values. For example,
   * a value of 1 for rC and 0 for gC and bC would assign each RGB value in a pixel to the
   * pixel's original red value. RGB values max out at 255 and min out at 0.
   *
   * @param rC
   *     Red coefficient – how to scale the original red channel
   * @param gC
   *     Green coefficient – how to scale the original green channel
   * @param bC
   *     Blue coefficient – how to scale the original blue channel
   * @param name
   *     The name of the image to edit
   * @param destName
   *     The name of the new image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void customGreyscale(double rC, double gC, double bC, String name, String destName)
          throws IllegalArgumentException;

  /**
   * Change the brightness of an image by adding or subtracting to the
   * RGB values for every pixel in the image. A positive value brightens the
   * image, whereas a negative value darkens the image. RGB values max out at 255
   * and min out at 0.
   *
   * @param increment
   *     The value to add or subtract from brightness
   * @param name
   *     The name of the image to be edited
   * @param destName
   *     The name of the new edited image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void brightness(int increment, String name, String destName) throws IllegalArgumentException;

  /**
   * Flip the image horizontally.
   *
   * @param name
   *     The name of the image to be flipped
   * @param destName
   *     The name of the new flipped image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void flipHorizontal(String name, String destName) throws IllegalArgumentException;

  /**
   * Flip the image vertically.
   *
   * @param name
   *     The name of the image to be flipped
   * @param destName
   *     The name of the new flipped image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void flipVertical(String name, String destName) throws IllegalArgumentException;

  /**
   * Applies a Gaussian blur filter to the given image.
   * It is possible to blur an image that has already been blurred.
   *
   * @param name
   *     The name of the image to blur
   * @param destName
   *     The name of the new blurred image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void gaussianBlur(String name, String destName) throws IllegalArgumentException;

  /**
   * Sharpens the given image. It is possible to sharpen an image that has already been sharpened.
   *
   * @param name
   *     The name of the image to sharpen
   * @param destName
   *     The name of the new sharpened image
   * @throws IllegalArgumentException
   *     If the provided name or destination name are invalid (i.e. "")
   */
  void sharpen(String name, String destName) throws IllegalArgumentException;

  /**
   * Apply a linear color transformation to the given image. A color transformation
   * matrix is a 3x3 matrix whose column values represent the coefficients of
   * r', g', and b' respectively. A linear color transformation is performed by multiplying this
   * matrix by the RGB vector for each pixel. The resulting vector [r', g', b'] represents the
   * new RGB values for a given pixel. RGB values max out at 255 and min out at 0.
   *
   * @param cMatrix
   *     A 3x3 matrix whose column values represent the coefficients of r', g', and b' respectively
   * @param name
   *     The name of the image to transform
   * @param destName
   *     The name of the new transformed image
   * @throws IllegalArgumentException
   *     If the size of the provided color transformation matrix is not 3x3
   *     (resulting in an invalid matrix-vector multiplication), or if the provided name
   *     or destination name are invalid (i.e. "")
   */
  void colorTransformation(double[][] cMatrix, String name, String destName)
          throws IllegalArgumentException;

  /**
   * Return the specified image as a BufferedImage. This does not
   * return a reference to the actual image state.
   *
   * @param name
   *     The image of interest
   * @return
   *     The image state as a BufferedImage
   * @throws IllegalArgumentException
   *     If the provided image name does not exist
   */
  BufferedImage getImageState(String name);
}
