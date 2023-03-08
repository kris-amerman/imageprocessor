package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.Box;
import javax.swing.JOptionPane;

import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import controller.ImageControllerFeatures;

/**
 * A GUI for an ImageProcessor. It visually supports the functionalities offered by the
 * ControllerFeatures interface. This GUI consists of 4 mains parts: an image,
 * a toolbar side panel, a histogram side panel, and an options panel.
 */
public class ImageGUIViewImpl extends JFrame implements ImageGUIView {

  private JLabel displayImage;
  private JPanel toolbarPanel;
  private JScrollPane imageScrollPane;
  private HistogramPanel redHistogram;
  private HistogramPanel greenHistogram;
  private HistogramPanel blueHistogram;
  private HistogramPanel intensityHistogram;
  private JButton openButton;
  private JButton saveButton;
  private JButton undoButton;
  private JButton redoButton;
  private JButton verticalFlipButton;
  private JButton horizontalFlipButton;
  private JSlider brightnessSlider;
  private JButton redCompButton;
  private JButton greenCompButton;
  private JButton blueCompButton;
  private JButton valueButton;
  private JButton intensityButton;
  private JButton lumaButton;
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton sepiaButton;
  private JButton customMatrixButton;

  /**
   * Construct the image processor GUI.
   */
  public ImageGUIViewImpl() {
    super("Image Processor");

    // window properties
    this.setSize(900, 600);
    this.setMinimumSize(new Dimension(900, 600));
    this.setLocation(100, 100);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // main panel to organize components
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(0, 20, 40, 20));
    this.add(mainPanel);

    // top panel -- contains title label and options panel
    JPanel topPanel = new JPanel();
    topPanel.setBorder(new EmptyBorder(10, 0, 20, 0)); // color
    topPanel.setLayout(new BorderLayout());
    mainPanel.add(topPanel, BorderLayout.NORTH);

    JLabel title = new JLabel("<html><font color='#41A317'>Better Photoshop</font></html>");
    title.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
    topPanel.add(title, BorderLayout.WEST);

    // options panel -- contains open, save, undo, and redo buttons
    JPanel optionsPanel = new JPanel();
    optionsPanel.setLayout(new GridLayout(1, 0, 2,0));
    topPanel.add(optionsPanel, BorderLayout.EAST);

    this.openButton = new JButton("Open");
    optionsPanel.add(this.openButton);

    this.saveButton = new JButton("Save");
    optionsPanel.add(this.saveButton);

    this.undoButton = new JButton("⟲");
    this.undoButton.setPreferredSize(new Dimension(20, 20));
    this.redoButton = new JButton("⟳");
    this.redoButton.setPreferredSize(new Dimension(20, 20));
    optionsPanel.add(this.undoButton);
    optionsPanel.add(this.redoButton);
    
    // toolbar side panel -- contains all image modification buttons,
    // invisible until a user loads their first image
    this.toolbarPanel = new JPanel();
    this.toolbarPanel.setVisible(false);
    this.toolbarPanel.setLayout(new GridLayout(0, 1, 0, 4));
    this.toolbarPanel.setBorder(BorderFactory.createTitledBorder("Tools"));
    mainPanel.add(this.toolbarPanel, BorderLayout.WEST);

    this.verticalFlipButton = new JButton("Flip Vertical");
    this.toolbarPanel.add(this.verticalFlipButton);

    this.horizontalFlipButton = new JButton("Flip Horizontal");
    this.toolbarPanel.add(this.horizontalFlipButton);

    this.redCompButton = new JButton("Red Channel");
    this.toolbarPanel.add(this.redCompButton);

    this.greenCompButton = new JButton("Green Channel");
    this.toolbarPanel.add(this.greenCompButton);

    this.blueCompButton = new JButton("Blue Channel");
    this.toolbarPanel.add(this.blueCompButton);

    this.valueButton = new JButton("Max Value");
    this.toolbarPanel.add(this.valueButton);

    this.intensityButton = new JButton("Intensity");
    this.toolbarPanel.add(this.intensityButton);

    this.lumaButton = new JButton("Luma");
    this.toolbarPanel.add(this.lumaButton);

    this.sepiaButton = new JButton("Sepia Tone");
    this.toolbarPanel.add(this.sepiaButton);

    this.blurButton = new JButton("Blur");
    this.toolbarPanel.add(this.blurButton);

    this.sharpenButton = new JButton("Sharpen");
    this.toolbarPanel.add(this.sharpenButton);

    this.customMatrixButton = new JButton("Custom Matrix");
    this.toolbarPanel.add(this.customMatrixButton);

    // brightness slider
    this.brightnessSlider = new JSlider(-255, 255, 0);
    this.brightnessSlider.setOrientation(JSlider.HORIZONTAL);
    this.brightnessSlider.setMinorTickSpacing(30);
    this.brightnessSlider.setMajorTickSpacing(255);
    this.brightnessSlider.setPaintTicks(true);
    Hashtable<Integer, JLabel> brightTitle = new Hashtable<Integer, JLabel>();
    brightTitle.put(0, new JLabel("Brightness"));
    this.brightnessSlider.setLabelTable(brightTitle);
    this.brightnessSlider.setPaintLabels(true);
    this.brightnessSlider.setBorder(new EmptyBorder(0,0,10,0));
    this.toolbarPanel.add(this.brightnessSlider);
    
    // image display
    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    this.displayImage = new JLabel();
    this.displayImage.setBorder(new EmptyBorder(50, 50, 50, 50));
    this.displayImage.setIcon(new ImageIcon());

    this.imageScrollPane = new JScrollPane(this.displayImage);
    this.imageScrollPane.setToolTipText("Load an image first");

    imagePanel.add(this.imageScrollPane, BorderLayout.CENTER);

    // histogram side panel (all histogram containers)
    JPanel histogramsPanel = new JPanel();
    histogramsPanel.setLayout(new GridLayout(0,1));

    // red histogram
    JPanel redHistContainer = new JPanel(); // contains a SINGLE histogram
    redHistContainer.setLayout(new FlowLayout());
    redHistContainer.setBorder(
            BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Red Channel"),
            new EmptyBorder(10, 10, 10, 10)));
    this.redHistogram = new HistogramPanel(null, "red");
    this.redHistogram.setPreferredSize(new Dimension(150, 75));
    redHistContainer.add(this.redHistogram);

    // green histogram
    JPanel greenHistContainer = new JPanel();
    greenHistContainer.setLayout(new FlowLayout());
    greenHistContainer.setBorder(
            BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Green Channel"),
            new EmptyBorder(10, 10, 10, 10)));
    this.greenHistogram = new HistogramPanel(null, "green");
    this.greenHistogram.setPreferredSize(new Dimension(150, 75));
    greenHistContainer.add(this.greenHistogram);

    // blue histogram
    JPanel blueHistContainer = new JPanel();
    blueHistContainer.setLayout(new FlowLayout());
    blueHistContainer.setBorder(
            BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Blue Channel"),
            new EmptyBorder(10, 10, 10, 10)));
    this.blueHistogram = new HistogramPanel(null, "blue");
    this.blueHistogram.setPreferredSize(new Dimension(150, 75));
    blueHistContainer.add(this.blueHistogram);

    // intensity histogram
    JPanel intensityHistContainer = new JPanel();
    intensityHistContainer.setLayout(new FlowLayout());
    intensityHistContainer.setBorder(
            BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Intensity"),
            new EmptyBorder(10, 10, 10, 10)));
    this.intensityHistogram = new HistogramPanel(null, "intensity");
    this.intensityHistogram.setPreferredSize(new Dimension(150, 75));
    intensityHistContainer.add(this.intensityHistogram);

    histogramsPanel.add(redHistContainer); // add red histogram to side panel
    histogramsPanel.add(greenHistContainer); // add green histogram to side panel
    histogramsPanel.add(blueHistContainer); // add green histogram to side panel
    histogramsPanel.add(intensityHistContainer); // add intensity histogram to side panel

    mainPanel.add(histogramsPanel, BorderLayout.EAST);

    setVisible(true);
  }

  @Override
  public void addFeatures(ImageControllerFeatures controller) throws IllegalArgumentException {
    if (controller == null) {
      throw new IllegalArgumentException("features cannot be null");
    }
    openButton.addActionListener(evt -> {
      String filepath = "";
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showOpenDialog(this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        filepath = f.getAbsolutePath();
      }
      controller.loadRequest(filepath);
    });

    saveButton.addActionListener(evt -> {
      String filepath = "";
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        filepath = f.getAbsolutePath();
      }
      controller.saveRequest(filepath);
    });

    undoButton.addActionListener(evt -> {
      controller.undoRequest();

      // return brightness slider back to 0 upon undo
      this.brightnessSlider.setValue(0);
    });

    redoButton.addActionListener(evt -> {
      controller.redoRequest();
      // return brightness slider back to 0 upon redo
      this.brightnessSlider.setValue(0);
    });

    verticalFlipButton.addActionListener(evt -> {
      controller.verticalRequest();
    });

    horizontalFlipButton.addActionListener(evt -> {
      controller.horizontalRequest();
    });

    brightnessSlider.addChangeListener(evt -> {
      controller.brightenRequest(brightnessSlider.getValue());
    });

    redCompButton.addActionListener(evt -> {
      controller.redCompRequest();
    });

    greenCompButton.addActionListener(evt -> {
      controller.greenCompRequest();
    });

    blueCompButton.addActionListener(evt -> {
      controller.blueCompRequest();
    });

    valueButton.addActionListener(evt -> {
      controller.valueRequest();
    });

    intensityButton.addActionListener(evt -> {
      controller.intensityRequest();
    });

    lumaButton.addActionListener(evt -> {
      controller.lumaRequest();
    });

    blurButton.addActionListener(evt -> {
      controller.blurRequest();
    });

    sharpenButton.addActionListener(evt -> {
      controller.sharpenRequest();
    });

    sepiaButton.addActionListener(evt -> {
      controller.sepiaRequest();
    });

    customMatrixButton.addActionListener(evt -> {
      // dialogue panel
      JPanel matrixPanel = new JPanel();
      matrixPanel.setLayout(new GridLayout(3,3));
      // dialogue fields (color matrix)
      String[] fieldNames = new String[]{"r0","r1","r2","g0","g1","g2","b0","b1","b2"};
      JFormattedTextField[] fields = new JFormattedTextField[fieldNames.length];

      for (int i = 0; i < fieldNames.length; i++) {
        // number formatter (user must submit numbers, allows +/-)
        NumberFormatter format = new NumberFormatter();
        format.setMaximum(999.99999);
        format.setMinimum(-999.99999);
        // text field with format
        JFormattedTextField field = new JFormattedTextField(format);
        field.setColumns(3); // set the width of the field
        if (field.getText().equals("")) {
          field.setValue(0); // default to 0 if empty or too big/small
        }
        fields[i] = field;
        matrixPanel.add(new JLabel(fieldNames[i]));
        matrixPanel.add(field);
        matrixPanel.add(Box.createHorizontalStrut(15));
      }
      // confirm-dialogue
      int result = JOptionPane.showConfirmDialog(null, matrixPanel,
              "Please Enter Color Matrix", JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.PLAIN_MESSAGE);
      // if the result of the confirm-dialogue is OK and not CANCEL, then apply color matrix
      if (result == JOptionPane.OK_OPTION) {
        double[][] cMatrix = new double[][]{
            new double[]{Double.valueOf(fields[0].getText()),
                        Double.valueOf(fields[1].getText()),
                        Double.valueOf(fields[2].getText())},
            new double[]{Double.valueOf(fields[3].getText()),
                        Double.valueOf(fields[4].getText()),
                        Double.valueOf(fields[5].getText())},
            new double[]{Double.valueOf(fields[6].getText()),
                        Double.valueOf(fields[7].getText()),
                        Double.valueOf(fields[8].getText())}
        };
        controller.customMatrixRequest(cMatrix);
      }
    });
  }

  @Override
  public void refreshImage(BufferedImage img) {
    if (img == null) {
      throw new IllegalArgumentException("image cannot be null");
    }

    this.displayImage.setIcon(new ImageIcon(img));
    this.redHistogram.refreshImage(img);
    this.greenHistogram.refreshImage(img);
    this.blueHistogram.refreshImage(img);
    this.intensityHistogram.refreshImage(img);
  }

  /**
   * After a successful loading sequence, the view should render the toolbar
   * and eliminate the hint message.
   */
  @Override
  public void firstMount() {
    this.toolbarPanel.setVisible(true);
    this.imageScrollPane.setToolTipText("");

    // return brightness slider back to 0 upon loading a new image
    this.brightnessSlider.setValue(0);
  }

  /**
   * Does not throw an IOException.
   */
  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(this, message,
            "Error", JOptionPane.WARNING_MESSAGE);
  }
}
