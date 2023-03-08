import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.ImageTextView;
import view.ImageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * To test the image processor controller.
 */
public class ImageControllerTest {

  @Test
  public void testProcessorInitialMessage() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Welcome! Please begin by loading an image!\n" +
            "Please input the instruction below, following by the filepath and its name!\n" +
            "load image-path image-name : load an image from a specified path. " +
            "If you would like to quit, press q or Q."));
  }

  // testing to see if the game ends properly after the user presses q
  @Test
  public void testProcessorQuitLower() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly after the user presses uppercase Q
  @Test
  public void testProcessorQuitUpper() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("Q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly even if q is the second input
  @Test
  public void testProcessorSecondQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("a q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly even if q is the third input
  @Test
  public void testProcessorThirdQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("a beautiful q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly even if q is the fourth input
  @Test
  public void testProcessorFourQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("a beautiful CAT q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game tells the user that their input is not right. (Did not do load).
  @Test
  public void testProcessorDisplayInputCorrect() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("a beautiful CAT q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing to see if the game ends properly after symbols are shown
  @Test
  public void testProcessorSymbolsQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("@ # $ q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game tells the user that their input is not right. (Did not do load).
  // This is a case with symbols.
  @Test
  public void testProcessorSymbolsIncorrectMessage() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("@ # $ q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing to see if the game ends properly after numbers are inputted
  @Test
  public void testProcessorNumbersQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("19 920 -34 q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly after the user
  // enters a modifying instruction before they loaded.
  @Test
  public void testProcessorQuitBeforeLoad() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("brighten 10 kirby kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly after the user
  // enters a modifying instruction before they loaded.
  @Test
  public void testProcessorInvalidModify() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("brighten 10 kirby kirby2 q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" + "\n" +
            "The image has been brightened or darkened!"));
  }

  // testing to see if the game tells users they are wrong as they
  // enter a modifying instruction before they loaded.
  @Test
  public void testProcessorAnotherModify() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("brighten 10 kirby kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing to see if the game ends properly after the user
  // enters a modifying instruction before they loaded.
  @Test
  public void testProcessorQuitBeforeLoadFlip() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("flip-vertical kirby kirby23 q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Successfully ended the program."));
  }

  // testing to see if the game ends properly after the user
  // enters a modifying instruction before they loaded.
  @Test
  public void testProcessorQuitNoFlip() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("flip-vertical kirby kirby23 q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing to see if the game ends properly after the user
  // enters a modifying instruction before they loaded.
  @Test
  public void testProcessorQuitFlipNotDone() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("flip-vertical kirby kirby23 q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" + "\n" +
            "The image is vertically flipped!"));
  }

  // testing to see if the game ends unexpectedly.
  // Someone may input q or Q in the arguments for load, and it quits. (It should not do that).
  @Test
  public void testWillNotQuitLoadFilePath() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load q Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The following error has occurred: " +
            "not a filepath " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing to see the program will tell us the file path is invalid
  @Test
  public void testFilePathIsWrong() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load elephantPathppm Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertTrue(log.toString().contains("The following error has occurred: " +
            "Invalid file: no extension. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing to see if the game ends unexpectedly.
  // Someone may input q or Q in the arguments for load, and it quits. (It should not do that).
  // Specifically for the name input
  @Test
  public void testWillNotQuitLoadName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if the program can load a ppm
  @Test
  public void testLoadPPM() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if the program can load a bmp
  @Test
  public void testLoadBMP() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneBMP.bmp one q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if the program can load a jpeg
  @Test
  public void testLoadJPEG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPEG.jpeg one q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if the program can load a jpg
  @Test
  public void testLoadJPG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPG.jpg one q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if the program can load a png
  @Test
  public void testLoadPNG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePNG.png one q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing to see if it still loads after a false input
  @Test
  public void testProcessorLoadAfterFalseInput() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("flip-vertical kirby kirby23 " +
            "load res/onePPM.ppm Kirby " +
            "q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" + "\n" +
            "The image is vertically flipped!"));
    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
  }

  // testing if user inputs a second load, will it run
  @Test
  public void testImageDoubleUpload() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load " +
            "res/onePPM.ppm " +
            "kirby " +
            "load " +
            "res/sharp.ppm" +
            " cat " +
            "q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";
    String testingString2 = "";

    for (int count = 24; count <= 24; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    for (int count = 3; count <= 3; count = count + 1) {
      testingString2 = testingString2 + arrayTest[count] + "\n";
    }

    assertEquals("An image has been successfully uploaded!\n", testingString);
    assertEquals("An image has been successfully uploaded!\n", testingString2);
  }

  // testing if user inputs a second load, will it move onto the next phase
  // where it allows user to use all the functionalities because it has been loaded.
  @Test
  public void testImageDoubleUploadNext() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load " +
            "res/onePPM.ppm " +
            "kirby " +
            "load " +
            "res/sharpen.ppm" +
            " cat " +
            "q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 4; count <= 23; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Welcome! Here is a list of commands you can do! " +
            "If you would like to learn the way to use them, use menu!\n" +
            "load : load an image from a specified path.\n" +
            "save : save the image to a specified path.\n" +
            "brighten : brighten or darken the image.\n" +
            "vertical-flip : flips the image upside-down.\n" +
            "horizontal-flip : flips the image horizontally.\n" +
            "red-component : creates a greyscale image with the red component.\n" +
            "green-component : creates a greyscale image with the green component.\n" +
            "blue-component : creates a greyscale image with the blue component.\n" +
            "value-component : creates a greyscale image with the value component\n" +
            "intensity-component : " +
            "creates a greyscale image with the intensity component\n" +
            "luma-component : creates a greyscale image with the luma component.\n" +
            "blur : blurs the image\n" +
            "sharpen : sharpens the image\n" +
            "sepia : greyscale the image through sepia\n" +
            "greyscale : custom greyscale " +
            "(you can try luma by inputting the right values)\n" +
            "format : view all supported formats that this program has!\n" +
            "menu : view all the functionalities if you have forgotten!\n" +
            "stored-images : view all the images you have stored.\n" +
            "q or Q : end the program.\n", testingString);
  }

  // testing to see if it still loads after a false input
  // and if the next phase will activate.
  @Test
  public void testProcessorNewPhase() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("flip-vertical kirby kirby23 " +
            "load res/Kirby.ppm Kirby " +
            "q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Welcome! Here is a list of commands you can do! " +
            "If you would like to learn the way to use them, use menu!\n" +
            "load : load an image from a specified path.\n" +
            "save : save the image to a specified path.\n" +
            "brighten : brighten or darken the image.\n" +
            "vertical-flip : flips the image upside-down.\n" +
            "horizontal-flip : flips the image horizontally.\n" +
            "red-component : creates a greyscale image with the red component.\n" +
            "green-component : creates a greyscale image with the green component.\n" +
            "blue-component : creates a greyscale image with the blue component.\n" +
            "value-component : creates a greyscale image with the value component\n" +
            "intensity-component : " +
            "creates a greyscale image with the intensity component\n" +
            "luma-component : creates a greyscale image with the luma component.\n" +
            "blur : blurs the image\n" +
            "sharpen : sharpens the image\n" +
            "sepia : greyscale the image through sepia\n" +
            "greyscale : custom greyscale " +
            "(you can try luma by inputting the right values)\n" +
            "format : view all supported formats that this program has!\n" +
            "menu : view all the functionalities if you have forgotten!\n" +
            "stored-images : view all the images you have stored.\n" +
            "q or Q : end the program.\n"));
  }

  // testing to see if brighten works as intended


  // testing if the brighten works
  @Test
  public void testProcessorBrighten() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 100 Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
  }

  // testing if the brighten works, when the increment is negative
  @Test
  public void testProcessorNegativeIncrementBrighten() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten -100 Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
  }

  // testing if the brighten works, when the increment is zero
  @Test
  public void testProcessorZeroIncrementBrighten() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 0 Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
  }

  // testing if the brighten works, when the negative increment is over max value of 255
  @Test
  public void testProcessorNegativeOverMaxValueBrighten() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten -1000 Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
  }

  // testing if the brighten works, when the positive increment is over max value of 255
  @Test
  public void testProcessorOverMaxValueBrighten() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 1000 Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
  }

  // testing if the brighten will work given an invalid increment.
  @Test
  public void testProcessorBrightenInvalidIncrement() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten increment Kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("Please make sure that the " +
            "increment of brighten is an integer."));
  }

  // testing if the brighten will work given an invalid increment.
  // There are other errors, but this test ensures that we have skipped those last two inputs
  // there will be no errors generated from those last two inputs despite them being invalid.
  @Test
  public void testProcessorBrightenInvalidIncrementOther() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten increment kirby kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("Please make sure that the" +
            " increment of brighten is an integer."));
  }

  // testing if the brighten will work given an invalid name.
  // (The name is not the same as the loaded one).
  // This tests if the lower case scenario will be an exception
  @Test
  public void testProcessorBrightenInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 kirby brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if the brighten will work given an invalid name.
  // (The name is not the same as the loaded one).
  // this tests if a different string will affect it
  @Test
  public void testProcessorBrightenDifferentName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 kirbypink brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirbypink\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if the brighten will work given an invalid name.
  // (The name is not the same as the loaded one).
  // this tests if a part of the string will cause it to work for some reason
  @Test
  public void testProcessorBrightenPartName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kir brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kir\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if the brighten will work given an invalid name.
  // (The name is not the same as the loaded one).
  // this tests if a part of the string will cause it to work for some reason
  // this tests that it will skip the last input as we will not deal
  // with that error since an error was already spotted for the brighten
  @Test
  public void testProcessorBrightenPartNameOther() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kir Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kir\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if the brighten will work given an invalid name.
  // (The name is not the same as the loaded one).
  // this tests if the process will work when the name is a number
  @Test
  public void testProcessorBrightenNumbersName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 2020 brightKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"2020\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // This tests if the new name will cause an error to occur
  // the new name should not be the same as the original name
  @Test
  public void testProcessorBrightenSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken." +
            " Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // This tests if the program will quit early when q is pressed for the brighten modification
  @Test
  public void testProcessorBrightenQuitEarlyThird() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 q Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"q\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // This tests if the program will quit early when q is pressed for the brighten modification
  @Test
  public void testProcessorBrightenQuitEarlyFourth() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertFalse(log.toString().contains("The following error " +
            "has occurred: cannot find image named:" +
            " \"q\". Please try again making sure you have inputted everything correctly."));
  }

  // This tests if the program will quit early when q is pressed for the brighten modification
  // Specifically, if an error was passed, will q be skipped, or will it end the game
  @Test
  public void testProcessorBrightenErrorQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten quitEarly q KirbyNew q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("Please make sure that the " +
            "increment of brighten is an integer."));
  }

  // This tests what happens when brighten is misspelled. Will everything be skipped?
  // Will everything else just be an invalid input?
  // It should show four invalid inputs since they become apart of the userInstruction
  @Test
  public void testProcessorBrightenMissSpell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighte 100 kirby KirbyNew q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("Please make sure that the " +
            "increment of brighten is an integer."));
    assertTrue(log.toString().contains("Please make sure the modification" +
            " is inputted correctly."));

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 27; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);
  }


  // This tests what happens when brighten is misspelled. Will everything be skipped?
  // Will everything else just be an invalid input?
  // It should show four invalid inputs since they become apart of the userInstruction
  @Test
  public void testProcessorBrightenMissSpellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighte 100 kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("Please make sure that" +
            " the increment of brighten is an integer."));
    assertTrue(log.toString().contains("Please make sure the" +
            " modification is inputted correctly."));

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 27; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);
  }

  // testing the vertical-flip

  // testing if the vertical-flip works
  @Test
  public void testProcessorVertical() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip Kirby verticalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
  }

  // testing if an error is shown when the name is not valid.
  // (The original name is not the same).
  @Test
  public void testProcessorVerticalInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip newKirby verticalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"newKirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will tests if the q will end it or have an error like it is suppose to
  @Test
  public void testProcessorVerticalInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip q verticalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is vertically flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // The original name here became a number
  @Test
  public void testProcessorVerticalInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip 20 verticalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"20\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  @Test
  public void testProcessorQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorVerticalSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-flip Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspell.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorVerticalMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-fli q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorVerticalMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-fli Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspell.
  @Test
  public void testProcessorVerticalMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "vertical-fli Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"newKirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if the modifications for horizontal-flip works


  // testing if the horizontal-flip works
  @Test
  public void testProcessorHorizontal() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip Kirby horizontalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorHorizontalInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip kirby horizontalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorHorizontalInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip 2020 horizontalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"2020\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorHorizontalInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip q horizontalKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"q\". " +
            "Please try again making sure you have inputted everything correctly."));
  }


  // testing if it will quit early if new name is q.
  // for a horizontal modification
  @Test
  public void testProcessorHorizontalQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is horizontally flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorHorizontalSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-flip Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorHorizontalMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-fli q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorHorizontalMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-fli Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorHorizontalMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "horizontal-fli Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is horizontally flipped!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing the functionalities of red-component

  // testing if the greyscale of red-component works
  @Test
  public void testProcessorRedComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorRedInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorRedInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"2020\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorRedInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"q\". " +
            "Please try again making sure you have inputted everything correctly."));
  }


  // testing if it will quit early if new name is q.
  // for a red-component greyscale modification
  @Test
  public void testProcessorRedQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorRedSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorRedMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken." +
            " Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorRedMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Red component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorRedMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "red-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Red component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"newKirby\" is already taken." +
            " Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing the functionalities of green-component

  // testing if the greyscale of green-component works
  @Test
  public void testProcessorGreenComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Green component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorGreenInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Green component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorGreenInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"2020\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorGreenInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }


  // testing if it will quit early if new name is q.
  // for a green-component greyscale modification
  @Test
  public void testProcessorGreenQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Green component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorGreenSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Green component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorGreenMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorGreenMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: T" +
            "he name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorGreenMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "green-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Green component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"newKirby\" is already taken." +
            " Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing the functionalities of blue-component

  // testing if the greyscale of blue-component works
  @Test
  public void testProcessorBlueComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorBlueInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Blue component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorBlueInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorBlueInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Blue component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\". " +
            "Please try again making sure you have inputted everything correctly."));
  }


  // testing if it will quit early if new name is q.
  // for a blue-component greyscale modification
  @Test
  public void testProcessorBlueQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorBlueSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorBlueMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorBlueMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken." +
            " Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorBlueMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Blue component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing the functionalities of value-component

  // testing if the greyscale of value-component works
  @Test
  public void testProcessorValueComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorValueInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorValueInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorValueInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Value component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a value-component greyscale modification
  @Test
  public void testProcessorValueQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Value component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken." +
            " Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorValueSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Value component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorValueMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            "The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorValueMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Value component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken." +
            " Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorValueMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Value component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"newKirby\" is already taken." +
            " Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }


  // testing the functionalities of intensity-component

  // testing if the greyscale of intensity-component works
  @Test
  public void testProcessorIntensityComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorIntensityInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Intensity component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorIntensityInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "value-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Intensity component!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"2020\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorIntensityInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\". " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for an intensity-component greyscale modification
  @Test
  public void testProcessorIntensityQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Intensity component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorIntensitySameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Intensity component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorIntensityMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "Intensity-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. " +
            "Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorIntensityMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Intensity component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorIntensityMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "intensity-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". Please try again making " +
            "sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }


  // testing the functionalities of luma-component

  // testing if the greyscale of luma-component works
  @Test
  public void testProcessorLumaComponent() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component Kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();



    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Luma component!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorLumaInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component kirby greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorLumaInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Luma component!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". Please try again making" +
            " sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorLumaInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component q greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Luma component!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"q\". Please try again making sure you have " +
            "inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a luma-component greyscale modification
  @Test
  public void testProcessorLumaQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorLumaSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-component Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorLumaMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-cpmponent q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorLumaMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-cpment Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Luma component!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorLumaMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "luma-cpmponent Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));
    assertFalse(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if the blur functionality works

  // testing if the blur works
  @Test
  public void testProcessorBlur() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur Kirby blurKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorBlurInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur kirby blurKirb q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorBlurInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur 2020 greyscaleKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". Please try again making" +
            " sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorBlurInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur q blurKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a blur modification
  @Test
  public void testProcessorBlurQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorBlurSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blur Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorBlurMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorBlurMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorBlurMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "blue Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a blur!"));
    assertFalse(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }




  // testing if the sharpen functionality works

  // testing if the sharpen functionality works
  @Test
  public void testProcessorSharpen() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen Kirby sharpenKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorSharpenInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen kirby sharpKirb q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorSharpInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen 2020 sharpKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". Please try again making" +
            " sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorSharpInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen q sharpKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a sharpen modification
  @Test
  public void testProcessorSharpQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorSharpSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharpen Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorSharpMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharp q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorSharpMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharp Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorSharpMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sharp Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "The image has undergone a sharpening!"));
    assertFalse(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }


  // testing if the sepia functionality works

  // testing if the sepia functionality works
  @Test
  public void testProcessorSepia() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia Kirby sepiaKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorSepiaInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia kirby sepiaKirb q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorSepiaInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia 2020 sepiaKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". Please try again making" +
            " sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorSepiaInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia q sepiaKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a sepia modification
  @Test
  public void testProcessorSepiaQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorSepiaSameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepia Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorSepiaMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepi q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 25; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorSepiaMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepi Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorSepiaMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "sepi Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }





  // testing if the custom greyscale functionality works

  // testing if the custom greyscale functionality works
  @Test
  public void testProcessorCustomGrey() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.5 0.9 Kirby greyKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  @Test
  public void testProcessorCustomGreyInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.4 0.8 kirby sepiaKirb q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the user inputs a name that is not the original name.
  // in this case, the name became a number
  @Test
  public void testProcessorCustomGreyInvalidNameNumber() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.4 0.5 2020 sepiaKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"2020\". Please try again making" +
            " sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the name is not valid. (The original name is not the same).
  // This will test if the q will end it or have an error like it is supposed to
  @Test
  public void testProcessorCustomGreyInvalidNameQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.4 0.8 q sepiaKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"q\"." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if it will quit early if new name is q.
  // for a greyscale custom modification
  @Test
  public void testProcessorCustomGreyQuitEarly() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.3 0.4 Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. " +
            "Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if an error is shown when the new name is the same as the original
  @Test
  public void testProcessorCustomGreySameName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.2 0.3 0.4 Kirby Kirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorCustomGreyMisspellQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "grayscale 0.2 0.2 0.2 q newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 28; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " cannot find image named: \"Kirby\"." +
            " Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"Kirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  // the game should end early if q is one of the inputs
  @Test
  public void testProcessorCustomGreyMisspellFourthQuit() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "grayscale 0.2 0.3 0.4 Kirby q q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 29; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Successfully ended the program.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "cannot find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred:" +
            " The name \"Kirby\" is already taken. Please choose a different name." +
            " Please try again making sure you have inputted everything correctly."));
  }

  // testing if inputs will have error accordingly due to a misspelling.
  @Test
  public void testProcessorCustomGreyMisspell() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "grayscale 0.2 0.34 0.54 Kirby newKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);


    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertFalse(log.toString().contains("The following error has occurred: cannot" +
            " find image named: \"Kirby\". " +
            "Please try again making sure you have inputted everything correctly."));
    assertFalse(log.toString().contains("The following error has occurred: " +
            "The name \"newKirby\" is already taken. Please choose a different name. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing if the values given are not doubles (checking the first one)
  @Test
  public void testProcessorCustomGreyFirstNotDouble() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale red 0.5 0.9 Kirby greyKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("Please make sure that the red value is an integer."));
  }

  // testing if the values given are not doubles (checking the second value)
  @Test
  public void testProcessorCustomGreySecondNotDouble() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.3 green 0.9 Kirby greyKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("Please make sure that the green value is an integer."));
  }

  // testing if the values given are not doubles (checking the third value)
  @Test
  public void testProcessorCustomGreyThirdNotDouble() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "greyscale 0.3 0.5 blue Kirby greyKirby q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The update to the image has been done! " +
            "Custom greyscale color transformation has been applied!"));
    assertTrue(log.toString().contains("Please make sure that the blue value is an integer."));
  }



  // testing the menu functionalities

  // testing if menu works before loading. It should not work.
  // The menu is not made available,
  // because we would not be able to do anything with it without an image.
  @Test
  public void testProcessorMenuError() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("menu q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing entering menu will return the instructions once more for the users
  @Test
  public void testProcessorMenu() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "menu q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 43; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Here is the list of commands you can do.\n" +
            "load image-path image-name : load an image from a specified path.\n" +
            "save image-path image-name : save the image to a specified path.\n" +
            "brighten increment image-name dest-image-name : " +
            "brighten or darken the image.\n" +
            "vertical-flip image-name dest-image-name :" +
            " flips the image upside-down.\n" +
            "horizontal-flip image-name dest-image-name :" +
            " flips the image horizontally.\n" +
            "red-component image-name dest-image-name : " +
            "creates a greyscale image with the red component.\n" +
            "green-component image-name dest-image-name : " +
            "creates a greyscale image with the green component.\n" +
            "blue-component image-name dest-image-name : " +
            "creates a greyscale image with the blue component.\n" +
            "value-component image-name dest-image-name : " +
            "creates a greyscale image with the value component\n" +
            "intensity-component image-name dest-image-name : " +
            "creates a greyscale image with the intensity component\n" +
            "luma-component image-name dest-image-name :" +
            " creates a greyscale image with the luma component.\n" +
            "blur image-name dest-image-name : blurs the image.\n" +
            "sharpen image-name dest-image-name : sharpens the image.\n" +
            "sepia image-name dest-image-name : greyscale it through sepia of the image.\n" +
            "greyscale redIncrement greenIncrement" +
            " blueIncrement image-name dest-image-name : custom greyscale.\n" +
            "format : view all supported formats that this program has!\n" +
            "menu : a reminder of the list of commands you can do.\n" +
            "stored-images : view all the images you have stored.\n" +
            "q or Q : end the program.\n", testingString);

  }

  // testing entering menu will return the instructions once more for the users
  // and seeing if it has any inputs following it
  @Test
  public void testProcessorMenuLetterAfter() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "menu a q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 44; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Here is the list of commands you can do.\n" +
                    "load image-path image-name : load an image from a specified path.\n" +
                    "save image-path image-name : save the image to a specified path.\n" +
                    "brighten increment image-name dest-image-name : " +
                    "brighten or darken the image.\n" +
                    "vertical-flip image-name dest-image-name :" +
                    " flips the image upside-down.\n" +
                    "horizontal-flip image-name dest-image-name :" +
                    " flips the image horizontally.\n" +
                    "red-component image-name dest-image-name : " +
                    "creates a greyscale image with the red component.\n" +
                    "green-component image-name dest-image-name : " +
                    "creates a greyscale image with the green component.\n" +
                    "blue-component image-name dest-image-name : " +
                    "creates a greyscale image with the blue component.\n" +
                    "value-component image-name dest-image-name : " +
                    "creates a greyscale image with the value component\n" +
                    "intensity-component image-name dest-image-name : " +
                    "creates a greyscale image with the intensity component\n" +
                    "luma-component image-name dest-image-name :" +
                    " creates a greyscale image with the luma component.\n" +
                    "blur image-name dest-image-name : blurs the image.\n" +
                    "sharpen image-name dest-image-name : sharpens the image.\n" +
                    "sepia image-name dest-image-name : " +
            "greyscale it through sepia of the image.\n" +
                    "greyscale redIncrement greenIncrement" +
                    " blueIncrement image-name dest-image-name : custom greyscale.\n" +
            "format : view all supported formats that this program has!\n" +
                    "menu : a reminder of the list of commands you can do.\n" +
                    "stored-images : view all the images you have stored.\n" +
                    "q or Q : end the program.\n" +
            "Please make sure the modification is inputted correctly.\n", testingString);
  }

  // testing entering menu will return the instructions once more for the users
  // and even after a modification
  @Test
  public void testProcessorMenuAfterModify() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby" +
            " menu q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 24; count <= 44; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("The update to the image has been done! " +
            "The image has been brightened or darkened!\n" +
            "Here is the list of commands you can do.\n" +
            "load image-path image-name : load an image from a specified path.\n" +
            "save image-path image-name : save the image to a specified path.\n" +
            "brighten increment image-name dest-image-name : " +
            "brighten or darken the image.\n" +
            "vertical-flip image-name dest-image-name :" +
            " flips the image upside-down.\n" +
            "horizontal-flip image-name dest-image-name :" +
            " flips the image horizontally.\n" +
            "red-component image-name dest-image-name : " +
            "creates a greyscale image with the red component.\n" +
            "green-component image-name dest-image-name : " +
            "creates a greyscale image with the green component.\n" +
            "blue-component image-name dest-image-name : " +
            "creates a greyscale image with the blue component.\n" +
            "value-component image-name dest-image-name : " +
            "creates a greyscale image with the value component\n" +
            "intensity-component image-name dest-image-name : " +
            "creates a greyscale image with the intensity component\n" +
            "luma-component image-name dest-image-name :" +
            " creates a greyscale image with the luma component.\n" +
            "blur image-name dest-image-name : blurs the image.\n" +
            "sharpen image-name dest-image-name : sharpens the image.\n" +
            "sepia image-name dest-image-name : greyscale it through sepia of the image.\n" +
            "greyscale redIncrement greenIncrement" +
            " blueIncrement image-name dest-image-name : custom greyscale.\n" +
            "format : view all supported formats that this program has!\n" +
            "menu : a reminder of the list of commands you can do.\n" +
            "stored-images : view all the images you have stored.\n" +
            "q or Q : end the program.\n", testingString);
  }

  // testing the stored-images functionalities

  // testing if the stored-images command works as intended
  // if this is called before load, then there will be an error message
  @Test
  public void testProcessorStoredError() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("stored-images q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Please make sure command is inputted correctly."));
  }

  // testing to see if the stored-images command works as intended
  @Test
  public void testProcessorStored() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "stored-images q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Here are all the images you have stored!\n" +
            "Kirby - loaded original image"));
  }

  // testing to see if the stored-images command works as intended
  // this checks if the original image was overwritten
  // and it only appears in the stored-images once.
  @Test
  public void testProcessorStoredOverwrite() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "load res/onePPM.ppm Kirby " +
            "stored-images q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    String[] arrayTest = log.toString().split("\n");

    String testingString = "";

    for (int count = 25; count <= 26; count = count + 1) {
      testingString = testingString + arrayTest[count] + "\n";
    }

    assertEquals("Here are all the images you have stored!\nKirby -" +
            " loaded original image\n", testingString);

  }

  // testing to see if the stored-images command works as intended
  // this checks if new loaded images are recorded accordingly
  @Test
  public void testProcessorStoredNewLoad() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "load res/onePPM.ppm onePiece " +
            "stored-images q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertTrue(log.toString().contains("Kirby - loaded original image"));
    assertTrue(log.toString().contains("onePiece - loaded original image"));
  }

  // testing to see if the stored-images command works as intended
  // this checks if new loaded images are recorded accordingly with their modifications
  @Test
  public void testProcessorStoredModifications() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " +
            "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " +
            "stored-images q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();


    assertTrue(log.toString().contains("Kirby - loaded original image"));
    assertTrue(log.toString().contains("newKirby - brightened or darkened image"));
    assertTrue(log.toString().contains("verticalKirby - vertical flipped image"));
    assertTrue(log.toString().contains("horiKirby - horizontally flipped image"));
    assertTrue(log.toString().contains("redKirby - greyscale through the Red-component"));
    assertTrue(log.toString().contains("greenKirby - greyscale through the Green-component"));
    assertTrue(log.toString().contains("blueKirby - greyscale through the Blue-component"));
    assertTrue(log.toString().contains("valueKirby - greyscale through the Value-component"));
    assertTrue(log.toString().contains("intensityKirby - " +
            "greyscale through the Intensity-component"));
    assertTrue(log.toString().contains("lumaKirby - greyscale through the Luma-component"));
  }

  // testing the save functionalities

  // testing to see if the save command works as intended
  // this tests of it can save a modified image to a new path we create
  @Test
  public void testProcessorSavedNewPath() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " + "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " + " save res/controllerTest.ppm newKirby q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save an image with another extension (ppm)
  @Test
  public void testProcessorSavedNewExtension() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPG.jpg one "
            + "save res/onePPM.ppm one q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save an image with another extension (jpeg)
  @Test
  public void testProcessorSavedNewExtensionJPEG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPG.jpg one "
            + "save res/oneJPEG.jpeg one q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save an image with another extension (bmp)
  @Test
  public void testProcessorSavedNewExtensionBMP() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPG.jpg one "
            + "save res/oneBMP.bmp one q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save an image with another extension (jpeg)
  @Test
  public void testProcessorSavedNewExtensionPNG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/oneJPG.jpg one "
            + "save res/onePNG.png one q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save an image with another extension (jpg)
  @Test
  public void testProcessorSavedNewExtensionJPG() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one "
            + "save res/oneJPG.jpg one q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save the original image to the same file.
  @Test
  public void testProcessorSavedOriginal() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " + "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " + " save res/Kirby.ppm Kirby q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }

  // testing to see if the save command works as intended
  // this tests to see if it can save the original image to the same file.
  // along with q not quitting too early
  @Test
  public void testProcessorSavedNameQ() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm q " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " +
            "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " + " save res/Kirby.ppm q q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
  }


  // testing to see if the save command works as intended
  // this tests to see if the name is invalid
  @Test
  public void testProcessorSavedInvalidName() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " +
            "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " + " save res/random.ppm hi q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The image has been saved! Check it out!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " Cannot find image with name hi. " +
            "Please try again making sure you have inputted everything correctly."));
  }

  // testing to see if the save command works as intended
  // this tests to see if the filepath is invalid
  @Test
  public void testProcessorSavedInvalidFilePath() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " +
            "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " +
            "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component newKirby lumaKirby " + " save res/random Kirby q ");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertFalse(log.toString().contains("The image has been saved! Check it out!"));
    assertTrue(log.toString().contains("The following error has occurred:" +
            " Invalid file: no extension. " +
            "Please try again making sure you have inputted everything correctly."));
  }


  // testing multiple modifications

  // testing to see if images will be remembered and can be used to modify the image.
  @Test
  public void testProcessorMultipleMotif() {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            "brighten 10 Kirby newKirby " + "vertical-flip newKirby verticalKirby " +
            "horizontal-flip verticalKirby horiKirby " + "red-component newKirby redKirby " +
            "green-component newKirby greenKirby " + "blue-component newKirby blueKirby " +
            "value-component newKirby valueKirby " +
            "intensity-component newKirby intensityKirby " +
            "luma-component Kirby lumaKirby " +
            "blur newKirby blurKirby " +
            "sharpen newKirby sharp " +
            "sepia newKirby sepiaKirby " +
            "greyscale 0.2 0.3 0.4 newKirby customkirby " +
            "stored-images menu format q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is horizontally flipped!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Red component!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Blue component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image has undergone a blur!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image has undergone a sharpening!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " Custom greyscale color transformation has been applied!"));


    assertTrue(log.toString().contains("Kirby - loaded original image"));
    assertTrue(log.toString().contains("newKirby - brightened or darkened image"));
    assertTrue(log.toString().contains("verticalKirby - vertical flipped image"));
    assertTrue(log.toString().contains("horiKirby - horizontally flipped image"));
    assertTrue(log.toString().contains("redKirby - greyscale through the Red-component"));
    assertTrue(log.toString().contains("greenKirby - greyscale through the Green-component"));
    assertTrue(log.toString().contains("blueKirby - greyscale through the Blue-component"));
    assertTrue(log.toString().contains("valueKirby - greyscale through the Value-component"));
    assertTrue(log.toString().contains("intensityKirby -" +
            " greyscale through the Intensity-component"));
    assertTrue(log.toString().contains("lumaKirby - greyscale through the Luma-component"));
    assertTrue(log.toString().contains("blurKirby - a blur image"));
    assertTrue(log.toString().contains("sharp - a sharpened image"));
    assertTrue(log.toString().contains("sepiaKirby - a sepia image"));
    assertTrue(log.toString().contains("customkirby - a custom greyscale image"));

    assertTrue(log.toString().contains("Here is the list of commands you can do."));

    assertTrue(log.toString().contains("The following are the formats this program can support:\n" +
            ".ppm\n" +
            ".jpg\n" +
            ".jpeg\n" +
            ".png\n" +
            ".bmp\n" +
            "Have fun!"));

  }


  // All constructor exceptions

  // the constructor will throw an exception if the model is null
  @Test
  public void testModelNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(null, new ImageTextView(new ImageProcessorImpl()),
              new InputStreamReader(System.in));
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if the view is null
  @Test
  public void testViewNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(new ImageProcessorImpl(), null,
              new InputStreamReader(System.in));
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if the view is invalid
  @Test
  public void testViewInvalidNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(new ImageProcessorImpl(), new ImageTextView(null),
              new InputStreamReader(System.in));
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if the readable is null
  @Test
  public void testReadableNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(new ImageProcessorImpl(),
              new ImageTextView(new ImageProcessorImpl()),
              null);
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if all are null
  @Test
  public void testAllNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(null, null, null);
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if model and view are null
  @Test
  public void testModelViewNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(null, null,
              new InputStreamReader(System.in));
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if view and readable are null
  @Test
  public void testViewReadableNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(new ImageProcessorImpl(), null, null);
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if model and readable are null
  @Test
  public void testModelReadableNull() {
    ImageControllerImpl c;

    try {
      c = new ImageControllerImpl(null,
              new ImageTextView(new ImageProcessorImpl()), null);
      fail("This test did not throw an exception.");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // testing StateExceptions for the load when the message cannot be rendered
  @Test
  public void testLoadMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the brighten when the message cannot be rendered
  @Test
  public void testBrightenMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " brighten 10 Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the vertical-flip when the message cannot be rendered
  @Test
  public void testVerticalMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " vertical-flip Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the horizontal-flip when the message cannot be rendered
  @Test
  public void testHorizontalMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " horizontal-flip Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the red-component when the message cannot be rendered
  @Test
  public void testRedMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " red-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the green-component when the message cannot be rendered
  @Test
  public void testGreenMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " green-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the blue-component when the message cannot be rendered
  @Test
  public void testBlueMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " blue-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the value-component when the message cannot be rendered
  @Test
  public void testValueMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " value-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the intensity-component when the message cannot be rendered
  @Test
  public void testIntensityMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " intensity-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the luma-component when the message cannot be rendered
  @Test
  public void testLumaMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " luma-component Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the blur function when the message cannot be rendered
  @Test
  public void testBlurFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " blur Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the sharpen function when the message cannot be rendered
  @Test
  public void testSharpenFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " sharpen Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the sepia function when the message cannot be rendered
  @Test
  public void testSepiaFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " sepia Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the custom greyscale
  // function when the message cannot be rendered
  @Test
  public void testCustomGreyFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " greyscale 0.2 0.3 0.4 Kirby newKirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the format function
  // when message is not rendered correctly
  @Test
  public void testFormatFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " format q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the save when the message cannot be rendered
  @Test
  public void testSaveMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load res/Kirby.ppm Kirby " +
            " save res/newOne.ppm Kirby q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the load when the message cannot be rendered
  @Test
  public void testDefaultMessageFail() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("load q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing StateExceptions for the quit when the message cannot be rendered
  @Test
  public void testQuitMessageFail() {
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, new CorruptAppend());
    StringReader in = new StringReader("q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, in);

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // testing noSuchElement

  // The user did not submit enough inputs for the load.
  @Test
  public void testNoElement() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/nyc.ppm q");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  @Test
  public void testNoElementOriginal() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/Kirby.ppm people" +
            " brighten 10 people newPeople");
    ImageControllerImpl c = new ImageControllerImpl(model,
            view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is only space
  @Test
  public void testNoElementOriginalSpace() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("          ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is only newline
  @Test
  public void testNoElementOriginalNewline() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader(" \n  \n    ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is the case where not all inputs are given for the greyscale
  @Test
  public void testGreyscaleInsufficient() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one greyscale" +
            " 0.34 0323 q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is the case where not all inputs are given for the blur
  @Test
  public void testBlurInsufficient() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one blur" +
            " elepahtn q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is the case where not all inputs are given for the sharpen
  @Test
  public void testSharpenInsufficient() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one sharpen" +
            " elepahtn q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }

  // throws an illegal state exception when game is not quit but runs out of inputs.
  // there is the case where not all inputs are given for the sepia
  @Test
  public void testSepiaInsufficient() {
    StringBuilder log = new StringBuilder();
    ImageProcessorImpl model = new ImageProcessorImpl();
    ImageTextView view = new ImageTextView(model, log);
    StringReader in = new StringReader("load res/onePPM.ppm one sepia" +
            " elepahtn q ");
    ImageControllerImpl c = new ImageControllerImpl(model, view, new CorruptRead());

    try {
      c.modifyImages();
      fail("This test did not throw an exception.");
    } catch (IllegalStateException e) {
      // an empty catch block
    }
  }


  // testing inputs

  // testing if the greyscale to the red-component is inputted

  @Test
  public void testGreyscaleRedInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "red-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Red, name = nyc, destName = redNyc"));

  }

  // testing if the greyscale to the green-component is inputted
  @Test
  public void testGreyscaleGreenInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "green-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Green, name = nyc, destName = redNyc"));
  }

  // testing if the greyscale to the blue-component is inputted
  @Test
  public void testGreyscaleBlueInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "blue-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Blue, name = nyc, destName = redNyc"));
  }

  // testing if the greyscale to the value-component is inputted
  @Test
  public void testGreyscaleValueInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "value-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Value, name = nyc, destName = redNyc"));
  }

  // testing if the greyscale to the intensity-component is inputted
  @Test
  public void testGreyscaleIntensityInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "intensity-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Intensity, name = nyc, destName = redNyc"));
  }

  // testing if the greyscale to the luma-component is inputted
  @Test
  public void testGreyscaleLumaInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "luma-component nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Luma, name = nyc, destName = redNyc"));
  }

  // testing if the greyscale to the sepia-component is inputted
  @Test
  public void testGreyscaleSepiaInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "sepia nyc sepiaNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: Sepia, name = nyc, destName = sepiaNyc"));
  }

  // testing if the greyscale custom is inputted
  @Test
  public void testGreyscaleCustomInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "greyscale 0.2 0.2 0.2 nyc redNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("greyscale: custom," +
            " rC = 0.200000, gC = 0.200000, bC = 0.200000, name = nyc, destName = redNyc"));
  }


  // testing if the brighten is inputted with positive number
  @Test
  public void testBrightPositiveInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "brighten 10 nyc brightNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("increment = 10, name = nyc," +
            " destName = brightNyc"));
  }

  // testing if the brighten is inputted with negative number
  @Test
  public void testBrightNegativeInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "brighten -10 nyc brightNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("increment = -10, name = nyc, destName = brightNyc"));
  }

  // testing if the flip-horizontal is inputted
  @Test
  public void testFlipHoriInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "horizontal-flip nyc flipHoriNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("flip-horizontal was called with, " +
            "name = nyc, destName = flipHoriNyc"));
  }

  // testing if the flip-vertical is inputted
  @Test
  public void testFlipVerticalInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "vertical-flip nyc flipVerNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("flip-vertical was called with, " +
            "name = nyc, destName = flipVerNyc"));
  }

  // testing if sharpen is inputted
  @Test
  public void testSharpen() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "sharpen nyc flipVerNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("sharpen is called with, " +
            "name = nyc, destname = flipVerNyc"));
  }

  // testing if the blur is inputted correctly
  @Test
  public void testBlur() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "blur nyc flipVerNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("gaussianBlur is called with, " +
            "name = nyc, destname = flipVerNyc"));
  }

  // testing if the load is inputted
  @Test
  public void testLoadInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc " +
            "vertical-flip nyc flipVerNyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/onePPM.ppm, name = nyc"));
  }

  // testing to see if render message is called when some
  // sort of error occurs with the inputs the user inserts.
  @Test
  public void testViewMock() {
    StringBuilder log = new StringBuilder();
    StringBuilder dontCareOutput = new StringBuilder();
    ImageProcessor model = new MockModelController(dontCareOutput);
    ImageView view = new MockViewController(log);
    StringReader in = new StringReader("testing error input q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if the hasLoaded has been called to check
  // if the hasLoaded is going to check the strings
  @Test
  public void testHasLoadedInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc save res/onePPM.ppm nyc q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("hasLoadedImage called with nyc"));
    assertFalse(log.toString().contains("extensionValid called with res/onePPM.ppm"));
  }

  // testing if the getSupportedFormats method is being called
  @Test
  public void testGetSupportedFormatInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    StringBuilder dontCareOutput = new StringBuilder();
    ImageView view = new ImageTextView(model, dontCareOutput);
    StringReader in = new StringReader("load res/onePPM.ppm nyc format q");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("getSupportedFormats called"));
  }

  // testing if it can read text files
  @Test
  public void testTextScript() throws FileNotFoundException {
    Appendable log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageView view = new ImageTextView(model, log);
    FileReader in = new FileReader("res/testCommand.txt");
    ImageController controller = new ImageControllerImpl(model, view, in);
    controller.modifyImages();

    assertTrue(log.toString().contains("Welcome! Please begin by loading an image!"));
    assertTrue(log.toString().contains("An image has been successfully uploaded!"));
    assertTrue(log.toString().contains("The image has been saved! Check it out!"));
    assertTrue(log.toString().contains("The following are " +
            "the formats this program can support:\n" +
            ".ppm\n" +
            ".jpg\n" +
            ".jpeg\n" +
            ".png\n" +
            ".bmp\n" +
            "Have fun!"));
    assertTrue(log.toString().contains("Here is the list of commands you can do."));

    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image has been brightened or darkened!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is vertically flipped!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is horizontally flipped!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Red component!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Green component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Blue component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Value component!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "The image is greyscale to the Intensity component!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image is greyscale to the Luma component!"));

    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image has undergone a blur!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " The image has undergone a sharpening!"));
    assertTrue(log.toString().contains("The update to the image has been done! " +
            "Sepia color transformation has been applied!"));
    assertTrue(log.toString().contains("The update to the image has been done!" +
            " Custom greyscale color transformation has been applied!"));
  }

  // testing if it can read text files
  @Test
  public void testTextScriptException() throws FileNotFoundException {
    try {
      Appendable log = new StringBuilder();
      ImageProcessor model = new ImageProcessorImpl();
      ImageView view = new ImageTextView(model, log);
      FileReader in = new FileReader("res/testCommand");
      ImageController controller = new ImageControllerImpl(model, view, in);
      controller.modifyImages();
      assertEquals("", log.toString());
    } catch (FileNotFoundException e) {
      // an empty catch block
    }

  }
}
