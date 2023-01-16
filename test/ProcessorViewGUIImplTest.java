
import org.junit.Test;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import controller.ProcessorControllerImplGUI;
import model.ImageProcessorImplFilter;
import view.ProcessorViewGUIImpl;

import static org.junit.Assert.assertEquals;

/**
 * Represents the testing class for the processor view GUI.
 */
public class ProcessorViewGUIImplTest {

  @Test
  public void testConstructor() {
    try {
      new ProcessorControllerImplGUI(null, new ProcessorViewGUIImpl("Our program"));
    } catch (IllegalArgumentException e) {
      assertEquals("Model and/or view is invalid.", e.getMessage());
    }
    try {
      new ProcessorControllerImplGUI(new ImageProcessorImplFilter(), null);
    } catch (IllegalArgumentException e) {
      assertEquals("Model and/or view is invalid.", e.getMessage());
    }
    try {
      new ProcessorControllerImplGUI(null, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Model and/or view is invalid.", e.getMessage());
    }

  }


  @Test
  public void testSetFileOpenDisplay2() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    view.setFileOpenDisplay("res/color.ppm");
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON3_DOWN_MASK, 123,
            "file open"));
    assertEquals("res/color.ppm\n" +
            "call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testRedComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Red-component"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testGreenComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Green-component"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testBlueComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Blue-component"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testHorizontalFlipComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Horizontal flip"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testVerticalFlipComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Vertical flip"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testBrightenComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Brighten "));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testSepiaComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Sepia "));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testGreyscale() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Greyscale "));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testBlurComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Blur "));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testSharpenComp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Sharpen "));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testBrightenIncrement() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "Brighten Options"));
    assertEquals("call to action listener was made.", viewResult.toString());
  }

  @Test
  public void testPopUp() {
    StringBuilder modelResult = new StringBuilder();
    MockModel model = new MockModel(modelResult);
    StringBuilder viewResult = new StringBuilder();
    ProcessorViewGUIMock view = new ProcessorViewGUIMock(viewResult);
    view.createErrorPopUp("Extension is not supported.");

    ProcessorControllerImplGUI controller = new ProcessorControllerImplGUI(model, view);
    controller.actionPerformed(new ActionEvent(InputEvent.BUTTON1_DOWN_MASK, 123,
            "file open"));
    assertEquals("Extension is not supported.\n" +
            "call to action listener was made.", viewResult.toString());

  }


}