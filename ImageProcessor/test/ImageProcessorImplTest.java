import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import model.ImageProcessor;
import model.ImageProcessorImpl;
import model.ImageUtil;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * To test the ppm image processor model.
 * <p></p>
 * It should be noted that these tests were performed on a macOS system.
 * However, the image processor is OS-agnostic. File system formats may differ.
 */
public class ImageProcessorImplTest {

  private final ImageUtil util;

  private final ImageProcessor processor;

  /**
   * Set up testing conditions.
   */
  public ImageProcessorImplTest() {
    this.util = new ImageUtil();

    // create a default ppm image processor (no images loaded)
    this.processor = new ImageProcessorImpl();
  }
  
  /**
   * Convert a ByteArrayOutputStream back to a pixel array for testing. This is
   * to avoid saving new image files on a user's computer when running tests.
   */
  private final int[][][] convertByteArrayOutputStream(ByteArrayOutputStream imageData) {
    ByteArrayInputStream input = new ByteArrayInputStream(imageData.toByteArray());
    BufferedImage img = null;
    try {
      img = ImageIO.read(input);
    } catch (Exception e) {
      fail();
    }

    int width = img.getWidth();
    int height = img.getHeight();
    int[][][] imgArr = new int[height][width][3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int pixel = img.getRGB(col, row);
        Color rgb = new Color(pixel);

        imgArr[row][col][0] = rgb.getRed();
        imgArr[row][col][1] = rgb.getGreen();
        imgArr[row][col][2] = rgb.getBlue();
      }
    }
    return imgArr;
  }

  /**
   * Determines whether a PPM image can be successfully loaded and saved using
   * the image processor. By extension, this tests the construction of the
   * image processor as a side effect.
   * <p></p>
   * It should be noted that these operations are the only mechanism for
   * inputting and outputting image data. It was decided that exposing the internal
   * state of the image processor was unnecessary from a client perspective, and
   * would only be useful for testing. In other words, a client need only import
   * and export images via the program.
   * <p></p>
   * As a result, loadImage and saveImage are further validated by
   * any tests that need to import and export data from the processor.
   * <p></p>
   * This test uses an image with an odd width and an odd height.
   */
  @Test
  public void testLoadAndSaveImageWorksOddByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/Kirby.ppm", "kirby");
    this.processor.saveImage(imageData, "kirby", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/Kirby.ppm")),
            imageData.toString());
  }

  /**
   * Tests loading and saving an image with an even width and even height.
   */
  @Test
  public void testLoadAndSaveImageWorksEvenByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/deadmau5.ppm", "deadmau5");
    this.processor.saveImage(imageData, "deadmau5", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/deadmau5.ppm")),
            imageData.toString());
  }

  /**
   * Tests loading and saving an image with an odd width and even height.
   * This also ensures that a portrait-oriented image can be loaded.
   */
  @Test
  public void testLoadAndSaveImageWorksOddByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/nyc.ppm", "nyc");
    this.processor.saveImage(imageData, "nyc", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/nyc.ppm")),
            imageData.toString());
  }

  /**
   * Tests loading and saving an image with an even width and odd height.
   * This also ensures that a landscape-oriented image can be loaded.
   */
  @Test
  public void testLoadAndSaveImageWorksEvenByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/NyanCat.ppm", "nyanCat");
    this.processor.saveImage(imageData, "nyanCat", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/NyanCat.ppm")),
            imageData.toString());
  }

  /**
   * Test that image can be overwritten using load.
   */
  @Test
  public void testLoadImageOverwrite() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/NyanCat.ppm", "sameName");
    this.processor.loadImage("res/deadmau5.ppm", "sameName");

    this.processor.saveImage(imageData, "sameName", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/deadmau5.ppm")),
            imageData.toString());
  }

  /**
   * Test loading PNG.
   */
  @Test
  public void testLoadPNGImage() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/onePNG.png", "onePNG");
    this.processor.saveImage(imageData, "onePNG", ".ppm");

    // Since other image formats are compressed, we must validate by saving as a PPM
    // and comparing the saved PPM version with the actual PPM version
    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/onePPM.ppm")),
            imageData.toString());
  }

  /**
   * Test loading JPG.
   */
  @Test
  public void testLoadJPGImage() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/oneJPG.jpg", "oneJPG");
    this.processor.saveImage(imageData, "oneJPG", ".ppm");

    // Since other image formats are compressed, we must validate by saving as a PPM
    // and comparing the saved PPM version with the actual PPM version
    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/onePPM.ppm")),
            imageData.toString());
  }

  /**
   * Test loading JPEG.
   */
  @Test
  public void testLoadJPEGImage() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/oneJPEG.jpeg", "oneJPEG");
    this.processor.saveImage(imageData, "oneJPEG", ".ppm");

    // Since other image formats are compressed, we must validate by saving as a PPM
    // and comparing the saved PPM version with the actual PPM version
    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/onePPM.ppm")),
            imageData.toString());
  }

  /**
   * Test loading BMP.
   */
  @Test
  public void testLoadBMPImage() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/oneBMP.bmp", "oneBMP");
    this.processor.saveImage(imageData, "oneBMP", ".ppm");

    // Since other image formats are compressed, we must validate by saving as a PPM
    // and comparing the saved PPM version with the actual PPM version
    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/onePPM.ppm")),
            imageData.toString());
  }

  /**
   * Test load as PNG, perform some operations, and save as BMP.
   */
  @Test
  public void testLoadAsPNGSaveAsBMPWithOperations() {
    ByteArrayOutputStream imageData1 = new ByteArrayOutputStream();
    ByteArrayOutputStream imageData2 = new ByteArrayOutputStream();

    // load PNG
    this.processor.loadImage("res/onePNG.png", "onePNG");

    // operations on PNG
    this.processor.redChannel("onePNG", "newOnePNG");
    this.processor.flipVertical("newOnePNG", "finalOnePNG");

    // save PNG as BMP
    this.processor.saveImage(imageData1, "finalOnePNG", ".bmp");

    // BMP pixel array from PNG with operations
    int[][][] pngToBmp = this.convertByteArrayOutputStream(imageData1);

    // load real BMP
    this.processor.loadImage("res/oneBMP.bmp", "oneBMP");

    // operations on real BMP
    this.processor.redChannel("oneBMP", "newOneBMP");
    this.processor.flipVertical("newOneBMP", "finalOneBMP");

    // save modified real BMP
    this.processor.saveImage(imageData2, "finalOnePNG", ".bmp");

    // BMP pixel array from real BMP
    int[][][] bmpToBmp = this.convertByteArrayOutputStream(imageData2);

    // validate that the saved bmp image's pixels match those of the original bmp image
    // with the operations performed on it
    assertArrayEquals(bmpToBmp, pngToBmp);
  }

  /**
   * Test load as PNG and save as PNG.
   */
  @Test
  public void testLoadAsPNGSaveAsPNG() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load PNG and save as PNG
    this.processor.loadImage("res/onePNG.png", "onePNG");
    this.processor.saveImage(imageData, "onePNG", ".png");

    // to validate the creation of a png image with byte array, we have to
    // read the byte array back into an image format and get its pixels
    int[][][] pngArr = this.convertByteArrayOutputStream(imageData);

    // validate that the saved png image's pixels match those of the original png image
    assertArrayEquals(this.util.readWithIO("res/onePNG.png"), pngArr);
  }

  /**
   * Test load as PNG and save as JPG.
   */
  @Test
  public void testLoadAsPNGSaveAsJPG() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load PNG and save as JPG
    this.processor.loadImage("res/onePNG.png", "onePNG");
    this.processor.saveImage(imageData, "onePNG", ".jpg");

    // to validate the creation of a jpg image with byte array, we have to
    // read the byte array back into an image format and get its pixels
    int[][][] jpgArr = this.convertByteArrayOutputStream(imageData);

    // validate that the saved jpg image's pixels match those of the original jpg image
    assertArrayEquals(this.util.readWithIO("res/oneJPG.jpg"), jpgArr);
  }

  /**
   * Test load as PNG and save as JPEG.
   */
  @Test
  public void testLoadAsPNGSaveAsJPEG() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load PNG and save as JPEG
    this.processor.loadImage("res/onePNG.png", "onePNG");
    this.processor.saveImage(imageData, "onePNG", ".jpeg");

    // to validate the creation of a jpeg image with byte array, we have to
    // read the byte array back into an image format and get its pixels
    int[][][] jpegArr = this.convertByteArrayOutputStream(imageData);

    // validate that the saved jpeg image's pixels match those of the original jpeg image
    assertArrayEquals(this.util.readWithIO("res/oneJPG.jpg"), jpegArr);
  }

  /**
   * Test load as PNG and save as BMP.
   */
  @Test
  public void testLoadAsPNGSaveAsBMP() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load PNG and save as BMP
    this.processor.loadImage("res/onePNG.png", "onePNG");
    this.processor.saveImage(imageData, "onePNG", ".bmp");

    // to validate the creation of a bmp image with byte array, we have to
    // read the byte array back into an image format and get its pixels
    int[][][] bmpArr = this.convertByteArrayOutputStream(imageData);

    // validate that the saved bmp image's pixels match those of the original bmp image
    assertArrayEquals(this.util.readWithIO("res/oneJPG.jpg"), bmpArr);
  }

  /**
   * Test hasLoadedImage.
   */
  @Test
  public void testHasLoadedImage() {
    this.processor.loadImage("res/onePNG.png", "onePNG");
    assertTrue(this.processor.hasLoadedImage("onePNG"));
    assertFalse(this.processor.hasLoadedImage("triangle"));
  }

  /**
   * Check that loaded image cannot have an empty name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLoadExceptionEmptyName() {
    this.processor.loadImage("res/Kirby.ppm", "");
  }

  /**
   * Check that loaded image cannot have space in its name.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLoadExceptionNameWithSpaces() {
    this.processor.loadImage("res/Kirby.ppm", "kir by ");
  }

  /**
   * Check that a bad file path results in an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLoadExceptionFileNotFound() {
    this.processor.loadImage("/nope", "kirby");
  }

  /**
   * Check that a ppm file in an invalid format (RAW ppm) throws an IllegalArgumentException.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLoadExceptionBadImageFormat() {
    // BadKirby.ppm is in RAW (P6) format
    this.processor.loadImage("res/BadKirby.ppm", "kirby");
  }

  /**
   * Check that an IllegalArgumentException is thrown when attempting to load an image
   * with an invalid file extension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testLoadExceptionUnsupportedFileFormat() {
    // Attempting to load a .gif file
    this.processor.loadImage("res/KirbyGIF.gif", "kirby");
  }

  /**
   * If an image has not been loaded yet, its name will not be in the map.
   * This will check that a user cannot save an image before loading an image.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testSaveExceptionImageNameDoesNotExist() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();
    this.processor.saveImage(imageData, "kirby", ".ppm"); // kirby was never loaded
  }

  /**
   * Checks that an IOException is caught when experiencing a transmission failure
   * to the image output stream.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testSaveExceptionWriteTransmissionFailure() {
    // this output stream will always throw an exception when attempting to write data
    CorruptOutputStream imageData = new CorruptOutputStream();
    this.processor.saveImage(imageData, "kirby", ".ppm");
  }

  /**
   * Checks that an IllegalArgumentException is thrown when attempting to save to
   * an invalid file format.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testSaveExceptionUnsupportedFileFormat() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    this.processor.loadImage("res/oneJPEG.jpeg", "oneJPEG");
    // attempt to save as a text file
    this.processor.saveImage(imageData, "oneJPEG", ".txt");
  }

  /**
   * Test getSupportedFormats returns a string array containing valid file extensions.
   */
  @Test
  public void testGetSupportedFormats() {
    String[] formats = new String[] {".ppm", ".jpg", ".jpeg", ".png", ".bmp"};
    assertArrayEquals(formats, this.processor.getSupportedFormats());
  }

  /**
   * Check whether the image processor can create images
   * that visualize the given color component.
   * <p></p>
   * Tests createNewImage, loadImage, and saveImage by extension.
   *
   * @param imagePath
   *     The path of the image to edit
   * @param imgArr
   *     The original image pixel array
   * @param comp
   *     The greyscale component to test for -- could be a channel value or one of
   *     Value, Intensity, Luma, or Custom
   */
  public void testGreyscaleHelper(String imagePath, int[][][] imgArr, String comp) {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage(imagePath, "old");

    int rgbIndex;

    // take the original image array and set each RGB value to the given component
    switch (comp) {
      case "Red":
        // greyscale by the red color channel
        this.processor.redChannel("old", "new");
        rgbIndex = 0;
        this.rgbHelper(imgArr, rgbIndex);
        break;
      case "Green":
        // greyscale by the green color channel
        this.processor.greenChannel("old", "new");
        rgbIndex = 1;
        this.rgbHelper(imgArr, rgbIndex);
        break;
      case "Blue":
        // greyscale by the blue color channel
        this.processor.redChannel("old", "new");
        rgbIndex = 2;
        this.rgbHelper(imgArr, rgbIndex);
        break;
      case "Value":
        // greyscale by maximum RGB value
        this.processor.maxVal("old", "new");
        this.valueHelper(imgArr);
        break;
      case "Intensity":
        // greyscale by intensity
        this.processor.intensity("old", "new");
        this.intensityHelper(imgArr);
        break;
      case "Luma":
        // greyscale by luma
        this.processor.luma("old", "new");
        this.lumaHelper(imgArr);
        break;
      case "Custom":
        // custom greyscale with coefficients 0.3, 0.5, 0.01
        // r', g', b' = 0.3 * r + 0.5 * g + 0.01 * b
        this.processor.customGreyscale(0.3, 0.5, 0.01, "old", "new");
        this.customHelper(imgArr);
        break;
      default:
        break;
    }
    // save the new image to output stream
    this.processor.saveImage(imageData, "new", ".ppm");

    // check that the saved image is the same as the modified image array
    assertEquals(ImageUtilTest.ppmStringFormat(imgArr), imageData.toString());
  }

  /**
   * Convert an image array to value-greyscale.
   *
   * @param imgArr
   *     Image pixel array
   */
  private void valueHelper(int[][][] imgArr) {
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        int maxValue = rgb[0];

        if (rgb[1] > maxValue) {
          maxValue = rgb[1];
        }
        if (rgb[2] > maxValue) {
          maxValue = rgb[2];
        }

        rgb[0] = maxValue;
        rgb[1] = maxValue;
        rgb[2] = maxValue;
      }
    }
  }

  /**
   * Convert an image array to intensity-greyscale.
   *
   * @param imgArr
   *     Image pixel array
   */
  private void intensityHelper(int[][][] imgArr) {
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        int average = (rgb[0] + rgb[1] + rgb[2]) / 3;

        rgb[0] = average;
        rgb[1] = average;
        rgb[2] = average;
      }
    }
  }

  /**
   * Convert an image array to luma-greyscale. This is, in essence, the previous
   * version of luma (greyscale) from the model. Testing via the old method has
   * allowed us to validate the newer version of luma.
   *
   * @param imgArr
   *     Image pixel array
   */
  private void lumaHelper(int[][][] imgArr) {
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        int sum = (int) ((0.2126 * rgb[0]) + (0.7152 * rgb[1]) + (0.0722 * rgb[2]));

        rgb[0] = sum;
        rgb[1] = sum;
        rgb[2] = sum;
      }
    }
  }

  /**
   * Convert an image array to RGB-greyscale.
   *
   * @param imgArr
   *     Image pixel array
   */
  private void rgbHelper(int[][][] imgArr, int rgbIndex) {
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        rgb[0] = rgb[rgbIndex];
        rgb[1] = rgb[rgbIndex];
        rgb[2] = rgb[rgbIndex];
      }
    }
  }

  /**
   * Convert an image to custom-greyscale.
   *
   * @param imgArr
   *     Image pixel array
   */
  private void customHelper(int[][][] imgArr) {
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        int newVal = (int) (0.03 * rgb[0] + 0.02 * rgb[1] + 0.01 * rgb[2]);
        if (newVal > 255) {
          newVal = 255;
        }
        if (newVal < 0) {
          newVal = 0;
        }

        rgb[0] = newVal;
        rgb[1] = newVal;
        rgb[2] = newVal;
      }
    }
  }

  /**
   * Test greyscale with RED component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleRedComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Red");
  }

  /**
   * Test greyscale with RED component for an image with an EVEN width and EVEN height.
   */
  private void testGreyscaleRedComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Red");
  }

  /**
   * Test greyscale with RED component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleRedComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Red");
  }

  /**
   * Test greyscale with RED component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleRedComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Red");
  }

  /**
   * Test greyscale with GREEN component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleGreenComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Green");
  }

  /**
   * Test greyscale with GREEN component for an image with an EVEN width and EVEN height.
   */
  private void testGreyscaleGreenComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Green");
  }

  /**
   * Test greyscale with GREEN component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleGreenComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Green");
  }

  /**
   * Test greyscale with GREEN component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleGreenComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Green");
  }

  /**
   * Test greyscale with BLUE component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleBlueComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Blue");
  }

  /**
   * Test greyscale with BLUE component for an image with an EVEN width and EVEN height.
   */
  private void testGreyscaleWorksBlueComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Blue");
  }

  /**
   * Test greyscale with BLUE component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleBlueComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Blue");
  }

  /**
   * Test greyscale with BLUE component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleBlueComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Blue");
  }

  /**
   * Test greyscale with VALUE component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleValueComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Value");
  }

  /**
   * Test greyscale with VALUE component for an image with an EVEN width and EVEN height.
   */
  private void testGreyscaleValueComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Value");
  }

  /**
   * Test greyscale with VALUE component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleValueComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Value");
  }

  /**
   * Test greyscale with VALUE component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleValueComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Value");
  }

  /**
   * Test greyscale with INTENSITY component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleIntensityComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Intensity");
  }

  /**
   * Test greyscale with INTENSITY component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleIntensityComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Intensity");
  }

  /**
   * Test greyscale with INTENSITY component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleIntensityComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Intensity");
  }

  /**
   * Test greyscale with INTENSITY component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleIntensityComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Intensity");
  }

  /**
   * Test greyscale with LUMA component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleLumaComponentOddByOdd() {
    this.testGreyscaleHelper("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "Luma");
  }

  /**
   * Test greyscale with LUMA component for an image with an ODD width and ODD height.
   */
  private void testGreyscaleLumaComponentEvenByEven() {
    this.testGreyscaleHelper("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "Luma");
  }

  /**
   * Test greyscale with LUMA component for an image with an ODD width and EVEN height.
   */
  private void testGreyscaleLumaComponentOddByEven() {
    this.testGreyscaleHelper("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "Luma");
  }

  /**
   * Test greyscale with LUMA component for an image with an EVEN width and ODD height.
   */
  private void testGreyscaleLumaComponentEvenByOdd() {
    this.testGreyscaleHelper("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "Luma");
  }

  /**
   * Test custom greyscale (color transformation) on PPM. Also tests max out at 255 and
   * min out at 0.
   */
  private void testCustomGreyscalePPM() {
    this.testGreyscaleHelper("res/onePPM.ppm",
            util.readPPM("res/onePPM.ppm"), "Custom");
  }

  /**
   * Test custom greyscale (color transformation) on PNG. Also tests max out at 255 and
   * min out at 0.
   */
  private void testCustomGreyscalePNG() {
    this.testGreyscaleHelper("res/onePNG.png",
            util.readWithIO("res/onePNG.png"), "Custom");
  }

  /**
   * Test custom greyscale (color transformation) on JPG. Also tests max out at 255 and
   * min out at 0.
   */
  private void testCustomGreyscaleJPG() {
    this.testGreyscaleHelper("res/oneJPG.jpg",
            util.readWithIO("res/oneJPG.jpg"), "Custom");
  }

  /**
   * Test custom greyscale (color transformation) on JPEG. Also tests max out at 255 and
   * min out at 0.
   */
  private void testCustomGreyscaleJPEG() {
    this.testGreyscaleHelper("res/oneJPEG.jpeg",
            util.readWithIO("res/oneJPEG.jpeg"), "Custom");
  }

  /**
   * Test custom greyscale (color transformation) on BMP. Also tests max out at 255 and
   * min out at 0.
   */
  private void testCustomGreyscaleBMP() {
    this.testGreyscaleHelper("res/oneBMP.bmp",
            util.readWithIO("res/oneBMP.bmp"), "Custom");
  }

  /**
   * Test general color transformation works. This also tests max out at 255
   * and min out at 0. Various formats are tested above by custom greyscale, which
   * uses color transformation. The sepia tone is tested in testSepiaColorTransformation.
   * The greyscale tone is tested by the various greyscale tests above (including the
   * improved luma color transformation).
   */
  public int[][][] testColorTransformationHelper(double[][] cMatrix, int[][][] imgArr) {
    // image pixel array
    int[][][] newImgArr = new int[imgArr.length][imgArr[0].length][3];

    for (int row = 0; row < imgArr.length; row++) {
      for (int col = 0; col < imgArr[row].length; col++) {
        for (int i = 0; i < 3; i++) {
          int newVal = (int) ((cMatrix[i][0] * imgArr[row][col][0])
                  + (cMatrix[i][1] * imgArr[row][col][1])
                  + (cMatrix[i][2] * imgArr[row][col][2]));

          if (newVal > 255) {
            newImgArr[row][col][i] = 255;
          }
          else if (newVal < 0) {
            newImgArr[row][col][i] = 255;
          }
          else {
            newImgArr[row][col][i] = newVal;
          }
        }
      }
    }
    return newImgArr;
  }

  /**
   * Test a custom color transformation with a variety of coefficient values.
   */
  private void testCustomColorTransformation() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    double[][] cMatrix = new double[][]{
        new double[]{0.2, -0.1, 1},
        new double[]{0.003, 1, 0.4},
        new double[]{0.246, 0, -0.0023}
    };

    int[][][] newImgArr = this.testColorTransformationHelper(cMatrix,
            util.readPPM("res/onePPM.ppm"));

    // load an image
    this.processor.loadImage("res/onePPM.ppm", "old");

    // color transformation with a custom matrix
    this.processor.colorTransformation(cMatrix, "old", "new");

    // save as PPM for validation
    this.processor.saveImage(imageData, "new", ".ppm");

    // check that the saved image is the same as the modified image array
    assertEquals(ImageUtilTest.ppmStringFormat(newImgArr), imageData.toString());
  }

  /**
   * Test multiple color transformations.
   */
  @Test
  public void testMultipleColorTransformations() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    double[][] cMatrix1 = new double[][]{
        new double[]{0.2, -0.1, 1},
        new double[]{0.003, 1, 0.4},
        new double[]{0.246, 0, -0.0023}
    };

    double[][] cMatrix2 = new double[][]{
        new double[]{0.001, 0, 1},
        new double[]{0.023, 1, -0.3},
        new double[]{0.6, 0, -0.1073}
    };

    int[][][] newImgArr1 = this.testColorTransformationHelper(cMatrix1,
            util.readPPM("res/onePPM.ppm"));
    int[][][] newImgArr2 = this.testColorTransformationHelper(cMatrix2, newImgArr1);

    // load an image
    this.processor.loadImage("res/onePPM.ppm", "old");

    // color transformation 1 with a custom matrix
    this.processor.colorTransformation(cMatrix1, "old", "new");

    // color transformation 2 with a custom matrix
    this.processor.colorTransformation(cMatrix2, "new", "final");

    // save as PPM for validation
    this.processor.saveImage(imageData, "final", ".ppm");

    // check that the saved image is the same as the modified image array
    assertEquals(ImageUtilTest.ppmStringFormat(newImgArr2), imageData.toString());
  }

  /**
   * Test sepia tone for color transformation.
   */
  private void testSepiaColorTransformation() {
    this.testColorTransformationHelper(new double[][]{
        new double[]{0.393, 0.769, 0.189},
        new double[]{0.349, 0.686, 0.168},
        new double[]{0.272, 0.534, 0.131}
    }, util.readPPM("res/onePPM.ppm"));
  }

  /**
   * Test that name and destName cannot be the same for any color transformation.
   * This covers: redChannel, greenChannel, blueChannel, maxVal, intensity,
   * luma, and customGreyscale.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testColorTransformationExceptionSameNames() {
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    this.processor.luma("kirby", "kirby");
  }

  /**
   * Test that color transformation throws an IllegalArgumentException when
   * given a name that does not exist in the map.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testColorTransformationExceptionNameDoesNotExist() {
    // image hasn't been loaded yet
    this.processor.redChannel("tree", "newTree");
  }

  /**
   * Test that color transformation throws an IllegalArgumentException when
   * given an invalid destination name (i.e., contains spaces or empty).
   */
  @Test (expected = IllegalArgumentException.class)
  public void testColorTransformationExceptionInvalidDestName() {
    // load an image
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    // image hasn't been loaded yet
    this.processor.redChannel("kirby", "");
  }

  /**
   * Test sharpen filter. This tests applyFilter, convolution, channelToRGBIndex,
   * and Filter by extension. If an image is successfully sharpened, each of these
   * methods (and the Filter class with its methods) will have behaved as expected.
   * This includes all four edge cases and all four corner cases for convolution.
   * If an image is successfully rendered, these edge/corner will have been tested
   * in the process.
   */
  @Test
  public void testSharpenFilter() {
    ByteArrayOutputStream imageData1 = new ByteArrayOutputStream();
    ByteArrayOutputStream imageData2 = new ByteArrayOutputStream();

    // load image
    this.processor.loadImage("res/onePPM.ppm", "onePPM");
    // sharpen the image
    this.processor.sharpen("onePPM", "sharpened");
    // save the new sharpened image
    this.processor.saveImage(imageData1, "sharpened", ".ppm");

    // load a sharpened version of the original image
    this.processor.loadImage("res/sharp.ppm", "realSharp");
    // re-save to get the byte array
    this.processor.saveImage(imageData2, "realSharp", ".ppm");

    // check that the ground truth sharpened image equals the sharpened PPM
    assertEquals(imageData2.toString(), imageData1.toString());
  }

  /**
   * Test multiple sharpen filters.
   */
  @Test
  public void testMultipleSharpenFilters() {
    ByteArrayOutputStream imageData1 = new ByteArrayOutputStream();
    ByteArrayOutputStream imageData2 = new ByteArrayOutputStream();

    // load image
    this.processor.loadImage("res/onePPM.ppm", "onePPM");
    // sharpen the image once
    this.processor.sharpen("onePPM", "sharpened1");
    // sharpen the image twice
    this.processor.sharpen("sharpened1", "sharpened2");
    // sharpen the image thrice
    this.processor.sharpen("sharpened2", "sharpened3");
    // save the new sharpened image
    this.processor.saveImage(imageData1, "sharpened3", ".ppm");

    // load a sharpened version of the original image
    this.processor.loadImage("res/multipleSharp.ppm", "realSharp");
    // re-save to get the byte array
    this.processor.saveImage(imageData2, "realSharp", ".ppm");

    // check that the ground truth sharpened image equals the sharpened PNG
    assertEquals(imageData2.toString(), imageData1.toString());
  }

  /**
   * Test gaussian blur filter. This tests applyFilter, convolution, channelToRGBIndex,
   * and Filter by extension. If an image is successfully blurred, each of these
   * methods (and the Filter class with its methods) will have behaved as expected.
   * This includes all four edge cases and all four corner cases for convolution.
   * If an image is successfully rendered, these edge/corner will have been tested
   * in the process.
   */
  @Test
  public void testBlurFilter() {
    ByteArrayOutputStream imageData1 = new ByteArrayOutputStream();
    ByteArrayOutputStream imageData2 = new ByteArrayOutputStream();

    // load image
    this.processor.loadImage("res/onePPM.ppm", "onePPM");
    // blur the image
    this.processor.gaussianBlur("onePPM", "blurred");
    // save the new blurred image
    this.processor.saveImage(imageData1, "blurred", ".ppm");

    // load a blurred version of the original image
    this.processor.loadImage("res/blur.ppm", "realBlur");
    // re-save to get the byte array
    this.processor.saveImage(imageData2, "realBlur", ".ppm");

    // check that the ground truth sharpened image equals the sharpened PPM
    assertEquals(imageData2.toString(), imageData1.toString());
  }

  // TODO different formats

  /**
   * Test multiple blur filters.
   */
  @Test
  public void testMultipleBlurFilters() {
    ByteArrayOutputStream imageData1 = new ByteArrayOutputStream();
    ByteArrayOutputStream imageData2 = new ByteArrayOutputStream();

    // load image
    this.processor.loadImage("res/onePPM.ppm", "onePPM");
    // blur the image once
    this.processor.gaussianBlur("onePPM", "blurred1");
    // blur the image twice
    this.processor.gaussianBlur("blurred1", "blurred2");
    // blur the image thrice
    this.processor.gaussianBlur("blurred2", "blurred3");
    // save the new blurred image
    this.processor.saveImage(imageData1, "blurred3", ".ppm");

    // load a blurred version of the original image
    this.processor.loadImage("res/multipleBlur.ppm", "realBlur");
    // re-save to get the byte array
    this.processor.saveImage(imageData2, "realBlur", ".ppm");

    // check that the ground truth sharpened image equals the sharpened PNG
    assertEquals(imageData2.toString(), imageData1.toString());
  }

  /**
   * Test that processor can brighten and darken images.
   * <p></p>
   * Tests createNewImage, loadImage, and saveImage by extension.
   *
   * @param imagePath
   *     The path of the image to edit
   * @param imgArr
   *     The associated pixel array of the original image
   * @param increment
   *     The amount to increase(+) or decrease(-) the brightness by
   */
  public void testGeneralBrightnessWorks(String imagePath, int[][][] imgArr, int increment) {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage(imagePath, "old");

    // adjust brightness by the given increment
    this.processor.brightness(increment, "old", "new");

    // save the new image to output stream
    this.processor.saveImage(imageData, "new", ".ppm");

    // take the original image array and add brightness to each RGB value
    for (int[][] col : imgArr) {
      for (int[] rgb : col) {
        for (int i = 0; i < 3; i++) {
          if (rgb[i] + increment > 255) {
            rgb[i] = 255;
          }
          else if (rgb[i] + increment < 0) {
            rgb[i] = 0;
          }
          else {
            rgb[i] = rgb[i] + increment;
          }
        }
      }
    }

    // check that the saved image is the same as the modified image array
    assertEquals(ImageUtilTest.ppmStringFormat(imgArr), imageData.toString());
  }

  /**
   * Test adding to brightness works for an image with an ODD width and an ODD height.
   */
  private void testBrightnessAddWorksOddByOdd() {
    this.testGeneralBrightnessWorks("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), 3);
  }

  /**
   * Test adding to brightness works for an image with an EVEN width and an EVEN height.
   */
  private void testBrightnessAddWorksEvenByEven() {
    this.testGeneralBrightnessWorks("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), 100);
  }

  /**
   * Test adding to brightness works for an image with an ODD width and an EVEN height.
   */
  private void testBrightnessAddWorksOddByEven() {
    this.testGeneralBrightnessWorks("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), 16);
  }

  /**
   * Test adding to brightness works for an image with an EVEN width and an ODD height.
   */
  private void testBrightnessAddWorksEvenByOdd() {
    this.testGeneralBrightnessWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), 25);
  }

  /**
   * Test subtracting from brightness works for an image with an ODD width and an ODD height.
   */
  private void testBrightnessSubtractWorksOddByOdd() {
    this.testGeneralBrightnessWorks("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), -53);
  }

  /**
   * Test subtracting from brightness works for an image with an EVEN width and an EVEN height.
   */
  private void testBrightnessSubtractWorksEvenByEven() {
    this.testGeneralBrightnessWorks("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), -12);
  }

  /**
   * Test subtracting from brightness works for an image with an ODD width and an EVEN height.
   */
  private void testBrightnessSubtractWorksOddByEven() {
    this.testGeneralBrightnessWorks("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), -2);
  }

  /**
   * Test subtracting from brightness works for an image with an EVEN width and an ODD height.
   */
  private void testBrightnessSubtractWorksEvenByOdd() {
    this.testGeneralBrightnessWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), -127);
  }

  /**
   * Test adding enough to brightness will max out at 255 for each RGB value.
   * This comparison is enforced in the test's helper method.
   */
  private void testBrightnessMaxoutAt255() {
    this.testGeneralBrightnessWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), 1000);
  }

  /**
   * Test subtracting enough from brightness will min out at 0 for each RGB value.
   * This comparison is enforced in the test's helper method.
   */
  private void testBrightnesssMinoutAt0() {
    this.testGeneralBrightnessWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), -1000);
  }

  /**
   * Test brightness with PNG.
   */
  private void testBrightnessPNG() {
    this.testGeneralBrightnessWorks("res/onePNG.png",
            util.readWithIO("res/onePNG.png"), 100);
  }

  /**
   * Test brightness with JPG.
   */
  private void testBrightnessJPG() {
    this.testGeneralBrightnessWorks("res/oneJPG.jpg",
            util.readWithIO("res/oneJPG.jpg"), 100);
  }

  /**
   * Test brightness with JPEG.
   */
  private void testBrightnessJPEG() {
    this.testGeneralBrightnessWorks("res/oneJPEG.jpeg",
            util.readWithIO("res/oneJPEG.jpeg"), 100);
  }

  /**
   * Test brightness with BMP.
   */
  private void testBrightnessBMP() {
    this.testGeneralBrightnessWorks("res/oneBMP.bmp",
            util.readWithIO("res/oneBMP.bmp"), 100);
  }

  /**
   * Test that name and destName cannot be the same.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBrightnessExceptionSameNames() {
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    this.processor.brightness(100, "kirby", "kirby");
  }

  /**
   * Test that brightness throws an IllegalArgumentException when
   * given a name that does not exist in the map.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBrightnessExceptionNameDoesNotExist() {
    // image hasn't been loaded yet
    this.processor.brightness(100, "tree", "newTree");
  }

  /**
   * Test that brightness throws an IllegalArgumentException when
   * given an invalid destination name (i.e., contains spaces or empty).
   */
  @Test (expected = IllegalArgumentException.class)
  public void testBrightnessExceptionInvalidDestName() {
    // load an image
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    // image hasn't been loaded yet
    this.processor.brightness(23, "kirby", "");
  }

  /**
   * Test that processor can perform a flip. Client must specify
   * either "horizontal" or "vertical" in the direction parameter.
   * <p></p>
   * Tests createNewImage, loadImage, and saveImage by extension.
   *
   * @param imagePath
   *     The path of the image to edit
   * @param imgArr
   *     The associated pixel array of the original image
   * @param direction
   *     Either "horizontal" or "vertical"
   */
  public void testGeneralFlipWorks(String imagePath, int[][][] imgArr, String direction) {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage(imagePath, "old");

    // flip horizontally or vertically based on instruction
    switch (direction) {
      case "horizontal":
        this.processor.flipHorizontal("old", "new");

        // take the original image array and reverse row values
        for (int row = 0; row < imgArr.length; row++) {
          for (int col = 0; col < Math.floor(imgArr[col].length / 2); col++) {
            int[] temp = imgArr[row][col];
            imgArr[row][col] = imgArr[row][imgArr[row].length - col - 1];
            imgArr[row][imgArr[row].length - col - 1] = temp;
          }
        }
        break;
      case "vertical":
        this.processor.flipVertical("old", "new");

        // reverse original image array
        for (int row = 0; row < Math.floor(imgArr.length / 2); row++) {
          int[][] temp = imgArr[row];
          imgArr[row] = imgArr[imgArr.length - row - 1];
          imgArr[imgArr.length - row - 1] = temp;
        }
        break;
      default:
        throw new IllegalArgumentException("direction parameter must be either \"horizontal\"" +
                " or \"vertical\"");
    }

    // save the new image to output stream
    this.processor.saveImage(imageData, "new", ".ppm");

    // check that the saved image is the same as the modified image array
    assertEquals(ImageUtilTest.ppmStringFormat(imgArr), imageData.toString());
  }

  /**
   * Test horizontal flip for an image with an ODD width and an ODD height.
   */
  private void testHorizontalWorksOddByOdd() {
    this.testGeneralFlipWorks("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "horizontal");
  }

  /**
   * Test horizontal flip for an image with an EVEN width and an EVEN height.
   */
  private void testHorizontalWorksEvenByEven() {
    this.testGeneralFlipWorks("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "horizontal");
  }

  /**
   * Test horizontal flip for an image with an ODD width and an EVEN height.
   */
  private void testHorizontalWorksOddByEven() {
    this.testGeneralFlipWorks("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "horizontal");
  }

  /**
   * Test horizontal flip for an image with an EVEN width and an ODD height.
   */
  private void testHorizontalWorksEvenByOdd() {
    this.testGeneralFlipWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "horizontal");
  }

  /**
   * Test vertical flip for an image with an ODD width and an ODD height.
   */
  private void testVerticalWorksOddByOdd() {
    this.testGeneralFlipWorks("res/Kirby.ppm",
            util.readPPM("res/Kirby.ppm"), "vertical");
  }

  /**
   * Test vertical flip for an image with an EVEN width and an EVEN height.
   */
  private void testVerticalWorksEvenByEven() {
    this.testGeneralFlipWorks("res/deadmau5.ppm",
            util.readPPM("res/deadmau5.ppm"), "vertical");
  }

  /**
   * Test vertical flip for an image with an ODD width and an EVEN height.
   */
  private void testVerticalWorksOddByEven() {
    this.testGeneralFlipWorks("res/nyc.ppm",
            util.readPPM("res/nyc.ppm"), "vertical");
  }

  /**
   * Test vertical flip for an image with an EVEN width and an ODD height.
   */
  private void testVerticalWorksEvenByOdd() {
    this.testGeneralFlipWorks("res/NyanCat.ppm",
            util.readPPM("res/NyanCat.ppm"), "vertical");
  }

  /**
   * Test horizontal flip with PNG.
   */
  private void testHorizontalPNG() {
    this.testGeneralFlipWorks("res/onePNG.png",
            util.readWithIO("res/onePNG.png"), "horizontal");
  }

  /**
   * Test horizontal flip with JPG.
   */
  private void testHorizontalJPG() {
    this.testGeneralFlipWorks("res/oneJPG.jpg",
            util.readWithIO("res/oneJPG.jpg"), "horizontal");
  }

  /**
   * Test horizontal flip with JPEG.
   */
  private void testHorizontalJPEG() {
    this.testGeneralFlipWorks("res/oneJPEG.jpeg",
            util.readWithIO("res/oneJPEG.jpeg"), "horizontal");
  }

  /**
   * Test horizontal flip with BMP.
   */
  private void testHorizontalBMP() {
    this.testGeneralFlipWorks("res/oneBMP.bmp",
            util.readWithIO("res/oneBMP.bmp"), "horizontal");
  }

  /**
   * Test vertical flip with PNG.
   */
  private void testVerticalPNG() {
    this.testGeneralFlipWorks("res/onePNG.png",
            util.readWithIO("res/onePNG.png"), "vertical");
  }

  /**
   * Test vertical flip with JPG.
   */
  private void testVerticalJPG() {
    this.testGeneralFlipWorks("res/oneJPG.jpg",
            util.readWithIO("res/oneJPG.jpg"), "vertical");
  }

  /**
   * Test vertical flip with JPEG.
   */
  private void testVerticalJPEG() {
    this.testGeneralFlipWorks("res/oneJPEG.jpeg",
            util.readWithIO("res/oneJPEG.jpeg"), "vertical");
  }

  /**
   * Test vertical flip with BMP.
   */
  private void testVerticalBMP() {
    this.testGeneralFlipWorks("res/oneBMP.bmp",
            util.readWithIO("res/oneBMP.bmp"), "vertical");
  }

  /**
   * Test that name and destName cannot be the same.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testHorizontalExceptionSameNames() {
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    this.processor.flipHorizontal("kirby", "kirby");
  }

  /**
   * Test that flipHorizontal throws an IllegalArgumentException when
   * given a name that does not exist in the map.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testHorizontalExceptionNameDoesNotExist() {
    // image hasn't been loaded yet
    this.processor.flipHorizontal("tree", "newTree");
  }

  /**
   * Test that flipHorizontal throws an IllegalArgumentException when
   * given an invalid destination name (i.e., contains spaces or empty).
   */
  @Test (expected = IllegalArgumentException.class)
  public void testHorizontalExceptionInvalidDestName() {
    // load an image
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    // image hasn't been loaded yet
    this.processor.flipHorizontal("kirby", "");
  }

  /**
   * Test that name and destName cannot be the same.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testVerticalExceptionSameNames() {
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    this.processor.flipVertical("kirby", "kirby");
  }

  /**
   * Test that flipVertical throws an IllegalArgumentException when
   * given a name that does not exist in the map.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testVerticalExceptionNameDoesNotExist() {
    // image hasn't been loaded yet
    this.processor.flipVertical("tree", "newTree");
  }

  /**
   * Test that flipVertical throws an IllegalArgumentException when
   * given an invalid destination name (i.e., contains spaces or empty).
   */
  @Test (expected = IllegalArgumentException.class)
  public void testVerticalExceptionInvalidDestName() {
    // load an image
    this.processor.loadImage("res/Kirby.ppm", "kirby");
    // image hasn't been loaded yet
    this.processor.flipVertical("kirby", "");
  }

  /**
   * Test that 2 horizontal flips for an ODD by ODD image produces the original image.
   */
  @Test
  public void testDoubleHorizontalOddByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/Kirby.ppm", "original");

    this.processor.flipHorizontal("original", "flipOnce");
    this.processor.flipHorizontal("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/Kirby.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 horizontal flips for an EVEN by EVEN image produces the original image.
   */
  @Test
  public void testDoubleHorizontalEvenByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/deadmau5.ppm", "original");

    this.processor.flipHorizontal("original", "flipOnce");
    this.processor.flipHorizontal("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/deadmau5.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 horizontal flips for an ODD by EVEN image produces the original image.
   */
  @Test
  public void testDoubleHorizontalOddByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/nyc.ppm", "original");

    this.processor.flipHorizontal("original", "flipOnce");
    this.processor.flipHorizontal("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/nyc.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 horizontal flips for an EVEN by ODD image produces the original image.
   */
  @Test
  public void testDoubleHorizontalEvenByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/NyanCat.ppm", "original");

    this.processor.flipHorizontal("original", "flipOnce");
    this.processor.flipHorizontal("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/NyanCat.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 vertical flips for an ODD by ODD image produces the original image.
   */
  @Test
  public void testDoubleVerticalOddByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/Kirby.ppm", "original");

    this.processor.flipVertical("original", "flipOnce");
    this.processor.flipVertical("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/Kirby.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 vertical flips for an EVEN by EVEN image produces the original image.
   */
  @Test
  public void testDoubleVerticalEvenByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/deadmau5.ppm", "original");

    this.processor.flipVertical("original", "flipOnce");
    this.processor.flipVertical("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/deadmau5.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 vertical flips for an ODD by EVEN image produces the original image.
   */
  @Test
  public void testDoubleVerticalOddByEven() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/nyc.ppm", "original");

    this.processor.flipVertical("original", "flipOnce");
    this.processor.flipVertical("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/nyc.ppm")),
            imageData.toString());
  }

  /**
   * Test that 2 vertical flips for an EVEN by ODD image produces the original image.
   */
  @Test
  public void testDoubleVerticalEvenByOdd() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    // load an image
    this.processor.loadImage("res/NyanCat.ppm", "original");

    this.processor.flipVertical("original", "flipOnce");
    this.processor.flipVertical("flipOnce", "flipTwice");

    this.processor.saveImage(imageData, "flipTwice", ".ppm");

    assertEquals(ImageUtilTest.ppmStringFormat(util.readPPM("res/NyanCat.ppm")),
            imageData.toString());
  }

  /**
   * Test that different types of operations can be performed on the same image.
   */
  @Test
  public void testBrightnessHorizontalIntensityVertical() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    int[][][] kirbyArr = util.readPPM("res/Kirby.ppm");

    // load an image
    this.processor.loadImage("res/Kirby.ppm", "original");

    // perform operations
    this.processor.brightness(93, "original", "brighten");
    this.processor.flipHorizontal("brighten", "horizontal");
    this.processor.intensity("horizontal", "intensity");
    this.processor.flipVertical("intensity", "finalImage");

    // save image
    this.processor.saveImage(imageData, "finalImage", ".ppm");

    // take the original image array and add brightness to each RGB value
    for (int[][] col : kirbyArr) {
      for (int[] rgb : col) {
        for (int i = 0; i < 3; i++) {
          if (rgb[i] + 93 > 255) {
            rgb[i] = 255;
          }
          else if (rgb[i] + 93 < 0) {
            rgb[i] = 0;
          }
          else {
            rgb[i] = rgb[i] + 93;
          }
        }
      }
    }

    // take the original image array and reverse row values
    for (int row = 0; row < kirbyArr.length; row++) {
      for (int col = 0; col < Math.floor(kirbyArr[col].length / 2); col++) {
        int[] temp = kirbyArr[row][col];
        kirbyArr[row][col] = kirbyArr[row][kirbyArr[row].length - col - 1];
        kirbyArr[row][kirbyArr[row].length - col - 1] = temp;
      }
    }

    // greyscale by intensity
    this.intensityHelper(kirbyArr);

    // reverse original image array
    for (int row = 0; row < Math.floor(kirbyArr.length / 2); row++) {
      int[][] temp = kirbyArr[row];
      kirbyArr[row] = kirbyArr[kirbyArr.length - row - 1];
      kirbyArr[kirbyArr.length - row - 1] = temp;
    }

    // compare result
    assertEquals(ImageUtilTest.ppmStringFormat(kirbyArr), imageData.toString());
  }
}