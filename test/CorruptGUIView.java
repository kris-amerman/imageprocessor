import java.awt.image.BufferedImage;
import java.io.IOException;

import controller.ImageControllerFeatures;
import view.ImageGUIView;

/**
 * A corrupt implementation of ImageGUIView that throws exceptions when possible.
 */
public class CorruptGUIView implements ImageGUIView {

  // default constructor

  @Override
  public void renderMessage(String message) throws IOException {
    throw new IOException();
  }

  @Override
  public void addFeatures(ImageControllerFeatures controller) throws IllegalArgumentException {
    throw new IllegalArgumentException();
  }

  @Override
  public void refreshImage(BufferedImage img) throws IllegalArgumentException {
    throw new IllegalArgumentException();
  }

  @Override
  public void firstMount() {
    // do nothing
  }
}
