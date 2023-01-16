package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.commands.BlueComp;
import controller.commands.Blur;
import controller.commands.Brighten;
import controller.commands.GreenComp;
import controller.commands.GreyscaleTransformation;
import controller.commands.HorizontalFlip;
import controller.commands.IntensityComp;
import controller.commands.LumaComp;
import controller.commands.RedComp;
import controller.commands.SepiaTransformation;
import controller.commands.Sharpen;
import controller.commands.ValueComp;
import controller.commands.VerticalFlip;
import model.ImageProcessorFilter;
import model.Pixel;
import view.ProcessorViewGUI;

/**
 * This class represents all the operations needed for controller of the image processing GUI
 * to handle action events. The user can interact with the program graphically.
 * It takes a model and GUI view, and based on an action event supplied by the user, performs said
 * action event by sending the necessary inputs to the model. Additionally, inputs are sent
 * to the view to update the GUI and display changes to the user.
 */
public class ProcessorControllerImplGUI extends Component implements ActionListener {
  private ImageProcessorFilter model;
  private ProcessorViewGUI view;
  private String fileName;
  private ImageConverter converter;
  private String recentImageName;
  private Integer increment;

  /**
   * Creates a controller object that takes in a model and view. Additionally, tells
   * the view to set the action listeners to the buttons displayed in the view.
   *
   * @param model the image processor filter model
   * @param view  the GUI view
   * @throws IllegalArgumentException if model and/or view is null
   */
  public ProcessorControllerImplGUI(ImageProcessorFilter model, ProcessorViewGUI view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Model and/or view is invalid.");
    }
    this.model = model;
    this.view = view;
    this.view.setActionListener(this::actionPerformed);
  }

  /**
   * Based on the action event supplied (in String form), performs a specific command.
   * For applying operations to an image like filters, the operations are applied to
   * the most recent image via the most recent image's name, which is continuously updated
   * when applying operations.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    switch (command) {
      case "Open file":
        this.openFileHelper();
        break;
      case "Save file":
        this.saveFileHelper();
        break;
      case "Red-Component":
        this.itemStateChangedHelper(new RedComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Sharpen":
        this.itemStateChangedHelper(new Sharpen(recentImageName,
                recentImageName + command), command);
        break;
      case "Sepia":
        this.itemStateChangedHelper(new SepiaTransformation(recentImageName,
                recentImageName + command), command);
        break;
      case "Greyscale":
        this.itemStateChangedHelper(new GreyscaleTransformation(recentImageName,
                recentImageName + command), command);
        break;
      case "Blur":
        this.itemStateChangedHelper(new Blur(recentImageName,
                recentImageName + command), command);
        break;
      case "Blue-Component":
        this.itemStateChangedHelper(new BlueComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Green-Component":
        this.itemStateChangedHelper(new GreenComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Intensity-Component":
        this.itemStateChangedHelper(new IntensityComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Value-Component":
        this.itemStateChangedHelper(new ValueComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Luma-Component":
        this.itemStateChangedHelper(new LumaComp(recentImageName,
                recentImageName + command), command);
        break;
      case "Horizontal Flip":
        this.itemStateChangedHelper(new HorizontalFlip(recentImageName,
                recentImageName + command), command);
        break;
      case "Vertical Flip":
        this.itemStateChangedHelper(new VerticalFlip(recentImageName,
                recentImageName + command), command);
        break;
      case "Brighten options":
        JComboBox comboBox = (JComboBox) e.getSource();
        increment = (Integer) comboBox.getSelectedItem();
        break;
      case "Brighten":
        if (increment != null) {
          this.itemStateChangedHelper(new Brighten(increment, recentImageName,
                          recentImageName + command + " by " + increment),
                  command + " by " + increment);
        } else {
          view.createErrorPopUp("Please select an increment to brighten or darken image by.");
        }
        break;
      default:
        // empty because no other action events to process and view doesn't need to be updated
    }
  }

  /**
   * Helper method for all the operations. Tells the model to execute the command for the
   * most recently loaded in image in the model. Updates the recent image name each time to load
   * a new entry into the model and tells the view to update the image and histogram display.
   *
   * @param commands controller commands
   * @param operation command in String form
   */
  private void itemStateChangedHelper(ControllerCommands commands, String operation) {
    commands.execute(model);
    recentImageName = recentImageName + operation;
    BufferedImage image =
            converter.getBufferedImage(model.getImage(recentImageName));
    view.setImage(image);
    view.setHistogramView(this.model.getHistogram(recentImageName));
  }

  /**
   * Receives the file path chosen by the user and depending on the file's extension type,
   * converts the file to an image to load into the model and sends a buffered image
   * to the view to display. Additionally, sends the updated array for the histogram to the
   * view to display the histogram chart.
   */
  private void openFileHelper() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, PNG, PPM & BMP", "jpg", "png", "ppm", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(ProcessorControllerImplGUI.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String filePathLoad = f.getAbsolutePath();
      this.updateConverter(filePathLoad);
      if (converter != null) {
        try {
          converter.reader(filePathLoad);
          fileName = f.getName();
          model.load(fileName, converter.getImage());
          recentImageName = fileName;
          this.increment = null;
          view.setFileOpenDisplay(filePathLoad);
          view.setImage(converter.getBufferedImage(converter.getImage()));
          view.setHistogramView(this.model.getHistogram(recentImageName));
        } catch (IllegalArgumentException e) {
          // catches exceptions thrown by reader, error message is displayed helper
        }
      }
    }
  }

  /**
   * Receives a file path inputted by the user and gets the most recent image
   * loaded into the model to that filepath. Sends file path to the view to display
   * if image was properly saved.
   */
  private void saveFileHelper() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(ProcessorControllerImplGUI.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      String filePathSave = f.getAbsolutePath();
      List<List<Pixel>> currentImage = this.model.getImage(recentImageName);
      this.updateConverter(filePathSave);
      if (converter != null) {
        converter.save(currentImage, filePathSave);
        view.setFileSaveDisplay(filePathSave);
      }
    }
  }

  /**
   * Updates the converter type used based on the file path provided. File path
   * currently supported are ppm, jpg, png and bmp. If the file path supplied is not
   * one of the extensions stated, then a message will be sent to the view to notify
   * the user.
   *
   * @param filePath file path provided
   */
  private void updateConverter(String filePath) {
    switch (filePath.substring(filePath.length() - 3)) {
      case "ppm":
        converter = new PPMImageConverter();
        break;
      case "jpg":
      case "png":
      case "bmp":
        converter = new StandardImageConverter();
        break;
      default:
        view.createErrorPopUp("Extension is not supported.");
    }
  }

}
