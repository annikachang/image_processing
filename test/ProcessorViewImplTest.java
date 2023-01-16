import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import view.ProcessorView;
import view.ProcessorViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents a testing class for the view.
 */
public class ProcessorViewImplTest {
  ProcessorView view1;
  Appendable resultView1;

  @Before
  public void initTest() {
    this.resultView1 = new StringBuilder();
    this.view1 = new ProcessorViewImpl(resultView1);
  }

  @Test
  public void testConstructorNull() {
    try {
      new ProcessorViewImpl(null);
    } catch (IllegalArgumentException e) {
      assertEquals("View supplied is invalid.", e.getMessage());
    }
  }

  @Test
  public void testRenderMessage() throws IOException {
    this.view1.renderMessage("Hello");
    assertEquals("Hello", this.resultView1.toString());
    this.view1.renderMessage("\nGoodbye.");
    assertEquals("Hello\n" +
            "Goodbye.", this.resultView1.toString());
  }

  @Test
  public void testRenderMessageException() {
    ProcessorView viewCorrupted = new ProcessorViewImpl(new TestingViewCorruptedClass());
    try {
      viewCorrupted.renderMessage("Goodbye.");
      fail("View is not corrupted.");
    } catch (IOException e) {
      assertEquals(null, e.getMessage());
    }
  }


}
