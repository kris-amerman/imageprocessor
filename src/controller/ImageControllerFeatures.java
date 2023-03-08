package controller;

/**
 * An image processor controller. It defines the various high-level features of its associated view.
 * User input is handled via requests, which are directed towards the model and the view.
 */
public interface ImageControllerFeatures {
  /**
   * Handle a request to load an image.
   *
   * @param filepath
   *     The filepath to load
   */
  void loadRequest(String filepath);

  /**
   * Handle a request to save an image.
   *
   * @param filepath
   *     The filepath to save to
   */
  void saveRequest(String filepath);

  /**
   * Handle a request to flip the image vertically.
   */
  void verticalRequest();

  /**
   * Handle a request to flip the image horizontally.
   */
  void horizontalRequest();

  /**
   * Handle a request to adjust the brightness of an image.
   *
   * @param increment
   *     The increment to adjust the brightness by (+/-)
   */
  void brightenRequest(int increment);

  /**
   * Handle a request to visualize the red channel of an image.
   */
  void redCompRequest();

  /**
   * Handle a request to visualize the green channel of an image.
   */
  void greenCompRequest();

  /**
   * Handle a request to visualize the blue channel of an image.
   */
  void blueCompRequest();

  /**
   * Handle a request to visualize the maximum RBG value of each pixel in the image.
   */
  void valueRequest();

  /**
   * Handle a request to visualize the intensity of an image.
   */
  void intensityRequest();

  /**
   * Handle a request to visualize the luma of an image.
   */
  void lumaRequest();

  /**
   * Handle a request to blur an image.
   */
  void blurRequest();

  /**
   * Handle a request to sharpen an image.
   */
  void sharpenRequest();

  /**
   * Handle a request to convert the image to sepia tone.
   */
  void sepiaRequest();

  /**
   * Handle a request to apply a custom color transformation to an image.
   *
   * @param cMatrix
   *     The color matrix to apply
   */
  void customMatrixRequest(double[][] cMatrix);

  /**
   * Handle a request to undo a previous operation on an image.
   */
  void undoRequest();

  /**
   * Handle a request to redo an operation that was undone.
   */
  void redoRequest();
}
