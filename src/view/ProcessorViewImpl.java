package view;

import java.io.IOException;

/**
 * Class that allows for viewing of instructions and other output from the
 * controller.
 */
public class ProcessorViewImpl implements ProcessorView {
  private Appendable resultView;

  /**
   * Creates view object.
   * @param resultView destination for the output to be displayed
   * @throws IllegalArgumentException if destination is null
   */
  public ProcessorViewImpl(Appendable resultView)
          throws IllegalArgumentException {
    if (resultView == null) {
      throw new IllegalArgumentException("View supplied is invalid.");
    }
    this.resultView = resultView;
  }

  /**
   * Sets default output destination as the console.
   */
  public ProcessorViewImpl() {
    this(System.out);
  }


  @Override
  public void renderMessage(String s) throws IOException {
    this.resultView.append(s);
  }
}
