import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.ImageProcessorImpl;
import view.ImageTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class represents the tests for the functionalities of the view.
 */
public class ImageViewTest {
  private ImageProcessorImpl kirby;
  private ImageTextView viewDefault;

  @Before
  public void initial() {
    kirby = new ImageProcessorImpl();
    kirby.loadImage(
            "/Users/rness123/Desktop/OOD/group/group-ood/Assignment4/assets/KirbyASCII.ppm/",
            "kirbySuper");
    viewDefault = new ImageTextView(kirby);
  }

  // testing if the render message will append the messages correctly.
  @Test
  public void testRenderMessage() throws IOException {
    Appendable log = new StringBuilder();

    viewDefault = new ImageTextView(kirby, log);

    assertEquals("", log.toString());

    viewDefault.renderMessage("Game Over!");

    assertEquals("Game Over!", log.toString());
    viewDefault.renderMessage("\nState of Game:");
    assertEquals("Game Over!\nState of Game:", log.toString());
    viewDefault.renderMessage("\n load : will load an image.\nThat update was successful!");
    assertEquals("Game Over!\nState of Game:\n load : will " +
            "load an image.\nThat update was successful!", log.toString());
  }

  // testing if the rendering of the message will throw exception
  // if there is a transmission error
  @Test
  public void testInvalidRenderMessageAppendable() {
    Appendable log = new StringBuilder();
    viewDefault = new ImageTextView(kirby, log);
    try {
      new CorruptAppend().append("Game Over!");
      fail("This test did not throw an exception.");
    } catch (IOException e) {
      // an empty catch block
    }
  }

  // testing if the rendering of the message will throw exception
  // if the readable was corrupted
  @Test
  public void testInvalidRenderMessageReadable() {
    Appendable log = new StringBuilder();
    viewDefault = new ImageTextView(kirby, new CorruptAppend());
    try {
      viewDefault.renderMessage("Game Success!");
      fail("This test did not throw an exception.");
    } catch (IOException e) {
      // an empty catch block
    }
  }

  // test and see if an exception was thrown when a model that is null is passed
  @Test
  public void testNullModel() {
    ImageTextView v;

    try {
      v = new ImageTextView(null);
      fail("This did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // an empty catch block
    }
  }

  // test and see if an exception was thrown when the model is null for second constructor
  @Test
  public void testModelNullDoubleCon() {
    ImageTextView v;

    try {
      v = new ImageTextView(null, System.out);
      fail("This did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // an empty catch block
    }
  }

  // test and see if an exception is thrown when the appendable was null.
  @Test
  public void testNullOut() {
    ImageTextView v;

    try {
      v = new ImageTextView(new ImageProcessorImpl(), null);
      fail("This did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // an empty catch block
    }
  }

  // tests and sees if an exception was thrown when both the model and appendable was null.
  @Test
  public void testBothNull() {
    ImageTextView v;

    try {
      v = new ImageTextView(null, null);
      fail("This did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // an empty catch block
    }
  }
}
