import java.io.OutputStream;
import java.util.Objects;

import model.ImageProcessor;

/**
 * This represents a mock model controller used to test the inputs of the user.
 */

public class MockModelController implements ImageProcessor {
  final StringBuilder log;

  /**
   * This represents the constructor that
   * will generate the output to see if the methods are called when certain inputs are submitted.
   * @param log the output of the user's input specifically if the model methods are used.
   */
  public MockModelController(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void redChannel(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Red, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void greenChannel(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Green, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void blueChannel(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Blue, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void luma(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Luma, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void maxVal(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Value, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void intensity(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Intensity, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void sepia(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: Sepia, %s, %s\n",
            name, destName));
    return;
  }

  @Override
  public void customGreyscale(double rC, double gC, double bC,
                              String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("greyscale: custom, rC = %f, gC = %f, " +
                    "bC = %f, name = %s, destName = %s\n",
            rC, gC, bC, name, destName));
    return;
  }

  @Override
  public void brightness(int increment, String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("increment = %d, name = %s, destName = %s\n",
            increment, name, destName));
    return;
  }

  @Override
  public void flipHorizontal(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("flip-horizontal was called with," +
                    " name = %s, destName = %s\n",
            name, destName));
    return;
  }

  @Override
  public void flipVertical(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("flip-vertical was called with," +
                    " name = %s, destName = %s\n",
            name, destName));
    return;
  }

  @Override
  public void gaussianBlur(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("gaussianBlur is called with," +
                    " name = %s, destname = %s\n",
            name, destName));
    return;
  }

  @Override
  public void sharpen(String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("sharpen is called with, name = %s, destname = %s\n",
            name, destName));
    return;
  }

  @Override
  public void colorTransformation(double[][] cMatrix,
                                  String name, String destName)
          throws IllegalArgumentException {
    log.append(String.format("color transform is called with, " +
                    "name = %s, destname = %s\n",
            name, destName));
    return;
  }

  @Override
  public boolean hasLoadedImage(String name) {
    log.append("hasLoadedImage called with " + name);
    return false;
  }

  // when do we use this?
  @Override
  public String[] getSupportedFormats() {
    log.append("getSupportedFormats called");
    return new String[0];
  }

  @Override
  public void loadImage(String imagePath, String name)
          throws IllegalArgumentException {
    log.append(String.format("load was called with imagePath = %s, name = %s\n",
            imagePath, name));
    return;
  }

  @Override
  public void saveImage(OutputStream imageData, String name, String extension)
          throws IllegalArgumentException {
    log.append(name + " has been saved, saveImage is being called\n");
    return;
  }
}
