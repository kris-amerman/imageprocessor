import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.HistogramPanel;

import static org.junit.Assert.assertEquals;

/**
 * This represents the testing for creating the histogram to display.
 */
public class HistogramTest {

  private BufferedImage img;
  private ImageProcessor model = new ImageProcessorImpl();
  private MockHistogram histogram;
  private Map<Integer, Integer> redValues = new HashMap<>();


  // This initializes the image we can test and see if our methods
  // designed to create the histogram works accordingly.
  @Before
  public void initImageLoad() {
    this.model.loadImage("res/oneJPG.jpg", "0");
    img = this.model.getImageState("0");
    StringBuilder log = new StringBuilder();
    histogram = new MockHistogram(img, log);
  }

  @Test
  public void testRedFrequencyHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(2));
    assertEquals(0, (int) histogram.redValues.get(3));
    assertEquals(0, (int) histogram.redValues.get(4));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(0, (int) histogram.redValues.get(7));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(10));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

  }

  // This tests if the refresh works after a modification to the current image.
  @Test
  public void testRedFrequencyAfterModificationHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the luma will update the values

    this.model.luma("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(255));

    // testing if brighten will affect the counts.

    this.model.brightness(100, "1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(0, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(13580, (int) histogram.redValues.get(255));

    // testing if darken will change the counts

    this.model.brightness(-100, "2", "3");

    img = this.model.getImageState("3");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(255));

  }

  @Test
  public void testRedFrequencyAfterRedGreyscale() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the red component will update the values

    this.model.redChannel("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterGreenGreyscale() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the green component will update the values

    this.model.greenChannel("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(248, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(508, (int) histogram.redValues.get(9));
    assertEquals(248, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterBlueGreyscale() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the blue component will update the values

    this.model.blueChannel("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(186, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(19, (int) histogram.redValues.get(8));
    assertEquals(23, (int) histogram.redValues.get(9));
    assertEquals(186, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterMaxValueGreyscale() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the max value component will update the values

    this.model.maxVal("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(0, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterIntensityGreyscale() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the intensity component will update the values

    this.model.intensity("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(0, (int) histogram.redValues.get(8));
    assertEquals(492, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterVerticalHorizontalFlip() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the intensity component will update the values

    this.model.flipHorizontal("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    this.model.flipVertical("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterSharpenBlur() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the blur will update the values

    this.model.gaussianBlur("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(355, (int) histogram.redValues.get(5));
    assertEquals(91, (int) histogram.redValues.get(6));
    assertEquals(62, (int) histogram.redValues.get(8));
    assertEquals(70, (int) histogram.redValues.get(9));
    assertEquals(0, (int) histogram.redValues.get(255));

    this.model.sharpen("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(2067, (int) histogram.redValues.get(0));
    assertEquals(53, (int) histogram.redValues.get(1));
    assertEquals(57, (int) histogram.redValues.get(5));
    assertEquals(33, (int) histogram.redValues.get(6));
    assertEquals(31, (int) histogram.redValues.get(8));
    assertEquals(32, (int) histogram.redValues.get(9));
    assertEquals(4804, (int) histogram.redValues.get(255));
  }

  @Test
  public void testRedFrequencyAfterSepia() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(2002, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(41, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(2, (int) histogram.redValues.get(255));

    // testing if the intensity component will update the values

    this.model.sepia("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.redValues.get(0));
    assertEquals(0, (int) histogram.redValues.get(1));
    assertEquals(0, (int) histogram.redValues.get(5));
    assertEquals(0, (int) histogram.redValues.get(6));
    assertEquals(0, (int) histogram.redValues.get(8));
    assertEquals(0, (int) histogram.redValues.get(9));
    assertEquals(9405, (int) histogram.redValues.get(255));
  }

  @Test
  public void testGreenFrequencyHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.greenValues.get(0));
    assertEquals(0, (int) histogram.greenValues.get(1));
    assertEquals(2002, (int) histogram.greenValues.get(2));
    assertEquals(492, (int) histogram.greenValues.get(3));
    assertEquals(107, (int) histogram.greenValues.get(4));
    assertEquals(248, (int) histogram.greenValues.get(5));
    assertEquals(0, (int) histogram.greenValues.get(6));
    assertEquals(0, (int) histogram.greenValues.get(7));
    assertEquals(41, (int) histogram.greenValues.get(8));
    assertEquals(508, (int) histogram.greenValues.get(9));
    assertEquals(632, (int) histogram.greenValues.get(10));
    assertEquals(0, (int) histogram.greenValues.get(255));
  }

  @Test
  public void testGreenFreqModificationHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.greenValues.get(0));
    assertEquals(0, (int) histogram.greenValues.get(1));
    assertEquals(2002, (int) histogram.greenValues.get(2));
    assertEquals(492, (int) histogram.greenValues.get(3));
    assertEquals(107, (int) histogram.greenValues.get(4));
    assertEquals(248, (int) histogram.greenValues.get(5));
    assertEquals(0, (int) histogram.greenValues.get(6));
    assertEquals(0, (int) histogram.greenValues.get(7));
    assertEquals(41, (int) histogram.greenValues.get(8));
    assertEquals(508, (int) histogram.greenValues.get(9));
    assertEquals(632, (int) histogram.greenValues.get(10));
    assertEquals(0, (int) histogram.greenValues.get(255));


    this.model.flipVertical("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.greenValues.get(0));
    assertEquals(0, (int) histogram.greenValues.get(1));
    assertEquals(2002, (int) histogram.greenValues.get(2));
    assertEquals(492, (int) histogram.greenValues.get(3));
    assertEquals(107, (int) histogram.greenValues.get(4));
    assertEquals(248, (int) histogram.greenValues.get(5));
    assertEquals(0, (int) histogram.greenValues.get(6));
    assertEquals(0, (int) histogram.greenValues.get(7));
    assertEquals(41, (int) histogram.greenValues.get(8));
    assertEquals(508, (int) histogram.greenValues.get(9));
    assertEquals(632, (int) histogram.greenValues.get(10));
    assertEquals(0, (int) histogram.greenValues.get(255));

    this.model.flipHorizontal("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.greenValues.get(0));
    assertEquals(0, (int) histogram.greenValues.get(1));
    assertEquals(2002, (int) histogram.greenValues.get(2));
    assertEquals(492, (int) histogram.greenValues.get(3));
    assertEquals(107, (int) histogram.greenValues.get(4));
    assertEquals(248, (int) histogram.greenValues.get(5));
    assertEquals(0, (int) histogram.greenValues.get(6));
    assertEquals(0, (int) histogram.greenValues.get(7));
    assertEquals(41, (int) histogram.greenValues.get(8));
    assertEquals(508, (int) histogram.greenValues.get(9));
    assertEquals(632, (int) histogram.greenValues.get(10));
    assertEquals(0, (int) histogram.greenValues.get(255));

    this.model.redChannel("2", "3");

    img = this.model.getImageState("3");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.greenValues.get(0));
    assertEquals(0, (int) histogram.greenValues.get(1));
    assertEquals(0, (int) histogram.greenValues.get(2));
    assertEquals(0, (int) histogram.greenValues.get(3));
    assertEquals(0, (int) histogram.greenValues.get(4));
    assertEquals(2002, (int) histogram.greenValues.get(5));
    assertEquals(0, (int) histogram.greenValues.get(6));
    assertEquals(0, (int) histogram.greenValues.get(7));
    assertEquals(41, (int) histogram.greenValues.get(8));
    assertEquals(0, (int) histogram.greenValues.get(9));
    assertEquals(0, (int) histogram.greenValues.get(10));
    assertEquals(2, (int) histogram.greenValues.get(255));

  }

  @Test
  public void testBlueFrequencyHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.blueValues.get(0));
    assertEquals(0, (int) histogram.blueValues.get(1));
    assertEquals(2002, (int) histogram.blueValues.get(2));
    assertEquals(492, (int) histogram.blueValues.get(3));
    assertEquals(107, (int) histogram.blueValues.get(4));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(0, (int) histogram.blueValues.get(6));
    assertEquals(0, (int) histogram.blueValues.get(7));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(508, (int) histogram.blueValues.get(9));
    assertEquals(632, (int) histogram.blueValues.get(10));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(0, (int) histogram.blueValues.get(34));
    assertEquals(0, (int) histogram.blueValues.get(100));
    assertEquals(0, (int) histogram.blueValues.get(255));
  }

  @Test
  public void testBlueFreqModificationHistogram() {
    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.blueValues.get(0));
    assertEquals(0, (int) histogram.blueValues.get(1));
    assertEquals(2002, (int) histogram.blueValues.get(2));
    assertEquals(492, (int) histogram.blueValues.get(3));
    assertEquals(107, (int) histogram.blueValues.get(4));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(0, (int) histogram.blueValues.get(6));
    assertEquals(0, (int) histogram.blueValues.get(7));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(508, (int) histogram.blueValues.get(9));
    assertEquals(632, (int) histogram.blueValues.get(10));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(0, (int) histogram.blueValues.get(34));
    assertEquals(0, (int) histogram.blueValues.get(100));
    assertEquals(0, (int) histogram.blueValues.get(255));

    this.model.flipVertical("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.blueValues.get(0));
    assertEquals(0, (int) histogram.blueValues.get(1));
    assertEquals(2002, (int) histogram.blueValues.get(2));
    assertEquals(492, (int) histogram.blueValues.get(3));
    assertEquals(107, (int) histogram.blueValues.get(4));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(0, (int) histogram.blueValues.get(6));
    assertEquals(0, (int) histogram.blueValues.get(7));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(508, (int) histogram.blueValues.get(9));
    assertEquals(632, (int) histogram.blueValues.get(10));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(0, (int) histogram.blueValues.get(34));
    assertEquals(0, (int) histogram.blueValues.get(100));
    assertEquals(0, (int) histogram.blueValues.get(255));

    this.model.flipHorizontal("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.blueValues.get(0));
    assertEquals(0, (int) histogram.blueValues.get(1));
    assertEquals(2002, (int) histogram.blueValues.get(2));
    assertEquals(492, (int) histogram.blueValues.get(3));
    assertEquals(107, (int) histogram.blueValues.get(4));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(0, (int) histogram.blueValues.get(6));
    assertEquals(0, (int) histogram.blueValues.get(7));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(508, (int) histogram.blueValues.get(9));
    assertEquals(632, (int) histogram.blueValues.get(10));
    assertEquals(248, (int) histogram.blueValues.get(5));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(0, (int) histogram.blueValues.get(34));
    assertEquals(0, (int) histogram.blueValues.get(100));
    assertEquals(0, (int) histogram.blueValues.get(255));

    this.model.redChannel("2", "3");

    img = this.model.getImageState("3");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.blueValues.get(0));
    assertEquals(0, (int) histogram.blueValues.get(1));
    assertEquals(0, (int) histogram.blueValues.get(2));
    assertEquals(0, (int) histogram.blueValues.get(3));
    assertEquals(0, (int) histogram.blueValues.get(4));
    assertEquals(2002, (int) histogram.blueValues.get(5));
    assertEquals(0, (int) histogram.blueValues.get(6));
    assertEquals(0, (int) histogram.blueValues.get(7));
    assertEquals(41, (int) histogram.blueValues.get(8));
    assertEquals(0, (int) histogram.blueValues.get(9));
    assertEquals(0, (int) histogram.blueValues.get(34));
    assertEquals(0, (int) histogram.blueValues.get(100));
    assertEquals(2, (int) histogram.blueValues.get(255));
  }

  @Test
  public void testIntenseFrequencyHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.intenseValues.get(0));
    assertEquals(0, (int) histogram.intenseValues.get(1));
    assertEquals(0, (int) histogram.intenseValues.get(2));
    assertEquals(2002, (int) histogram.intenseValues.get(3));
    assertEquals(0, (int) histogram.intenseValues.get(4));
    assertEquals(0, (int) histogram.intenseValues.get(5));
    assertEquals(0, (int) histogram.intenseValues.get(6));
    assertEquals(0, (int) histogram.intenseValues.get(7));
    assertEquals(0, (int) histogram.intenseValues.get(8));
    assertEquals(492, (int) histogram.intenseValues.get(9));
    assertEquals(0, (int) histogram.intenseValues.get(10));
    assertEquals(54, (int) histogram.intenseValues.get(34));
    assertEquals(799, (int) histogram.intenseValues.get(100));
    assertEquals(0, (int) histogram.intenseValues.get(255));

  }

  @Test
  public void testIntenseFrequencyFlipsHistogram() {

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.intenseValues.get(0));
    assertEquals(0, (int) histogram.intenseValues.get(1));
    assertEquals(0, (int) histogram.intenseValues.get(2));
    assertEquals(2002, (int) histogram.intenseValues.get(3));
    assertEquals(0, (int) histogram.intenseValues.get(4));
    assertEquals(0, (int) histogram.intenseValues.get(5));
    assertEquals(0, (int) histogram.intenseValues.get(6));
    assertEquals(0, (int) histogram.intenseValues.get(7));
    assertEquals(0, (int) histogram.intenseValues.get(8));
    assertEquals(492, (int) histogram.intenseValues.get(9));
    assertEquals(0, (int) histogram.intenseValues.get(10));
    assertEquals(54, (int) histogram.intenseValues.get(34));
    assertEquals(799, (int) histogram.intenseValues.get(100));
    assertEquals(0, (int) histogram.intenseValues.get(255));

    this.model.flipVertical("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.intenseValues.get(0));
    assertEquals(0, (int) histogram.intenseValues.get(1));
    assertEquals(0, (int) histogram.intenseValues.get(2));
    assertEquals(2002, (int) histogram.intenseValues.get(3));
    assertEquals(0, (int) histogram.intenseValues.get(4));
    assertEquals(0, (int) histogram.intenseValues.get(5));
    assertEquals(0, (int) histogram.intenseValues.get(6));
    assertEquals(0, (int) histogram.intenseValues.get(7));
    assertEquals(0, (int) histogram.intenseValues.get(8));
    assertEquals(492, (int) histogram.intenseValues.get(9));
    assertEquals(0, (int) histogram.intenseValues.get(10));
    assertEquals(54, (int) histogram.intenseValues.get(34));
    assertEquals(799, (int) histogram.intenseValues.get(100));
    assertEquals(0, (int) histogram.intenseValues.get(255));

    this.model.flipHorizontal("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.intenseValues.get(0));
    assertEquals(0, (int) histogram.intenseValues.get(1));
    assertEquals(0, (int) histogram.intenseValues.get(2));
    assertEquals(2002, (int) histogram.intenseValues.get(3));
    assertEquals(0, (int) histogram.intenseValues.get(4));
    assertEquals(0, (int) histogram.intenseValues.get(5));
    assertEquals(0, (int) histogram.intenseValues.get(6));
    assertEquals(0, (int) histogram.intenseValues.get(7));
    assertEquals(0, (int) histogram.intenseValues.get(8));
    assertEquals(492, (int) histogram.intenseValues.get(9));
    assertEquals(0, (int) histogram.intenseValues.get(10));
    assertEquals(54, (int) histogram.intenseValues.get(34));
    assertEquals(799, (int) histogram.intenseValues.get(100));
    assertEquals(0, (int) histogram.intenseValues.get(255));

    this.model.redChannel("2", "3");

    img = this.model.getImageState("3");

    histogram.refreshImage(img);

    assertEquals(0, (int) histogram.intenseValues.get(0));
    assertEquals(0, (int) histogram.intenseValues.get(1));
    assertEquals(0, (int) histogram.intenseValues.get(2));
    assertEquals(0, (int) histogram.intenseValues.get(3));
    assertEquals(0, (int) histogram.intenseValues.get(4));
    assertEquals(2002, (int) histogram.intenseValues.get(5));
    assertEquals(0, (int) histogram.intenseValues.get(6));
    assertEquals(0, (int) histogram.intenseValues.get(7));
    assertEquals(41, (int) histogram.intenseValues.get(8));
    assertEquals(0, (int) histogram.intenseValues.get(9));
    assertEquals(0, (int) histogram.intenseValues.get(10));
    assertEquals(0, (int) histogram.intenseValues.get(34));
    assertEquals(0, (int) histogram.intenseValues.get(100));
    assertEquals(2, (int) histogram.intenseValues.get(255));
  }

  @Test
  public void testScaleModificationPoints() {
    histogram.refreshImage(img);

    // this will be that number because log with 0 will be undefined.
    assertEquals(new Point(0,-2147483648), histogram.points[1]);
    assertEquals(new Point(1,-2147483648), histogram.points[2]);
    assertEquals(new Point(1,-2147483648), histogram.points[3]);
    assertEquals(new Point(2,-2147483648), histogram.points[4]);
    assertEquals(new Point(2, 38), histogram.points[5]);
    assertEquals(new Point(140, 3), histogram.points[255]);

    this.model.flipVertical("0", "1");

    img = this.model.getImageState("1");

    histogram.refreshImage(img);

    // this will be that number because log with 0 will be undefined.
    assertEquals(new Point(0,-2147483648), histogram.points[1]);
    assertEquals(new Point(1,-2147483648), histogram.points[2]);
    assertEquals(new Point(1,-2147483648), histogram.points[3]);
    assertEquals(new Point(2,-2147483648), histogram.points[4]);
    assertEquals(new Point(2, 38), histogram.points[5]);
    assertEquals(new Point(140, 3), histogram.points[255]);

    this.model.flipHorizontal("1", "2");

    img = this.model.getImageState("2");

    histogram.refreshImage(img);
    // this will be that number because log with 0 will be undefined.
    assertEquals(new Point(0,-2147483648), histogram.points[1]);
    assertEquals(new Point(1,-2147483648), histogram.points[2]);
    assertEquals(new Point(1,-2147483648), histogram.points[3]);
    assertEquals(new Point(2,-2147483648), histogram.points[4]);
    assertEquals(new Point(2, 38), histogram.points[5]);
    assertEquals(new Point(140, 3), histogram.points[255]);

    this.model.redChannel("2", "3");

    img = this.model.getImageState("3");

    histogram.refreshImage(img);
    // this will be that number because log with 0 will be undefined.
    assertEquals(new Point(0,-2147483648), histogram.points[1]);
    assertEquals(new Point(1,-2147483648), histogram.points[2]);
    assertEquals(new Point(1,-2147483648), histogram.points[3]);
    assertEquals(new Point(2,-2147483648), histogram.points[4]);
    assertEquals(new Point(2, 38), histogram.points[5]);
    assertEquals(new Point(140, 3), histogram.points[255]);

  }

  @Test
  public void testScalePoints() {
    histogram.refreshImage(img);

    // this will be that number because log with 0 will be undefined.
    assertEquals(new Point(0,-2147483648), histogram.points[1]);
    assertEquals(new Point(1,-2147483648), histogram.points[2]);
    assertEquals(new Point(1,-2147483648), histogram.points[3]);
    assertEquals(new Point(2,-2147483648), histogram.points[4]);
    assertEquals(new Point(2, 38), histogram.points[5]);
    assertEquals(new Point(140, 3), histogram.points[255]);


  }

  // throws an exception when given a null color channel
  @Test (expected = IllegalArgumentException.class)
  public void testImageNull() {
    HistogramPanel c = new HistogramPanel(
            new BufferedImage(5, 5, 0), null);
  }
}
