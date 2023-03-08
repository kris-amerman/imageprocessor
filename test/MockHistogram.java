import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;

/**
 * This represents the panel that creates the histogram for users to see.
 * It will be in the form specifically pf a line chart.
 */
public class MockHistogram extends JPanel {

  public final StringBuilder log;

  private BufferedImage image;

  public Point[] points;

  public Map<Integer, Integer> redValues = new HashMap<>();
  public Map<Integer, Integer> greenValues = new HashMap<>();
  public Map<Integer, Integer> blueValues = new HashMap<>();
  public Map<Integer, Integer> intenseValues = new HashMap<>();

  /**
   * This constructor will initialize the image we will be using to create histograms.
   * @param image
   *      The image we will be using to see if the values are correct.
   * @param log
   *      To see if certain methods are called.
   * @throws IllegalArgumentException
   *      If there is not image given, then we will throw an exception.
   */
  public MockHistogram(BufferedImage image, StringBuilder log)
          throws IllegalArgumentException  { // TODO how to pass channel
    if (image == null) {
      throw new IllegalArgumentException("There has to be an image.");
    }

    this.log = Objects.requireNonNull(log);

    this.image = image;
    this.points = new Point[256];
  }



  /**
   * This method will refresh the image with its updated count values.
   * @param image
   *      The new image that we will be counting the RGB and intensity values for.
   */
  public void refreshImage(BufferedImage image) {
    this.image = image;
    redValues = new HashMap<>();
    greenValues = new HashMap<>();
    blueValues = new HashMap<>();
    intenseValues = new HashMap<>();
    redValues = (this.redFrequency(this.image));
    greenValues = this.greenFrequency(this.image);
    blueValues = this.blueFrequency(this.image);
    intenseValues = this.intenseFrequency(this.image);
    this.points = new Point[256];
    channelPoints(redValues);
  }


  /**
   * This method is part of the channelFrequency
   * method but we separated it into only red
   * So that we can clearly tests the red
   * values to see if the Red values are counted accordingly.
   * @param img
   *      The image we will be counting the red values of.
   * @return
   *      The map of all the counts for the 0 - 255 red values.
   */
  private Map<Integer, Integer> redFrequency(BufferedImage img) {
    for (int x = 0; x < img.getWidth() - 1; x++) {
      for (int y = 0; y < img.getHeight() - 1; y++) {
        Color c = new Color(img.getRGB(x, y));
        int val = c.getRed();
        if (redValues.containsKey(val)) {
          redValues.put(val, redValues.get(val) + 1);
        } else {
          redValues.put(val, 1);
        }
      }
    }

    for (int z = 0; z < 256; z++) {
      if (!redValues.containsKey(z)) {
        redValues.put(z, 0);
      }
    }
    return redValues;
  }

  /**
   * This method is part of the channelFrequency
   * method but we separated it into only green
   * So that we can clearly tests the green
   * values to see if the Green values are counted accordingly.
   * @param img
   *      The image we will be counting the red values of.
   * @return
   *      The map of all the counts for the 0 - 255 green values.
   */
  private Map<Integer, Integer> greenFrequency(BufferedImage img) {
    for (int x = 0; x < img.getWidth() - 1; x++) {
      for (int y = 0; y < img.getHeight() - 1; y++) {
        Color c = new Color(img.getRGB(x, y));
        int val = c.getGreen();
        if (greenValues.containsKey(val)) {
          greenValues.put(val, greenValues.get(val) + 1);
        } else {
          greenValues.put(val, 1);
        }
      }
    }

    for (int z = 0; z < 256; z++) {
      if (!greenValues.containsKey(z)) {
        greenValues.put(z, 0);
      }
    }
    return greenValues;
  }

  /**
   * This method is part of the channelFrequency
   * method but we separated it into only blue
   * So that we can clearly tests the blue
   * values to see if the Blue values are counted accordingly.
   * @param img
   *      The image we will be counting the ble values of.
   * @return
   *      The map of all the counts for the 0 - 255 blue values.
   */
  private Map<Integer, Integer> blueFrequency(BufferedImage img) {
    for (int x = 0; x < img.getWidth() - 1; x++) {
      for (int y = 0; y < img.getHeight() - 1; y++) {
        Color c = new Color(img.getRGB(x, y));
        int val = c.getGreen();
        if (blueValues.containsKey(val)) {
          blueValues.put(val, blueValues.get(val) + 1);
        } else {
          blueValues.put(val, 1);
        }
      }
    }

    for (int z = 0; z < 256; z++) {
      if (!blueValues.containsKey(z)) {
        blueValues.put(z, 0);
      }
    }
    return blueValues;
  }

  /**
   * This method is part of the channelFrequency
   * method but we separated it into only intense
   * So that we can clearly test the intense
   * values to see if the Intense values are counted accordingly.
   * @param img
   *      The image we will be counting the intense values of.
   * @return
   *      The map of all the counts for the 0 - 255 intense values.
   */
  private Map<Integer, Integer> intenseFrequency(BufferedImage img) {
    for (int x = 0; x < img.getWidth() - 1; x++) {
      for (int y = 0; y < img.getHeight() - 1; y++) {
        Color c = new Color(img.getRGB(x, y));
        int val = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
        if (intenseValues.containsKey(val)) {
          intenseValues.put(val, intenseValues.get(val) + 1);
        } else {
          intenseValues.put(val, 1);
        }
      }
    }

    for (int z = 0; z < 256; z++) {
      if (!intenseValues.containsKey(z)) {
        intenseValues.put(z, 0);
      }
    }
    return intenseValues;
  }


  /**
   * This represents how we will scale the values on the histogram.
   * @param freq
   *      The frequency we will be using.
   */
  private void channelPoints(Map<Integer, Integer> freq) {
    double xScale = 0.55; // TODO calculate, name
    double yScale = 5;

    for (int i = 0; i < this.points.length; i++) {
      int x = (int) (i * xScale);
      int y;
      if (!freq.containsKey(i)) {
        y = 0;
      }
      else {
        y = (int) (Math.log(freq.get(i)) * yScale); // logarithmic scale
      }
      this.points[i] = new Point(x, y);
    }
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    log.append("The paint component was called.");
    return;
  }

}