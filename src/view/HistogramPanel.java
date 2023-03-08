package view;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/**
 * A custom JPanel that displays an RGB-intensity histogram of an image.
 */
public class HistogramPanel extends JPanel {

  private BufferedImage image;
  private final String channel;
  private Point[] points;
  private int totalPixels;
  private final double yScale;
  private final double xScale;

  /**
   * Construct a histogram panel.
   *
   * @param image
   *     The image to use as the basis of this histogram
   * @param channel
   *     The color channel to visualize (including intensity)
   * @throws IllegalArgumentException
   *     If the channel is not one of: red, green, blue, or intensity
   */
  public HistogramPanel(BufferedImage image, String channel) throws IllegalArgumentException {
    if (!channel.equals("red")
            && !channel.equals("green")
            && !channel.equals("blue")
            && !channel.equals("intensity")) {
      System.out.println(channel);
      throw new IllegalArgumentException("Histogram panel only supports red, " +
              "green, blue, or intensity");
    }
    this.image = image;
    this.channel = channel;
    this.points = new Point[256];
    this.totalPixels = 0;
    this.yScale = 5;
    this.xScale = 0.55;
    this.channelPoints(this.channelFrequencies(this.image));
  }

  /**
   * Update the histogram image.
   *
   * @param image
   *     The image to use for this histogram
   */
  public void refreshImage(BufferedImage image) {
    this.image = image;
    this.channelPoints(this.channelFrequencies(this.image));
  }

  /**
   * Compute the frequency of values for the channel of interest. These
   * are returned as a map of 0-255-value : count.
   *
   * @param img
   *     The histogram image
   * @return
   *     A map of 0-255-value : count
   */
  private Map<Integer, Integer> channelFrequencies(BufferedImage img) {
    if (img == null) {
      return new HashMap<>();
    }

    this.totalPixels = img.getWidth() * img.getHeight();

    // 0-255 val : count
    Map<Integer, Integer> values = new HashMap<>();

    for (int x = 0; x < img.getWidth() - 1; x++) {
      for (int y = 0; y < img.getHeight() - 1; y++) {
        Color c = new Color(img.getRGB(x, y));
        int val;
        switch (this.channel) {
          case "red":
            val = c.getRed();
            if (values.containsKey(val)) {
              values.put(val, values.get(val) + 1);
            }
            else {
              values.put(val, 1);
            }
            break;
          case "green":
            val = c.getGreen();
            if (values.containsKey(val)) {
              values.put(val, values.get(val) + 1);
            }
            else {
              values.put(val, 1);
            }
            break;
          case "blue":
            val = c.getBlue();
            if (values.containsKey(val)) {
              values.put(val, values.get(val) + 1);
            }
            else {
              values.put(val, 1);
            }
            break;
          case "intensity":
            val = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
            if (values.containsKey(val)) {
              values.put(val, values.get(val) + 1);
            }
            else {
              values.put(val, 1);
            }
            break;
          default:
            break;
        }
      }
    }
    return values;
  }

  /**
   * Generate the points to display on the histogram.
   *
   * @param freq
   *     The map of 0-255-value frequencies -- these represent the y-values for each point
   */
  private void channelPoints(Map<Integer, Integer> freq) {

    for (int i = 0; i < this.points.length; i++) {
      int x = (int) (i * this.xScale);
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

  /**
   * Draw the histogram as a line graph with the appropriate color.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int totalAmt = (int) (Math.log(totalPixels) * yScale);

    Graphics2D g2 = (Graphics2D) g;

    switch (this.channel) {
      case "red":
        g2.setColor(Color.red);
        break;
      case "green":
        g2.setColor(Color.green);
        break;
      case "blue":
        g2.setColor(Color.blue);
        break;
      case "intensity":
        g2.setColor(Color.gray);
        break;
      default:
        break;
    }

    for (int i = 0; i < this.points.length - 1; i++) { // minus 1
      int x0 = (int) this.points[i].getX();
      int x1 = (int) this.points[i + 1].getX();
      int y0 = (int) (totalAmt - this.points[i].getY());
      int y1 = (int) (totalAmt - this.points[i + 1].getY());
      g2.drawLine(x0, y0, x1, y1);
      g2.drawLine(254, 51, 255, 0);
    }
  }
}
