import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

import controller.ImageControllerFeatures;
import controller.ImageControllerFeaturesImpl;
import controller.ImageControllerImpl;
import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.ImageGUIViewImpl;
import view.ImageTextView;

/**
 * This represents a class that will run the program.
 */
public class ImageProcessorMain {
  /**
   * The main method will begin the game with its given settings.
   * @param args the user's inputs for their desired settings
   * @throws IllegalStateException
   *      if there is an error in the transmission of rendering the message
   */
  public static void main(String[] args) throws IllegalStateException {
    ImageProcessor model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model);
    ImageControllerImpl controller = new ImageControllerImpl(model, view,
            new InputStreamReader(System.in));

    if (args.length == 0) {
      ImageGUIViewImpl guiView = new ImageGUIViewImpl();
      ImageControllerFeatures guiController = new ImageControllerFeaturesImpl(model, guiView);
    } else if (args[0].equals("-file")) {
      try {
        controller = new ImageControllerImpl(model, view,
                new FileReader(args[1]));

        beginProcessor(controller);

      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("Invalid filepath. Please make sure " +
                "you use a .txt file that exists.");
      }
    } else if (args[0].equals("-text")) {
      beginProcessor(controller);
    } else {
      throw new IllegalArgumentException("Invalid arguments. Please make sure " +
              "you either inputted:\n" +
              "Nothing for GUI processor\n" +
              "-file filename.txt for script file\n" +
              "-text for the image processor.");
    }
  }

  /**
   * This will determine which controller to use for modifying the image
   * Specifically, if we want the player to interact
   * or to simply have a text file run the commands.
   *
   * @param controller the controller we will use to modify images.
   *                   It determines if we are using a text file or having players input it
   * @throws IllegalStateException
   *      if there is an error in the transmission of rendering the message
   */
  private static void beginProcessor(ImageControllerImpl controller)
          throws IllegalStateException {
    try {
      controller.modifyImages();
    } catch (IllegalStateException r) {
      throw new IllegalStateException(r.getMessage());
    }
  }
}

