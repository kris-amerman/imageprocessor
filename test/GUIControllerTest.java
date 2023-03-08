import org.junit.Test;

import controller.ImageControllerFeatures;
import controller.ImageControllerFeaturesImpl;
import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.ImageGUIView;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * To test if the controller for GUI specifically will work as we intend.
 * For example, if it calls methods or throws exception where we intend it to.
 */

public class GUIControllerTest {

  // the constructor will throw an exception if the model given is null.
  @Test
  public void testNullModel() {
    ImageControllerFeaturesImpl c;
    StringBuilder log = new StringBuilder();

    try {
      c = new ImageControllerFeaturesImpl(null, new MockViewController(log));
      fail("Did not catch exception");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // the constructor will throw an exception if the view is null.
  @Test
  public void testNullView() {
    ImageControllerFeaturesImpl c;

    try {
      c = new ImageControllerFeaturesImpl(new ImageProcessorImpl(), null);
      fail("Did not catch exception");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }


  // the constructor will throw an exception if the view and model provided are null.
  @Test
  public void testModelViewNull() {
    ImageControllerFeaturesImpl c;

    try {
      c = new ImageControllerFeaturesImpl(null, null);
      fail("Did not catch exception");
    } catch (IllegalArgumentException e) {
      // catch an empty block
    }
  }

  // Testing if an exception is thrown
  // when something that is not a number if given to brighten
  @Test
  public void testBrightenInvalidInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new MockModelController(log);
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

      controller.loadRequest("res/Kirby.ppm");
      controller.brightenRequest(Integer.parseInt("elephqnt"));
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // Testing if an exception is thrown
  // when something that is not a number if given to brighten
  // this is testing when we have an empty
  @Test
  public void testBrightenEmptyInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new MockModelController(log);
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

      controller.loadRequest("res/Kirby.ppm");
      controller.brightenRequest(Integer.parseInt(""));
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // Testing if an exception is thrown
  // when something that is not a number if given to brighten
  // this is testing when we have a symbol
  @Test
  public void testBrightenSymbolInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new MockModelController(log);
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

      controller.loadRequest("res/Kirby.ppm");
      controller.brightenRequest(Integer.parseInt("#$%"));
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // Testing if the program still runs and if the index remains constant
  // if we undid something that was already at index 0 (the original image)
  @Test
  public void testManyUndo() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.verticalRequest();
    controller.horizontalRequest();
    controller.lumaRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.verticalRequest();

    assertTrue(log.toString().contains("flip-vertical was called with, name = 0, destName = 1"));
  }

  // Testing if the program still runs and if the index remains constant
  // if we redid something that did not have a redo.
  @Test
  public void testManyRedo() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.verticalRequest();
    controller.horizontalRequest();
    controller.redoRequest();
    controller.lumaRequest();
    controller.undoRequest();
    controller.redoRequest();
    controller.redoRequest();
    controller.redoRequest();
    controller.verticalRequest();

    assertTrue(log.toString().contains("flip-vertical was called with, name = 3, destName = 4"));
  }


  // Testing if the index is started off properly.
  @Test
  public void testLoadInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");

    assertTrue(log.toString().contains("load was called with imagePath = res/Kirby.ppm, name = 0"));
  }

  // Testing if the getImageState was called
  @Test
  public void testGetImageStateInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");

    assertTrue(log.toString().contains("getImageState was called with name = 0"));
  }

  // Testing if the index will be reset.
  // if multiple loads happen, will our index reset.
  @Test
  public void testMultipleLoadInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.redCompRequest();
    controller.verticalRequest();
    controller.lumaRequest();

    controller.loadRequest("res/oneJPEG.jpeg");

    controller.blueCompRequest();
    controller.undoRequest();
    controller.sepiaRequest();

    assertTrue(log.toString().contains("load was called with imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("greyscale: Red, name = 0, destName = 1"));
    assertTrue(log.toString().contains("flip-vertical was called with, name = 1, destName = 2"));
    assertTrue(log.toString().contains("greyscale: Luma, name = 2, destName = 3"));
    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPEG.jpeg, name = 0"));
    assertTrue(log.toString().contains("greyscale: Blue, name = 0, destName = 1"));
    assertTrue(log.toString().contains("greyscale: Sepia, name = 0, destName = 1"));
  }

  // Testing if the vertical-flip modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testVerticalFlipInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.verticalRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("flip-vertical was called with, name = 0, destName = 1\n"));
  }

  // Testing if the horizontal-flip modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testHorizontalFlipInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.horizontalRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("flip-horizontal was called with, " +
            "name = 0, destName = 1\n"));
  }

  // Testing if the luma greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testLumaInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.lumaRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("greyscale: Luma, name = 0, destName = 1\n"));
  }

  // Testing if the intensity greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testIntensityInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.intensityRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("greyscale: Intensity, name = 0, destName = 1\n"));
  }

  // Testing if the value greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testValueInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.valueRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("greyscale: Value, name = 0, destName = 1\n"));
  }

  // Testing if the blue greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testBlueInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.blueCompRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0\n"));
    assertTrue(log.toString().contains("greyscale: Blue, name = 0, destName = 1\n"));
  }

  // Testing if the green greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testGreenInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.greenCompRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("greyscale: Green, name = 0, destName = 1"));
  }


  // Testing if the red greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testRedInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.redCompRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("greyscale: Red, name = 0, destName = 1"));
  }

  // Testing if sharpen modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testSharpenInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.sharpenRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("sharpen is called with, name = 0, destname = 1"));
  }

  // Testing if blur modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testBlurInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.blurRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("gaussianBlur is called with, " +
            "name = 0, destname = 1"));
  }

  // Testing if sepia modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testSepiaInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.sepiaRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("greyscale: Sepia, name = 0, destName = 1"));
  }

  // Testing if brighten modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testBrightenInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.brightenRequest(10);

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("increment = 10, name = 0, destName = 1"));
  }

  // Testing if brighten modification works as intended. Even when it is over 255
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testBrightenOverInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.brightenRequest(300);

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("increment = 300, name = 0, destName = 1"));
  }

  // Testing if brighten modification works as intended. Even when it is over -255
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testDarkenOverInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.brightenRequest(-300);

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("increment = -300, name = 0, destName = 1"));
  }

  // Testing if darken modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testDarkenInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.brightenRequest(-10);

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("increment = -10, name = 0, destName = 1"));
  }

  // Testing if custom greyscale modification works as intended.
  // It will increase the index. We also test if we are calling the method
  // to enact the modification upon the image.
  @Test
  public void testCustomGreyscaleInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.customMatrixRequest(new double[5][5]);

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/Kirby.ppm, name = 0"));

    assertTrue(log.toString().contains("color transform is called with, name = 0, destname = 1"));
  }

  // Testing if the save method works appropriately.
  // Will it be able to save image after an image was given.
  // We will also see if the methods to test exceptions are called.
  @Test
  public void testSaveInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/oneJPG.jpg");
    controller.saveRequest("res/emptyTesting.bmp");

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPG.jpg, name = 0"));

    assertTrue(log.toString().contains("hasLoadedImage called with 0"));
    assertTrue(log.toString().contains("getSupportedFormats called"));
    assertTrue(log.toString().contains("0 has been saved, saveImage is being called"));
  }

  // Testing if the save method works appropriately.
  // Can it save an image when it is undo.
  @Test
  public void testSaveImageUndone() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/oneJPG.jpg");
    controller.verticalRequest();
    controller.horizontalRequest();
    controller.undoRequest();
    controller.saveRequest("res/vertical.jpg");

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPG.jpg, name = 0"));
    assertTrue(log.toString().contains("hasLoadedImage called with 1"));
    assertTrue(log.toString().contains("1 has been saved, saveImage is being called"));
  }

  // Testing if the save method works appropriately.
  // Can it save an image when it is redone.
  @Test
  public void testSaveImageRedone() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/oneJPG.jpg");
    controller.verticalRequest();
    controller.horizontalRequest();
    controller.undoRequest();
    controller.redoRequest();
    controller.saveRequest("res/horizontal.jpg");

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPG.jpg, name = 0"));
    assertTrue(log.toString().contains("hasLoadedImage called with 2"));
    assertTrue(log.toString().contains("2 has been saved, saveImage is being called"));
  }

  // Testing if the undo method works appropriately.
  @Test
  public void testUndoInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/oneJPG.jpg");
    controller.verticalRequest();
    controller.horizontalRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPG.jpg, name = 0\n"));
    assertTrue(log.toString().contains("flip-vertical was called with, " +
            "name = 0, destName = 1\n"));
    assertTrue(log.toString().contains("flip-horizontal was called with, " +
            "name = 1, destName = 2\n"));

    controller.undoRequest();
    controller.undoRequest();

    controller.verticalRequest();

    assertTrue(log.toString().contains("flip-vertical was called with, name " +
            "= 0, destName = 1"));
  }

  // Testing if the redo method works appropriately.
  @Test
  public void testRedoInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/oneJPG.jpg");
    controller.verticalRequest();
    controller.horizontalRequest();

    assertTrue(log.toString().contains("load was called with " +
            "imagePath = res/oneJPG.jpg, name = 0\n"));
    assertTrue(log.toString().contains("flip-vertical was called with, " +
            "name = 0, destName = 1\n"));
    assertTrue(log.toString().contains("flip-horizontal was called with, " +
            "name = 1, destName = 2\n"));

    controller.undoRequest();
    controller.undoRequest();

    controller.redoRequest();
    controller.lumaRequest();

    assertTrue(log.toString().contains("greyscale: Luma, name = 1, destName = 2"));
  }

  // testing whether we can load something that has the wrong extension.
  @Test
  public void testLoadWrongExtension() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/oneBMP.html");

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing whether we can load something that has the wrong extension.
  @Test
  public void testNoLoadExtension() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("noExtension");

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing whether if the loaded filepath is an image or not.
  @Test
  public void testNotImage() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/testCommand.txt");

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }


  // testing if we brighten a non-existent image, will we throw an exception.
  @Test
  public void testBrightenBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.brightenRequest(10);

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));

  }

  // testing if we darken a non-existent image, will we throw an exception.
  @Test
  public void testDarkenBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.brightenRequest(-10);

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we sepia a non-existent image, will we throw an exception.
  @Test
  public void testSepiaBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.sepiaRequest();

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we blur a non-existent image, will we throw an exception.
  @Test
  public void testBlurBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.blurRequest();

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we sharpen a non-existent image, will we throw an exception.
  @Test
  public void testSharpenBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.sharpenRequest();

    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we red-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testRedBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.redCompRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we green-component greyscale a non-existent image.
  @Test
  public void testGreenBeforeLoadException() {

    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.greenCompRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we blue-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testBlueBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.blueCompRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we value-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testValueBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.valueRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we intensity-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testIntensityBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.intensityRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we luma-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testLumaBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.lumaRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we greyscale-component greyscale a non-existent image, will we throw an exception.
  @Test
  public void testLumaGreyscaleBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.customMatrixRequest(new double[5][3]);
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if we input something that was not valid.
  // this is testing when someone enters nothing
  @Test
  public void testLumaGreyscaleInvalidInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new ImageProcessorImpl();
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
      controller.customMatrixRequest(new double[][]{
          new double[]{Double.valueOf(""), Double.valueOf(""), Double.valueOf("")},
          new double[]{Double.valueOf(""), Double.valueOf(""), Double.valueOf("")},
          new double[]{Double.valueOf(""), Double.valueOf(""), Double.valueOf("")}
      });
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if we input something that was not valid.
  // this is testing if a string was entered instead of a number
  @Test
  public void testLumaGreyscaleNotNumberInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new ImageProcessorImpl();
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
      controller.customMatrixRequest(new double[][]{
          new double[]{Double.valueOf("elephant"), Double.valueOf("ifrit jambe"),
                      Double.valueOf("hey")},
          new double[]{Double.valueOf("one"), Double.valueOf("punch"),
                      Double.valueOf("man")},
          new double[]{Double.valueOf("awakened"), Double.valueOf("cosmic"),
                      Double.valueOf("garou")}
      });
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if we input something that was not valid.
  // this is testing if a symbol was entered instead of a number
  @Test
  public void testLumaGreyscaleSymbolsInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new ImageProcessorImpl();
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
      controller.customMatrixRequest(new double[][]{
          new double[]{Double.valueOf("#$%"), Double.valueOf("@#"),
                      Double.valueOf("     ")},
          new double[]{Double.valueOf("="), Double.valueOf("-"),
                      Double.valueOf("#$$#%")},
          new double[]{Double.valueOf("1"), Double.valueOf("464"),
                      Double.valueOf("4")}
      });
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if some inputs were empty.
  @Test
  public void testLumaGreyscaleSomeEmptyInput() {
    try {
      StringBuilder log = new StringBuilder();
      ImageProcessor model = new ImageProcessorImpl();
      ImageGUIView view = new MockViewController(log);
      ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
      controller.customMatrixRequest(new double[][]{
          new double[]{Double.valueOf("424"), Double.valueOf("34"),
                      Double.valueOf("     ")},
          new double[]{Double.valueOf("       "), Double.valueOf("3"),
                      Double.valueOf("34")},
          new double[]{Double.valueOf("1"), Double.valueOf("464"),
                      Double.valueOf("4")}
      });
      fail("Did not catch exception");
    } catch (NumberFormatException e) {
      // an empty catch block
    }
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if some inputs were negative. It should not.
  @Test
  public void testLumaGreyscaleNegativeInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/Kirby.ppm");
    controller.customMatrixRequest(new double[][]{
        new double[]{Double.valueOf(-424), Double.valueOf(-34),
                    Double.valueOf(-8)},
        new double[]{Double.valueOf(-7), Double.valueOf(-3),
                    Double.valueOf(-34)},
        new double[]{Double.valueOf(-1), Double.valueOf(-464),
                    Double.valueOf(-4)}
    });

    assertTrue(log.toString().contains("color transform is called with, name = 0, destname = 1"));

  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if some inputs were doubles but with spaces
  @Test
  public void testLumaGreyscaleWeirdInputSpace() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/Kirby.ppm");
    controller.customMatrixRequest(new double[][]{
        new double[]{Double.valueOf(-424), Double.valueOf(-34), Double.valueOf(-8)},
        new double[]{Double.valueOf(-7), Double.valueOf(-3), Double.valueOf(-34)},
        new double[]{Double.valueOf(1), Double.valueOf(-464), Double.valueOf(-4)}
    });

    assertTrue(log.toString().contains("color transform is called with, name = 0, destname = 1"));
  }

  // testing if we greyscale-component greyscale will we throw an exception.
  // if some inputs were over the limit of 255
  @Test
  public void testLumaGreyscaleOverLimit() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/Kirby.ppm");
    controller.customMatrixRequest(new double[][]{
        new double[]{Double.valueOf(267), Double.valueOf(-34), Double.valueOf(-8)},
        new double[]{Double.valueOf(0), Double.valueOf(-3), Double.valueOf(-34)},
        new double[]{Double.valueOf(34), Double.valueOf(34), Double.valueOf(-4)}
    });

    assertTrue(log.toString().contains("color transform is called with, name = 0, destname = 1"));
  }

  // testing if we vertically flipped a non-existent image, will we throw an exception.
  @Test
  public void testVerticalBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.verticalRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we horizontally flipped a non-existent image, will we throw an exception.
  @Test
  public void testHorizontalBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.horizontalRequest();
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we save a non-existent image, will we throw an exception.
  @Test
  public void testSaveBeforeLoadException() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.saveRequest("res/emptyTesting.jpg");
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we give an invalid filepath (no extension), will it save.
  @Test
  public void testSaveInvalidFilepath() {

    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/onePPM.ppm");
    controller.saveRequest("res/emptyFailed");
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // testing if we give an invalid filepath (invalid extension), will it save.
  @Test
  public void testSaveInvalidFilepathExtension() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);
    controller.loadRequest("res/onePPM.ppm");
    controller.saveRequest("res/emptyFailed.com");
    assertTrue(log.toString().contains("addFeatures is being called"));
    assertTrue(log.toString().contains("renderMessage is being called"));
  }

  // Testing if the program can run with a series of inputs.
  // Testing to see if our program has any sort of limitations.
  @Test
  public void testSeriesOfInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    controller.greenCompRequest();
    controller.redCompRequest();
    controller.brightenRequest(20);
    controller.sepiaRequest();
    controller.undoRequest();
    controller.undoRequest();
    controller.horizontalRequest();
    controller.undoRequest();
    controller.redoRequest();

    controller.loadRequest("res/Kirby.ppm");

    controller.verticalRequest();
    controller.brightenRequest(100);
    controller.horizontalRequest();
    controller.undoRequest();
    controller.undoRequest();

    assertTrue(log.toString().contains("load was called with imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("greyscale: Green, name = 0, destName = 1"));
    assertTrue(log.toString().contains("greyscale: Red, name = 1, destName = 2"));
    assertTrue(log.toString().contains("increment = 20, name = 2, destName = 3"));
    assertTrue(log.toString().contains("greyscale: Sepia, name = 3, destName = 4"));
    assertTrue(log.toString().contains("flip-horizontal was called with, name = 2, destName = 3"));
    assertTrue(log.toString().contains("load was called with imagePath = res/Kirby.ppm, name = 0"));
    assertTrue(log.toString().contains("flip-vertical was called with, name = 0, destName = 1"));
    assertTrue(log.toString().contains("increment = 100, name = 1, destName = 2"));
    assertTrue(log.toString().contains("flip-horizontal was called with, name = 2, destName = 3"));
  }

  // This tests if the refresh method is called when load is called.
  @Test
  public void testRefreshInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    assertTrue(log.toString().contains("refreshImage is being called"));
  }

  // This tests if the firstMount method is called when load is called.
  @Test
  public void testFirstMountInput() {
    StringBuilder log = new StringBuilder();
    ImageProcessor model = new MockModelController(log);
    ImageGUIView view = new MockViewController(log);
    ImageControllerFeatures controller = new ImageControllerFeaturesImpl(model, view);

    controller.loadRequest("res/Kirby.ppm");
    assertTrue(log.toString().contains("firstMount is being called"));
  }

}


