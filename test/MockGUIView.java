import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import controller.ImageControllerFeatures;
import view.ImageGUIView;

/**
 * A mock GUI view that logs input. This is used for testing calls to the GUI view.
 */
public class MockGUIView implements ImageGUIView {

  private final StringBuilder log;

  /**
   * Constructs an instance of the mock view. Records operations in the log.
   *
   * @param log
   *     A log of the operations
   */
  public MockGUIView(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.log.append("renderMessage called with " + message);
  }

  @Override
  public void addFeatures(ImageControllerFeatures controller) {
    this.log.append("addFeatures called ");
  }

  @Override
  public void refreshImage(BufferedImage img) {
    this.log.append("refreshImage called with image height: " + img.getHeight() +
            " and image width: " + img.getWidth());
  }

  @Override
  public void firstMount() {
    this.log.append("firstMount called");
  }
}
