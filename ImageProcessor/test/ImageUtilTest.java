import org.junit.Test;

import java.io.ByteArrayOutputStream;

import model.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * To test ImageUtil.
 */
public class ImageUtilTest {

  private final ImageUtil util;

  /**
   * To set up the testing conditions for ImageUtil.
   */
  public ImageUtilTest() {
    this.util = new ImageUtil();
  }

  /**
   * Converts a ppm image array to string format.
   *
   * @param img
   *     The ppm image pixel array
   * @return
   *     A string containing ppm image data
   */
  public static String ppmStringFormat(int[][][] img) {
    StringBuilder builder = new StringBuilder();
    builder.append("P3\n");
    builder.append(img[0].length + " " + img.length + "\n");
    builder.append("255\n");

    for (int[][] col : img) {
      for (int[] rgb : col) {
        builder.append(rgb[0] + "\n");
        builder.append(rgb[1] + "\n");
        builder.append(rgb[2] + "\n");
      }
    }
    return builder.toString();
  }

  /**
   * Test readPPM and savePPM.
   */
  @Test
  public void testReadPPMAndSavePPM() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    int[][][] imgArr = this.util.readPPM("res/onePPM.ppm");

    this.util.savePPM(imageData, imgArr);

    assertEquals(this.ppmStringFormat(util.readPPM("res/onePPM.ppm")), imageData.toString());
  }

  /**
   * Test readWithIO and saveWithIO.
   */
  @Test
  public void testReadWithIOAndSaveWithIO() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    int[][][] imgArr = this.util.readWithIO("res/onePNG.png");

    this.util.saveWithIO(imageData, imgArr);

    assertEquals(this.ppmStringFormat(util.readWithIO("res/onePNG.png")),
            imageData.toString());
  }

  /**
   * Test readPPM and saveWithIO.
   */
  @Test
  public void testReadPPMAndSaveWithIO() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    int[][][] imgArr = this.util.readPPM("res/onePPM.ppm");

    this.util.saveWithIO(imageData, imgArr);

    assertEquals(this.ppmStringFormat(util.readPPM("res/onePPM.ppm")), imageData.toString());
  }

  /**
   * Test readWithIO and savePPM.
   */
  @Test
  public void testReadWithIOAndSavePPM() {
    ByteArrayOutputStream imageData = new ByteArrayOutputStream();

    int[][][] imgArr = this.util.readWithIO("res/onePNG.png");

    this.util.savePPM(imageData, imgArr);

    assertEquals(this.ppmStringFormat(util.readWithIO("res/onePNG.png")), imageData.toString());
  }

  /**
   * Test getMaxValue.
   */
  @Test
  public void testGetMaxValue() {
    int[][][] imgArr = this.util.readWithIO("res/onePNG.png");
    assertEquals(255, this.util.getMaxValue(imgArr));
  }

  /**
   * Test getHeight.
   */
  @Test
  public void testGetHeight() {
    int[][][] imgArr = this.util.readWithIO("res/onePNG.png");
    assertEquals(216, this.util.getHeight(imgArr));
  }

  /**
   * Test getWidth.
   */
  @Test
  public void testGetWidth() {
    int[][][] imgArr = this.util.readWithIO("res/onePNG.png");
    assertEquals(144, this.util.getWidth(imgArr));
  }

  /**
   * Test getExtension.
   */
  @Test
  public void testGetExtension() {
    assertEquals(".ppm", this.util.getExtension("okAy.ppm"));
    assertEquals(".png", this.util.getExtension("HellO.png"));
    assertEquals(".jpg", this.util.getExtension("YeS123.jpg"));
    assertEquals(".jpeg", this.util.getExtension("nO.jpeg"));
    assertEquals(".bmp", this.util.getExtension("Hi.bmp"));
  }

  /**
   * Test no extension for getExtension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidFileExtensionNoExtension() {
    this.util.getExtension("hello");
  }

  /**
   * Test invalid file extension (just a dot) for getExtension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidFileExtensionDot() {
    this.util.getExtension("okAy.");
  }

  /**
   * Test invalid file extension (ends with dot) for getExtension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidFileExtensionBadEndDot() {
    this.util.getExtension("okAy.ppm.");
  }

  /**
   * Test invalid file extension (ends with invalid character) for getExtension.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testInvalidFileExtensionBadEndCharacter() {
    this.util.getExtension("okAy.ppm#");
  }
}