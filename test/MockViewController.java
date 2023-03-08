import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import controller.ImageControllerFeatures;
import view.ImageGUIView;

/**
 * This represents a mock view controller used to test
 * the inputs of the user for the view methods.
 */
public class MockViewController implements ImageGUIView {
  public final StringBuilder log;

  /**
   * This represents the constructor that will generate
   * the output to see if the methods are called when certain inputs are submitted.
   * @param log the output of the user's inputs specifically if view methods are used.
   */
  public MockViewController(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  @Override
  public void renderMessage(String message) throws IOException {
    log.append("renderMessage is being called\n");
    return;
  }

  @Override
  public void addFeatures(ImageControllerFeatures controller) {
    log.append("addFeatures is being called\n");
    return;
  }

  @Override
  public void refreshImage(BufferedImage img) {
    log.append("refreshImage is being called\n");
    return;
  }

  @Override
  public void firstMount() {
    log.append("firstMount is being called\n");
    return;
  }
}

