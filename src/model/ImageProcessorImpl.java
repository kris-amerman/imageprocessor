package model;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the model for a ppm image processing application.
 * It implements the functionality offered in the ImageProcessor interface.
 * The state of the model is represented by a map of loaded images. Images are
 * represented by a given name and an array of pixels. The state of the model can
 * be updated by loading an image or performing an operation on an image
 * that has already been loaded.
 */
public class ImageProcessorImpl implements ImageProcessor {

  /**
   * Maps image-name -> pixel-array.
   */
  private Map<String, int[][][]> imageStates;

  /**
   * Create a new image processing application. The default
   * image processor has no loaded images.
   */
  public ImageProcessorImpl() {
    this.imageStates = new HashMap<>();
  }

  /**
   * Creates a new empty array of pixels with the same dimensions
   * as the given image. The new image (pixel array) is added
   * to the map of loaded images. This method returns the reference
   * to the new image (pixel array).
   *
   * @param name
   *     The name of the original image
   * @param destName
   *     What name to save the new image under
   * @return
   *     The new image (pixel array)
   * @throws IllegalArgumentException
   *     If the provided name for the original image or the
   *     destination name are invalid. The original image must
   *     exist within the map of loaded images. The new name
   *     cannot be the same as the old name.
   */
  private int[][][] createNewImage(String name, String destName) throws IllegalArgumentException {
    if (!(this.imageStates.containsKey(name))) {
      throw new IllegalArgumentException("cannot find image named: \"" + name + "\"");
    }
    if (destName.length() == 0 || destName.contains(" ")) {
      throw new IllegalArgumentException("the destination name cannot be empty or contain spaces");
    }
    if (destName.equals(name)) {
      throw new IllegalArgumentException("The name \"" + destName + "\" is already taken. " +
              "Please choose a different name");
    }

    int[][][] oldImg = this.imageStates.get(name);
    int[][][] newImg = new int[ImageUtil.getHeight(oldImg)][ImageUtil.getWidth(oldImg)][3];
    this.imageStates.put(destName, newImg);

    return newImg;
  }

  /**
   * Apply the given kernel filter to the given image. The filter will be applied
   * on the specified color channel (red, green, or blue). This method returns a
   * new array with the modifications; it does NOT store this array in image states, as
   * this operation can be performed in suces
   *
   * @param k
   *     The filter kernel (an NxM matrix, such that N and M are odd)
   * @param c
   *     The color channel to modify (red, green, or blue)
   * @param oldImg
   *     The image pixel array to modify
   * @throws IllegalArgumentException
   *     If the kernel is an invalid size (less than 1x1 or greater than the image size), or
   *     if the provided color channel is invalid
   */
  private int[][][] applyFilter(Filter k, Channel c, int[][][] oldImg)
          throws IllegalArgumentException {
    if (k.getHeight() < 1 || k.getWidth() < 1) {
      throw new IllegalArgumentException("Invalid kernel size. Must be larger than 1x1.");
    }
    if (k.getHeight() > ImageUtil.getHeight(oldImg) || k.getWidth() > ImageUtil.getWidth(oldImg)) {
      throw new IllegalArgumentException("Invalid kernel size. Must be smaller than the image.");
    }

    int[][][] newImg = new int[ImageUtil.getHeight(oldImg)][ImageUtil.getWidth(oldImg)][3];

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        int rgbVal = this.channelToRGBIndex(c);
        // perform a convolution centered at the current pixel (row, col)
        int sum = this.convolution(k, oldImg, row, col, rgbVal);
        // if the sum is over 255, make it 255
        if (sum > 255) {
          sum = 255;
        }
        // if the sum is below 0, we make it 0
        if (sum < 0) {
          sum = 0;
        }
        // copy over the unmodified RGB values from the original image and updated the
        // specified RGB channel with the sum
        for (int i = 0; i < 3; i++) {
          if (i != rgbVal) {
            newImg[row][col][i] = oldImg[row][col][i];
          } else {
            newImg[row][col][rgbVal] = sum;
          }
        }
      }
    }
    return newImg;
  }

  /**
   * Convert an RGB channel to its associated array index:
   * 0 for red, 1 for green, and 2 for blue.
   *
   * @param c
   *     The RBG channel
   * @return
   *     The RGB array index
   */
  private final int channelToRGBIndex(Channel c) {
    int index = 0;
    if (c == Channel.Green) {
      index = 1;
    }
    if (c == Channel.Blue) {
      index = 2;
    }
    return index;
  }

  /**
   * Compute a convolution for a set of pixels centered at (origRow, origCol).
   * This method will return the sum of the convolution as an integer. This represents
   * the new value for the center pixel.
   *
   * @param k
   *     The filter kernel
   * @param img
   *     The reference image for this convolution (as an array of pixels)
   * @param origRow
   *     The row-position of the center pixel
   * @param origCol
   *     The column-position of the center pixel
   * @param rgbVal
   *     The color channel to base the convolution on
   * @return
   *     The sum resulting from a single convolution; represents the new value for the
   *     center pixel and the provided color channel
   * @throws IllegalArgumentException
   *     If the provided RGB index is not one of 0, 1, or 2, or if the provided row
   *     and column positions are out of bounds for the given image
   */
  private final int convolution(Filter k, int[][][] img, int origRow, int origCol, int rgbVal)
          throws IllegalArgumentException {
    if (rgbVal > 2 || rgbVal < 0) {
      throw new IllegalArgumentException("RGB index must be 0 (red), 1 (green), or 2 (blue)");
    }
    if (origRow < 0 || origRow > img.length || origCol < 0 || origCol > img[0].length) {
      throw new IllegalArgumentException("out of bounds row and/or column for given image");
    }

    double sum = 0.0;
    int kRow;
    int kCol;
    // if kernel extends beyond the top of the image, start from the top-edge of the image
    if (origRow < k.getYOffset()) {
      kRow = k.getYOffset() - origRow;
    }
    else {
      kRow = 0;
    }
    // if kernel extends beyond the left-edge of the image, start from the left-edge of the image
    if (origCol < k.getXOffset()) {
      kCol = k.getXOffset() - origCol;
    }
    else {
      kCol = 0;
    }
    // height and width of the original image
    int height = img.length;
    int width = img[0].length;
    // perform convolution by iterating through kernel overlaid with the original image
    while (kRow < height - origRow + k.getYOffset() && kRow < k.getHeight()) {
      while (kCol < width - origCol + k.getXOffset() && kCol < k.getWidth()) {
        int shiftRow = origRow + kRow - k.getYOffset();
        int shiftCol = origCol + kCol - k.getXOffset();
        // kernel value and the corresponding RGB value from the original image
        double kVal = k.getValAt(kRow, kCol);
        int oldVal = img[shiftRow][shiftCol][rgbVal];
        // update sum with the product of the kernel value and original RGB value
        sum += oldVal * kVal;
        kCol++;
      }
      kRow++;
      // reset the kernel column (must check for edge)
      if (origCol < k.getXOffset()) {
        kCol = k.getXOffset() - origCol;
      }
      else {
        kCol = 0;
      }
    }
    return (int) sum;
  }

  @Override
  public void gaussianBlur(String name, String destName) throws IllegalArgumentException {
    if (!(this.imageStates.containsKey(name))) {
      throw new IllegalArgumentException("cannot find image named: \"" + name + "\"");
    }

    if (destName.equals(name)) {
      throw new IllegalArgumentException("The name \"" + destName + "\" is already taken. " +
              "Please choose a different name");
    }

    int[][][] oldImg = this.imageStates.get(name);

    Filter filter = new Filter(new double[][]{
        new double []{0.0625, 0.125, 0.0625},
        new double []{0.125, 0.25, 0.125},
        new double []{0.0625, 0.125, 0.0625}
    });

    int[][][] redChannel = this.applyFilter(filter, Channel.Red, oldImg);
    int[][][] greenChannel = this.applyFilter(filter, Channel.Green, redChannel);
    int[][][] blueChannel = this.applyFilter(filter, Channel.Blue, greenChannel);

    this.imageStates.put(destName, blueChannel);
  }

  @Override
  public void sharpen(String name, String destName) throws IllegalArgumentException {
    if (!(this.imageStates.containsKey(name))) {
      throw new IllegalArgumentException("cannot find image named: \"" + name + "\"");
    }

    if (destName.equals(name)) {
      throw new IllegalArgumentException("The name \"" + destName + "\" is already taken. " +
              "Please choose a different name");
    }
    int[][][] oldImg = this.imageStates.get(name);

    Filter filter = new Filter(new double[][]{
        new double []{-0.125, -0.125, -0.125, -0.125, -0.125},
        new double []{-0.125, 0.25, 0.25, 0.25, -0.125},
        new double []{-0.125, 0.25, 1, 0.25, -0.125},
        new double []{-0.125, 0.25, 0.25, 0.25, -0.125},
        new double []{-0.125, -0.125, -0.125, -0.125, -0.125}
    });

    int[][][] redChannel = this.applyFilter(filter, Channel.Red, oldImg);
    int[][][] greenChannel = this.applyFilter(filter, Channel.Green, redChannel);
    int[][][] blueChannel = this.applyFilter(filter, Channel.Blue, greenChannel);

    this.imageStates.put(destName, blueChannel);
  }

  @Override
  public void colorTransformation(double[][] cMatrix, String name, String destName)
          throws IllegalArgumentException {

    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        for (int i = 0; i < 3; i++) {
          int newVal = (int) ((cMatrix[i][0] * oldImg[row][col][0])
                  + (cMatrix[i][1] * oldImg[row][col][1])
                  + (cMatrix[i][2] * oldImg[row][col][2]));

          if (newVal > 255) {
            newImg[row][col][i] = 255;
          }
          else if (newVal < 0) {
            newImg[row][col][i] = 255;
          }
          else {
            newImg[row][col][i] = newVal;
          }
        }
      }
    }
  }

  @Override
  public void sepia(String name, String destName) throws IllegalArgumentException {
    this.colorTransformation(new double[][]{
        new double[]{0.393, 0.769, 0.189},
        new double[]{0.349, 0.686, 0.168},
        new double[]{0.272, 0.534, 0.131}
    }, name, destName);
  }

  @Override
  public void redChannel(String name, String destName) throws IllegalArgumentException {
    this.colorTransformation(new double[][]{
        new double[]{1, 0, 0},
        new double[]{1, 0, 0},
        new double[]{1, 0, 0}
    }, name, destName);
  }

  @Override
  public void greenChannel(String name, String destName) throws IllegalArgumentException {
    this.colorTransformation(new double[][]{
        new double[]{0, 1, 0},
        new double[]{0, 1, 0},
        new double[]{0, 1, 0}
    }, name, destName);
  }

  @Override
  public void blueChannel(String name, String destName) throws IllegalArgumentException {
    this.colorTransformation(new double[][]{
        new double[]{0, 0, 1},
        new double[]{0, 0, 1},
        new double[]{0, 0, 1}
    }, name, destName);
  }

  @Override
  public void luma(String name, String destName) throws IllegalArgumentException {
    this.colorTransformation(new double[][]{
        new double[]{0.2126, 0.7152, 0.0722},
        new double[]{0.2126, 0.7152, 0.0722},
        new double[]{0.2126, 0.7152, 0.0722}
    }, name, destName);
  }

  @Override
  public void maxVal(String name, String destName) throws IllegalArgumentException {
    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        int maxValue = oldImg[row][col][0];

        if (oldImg[row][col][1] > maxValue) {
          maxValue = oldImg[row][col][1];
        }
        if (oldImg[row][col][2] > maxValue) {
          maxValue = oldImg[row][col][2];
        }

        newImg[row][col][0] = maxValue;
        newImg[row][col][1] = maxValue;
        newImg[row][col][2] = maxValue;
      }
    }
  }

  @Override
  public void intensity(String name, String destName) throws IllegalArgumentException {
    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        int average = (oldImg[row][col][0]
                + oldImg[row][col][1]
                + oldImg[row][col][2]) / 3;

        newImg[row][col][0] = average;
        newImg[row][col][1] = average;
        newImg[row][col][2] = average;
      }
    }
  }

  @Override
  public void customGreyscale(double rC, double gC, double bC, String name, String destName)
          throws IllegalArgumentException {

    this.colorTransformation(new double[][]{
        new double[]{rC, gC, bC},
        new double[]{rC, gC, bC},
        new double[]{rC, gC, bC}
    }, name, destName);
  }

  @Override
  public void brightness(int increment, String name, String destName)
          throws IllegalArgumentException {

    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        for (int i = 0; i < 3; i++) {
          if (oldImg[row][col][i] + increment > 255) {
            newImg[row][col][i] = 255;
          }
          else if (oldImg[row][col][i] + increment < 0) {
            newImg[row][col][i] = 0;
          }
          else {
            newImg[row][col][i] = oldImg[row][col][i] + increment;
          }
        }
      }
    }
  }

  @Override
  public void flipHorizontal(String name, String destName) throws IllegalArgumentException {

    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int row = 0; row < oldImg.length; row++) {
      for (int col = 0; col < oldImg[row].length; col++) {
        // replace current column val with last column val
        newImg[row][col] = oldImg[row][oldImg[row].length - col - 1];
      }
    }
  }

  @Override
  public void flipVertical(String name, String destName) throws IllegalArgumentException {

    int[][][] newImg = this.createNewImage(name, destName);
    int[][][] oldImg = this.imageStates.get(name);

    for (int i = 0; i < oldImg.length; i++) {
      // replace current row with last row
      newImg[i] = oldImg[oldImg.length - i - 1];
    }
  }

  @Override
  public boolean hasLoadedImage(String name) {
    return this.imageStates.containsKey(name);
  }

  /**
   * Load a PPM image as a 3D array of RGB values. Uses readPPM() from ImageUtil.
   * The outermost array represents row values. The inner array represents column values.
   * The innermost array represents the set of RGB values for each pixel.
   */
  @Override
  public void loadImage(String imagePath, String name) throws IllegalArgumentException {
    if (name.length() == 0 || name.contains(" ")) {
      throw new IllegalArgumentException("invalid image name");
    }

    String extension = ImageUtil.getExtension(imagePath);
    int[][][] loadedImage;

    switch (extension) {
      case ".ppm":
        loadedImage = ImageUtil.readPPM(imagePath);
        break;
      case ".jpg":
      case ".jpeg":
      case ".png":
      case ".bmp":
        loadedImage = ImageUtil.readWithIO(imagePath);
        break;
      default:
        throw new IllegalArgumentException("Invalid file: \"" + extension + "\" extension is " +
                "not supported. Please use one of: .ppm (ASCII), .jpg, .jpeg, .png, .bmp");
    }
    this.imageStates.put(name, loadedImage);
  }

  @Override
  public void saveImage(OutputStream imageData, String name, String extension)
          throws IllegalArgumentException {
    if (!(this.imageStates.containsKey(name))) {
      throw new IllegalArgumentException("invalid image name");
    }

    int[][][] img = this.imageStates.get(name);

    switch (extension) {
      case ".ppm":
        ImageUtil.savePPM(imageData, img);
        break;
      case ".jpg":
      case ".jpeg":
      case ".png":
      case ".bmp":
        ImageUtil.saveWithIO(imageData, img);
        break;
      default:
        throw new IllegalArgumentException("Invalid file: \"" + extension + "\" extension is " +
                "not supported. Please use one of: .ppm (ASCII), .jpg, .jpeg, .png, .bmp");
    }
  }

  /**
   * This image processor supports the file extensions returned by this method.
   *
   * @return
   *     An array of supported file extensions as strings
   */
  @Override
  public String[] getSupportedFormats() {
    return new String[]{".ppm", ".jpg", ".jpeg", ".png", ".bmp"};
  }

  /**
   * Does not return a reference to the internal image state. Returns a copy of the image
   * as a BufferedImage.
   */
  @Override
  public BufferedImage getImageState(String name)
          throws IllegalArgumentException {
    if (!(this.imageStates.containsKey(name))) {
      throw new IllegalArgumentException("cannot find image named: \"" + name + "\"");
    }
    int[][][] oldImg = this.imageStates.get(name);
    return ImageUtil.generateBufferedImage(oldImg);
  }


  /**
   * Represents a filter kernel. Kernel values are stored in a 2D array (matrix).
   * This matrix can be applied in a filtering operation.
   */
  private final class Filter {
    private final double[][] filter;

    /**
     * Create a filter kernel.
     *
     * @param filter
     *     The filter matrix
     * @throws IllegalArgumentException
     *     If height or width of the matrix is not odd (a kernel must have a pixel origin)
     */
    public Filter(double[][] filter) throws IllegalArgumentException {
      this.filter = filter;
      if (this.getHeight() % 2 == 0 || this.getWidth() % 2 == 0) {
        throw new IllegalArgumentException("must have odd width and height");
      }
    }

    /**
     * Retrieve a value from this matrix (primitive type).
     *
     * @param row
     *     The row position of the value
     * @param col
     *     The column position of the value
     * @return
     *     The value (double) at the specified location in this matrix
     * @throws IllegalArgumentException
     *     If the given position is invalid (out of bounds)
     */
    public double getValAt(int row, int col) throws IllegalArgumentException {
      if (row < 0 || col < 0 || row > this.getHeight() - 1 || col > this.getWidth() - 1) {
        throw new IllegalArgumentException("out of bounds");
      }
      return this.filter[row][col];
    }

    /**
     * Return the height of this filter (in terms of pixels).
     * @return
     *     Height in pixels
     */
    public int getHeight() {
      return this.filter.length;
    }

    /**
     * Return the width of this filter (in terms of pixels).
     * @return
     *     Width in pixels
     */
    public int getWidth() {
      return this.filter[0].length;
    }

    /**
     * Get the x-offset. This is defined as the x-value for this kernel's origin.
     * This value is obtained by subtracting 1 from the width of the kernel and dividing
     * by 2.
     *
     * @return
     *     The x-offset value
     */
    public int getXOffset() {
      return (this.getWidth() - 1) / 2;
    }

    /**
     * Get the y-offset. This is defined as the y-value for this kernel's origin.
     * This value is obtained by subtracting 1 from the height of the kernel and dividing
     * by 2.
     *
     * @return
     *     The y-offset value
     */
    public int getYOffset() {
      return (this.getHeight() - 1) / 2;
    }
  }
}
