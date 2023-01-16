package view;

import java.io.IOException;

/**
 * Transmits output from the controller to the user.
 */
public interface ProcessorView {

  /**
   * Renders given message to given destination.
   * @param s String to append to destination
   * @throws IOException if output is not able to be transmitted
   */
  void renderMessage(String s) throws IOException;
}

