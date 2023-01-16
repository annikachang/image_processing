import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import view.ProcessorViewGUI;

/**
 * Represents view GUI mock for testing purposes.
 */
public class ProcessorViewGUIMock extends JFrame implements ProcessorViewGUI  {
  private StringBuilder log;

  public ProcessorViewGUIMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setActionListener(ActionListener listener) {
    log.append("call to action listener was made.");
  }

  @Override
  public void setFileOpenDisplay(String text) {
    log.append(text + "\n");
  }

  @Override
  public void setImage(BufferedImage arrayImage) {
    return;
  }

  @Override
  public void setFileSaveDisplay(String text) {
    log.append(text + "\n");
  }

  @Override
  public void createErrorPopUp(String text) {
    log.append(text + "\n");
  }

  @Override
  public void setHistogramView(int[][] histogramArray) {
    log.append("call to make histogram");
  }
}
