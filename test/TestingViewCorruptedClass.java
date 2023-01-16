import java.io.IOException;

/**
 * This class represents a corrupted class for testing purposes. It implements all the methods
 * inherited by the Appendable but overrides them to always throw an IOException.
 */
public class TestingViewCorruptedClass implements Appendable {

  /**
   * Represents the constructor so that this corrupted class can be called when testing
   * the controller.
   */
  public TestingViewCorruptedClass() {
    // empty constructor for testing.
  }

  /**
   * Overrides the append method in the Appendable interface to throw an IOException.
   * Throws an IOException regardless.
   *
   * @param csq The character sequence to append.  If {@code csq} is
   *            {@code null}, then the four characters {@code "null"} are
   *            appended to this Appendable.
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  /**
   * Overrides the append method in the Appendable interface to throw an IOException.
   * Throws an IOException regardless.
   *
   * @param csq   The character sequence from which a subsequence will be
   *              appended.  If {@code csq} is {@code null}, then characters
   *              will be appended as if {@code csq} contained the four
   *              characters {@code "null"}.
   * @param start The index of the first character in the subsequence
   * @param end   The index of the character following the last character in the
   *              subsequence
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  /**
   * Overrides the append method in the Appendable interface to throw an IOException.
   * Throws an IOException regardless.
   *
   * @param c The character to append
   */
  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
