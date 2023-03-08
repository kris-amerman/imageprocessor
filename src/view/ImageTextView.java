package view;

import java.io.IOException;

import controller.ImageControllerFeatures;
import model.ImageReadWrite;

/**
 * This represents the view, which is tasked with displaying messages to the user.
 */

public class ImageTextView implements ImageView {
  private ImageReadWrite state;
  private Appendable out;

  /**
   * This represents a convenience constructor that takes in the model.
   * @param state the model that the program will be using
   * @throws IllegalArgumentException
   *      if the given model is null
   */
  public ImageTextView(ImageReadWrite state) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("The provided model is null.");
    }

    this.state = state;
    this.out = System.out;
  }

  /**
   * This represents a convenience constructor that takes in the model and an appendable.
   * @param state the model that the program will be using
   * @param out the destination where we output the message
   * @throws IllegalArgumentException
   *      if either or both the model or appendable is null
   */
  public ImageTextView(ImageReadWrite state, Appendable out) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("The provided model is null.");
    }
    if (out == null) {
      throw new IllegalArgumentException("The input cannot be null.");
    }
    this.state = state;
    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IOException();
    }
  }

  /**
   * The text view has no features to support.
   */
  @Override
  public void addFeatures(ImageControllerFeatures controller) {
    //  does nothing
  }
}
