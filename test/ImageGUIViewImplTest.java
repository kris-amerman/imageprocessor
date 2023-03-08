import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;

import controller.ImageControllerFeatures;
import controller.ImageControllerFeaturesImpl;
import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.ImageGUIView;
import view.ImageGUIViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * To test the GUI view. The view is largely composed of Swing components.
 * We follow the features design, using callbacks for our various components.
 * The various features have been tested independently in the controller tests.
 *
 */
public class ImageGUIViewImplTest {

  /**
   * Test that message transmits to the view appropriately.
   */
  @Test
  public void testRenderMessage() {
    try {
      StringBuilder log = new StringBuilder();
      ImageGUIView view = new MockGUIView(log);

      assertEquals("", log.toString());

      view.renderMessage("test");

      assertEquals("renderMessage called with test", log.toString());
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Test that a corrupt implementation of ImageGUIView throws an IOException for
   * renderMessage.
   */
  @Test (expected = IOException.class)
  public void testExceptionCorruptViewRenderMessage() throws IOException {
    ImageGUIView view = new CorruptGUIView();
    view.renderMessage("test");
  }

  /**
   * Test that addFeatures can be called with the proper controller features and model.
   */
  @Test
  public void testAddFeatures() {
    StringBuilder log = new StringBuilder();

    ImageGUIView view = new MockGUIView(log);
    ImageProcessor model = new ImageProcessorImpl();
    ImageControllerFeatures features = new ImageControllerFeaturesImpl(model, view);

    // addFeatures should be called during the construction of controller features
    assertEquals("addFeatures called ", log.toString());

    view.addFeatures(features);

    // addFeatures called again (although this wouldn't actually happen during the lifecycle
    // of the application
    assertEquals("addFeatures called addFeatures called ", log.toString());
  }

  /**
   * Test that passing null into addFeatures throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testExceptionNullFeatures() {
    ImageGUIView view = new ImageGUIViewImpl();
    view.addFeatures(null);
  }

  /**
   * Test that refresh image can be called with an appropriate image.
   */
  @Test
  public void testRefreshImage() {
    StringBuilder log = new StringBuilder();

    ImageGUIView view = new MockGUIView(log);
    BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

    assertEquals("", log.toString());

    view.refreshImage(image);

    assertEquals("refreshImage called with image height: 10 and image width: 10",
            log.toString());
  }

  /**
   * Test that a null image in refreshImage throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testExceptionNullImage() {
    ImageGUIView view = new ImageGUIViewImpl();
    view.refreshImage(null);
  }

  /**
   * Test that first mount can be called appropriately.
   */
  @Test
  public void testFirstMount() {
    StringBuilder log = new StringBuilder();

    ImageGUIView view = new MockGUIView(log);
    BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);

    assertEquals("", log.toString());

    view.firstMount();

    assertEquals("firstMount called", log.toString());
  }
}