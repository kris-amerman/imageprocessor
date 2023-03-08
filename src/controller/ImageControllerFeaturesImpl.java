package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import model.ImageProcessor;
import model.ImageUtil;
import view.ImageGUIView;

/**
 * Fulfills the requirements of the ControllerFeatures interface. Internal and external
 * requests are handled via this implementation. Although this implementation is oblivious to
 * the internal state of the image processor (i.e. the way it stores image data), it is
 * keenly aware of the ImageProcessor interface, which defines a set of operations that
 * take a "name" and a "destination name." As a result, this implementation must automate the
 * generation of these parameters, since it is not responsible for parsing any user input. This
 * is achieved via a simple counter--image states are saved in the model under names
 * starting from 0. Using this, it is also possible to define more complex features,
 * such as dynamic brightness, undo, and redo.
 * <p></p>
 * While it is possible to load new images, this implementation forces a user
 * to work on one image at a time. It is possible to traverse the states of the current image
 * using undo and redo.
 * <p></p>
 * Importantly, this implementation acts as a GUI-specific controller. Although this design is less
 * flexible than a view-agnostic controller, it was determined that separating the
 * controller implementations was beneficial from a representational standpoint. Both controller
 * implementations could be combined into one, particularly via composition. However, in the name
 * of the open-closed principle, we decided not to modify legacy code, opting instead
 * to build on top of it. The features that come with a GUI should not have an impact on the
 * base controller. Instead, the GUI serves as an accessory to the original implementation.
 * As a result, it is separate from the rest of the application.
 */
public class ImageControllerFeaturesImpl implements ImageControllerFeatures {

  private final ImageProcessor model;

  private final ImageGUIView view;

  /**
   * The name to give to the model when an operation is performed. Saving images under
   * an index is also beneficial for undoing and redoing operations.
   */
  
  private int indexKey;

  /**
   * The name of the base image to modify. This is especially useful for brightness, which
   * modifies the brightness of an underlying image rather than exponentially adding to or
   * subtracting from the brightness of that image.
   */
  private int currentIndex;

  /**
   * The number of times a user has requested to undo since the last operation.
   */
  private int undoCount;

  /**
   * Constructs an instance of the controller features implementation. This design is
   * specific to the ImageGUIView interface, as the features are the responsibilities
   * that a controller has to uphold for this view.
   *
   * @param model
   *     An object of the ImageProcessor interface
   * @param view
   *     An object of the ImageGUIView interface
   * @throws IllegalArgumentException
   *     If the provided model or view are null
   */
  public ImageControllerFeaturesImpl(ImageProcessor model, ImageGUIView view)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The view cannot be null.");
    }

    this.model = model;

    this.view = view;
    this.view.addFeatures(this);


    // initialize all indices to 0
    this.indexKey = 0;
    this.currentIndex = 0;
    this.undoCount = 0;
  }

  @Override
  public void loadRequest(String filepath) {
    try {
      this.indexKey = 0;
      this.currentIndex = 0;
      this.undoCount = 0;
      this.model.loadImage(filepath, Integer.toString(this.indexKey));
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
      this.view.firstMount();
    } catch (IllegalArgumentException e) {
      this.displayMessage("The following error has occurred: " + e.getMessage());
    }
  }

  @Override
  public void saveRequest(String filepath) throws IllegalStateException {
    try {
      
      String extension = ImageUtil.getExtension(filepath);

      // This must be checked in the controller before a file output stream can be generated
      if (!Arrays.asList(model.getSupportedFormats()).contains(extension)) {
        throw new IllegalArgumentException("Please make sure the extension is correct. " +
                "We only support: .ppm, .jpeg, .jpg, .bmp, .png");
      }
      OutputStream imageData = new FileOutputStream(filepath);
      this.model.saveImage(imageData, Integer.toString(this.indexKey), extension);
    } catch (Exception e) {
      this.displayMessage("There was a problem saving your file to: " + filepath);
    }
  }

  @Override
  public void verticalRequest() {
    try {
      this.model.flipVertical(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void horizontalRequest() {
    try {
      this.model.flipHorizontal(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  /**
   * A brightness request operates on top of a base image. This allows to user to specify
   * exactly what they want the updated brightness of the original image to be.
   * Without this, converting an image's brightness from +2 to +4 would actually yield
   * a final brightness of +6, as an additional +4 brightness would be added to the
   * previous +2 brightness, rather than modifying the original image with a brightness of 0.
   */
  @Override
  public void brightenRequest(int increment) {
    try {
      this.model.brightness(increment, Integer.toString(this.currentIndex),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void redCompRequest() {
    try {
      this.model.redChannel(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void greenCompRequest() {
    try {
      this.model.greenChannel(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void blueCompRequest() {
    try {
      this.model.blueChannel(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void valueRequest() {
    try {
      this.model.maxVal(Integer.toString(this.indexKey), Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void intensityRequest() {
    try {
      this.model.intensity(Integer.toString(this.indexKey), Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void lumaRequest() {
    try {
      this.model.luma(Integer.toString(this.indexKey), Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void blurRequest() {
    try {
      this.model.gaussianBlur(Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void sharpenRequest() {
    try {
      this.model.sharpen(Integer.toString(this.indexKey), Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void sepiaRequest() {
    try {
      this.model.sepia(Integer.toString(this.indexKey), Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void customMatrixRequest(double[][] cMatrix) { // TODO handle exceptions
    try {
      this.model.colorTransformation(cMatrix, Integer.toString(this.indexKey),
              Integer.toString(this.indexKey + 1));
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount = 0;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    } catch (IllegalArgumentException e) {
      this.displayMessage("Please load an image.");
    }
  }

  @Override
  public void undoRequest() {
    if (this.indexKey != 0) {
      this.indexKey--;
      this.currentIndex = this.indexKey;
      this.undoCount++;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    }
  }

  /**
   * If an operation is performed after undoing, the modification head will be set to
   * the image resulting from that operation. In other words, one can only redo up to the
   * most recent modification.
   */
  @Override
  public void redoRequest() {
    if (this.undoCount != 0) {
      this.indexKey++;
      this.currentIndex = this.indexKey;
      this.undoCount--;
      this.view.refreshImage(this.model.getImageState(Integer.toString(indexKey)));
    }
  }

  /**
   * Tell the view to render an informational message.
   *
   * @param message
   *     The message to render
   */
  private void displayMessage(String message) {
    try {
      this.view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("The message could not be rendered.");
    }
  }
}
