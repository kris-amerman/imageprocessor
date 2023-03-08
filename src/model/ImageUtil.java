package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read an image from a file.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename The path of the file
   */
  public static int[][][] readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();

    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();

      // if P6 formatting is ever encountered, stop reading (file invalid)
      if (s.equals("P6")) {
        throw new IllegalArgumentException("Cannot be in RAW formatting (P6)");
      }

      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token = sc.next();

    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain ASCII file should begin with P3");
    }

    int width = sc.nextInt();

    int height = sc.nextInt();

    // must be included to skip (otherwise image RGBs will shift, becoming green and purple)
    int maxValue = sc.nextInt();

    int[][][] pixels = new int[height][width][3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        pixels[row][col][0] = sc.nextInt(); // r
        pixels[row][col][1] = sc.nextInt(); // g
        pixels[row][col][2] = sc.nextInt(); // b
      }
    }
    return pixels;
  }

  /**
   * Read an image file using ImageIO.
   *
   * @param filename
   *     The file to read from
   * @return
   *     The image's pixel array
   */
  public static int[][][] readWithIO(String filename) {
    BufferedImage img;

    try {
      img = ImageIO.read(new FileInputStream(filename));
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not read from: " + filename);
    }

    int width = img.getWidth();
    int height = img.getHeight();
    int[][][] pixels = new int[height][width][3];

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int pixel = img.getRGB(col, row);
        Color rgb = new Color(pixel);

        pixels[row][col][0] = rgb.getRed(); // r
        pixels[row][col][1] = rgb.getGreen(); // g
        pixels[row][col][2] = rgb.getBlue(); // b
      }
    }
    return pixels;
  }

  /**
   * Saves an image in PPM ASCII format.
   *
   * @throws IllegalArgumentException
   *     If the given image name does not exist in the map of loaded images,
   *     or if the file transmission fails (IOException is caught; this
   *     includes if the filename or extension contains any invalid characters)
   */
  public static void savePPM(OutputStream imageData, int[][][] img) {
    try {
      imageData.write("P3\n".getBytes());
      imageData.write((ImageUtil.getWidth(img) + " " + ImageUtil.getHeight(img) + "\n").getBytes());
      imageData.write((ImageUtil.getMaxValue(img) + "\n").getBytes()); // max value

      for (int[][] col : img) {
        for (int[] rgb : col) {
          imageData.write((rgb[0] + "\n").getBytes());
          imageData.write((rgb[1] + "\n").getBytes());
          imageData.write((rgb[2] + "\n").getBytes());
        }
      }
      imageData.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("encountered IOException: could not transmit to file");
    }
  }

  /**
   * Save using ImageIO.
   *
   * @param imageData
   *     The output stream to save to
   * @param img
   *     The image array to save
   */
  public static void saveWithIO(OutputStream imageData, int[][][] img) {
    int width = ImageUtil.getWidth(img);
    int height = ImageUtil.getHeight(img);
    BufferedImage bufferedImage = ImageUtil.generateBufferedImage(img);

    try {
      ImageIO.write(bufferedImage, "gif", imageData);
      imageData.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("encountered IOException: could not transmit to file");
    }
  }

  /**
   * Generate a buffered image from an array of pixels.
   *
   * @param img
   *     The image pixel array
   * @return
   *     A buffered image based on the given pixel array
   */
  public static BufferedImage generateBufferedImage(int[][][] img) {
    int width = ImageUtil.getWidth(img);
    int height = ImageUtil.getHeight(img);
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = img[row][col][0];
        int g = img[row][col][1];
        int b = img[row][col][2];
        bufferedImage.setRGB(col, row, new Color(r, g, b).getRGB());
      }
    }

    return bufferedImage;
  }

  /**
   * Get the max RGB value in the image array.
   *
   * @param img
   *    The image pixel array
   * @return
   *    The max RGB value
   */
  public static int getMaxValue(int[][][] img) {
    int maxVal = 255;

    for (int row = 0; row < ImageUtil.getHeight(img); row++) {
      for (int col = 0; col < ImageUtil.getWidth(img); col++) {
        for (int i = 0; i < img[row][col].length; i++) {
          if (img[row][col][i] > maxVal) {
            maxVal = img[row][col][i];
          }
        }
      }
    }
    return maxVal;
  }

  /**
   * Get the height of an image in pixels.
   *
   * @param img
   *     The image's pixel array
   * @return
   *     The height of the image in pixels
   */
  public static int getHeight(int[][][] img) {
    return img.length;
  }

  /**
   * Get the width of an image in pixels.
   *
   * @param img
   *     The image's pixel array
   * @return
   *     The width of the image in pixels
   */
  public static int getWidth(int[][][] img) {
    return img[0].length;
  }

  /**
   * Get the extension from a given image filepath.
   *
   * @param imagePath
   *     The image filepath
   * @return
   *     The extension (includes ".")
   */
  public static String getExtension(String imagePath) throws IllegalArgumentException {
    if (imagePath.length() < 2) {
      throw new IllegalArgumentException("not a filepath");
    }

    if (!Character.isLetter(imagePath.charAt(imagePath.length() - 1))) {
      throw new IllegalArgumentException("not a filepath");
    }

    int dotIndex = imagePath.lastIndexOf(".");
    if (dotIndex == -1) {
      throw new IllegalArgumentException("Invalid file: no extension.");
    }
    return imagePath.substring(dotIndex);
  }
}

