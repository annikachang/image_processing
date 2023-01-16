package view;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * This class represents an implementation for the ProcessorViewGUI. It extends the JFrame
 * to allow JPanels, JLabels and JButtons to be displayed upon. All elements are stored in
 * the main panel. When the user clicks the load file button, all the commands, image display and
 * save panel is displayed on the screen for the user to start image processing.
 */
public class ProcessorViewGUIImpl extends JFrame implements ProcessorViewGUI {
  private final JButton[] commandButtons;
  private final JLabel fileOpenDisplay;
  public final JButton fileOpenButton;
  private final JPanel mainPanel;
  private final JLabel imageLabel;
  private final JScrollPane imageScrollPane;
  private final JPanel checkBoxPanel;
  private final JLabel fileSaveDisplay;

  private final JButton fileSaveButton;
  private final JPanel filesavePanel;

  private final JComboBox<Integer> combobox;
  private final JPanel histogramPanel;


  /**
   * Creates a view GUI object with the title of the GUI passed in.
   * The main panel is first created to add all the panels to the main panel either when it's
   * initialized or when the action event is triggered by the action listener.
   * The load panel is first added to the main panel. When the load button is triggered,
   * the image label, buttons for the commands and save panel is generated.
   *
   * @param title title for GUI
   */

  public ProcessorViewGUIImpl(String title) {
    super(title);
    setSize(1500, 1500);

    // The main container of this GUI (where all parts will be stored)
    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    mainPanel.setPreferredSize(new Dimension(600, 600));
    JScrollPane mainScrollPanel = new JScrollPane(mainPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    // adds main panel to JFrame
    this.add(mainScrollPanel);

    // Panel for loading the image
    JPanel loadImagePanel = new JPanel();
    loadImagePanel.setBorder(BorderFactory.createTitledBorder("Please load image to " +
            "start image processing : "));
    loadImagePanel.setLayout(new BoxLayout(loadImagePanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(loadImagePanel);

    // Panel for file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    loadImagePanel.add(fileopenPanel);
    fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");

    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    // Panel for histogram display
    histogramPanel = new JPanel();
    histogramPanel.setLayout(new CardLayout());
    histogramPanel.setBorder(BorderFactory.createTitledBorder("Histogram"));
    histogramPanel.setPreferredSize(new Dimension(this.getWidth(), 500));


    // Panel for image display
    imageLabel = new JLabel();
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image selected: "));
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
    imagePanel.add(imageLabel);
    imageScrollPane = new JScrollPane(imagePanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // Panel for check box commands
    checkBoxPanel = new JPanel();
    checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
    checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));
    String[] commands = new String[]{"Red-Component", "Sharpen", "Sepia", "Greyscale",
                                     "Blur", "Blue-Component", "Green-Component",
                                     "Intensity-Component", "Value-Component", "Luma-Component",
                                     "Horizontal Flip", "Vertical Flip", "Brighten"
    };
    commandButtons = new JButton[commands.length];

    for (int i = 0; i < commandButtons.length; i++) {
      commandButtons[i] = new JButton(commands[i]);
      commandButtons[i].setSelected(false);
      commandButtons[i].setActionCommand(commands[i]);

      checkBoxPanel.add(commandButtons[i]);
    }


    // Panel for saving image
    filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);

    JPanel comboboxPanel = new JPanel();
    comboboxPanel.setBorder(BorderFactory.createTitledBorder("Please enter a value to brighten " +
            "the image by."));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    checkBoxPanel.add(comboboxPanel);

    combobox = new JComboBox<Integer>();
    combobox.setActionCommand("Brighten options");
    combobox.addItem(null);
    for (int i = -255; i < 256; i++) {
      combobox.addItem(i);
      setVisible(true);
      pack();
    }
    comboboxPanel.add(combobox);

    setVisible(true);
    pack();
  }


  @Override
  public void setActionListener(ActionListener listener) {
    for (int i = 0; i < commandButtons.length; i++) {
      commandButtons[i].addActionListener(listener);
    }
    fileOpenButton.addActionListener(listener);
    fileSaveButton.addActionListener(listener);
    combobox.addActionListener(listener);
  }


  @Override
  public void setFileOpenDisplay(String text) {
    this.fileOpenDisplay.setText(text);
    JPanel imageContainer = new JPanel();
    imageContainer.setLayout(new GridLayout());
    imageContainer.add(checkBoxPanel);
    this.combobox.setSelectedIndex(0);
    imageContainer.add(imageScrollPane);
    mainPanel.add(imageContainer);
    JPanel mainImageContainer = new JPanel();
    mainImageContainer.setLayout(new BoxLayout(mainImageContainer, BoxLayout.PAGE_AXIS));
    mainImageContainer.add(imageContainer);
    mainImageContainer.add(filesavePanel);
    mainPanel.add(mainImageContainer);
  }


  @Override
  public void setImage(BufferedImage arrayImage) {
    imageLabel.setIcon(new ImageIcon(arrayImage));
  }

  @Override
  public void setFileSaveDisplay(String text) {
    fileSaveDisplay.setText(text);
  }

  @Override
  public void createErrorPopUp(String text) {
    JOptionPane.showMessageDialog(this, text, "Error",
            JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void setHistogramView(int[][] histogramArray) {
    histogramPanel.removeAll();
    JPanel histogram = new JPanel();
    histogram.setBackground(Color.white);
    histogram.setLayout(null);
    histogram.setSize(new Dimension(100, 100));
    for (int i = 0; i < histogramArray.length; i++) {
      Color[] colors = new Color[]{Color.red, Color.green, Color.blue, Color.black};
      for (int j = 0; j < histogramArray[i].length; j++) {
        JPanel bar = new JPanel();
        bar.setBounds(j * 5, 0, 5, histogramArray[i][j] / 100);
        bar.setBackground(new Color(colors[i].getRed(), colors[i].getGreen(),
                colors[i].getBlue(), 50));
        histogram.add(bar);
      }
    }
    // HISTOGRAM
    histogramPanel.add(histogram);
    this.setSize(new Dimension(this.getWidth() + 1, this.getHeight() + 1));
    histogramPanel.setVisible(true);
    mainPanel.add(histogramPanel);

  }
}
