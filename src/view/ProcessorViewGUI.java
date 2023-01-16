package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * This interface represents all the operations offered by the image processing view for the GUI.
 * The operations below updates or adds to specific JPanels or JLabels in the frame based on
 * the inputs that are sent from the controller.
 */
public interface ProcessorViewGUI {

  /**
   * Adds the action listener supplied to all the buttons in the JFrame so that an action
   * event is handled properly when the user clicks a button.
   * @param listener action listener
   */
  void setActionListener(ActionListener listener);

  /**
   * Updates the file path for the load panel in the GUI to display the file path that
   * the image is loaded from to the user. When the image is added, the commands check box
   * panel, image panel, and save panel is added to the main panel.
   * @param text file path of image loaded in
   */
  void setFileOpenDisplay(String text);

  /**
   * Updates the image in the image label to display the most currently updated buffered
   * image with the operations applied or displays the image that the user loads in.
   * @param arrayImage most updated buffered image to be displayed
   */
  void setImage(BufferedImage arrayImage);

  /**
   * Updates the file path in the save panel to display the file path that the image is
   * saved to.
   * @param text file path to be displayed
   */
  void setFileSaveDisplay(String text);

  /**
   * Creates a popup on to the JFrame with the error message supplied to prompt the user.
   * @param text error message
   */
  void createErrorPopUp(String text);

  /**
   * Changes the histogram panel to update the bars added to reflect the updated histogram
   * array with the appropriate values for the image passed in. Uses transparent red, green,
   * blue and black colored JPanels as the bars to represent the red-component, green-component,
   * blue component and intensity component of the pixels, respectively.
   *
   * @param histogramArray 2D array of values (4 arrays of the frequency of pixels for values
   *                       0 - 256, 4 arrays for each color stated)
   */
  void setHistogramView(int[][] histogramArray);

}
