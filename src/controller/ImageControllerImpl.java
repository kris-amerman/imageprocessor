package controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.ImageProcessor;
import model.ImageUtil;
import view.ImageView;

/**
 * This represents the implementation of the controller.
 * The controller will take in inputs, enact functionalities, and output for the client to react to.
 */

public class ImageControllerImpl implements ImageController {

  private ImageProcessor model;
  private ImageView view;
  private Readable input;

  /**
   * This represents a constructor that will take in the model, the view, and the readable input.
   * The controller will know which model or view to use,
   * in addition to being able to obtain readable inputs from the users.
   *
   * @param model the given model that the controller will be using
   * @param view  the given view that the controller will be using
   * @param input the input that the program will be receiving
   * @throws IllegalArgumentException if the model, view, or given input is null
   */
  public ImageControllerImpl(ImageProcessor model, ImageView view, Readable input)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null.");
    }

    if (view == null) {
      throw new IllegalArgumentException("The view cannot be null.");
    }

    if (input == null) {
      throw new IllegalArgumentException("The read input cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.input = input;
  }

  @Override
  public void modifyImages() throws IllegalStateException {
    Scanner sc = new Scanner(this.input);
    String modifyInstruction;

    // it will contain all the images the user has loaded and modified.
    ArrayList<String> listImages = new ArrayList<>();
    // if we continue to update images
    boolean continueProgram = true;
    // if we are in the first phase (waiting for an image to be loaded)
    boolean firstLoad = true;

    // welcoming message. Tell the user to load an image and how to quit.
    displayMessage(
            "Welcome! Please begin by loading an image!\n" +
                    "Please input the instruction below, " +
                    "following by the filepath and its name!\n" +
                    "load image-path image-name : load an image from a specified path." +
                    " If you would like to quit, press q or Q.");

    try {
      while (firstLoad) {
        modifyInstruction = sc.next();

        switch (modifyInstruction) {
          case "load":
            try {
              String filePath = sc.next();
              String name = sc.next();
              this.model.loadImage(filePath, name);
              // add the name of the loaded image. (If user forgets what they added,
              // they can get see it
              listImages.add(name + " - loaded original image");
              displayMessage("An image has been successfully uploaded!");
              firstLoad = false;
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      " Please try again making sure you have inputted everything correctly.");
            }
            break;
          case "q":
          case "Q":
            displayMessage("Successfully ended the program.");
            return;
          default:
            displayMessage("Please make sure command is inputted correctly.");
            break;
        }
      }

      // Once user inputs the image, they can edit or load more.
      // We will show them the command initially.
      // If they forget, they can type menu.
      displayMessage(
              "Welcome! Here is a list of commands you can do! " +
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
                      "q or Q : end the program.");

      while (continueProgram) {

        modifyInstruction = sc.next();

        switch (modifyInstruction) {
          case "load":
            try {
              String filePath = sc.next();
              String name = sc.next();
              this.model.loadImage(filePath, name);

              if (!(listImages.contains(name + " - loaded original image"))) {
                listImages.add(name + " - loaded original image");
              }
              displayMessage("An image has been successfully uploaded!");
              break;
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }
            break;

          // We made it skip three inputs if the second input was inputted incorreclty.
          case "brighten":
            int brightIncrement = 0;


            // if the brightIncrement is not a number, then render an error message.

            try {
              brightIncrement = sc.nextInt();
            } catch (InputMismatchException e) {
              String skip = sc.next();
              String origName = sc.next();
              String newName = sc.next();
              displayMessage("Please make sure that the " +
                      "increment of brighten is an integer.");
              break;
            }


            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.brightness(brightIncrement, origName, newName);
              listImages.add(newName + " - brightened or darkened image");
              displayMessage("The update to the image has been done! " +
                      "The image has been brightened or darkened!");
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "vertical-flip":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.flipVertical(origName, newName);
              listImages.add(newName + " - vertical flipped image");
              displayMessage("The update to the image has been done! " +
                      "The image is vertically flipped!");
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "horizontal-flip":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.flipHorizontal(origName, newName);
              listImages.add(newName + " - horizontally flipped image");
              displayMessage("The update to the image has been done! " +
                      "The image is horizontally flipped!");
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "red-component":
            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.redChannel(origName, newName);
              listImages.add(newName + " - greyscale through the Red-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Red component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "green-component":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.greenChannel(origName, newName);
              listImages.add(newName + " - greyscale through the Green-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Green component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "blue-component":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.blueChannel(origName, newName);
              listImages.add(newName + " - greyscale through the Blue-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Blue component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "value-component":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.maxVal(origName, newName);
              listImages.add(newName + " - greyscale through the Value-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Value component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "intensity-component":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.intensity(origName, newName);
              listImages.add(newName + " - greyscale through the Intensity-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Intensity component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "luma-component":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.luma(origName, newName);
              listImages.add(newName + " - greyscale through the Luma-component");
              displayMessage("The update to the image has been done!" +
                      " The image is greyscale to the Luma component!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "blur":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.gaussianBlur(origName, newName);
              listImages.add(newName + " - a blur image");
              displayMessage("The update to the image has been done! " +
                      "The image has undergone a blur!");
            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "sharpen":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.sharpen(origName, newName);
              listImages.add(newName + " - a sharpened image");
              displayMessage("The update to the image has been done! " +
                      "The image has undergone a sharpening!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "sepia":

            try {
              String origName = sc.next();
              String newName = sc.next();
              this.model.sepia(origName, newName);
              listImages.add(newName + " - a sepia image");
              displayMessage("The update to the image has been done! " +
                      "Sepia color transformation has been applied!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }

            break;

          case "greyscale":

            try {
              double red = 0.0;
              double green = 0.0;
              double blue = 0.0;

              try {
                red = sc.nextDouble();
              } catch (InputMismatchException e) {
                String skip = sc.next();
                String secondSkip = sc.next();
                String origName = sc.next();
                String newName = sc.next();
                displayMessage("Please make sure that the " +
                        "red value is an integer.");
                break;
              }

              try {
                green = sc.nextDouble();
              } catch (InputMismatchException e) {
                String skip = sc.next();
                String origName = sc.next();
                String newName = sc.next();
                displayMessage("Please make sure that the " +
                        "green value is an integer.");
                break;
              }

              try {
                blue = sc.nextDouble();
              } catch (InputMismatchException e) {
                String origName = sc.next();
                String newName = sc.next();
                displayMessage("Please make sure that the " +
                        "blue value is an integer.");
                break;
              }

              String origName = sc.next();
              String newName = sc.next();

              this.model.customGreyscale(red, green, blue, origName, newName);

              listImages.add(newName + " - a custom greyscale image");
              displayMessage("The update to the image has been done! " +
                      "Custom greyscale color transformation has been applied!");

            } catch (IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      ". Please try again making sure you have inputted everything correctly.");
            }


            break;

          case "save":
            // will catch the first exception that's encountered
            try {
              String filePath = sc.next();
              String imageName = sc.next();

              if (!this.model.hasLoadedImage(imageName)) {
                throw new IllegalArgumentException("Cannot find image with name "
                        + imageName + ".");
              }

              String extension = ImageUtil.getExtension(filePath);

              if (!Arrays.asList(model.getSupportedFormats()).contains(extension)) {
                throw new IllegalArgumentException("Cannot find image with name "
                        + imageName + ".");
              }

              OutputStream imageData = new FileOutputStream(filePath);
              this.model.saveImage(imageData, imageName, extension);

              displayMessage("The image has been saved! Check it out!");
            } catch (FileNotFoundException | IllegalArgumentException e) {
              displayMessage("The following error has occurred: " + e.getMessage() +
                      " Please try again making sure you have inputted everything correctly.");
            }

            break;
          case "menu":
            displayMessage("Here is the list of commands you can do.\n" +
                    "load image-path image-name : load an image from a specified path.\n" +
                    "save image-path image-name : save the image to a specified path.\n" +
                    "brighten increment image-name dest-image-name :" +
                    " brighten or darken the image.\n" +
                    "vertical-flip image-name dest-image-name : flips the image upside-down.\n" +
                    "horizontal-flip image-name dest-image-name : " +
                    "flips the image horizontally.\n" +
                    "red-component image-name dest-image-name : " +
                    "creates a greyscale image with the red component.\n" +
                    "green-component image-name dest-image-name : " +
                    "creates a greyscale image with the green component.\n" +
                    "blue-component image-name dest-image-name : " +
                    "creates a greyscale image with the blue component.\n" +
                    "value-component image-name dest-image-name :" +
                    " creates a greyscale image with the value component\n" +
                    "intensity-component image-name dest-image-name :" +
                    " creates a greyscale image with the intensity component\n" +
                    "luma-component image-name dest-image-name :" +
                    " creates a greyscale image with the luma component.\n" +
                    "blur image-name dest-image-name :" +
                    " blurs the image.\n" +
                    "sharpen image-name dest-image-name :" +
                    " sharpens the image.\n" +
                    "sepia image-name dest-image-name :" +
                    " greyscale it through sepia of the image.\n" +
                    "greyscale redIncrement greenIncrement " +
                    "blueIncrement image-name dest-image-name :" +
                    " custom greyscale.\n" +
                    "format : view all supported formats that this program has!\n" +
                    "menu : a reminder of the list of commands you can do.\n" +
                    "stored-images : view all the images you have stored.\n" +
                    "q or Q : end the program.");
            break;
          case "stored-images":
            String displayMessage = "";
            for (int count = 0; count < listImages.size(); count++) {
              if (count == listImages.size() - 1) {
                displayMessage = displayMessage + (listImages.get(count));
              } else {
                displayMessage = displayMessage + (listImages.get(count) + "\n");
              }
            }

            displayMessage("Here are all the images you have stored!\n"
                    + displayMessage);
            break;
          case "format" :
            String allFormats = "";

            for (String s : this.model.getSupportedFormats()) {
              allFormats = allFormats + s + "\n";
            }

            displayMessage("The following are the formats this " +
                    "program can support:\n" +
                    allFormats + "Have fun!");

            break;

          case "q":
          case "Q":
            continueProgram = false;
            break;
          default:
            displayMessage("Please make sure the" +
                    " modification is inputted correctly.");
            break;
        }
      }

      displayMessage("Successfully ended the program.");
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("There is not enough inputs.");
    }
  }

  /**
   * A helper method that focuses on rendering the message
   * and catching an exception if an error with the transmission.
   *
   * @param errorMessage the message that is intended to be rendered
   * @throws IllegalStateException if there is a failure with the transmission
   */
  private void displayMessage(String errorMessage) throws IllegalStateException {
    try {
      this.view.renderMessage(errorMessage + "\n");
    } catch (IOException r) {
      throw new IllegalStateException(r.getMessage());
    }
  }
}

