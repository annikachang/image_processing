import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import controller.ProcessorController;
import controller.ProcessorControllerImpl;
import model.ImageProcessorFilter;
import model.ImageProcessorImplFilter;
import view.ProcessorView;
import view.ProcessorViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Represents the testing class for the controller.
 */

public class ProcessorControllerImplTest {
  private ImageProcessorFilter model1;
  private ProcessorView view1;
  private Appendable resultView1;



  @Before
  public void testInit() {
    this.model1 = new ImageProcessorImplFilter();
    this.resultView1 = new StringBuilder();
    this.view1 = new ProcessorViewImpl(this.resultView1);
  }

  @Test
  public void testConstructorNull() {
    try {
      new ProcessorControllerImpl(null, model1, view1);
    } catch (IllegalArgumentException e) {
      assertEquals("Model, view and/or readable supplied is invalid.", e.getMessage());
    }
    try {
      new ProcessorControllerImpl(new StringReader("q"), null, view1);
    } catch (IllegalArgumentException e) {
      assertEquals("Model, view and/or readable supplied is invalid.", e.getMessage());
    }
    try {
      new ProcessorControllerImpl(new StringReader("q"), model1, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Model, view and/or readable supplied is invalid.", e.getMessage());
    }
  }


  @Test
  public void testLoadFiles() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImage").size());
    assertEquals(2, this.model1.getImage("colorImage").get(0).size());
    assertEquals(2, this.model1.getImage("colorImage").get(1).size());
    assertEquals(2, this.model1.getImage("colorImage").get(2).size());
    assertEquals("'colorImage' was loaded into program from path res/imageSmall.ppm.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testLoadFilesMultiple() {
    Readable rd1 = new StringReader("load res/brown.ppm brownImage\n" +
            "load res/imageSmall.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(2, this.model1.getImage("brownImage").size());
    assertEquals(3, this.model1.getImage("brownImage").get(0).size());
    assertEquals(3, this.model1.getImage("brownImage").get(1).size());
    assertEquals(3, this.model1.getImage("colorImage").size());
    assertEquals(2, this.model1.getImage("colorImage").get(0).size());
    assertEquals(2, this.model1.getImage("colorImage").get(1).size());
    assertEquals(2, this.model1.getImage("colorImage").get(2).size());
    assertEquals("'brownImage' was loaded into program from path res/brown.ppm.",
            this.resultView1.toString().split("\n")[11]);
    assertEquals("'colorImage' was loaded into program from path res/imageSmall.ppm.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testLoadFilesEmptyFile() {
    Readable rd1 = new StringReader("load res/colorimageEmpty.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("PPM file supplied is invalid.",
            resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testLoadFilesP3FormatInvalid() {
    Readable rd1 = new StringReader("load res/colorImageP3Invalid.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is not in P3 format.",
            resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testPixelCompMoreThanMax() {
    Readable rd1 = new StringReader("load res/colorImageMaxValGreater.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("RGB components can't be negative or greater than 255.",
            resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testPixelCompMoreThanNeg() {
    Readable rd1 = new StringReader("load res/colorimageNeg.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("RGB components can't be negative or greater than 255.",
            resultView1.toString().split("\n")[11]);
  }


  @Test
  public void testFileNoDimension() {
    Readable rd1 = new StringReader("load res/colorImageNoDim.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("PPM file supplied is invalid.",
            resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testFileNoPixels() {
    Readable rd1 = new StringReader("load res/colorimageEmpty.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("PPM file supplied is invalid.",
            resultView1.toString().split("\n")[11]);
  }


  @Test
  public void testFileNotFound() {
    Readable rd1 = new StringReader("load res/colorImageNotFound.ppm colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is not found.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testRedComp() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red-component colorImage colorImageRed\n" +
            "save res/ColorImageRed.ppm colorImageRed\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(255, this.model1.getImage("colorImageRed")
            .get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageRed")
            .get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageRed")
            .get(0).get(0).getColorComp(2));
    assertEquals(101, this.model1.getImage("colorImageRed")
            .get(0).get(1).getColorComp(0));
    assertEquals(101, this.model1.getImage("colorImageRed")
            .get(0).get(1).getColorComp(1));
    assertEquals(101, this.model1.getImage("colorImageRed")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testBlueComp() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "blue-component colorImage colorImageB\n" +
            "save res/ColorImageBlue.ppm colorImageB\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(127, this.model1.getImage("colorImageB")
            .get(0).get(0).getColorComp(0));
    assertEquals(127, this.model1.getImage("colorImageB")
            .get(0).get(0).getColorComp(1));
    assertEquals(127, this.model1.getImage("colorImageB")
            .get(0).get(0).getColorComp(2));
    assertEquals(137, this.model1.getImage("colorImageB")
            .get(0).get(1).getColorComp(0));
    assertEquals(137, this.model1.getImage("colorImageB")
            .get(0).get(1).getColorComp(1));
    assertEquals(137, this.model1.getImage("colorImageB")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testGreenComp() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "green-component colorImage colorImageGreen\n" +
            "save res/ColorImageGreen.ppm colorImageGreen\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(255, this.model1.getImage("colorImageGreen")
            .get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageGreen")
            .get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageGreen")
            .get(0).get(0).getColorComp(2));
    assertEquals(143, this.model1.getImage("colorImageGreen")
            .get(0).get(1).getColorComp(0));
    assertEquals(143, this.model1.getImage("colorImageGreen")
            .get(0).get(1).getColorComp(1));
    assertEquals(143, this.model1.getImage("colorImageGreen")
            .get(0).get(1).getColorComp(2));
  }


  @Test
  public void testValueCommand() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "value-component colorImage colorImageValue\n" +
            "save res/ColorImageValue.ppm colorImageValue\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(255, this.model1.getImage("colorImageValue")
            .get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageValue")
            .get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageValue")
            .get(0).get(0).getColorComp(2));
    assertEquals(143, this.model1.getImage("colorImageValue")
            .get(0).get(1).getColorComp(0));
    assertEquals(143, this.model1.getImage("colorImageValue")
            .get(0).get(1).getColorComp(1));
    assertEquals(143, this.model1.getImage("colorImageValue")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testLumaCommand() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "luma-component colorImage colorImageLuma\n" +
            "save res/ColorImageLuma.ppm colorImageLuma\n");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(246, this.model1.getImage("colorImageLuma")
            .get(0).get(0).getColorComp(0));
    assertEquals(246, this.model1.getImage("colorImageLuma")
            .get(0).get(0).getColorComp(1));
    assertEquals(246, this.model1.getImage("colorImageLuma")
            .get(0).get(0).getColorComp(2));
    assertEquals(134, this.model1.getImage("colorImageLuma")
            .get(0).get(1).getColorComp(0));
    assertEquals(134, this.model1.getImage("colorImageLuma")
            .get(0).get(1).getColorComp(1));
    assertEquals(134, this.model1.getImage("colorImageLuma")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testIntensityCommand() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "intensity-component colorImage colorImageIntensity\n" +
            "save res/ColorImageIntensity.ppm colorImageIntensity\n");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(212, this.model1.getImage("colorImageIntensity")
            .get(0).get(0).getColorComp(0));
    assertEquals(212, this.model1.getImage("colorImageIntensity")
            .get(0).get(0).getColorComp(1));
    assertEquals(212, this.model1.getImage("colorImageIntensity")
            .get(0).get(0).getColorComp(2));
    assertEquals(127, this.model1.getImage("colorImageIntensity")
            .get(0).get(1).getColorComp(0));
    assertEquals(127, this.model1.getImage("colorImageIntensity")
            .get(0).get(1).getColorComp(1));
    assertEquals(127, this.model1.getImage("colorImageIntensity")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testVerticalFlip() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "vertical-flip colorImage colorImageV\n" +
            "save res/ColorImageVertical.ppm colorImageV\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(119, this.model1.getImage("colorImageV")
            .get(0).get(0).getColorComp(0));
    assertEquals(208, this.model1.getImage("colorImageV")
            .get(0).get(0).getColorComp(1));
    assertEquals(167, this.model1.getImage("colorImageV")
            .get(0).get(0).getColorComp(2));
    assertEquals(170, this.model1.getImage("colorImageV")
            .get(0).get(1).getColorComp(0));
    assertEquals(144, this.model1.getImage("colorImageV")
            .get(0).get(1).getColorComp(1));
    assertEquals(160, this.model1.getImage("colorImageV")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testHorizontalFlip() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "horizontal-flip colorImage colorImageH\n" +
            "save res/ColorImageHorizontal.ppm colorImageH\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(101, this.model1.getImage("colorImageH")
            .get(0).get(0).getColorComp(0));
    assertEquals(143, this.model1.getImage("colorImageH")
            .get(0).get(0).getColorComp(1));
    assertEquals(137, this.model1.getImage("colorImageH")
            .get(0).get(0).getColorComp(2));
    assertEquals(255, this.model1.getImage("colorImageH")
            .get(0).get(1).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageH")
            .get(0).get(1).getColorComp(1));
    assertEquals(127, this.model1.getImage("colorImageH")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testBrighten() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "brighten 100 colorImage colorImageBright\n" +
            "save res/ColorImageBrighter.ppm colorImageBright\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(255, this.model1.getImage("colorImageBright")
            .get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageBright")
            .get(0).get(0).getColorComp(1));
    assertEquals(227, this.model1.getImage("colorImageBright")
            .get(0).get(0).getColorComp(2));
    assertEquals(201, this.model1.getImage("colorImageBright")
            .get(0).get(1).getColorComp(0));
    assertEquals(243, this.model1.getImage("colorImageBright")
            .get(0).get(1).getColorComp(1));
    assertEquals(237, this.model1.getImage("colorImageBright")
            .get(0).get(1).getColorComp(2));
  }

  @Test
  public void testDarker() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "brighten -100 colorImage colorImageBright\n" +
            "save res/ColorImageDarker.ppm colorImageBright\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testSave() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red-component colorImage colorImageRed\n" +
            "save res/imageSmallRed.ppm colorImageRed\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(3, this.model1.getImage("colorImageRed").size());
    assertEquals(2, this.model1.getImage("colorImageRed").get(0).size());
  }

  @Test
  public void testSaveThenLoad() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red-component colorImage colorImageRed\n" +
            "save res/colorImageRed.ppm colorImageRed\n" +
            "load res/colorImageRed.ppm colorImageRed\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.", this.resultView1.toString().
            split("\n")[12]);
  }

  @Test
  public void testSaveInvalid() {
    Readable rd1 = new StringReader("save res/colorImageBright.ppm colorImageBright\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File not found.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testFileNotFoundInput() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red-component colorImageRed colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File not found.", this.resultView1.toString().split("\n")[12]);
  }


  @Test
  public void testInvalidCommand() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red colorImage colorRed\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Command is not supported yet :).",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testQuit() {
    Readable rd1 = new StringReader("q");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Program has quit.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testMultipleOps() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\n" +
            "red-component colorImage colorImageRed\n" +
            "vertical-flip colorImageRed colorImageRedVertical\n" +
            "brighten 10 colorImageRedVertical colorImageRedVerticalBright\n");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Welcome! This image processing program supports " +
            "many operations to load, manipulate and save image.\n" +
            "To load an image : 'load image-path image-name'\n" +
            "To flip an image horizontally or vertically : 'horizontal-flip " +
            "image-name dest-image-name' or 'vertical-flip image-name dest-image-name\n" +
            "To brighten an image : 'brighten increment image-name dest-image-name'\n" +
            "To grey-scale an image based on a channel: 'channel-component image-name " +
            "dest-image-name'\n" +
            "(channels : red, green, blue, value, intensity and luma)\n" +
            "Example : 'red-component image-name dest-image-name'\n" +
            "To perform a color transformation : 'transformation image-name dest-image-name'" +
            "(transformations : greyscale, sepia)\n" +
            "To filter : 'filter image-name dest-image-name (filters : blur, sharpen)\n" +
            "To save an image : 'save image-path image-name'\n" +
            "To quit the program : 'quit' or 'q'\n" +
            "'colorImage' was loaded into program from path res/imageSmall.ppm.\n" +
            "Operation performed successfully.\n" +
            "Operation performed successfully.\n" +
            "Operation performed successfully.\n", resultView1.toString());
    assertEquals(129, this.model1.getImage("colorImageRedVerticalBright").
            get(0).get(0).getColorComp(0));
    assertEquals(129, this.model1.getImage("colorImageRedVerticalBright").
            get(0).get(0).getColorComp(1));
    assertEquals(129, this.model1.getImage("colorImageRedVerticalBright").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testMenu() {
    Readable rd1 = new StringReader("q");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Welcome! This image processing program supports many operations " +
            "to load, manipulate and save image.\n" +
            "To load an image : 'load image-path image-name'\n" +
            "To flip an image horizontally or vertically : 'horizontal-flip image-name dest-" +
            "image-name' or 'vertical-flip image-name dest-image-name\n" +
            "To brighten an image : 'brighten increment image-name dest-image-name'\n" +
            "To grey-scale an image based on a channel: 'channel-component image-name dest-" +
            "image-name'\n" +
            "(channels : red, green, blue, value, intensity and luma)\n" +
            "Example : 'red-component image-name dest-image-name'\n" +
            "To perform a color transformation : 'transformation image-name dest-image-name'" +
            "(transformations : greyscale, sepia)\n" +
            "To filter : 'filter image-name dest-image-name (filters : blur, sharpen)\n" +
            "To save an image : 'save image-path image-name'\n" +
            "To quit the program : 'quit' or 'q'\n" +
            "Program has quit.", this.resultView1.toString());
  }


  @Test
  public void testCorruptedAppendable() {
    Readable rd1 = new StringReader("q");
    ProcessorView view = new ProcessorViewImpl(new TestingViewCorruptedClass());
    ProcessorController controller = new ProcessorControllerImpl(rd1, model1, view);
    try {
      controller.control();
    } catch (IllegalStateException e) {
      assertEquals("Output is not transmittable.", e.getMessage());
    }
  }

  @Test
  public void testWithMockModelFlip() {
    StringBuilder output = new StringBuilder();
    model1 = new MockModel(output);
    Readable rd1 = new StringReader(
            "horizontal-flip colorImage colorImageHF\n" +
                    "vertical-flip colorImage colorImageVF\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("colorImage colorImageHF\n" +
            "colorImage colorImageVF\n", output.toString());
  }

  @Test
  public void testWithMockModelBrighten() {
    StringBuilder output = new StringBuilder();
    model1 = new MockModel(output);
    Readable rd1 = new StringReader(
            "brighten 10 colorImage colorBrighter\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("10  colorImage colorBrighter", output.toString());
  }


  @Test
  public void testWithMockModelComponent() {
    StringBuilder output = new StringBuilder();
    model1 = new MockModel(output);
    Readable rd1 = new StringReader(
            "red-component colorImage colorRed\n" +
                    "blue-component colorImage colorBlue\n" +
                    "green-component colorImage colorGreen\n" +
                    "value-component colorImage colorValue\n" +
                    "intensity-component colorImage colorIntensity\n" +
                    "luma-component colorImage colorLuma\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("colorImage colorRed\n" +
            "colorImage colorBlue\n" +
            "colorImage colorGreen\n" +
            "colorImage colorValue\n" +
            "colorImage colorIntensity\n" +
            "colorImage colorLuma\n", output.toString());
  }

  // test loading png image
  @Test
  public void testLoadingPNG() {
    Readable rd1 = new StringReader("load res/p3image.png greyImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("'greyImage' was loaded into program from path res/p3image.png.",
            this.resultView1.toString().split("\n")[11]);
    assertEquals(192, this.model1.getImage("greyImage").
            get(0).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("greyImage").
            get(0).get(0).getColorComp(1));
    assertEquals(192, this.model1.getImage("greyImage").
            get(0).get(0).getColorComp(2));
    assertEquals(84, this.model1.getImage("greyImage").
            get(1).get(1).getColorComp(0));
    assertEquals(90, this.model1.getImage("greyImage").
            get(1).get(1).getColorComp(1));
    assertEquals(92, this.model1.getImage("greyImage").
            get(1).get(1).getColorComp(2));
  }

  @Test
  public void testLoadingPNGColor() {
    Readable rd1 = new StringReader("load res/pastel.png colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("'colorImage' was loaded into program from path res/pastel.png.",
            this.resultView1.toString().split("\n")[11]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(112, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(152, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(121, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
  }

  // test loading png file when file does not exist
  @Test
  public void testIncorrectExtensionFilePathPNG() {
    Readable rd1 = new StringReader("load res/colorImage.png colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is invalid.",
            this.resultView1.toString().split("\n")[11]);
  }

  // test loading corrupted png file
  @Test
  public void testCorruptedPNGfile() {
    Readable rd1 = new StringReader("load res/corrupted.png corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is not in proper format.",
            this.resultView1.toString().split("\n")[11]);
  }

  // test loading empty png file
  @Test
  public void testEmptyPNGfile() {
    Readable rd1 = new StringReader("load res/empty.png corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is empty.",
            this.resultView1.toString().split("\n")[11]);
  }

  // test loading jpg file
  @Test
  public void testLoadingJPG() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("'colorImage' was loaded into program from path res/pastel.jpg.",
            this.resultView1.toString().split("\n")[11]);
    assertEquals(138, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(145, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(101, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(159, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(150, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(91, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
  }

  // test loading jpg file that does not exist
  @Test
  public void testIncorrectExtensionFilePathJPG() {
    Readable rd1 = new StringReader("load res/invalidPath.jpg colorImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is invalid.",
            this.resultView1.toString().split("\n")[11]);
  }

  // test loading corrupted jpg file
  @Test
  public void testCorruptedJPGfile() {
    Readable rd1 = new StringReader("load res/corrupted.jpg corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is not in proper format.",
            this.resultView1.toString().split("\n")[11]);
  }

  // test loading empty jpg file
  @Test
  public void testEmptyJPGfile() {
    Readable rd1 = new StringReader("load res/empty.jpg corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is empty.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testIncorrectBMPFilepath() {
    Readable rd1 = new StringReader("load res/fanoweap.bmp corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is invalid.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testEmptyBMPFile() {
    Readable rd1 = new StringReader("load res/empty.bmp corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is empty.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testCorruptedBMPFile() {
    Readable rd1 = new StringReader("load res/corrupted.bmp corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File is not in proper format.",
            this.resultView1.toString().split("\n")[11]);
  }

  @Test
  public void testLoadingInvalidExtension() {
    Readable rd1 = new StringReader("load res/corrupted.img corruptedImage");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File extension is not supported.",
            this.resultView1.toString().split("\n")[11]);
  }


  @Test
  public void testRedCompOnJPG() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\nred-component " +
            "colorImage colorImageR\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(138, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(145, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(101, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(138, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(0));
    assertEquals(138, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(1));
    assertEquals(138, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(2));
  }


  @Test
  public void testValueCompPNG() {
    Readable rd1 = new StringReader("load res/pastel.png colorImage\nvalue-component " +
            "colorImage colorImageV\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(1));
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testGreyScalePNG() {
    Readable rd1 = new StringReader("load res/pastel.png colorImage\ngreyscale " +
            "colorImage colorImageG\nsave res/pastelGrey.ppm colorImageG\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testGreyScaleJPG() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\ngreyscale " +
            "colorImage colorImageG\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(138, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(145, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(101, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(140, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(140, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(140, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testGreyScalePPM() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\ngreyscale " +
            "colorImage colorImageG\nsave res/imageSmallGrey.jpg colorImageG\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(100, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(1));
    assertEquals(161, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(2));
    assertEquals(170, this.model1.getImage("colorImageG").
            get(1).get(0).getColorComp(0));
    assertEquals(170, this.model1.getImage("colorImageG").
            get(1).get(0).getColorComp(1));
    assertEquals(170, this.model1.getImage("colorImageG").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testGreyScaleBMP() {
    Readable rd1 = new StringReader("load res/pastel.bmp colorImage\ngreyscale " +
            "colorImage colorImageG\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));
  }



  @Test
  public void testSepiaPPM() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\nsepia " +
            "colorImage colorImageS\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(100, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(1));
    assertEquals(161, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(2));
    assertEquals(217, this.model1.getImage("colorImageS").
            get(1).get(0).getColorComp(0));
    assertEquals(194, this.model1.getImage("colorImageS").
            get(1).get(0).getColorComp(1));
    assertEquals(151, this.model1.getImage("colorImageS").
            get(1).get(0).getColorComp(2));
    assertEquals(255, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(1));
    assertEquals(222, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSepiaJPG() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\nsepia " +
            "colorImage colorImageS\nsave res/pastelSepia.bmp colorImageS\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(138, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(145, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(101, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(185, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(0));
    assertEquals(164, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(1));
    assertEquals(128, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSepiaPNG() {
    Readable rd1 = new StringReader("load res/pastel.png colorImage\nsepia " +
            "colorImage colorImageS\nsave res/pastelSepia.ppm colorImageS\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(201, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(0));
    assertEquals(180, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(1));
    assertEquals(140, this.model1.getImage("colorImageS").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testBMPSepia() {
    Readable rd1 = new StringReader("load res/pastel.bmp colorImage\nsepia " +
            "colorImage colorImageSepia\nsave res/pastelSepia.png colorImageSepia\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(151, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(75, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(201, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(0));
    assertEquals(180, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(1));
    assertEquals(140, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testWithMockModelGreyScale() {
    StringBuilder output = new StringBuilder();
    model1 = new MockModel(output);
    Readable rd1 = new StringReader(
            "greyscale colorImage colorImageGrey\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("colorImage colorImageGrey\n", output.toString());
  }

  @Test
  public void testWithMockModelSepia() {
    StringBuilder output = new StringBuilder();
    model1 = new MockModel(output);
    Readable rd1 = new StringReader(
            "sepia colorImage colorImageSepia\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("colorImage colorImageSepia\n", output.toString());
  }

  @Test
  public void testSaveJPGFormat() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\nsepia " +
            "colorImage colorImageS\nsave res/pastelSepia.jpg colorImageS\nload " +
            "res/pastelSepia.jpg colorImageSFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals("'colorImageS' was saved to path res/pastelSepia.jpg.",
            this.resultView1.toString().split("\n")[13]);
    assertEquals(2, this.model1.getImage("colorImageS").size());
    assertEquals(3, this.model1.getImage("colorImageS").get(0).size());

    assertEquals(2, this.model1.getImage("colorImageSFile").size());
    assertEquals(3, this.model1.getImage("colorImageSFile").get(0).size());
    assertEquals(176, this.model1.getImage("colorImageSFile").
            get(0).get(0).getColorComp(0));
    assertEquals(157, this.model1.getImage("colorImageSFile").
            get(0).get(0).getColorComp(1));
    assertEquals(124, this.model1.getImage("colorImageSFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSavePPMFormat() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\nred-component " +
            "colorImage colorImageR\nsave res/imageSmallRFile.ppm colorImageR\nload " +
            "res/imageSmallRFile.ppm colorImageRFile");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals(3, this.model1.getImage("colorImageR").size());
    assertEquals(2, this.model1.getImage("colorImageR").get(0).size());
    assertEquals(255, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageR").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageRFile").size());
    assertEquals(2, this.model1.getImage("colorImageRFile").get(0).size());
    assertEquals(255, this.model1.getImage("colorImageRFile").
            get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageRFile").
            get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageRFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSavePNGFormat() {
    Readable rd1 = new StringReader("load res/pastel.png colorImage\ngreyscale " +
            "colorImage colorImageG\nsave res/pastelGrey.png colorImageG\nload " +
            "res/pastelGrey.png colorImageGFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImageG").size());
    assertEquals(4, this.model1.getImage("colorImageG").get(0).size());
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageGFile").size());
    assertEquals(4, this.model1.getImage("colorImageGFile").get(0).size());
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSaveBMPFile() {
    Readable rd1 = new StringReader("load res/pastel.bmp colorImage\ngreyscale " +
            "colorImage colorImageG\nsave res/pastelGrey.bmp colorImageG\nload " +
            "res/pastelGrey.bmp colorImageGFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImageG").size());
    assertEquals(4, this.model1.getImage("colorImageG").get(0).size());
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageGFile").size());
    assertEquals(4, this.model1.getImage("colorImageGFile").get(0).size());
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(0));
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(1));
    assertEquals(156, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testPPMTOJPG() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\nblue-component " +
            "colorImage colorImageB\nsave res/imageSmall.jpg colorImageB\nload " +
            "res/imageSmall.jpg colorImageBFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImageB").size());
    assertEquals(2, this.model1.getImage("colorImageB").get(0).size());
    assertEquals(127, this.model1.getImage("colorImageB").
            get(0).get(0).getColorComp(0));
    assertEquals(127, this.model1.getImage("colorImageB").
            get(0).get(0).getColorComp(1));
    assertEquals(127, this.model1.getImage("colorImageB").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageBFile").size());
    assertEquals(2, this.model1.getImage("colorImageBFile").get(0).size());
  }

  @Test
  public void testPPMTOPNG() {
    Readable rd1 = new StringReader("load res/imageSmall.ppm colorImage\ngreen-component " +
            "colorImage colorImageG\nsave res/imageSmall.png colorImageG\nload " +
            "res/imageSmall.png colorImageGFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImageG").size());
    assertEquals(2, this.model1.getImage("colorImageG").get(0).size());
    assertEquals(255, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageGFile").size());
    assertEquals(2, this.model1.getImage("colorImageGFile").get(0).size());
    assertEquals(255, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageGFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testBMPTOJPG() {
    Readable rd1 = new StringReader("load res/pastel.bmp colorImage\nvalue-component " +
            "colorImage colorImageV\nsave res/pastelV.jpg colorImageV\nload " +
            "res/pastelV.jpg colorImageVFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(3, this.model1.getImage("colorImageV").size());
    assertEquals(4, this.model1.getImage("colorImageV").get(0).size());
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(0));
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(1));
    assertEquals(166, this.model1.getImage("colorImageV").
            get(0).get(0).getColorComp(2));

    assertEquals(3, this.model1.getImage("colorImageVFile").size());
    assertEquals(4, this.model1.getImage("colorImageVFile").get(0).size());
  }


  @Test
  public void testJPGtoPNG() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\nluma-component " +
            "colorImage colorImageL\nsave res/pastelLuma.png colorImageL\nload " +
            "res/pastelLuma.png colorImageLFile\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals(2, this.model1.getImage("colorImageL").size());
    assertEquals(3, this.model1.getImage("colorImageL").get(0).size());
    assertEquals(140, this.model1.getImage("colorImageL").
            get(0).get(0).getColorComp(0));
    assertEquals(140, this.model1.getImage("colorImageL").
            get(0).get(0).getColorComp(1));
    assertEquals(140, this.model1.getImage("colorImageL").
            get(0).get(0).getColorComp(2));

    assertEquals(2, this.model1.getImage("colorImageLFile").size());
    assertEquals(3, this.model1.getImage("colorImageLFile").get(0).size());
    assertEquals(140, this.model1.getImage("colorImageLFile").
            get(0).get(0).getColorComp(0));
    assertEquals(140, this.model1.getImage("colorImageLFile").
            get(0).get(0).getColorComp(1));
    assertEquals(140, this.model1.getImage("colorImageLFile").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testSaveFileDoesNotExist() {
    Readable rd1 = new StringReader("load res/pastel.jpg colorImage\nluma-component " +
            "colorImage colorImageL\nsave res/rneakorn.png colorImageNOExist\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("File not found.",
            this.resultView1.toString().split("\n")[13]);
  }


  @Test
  public void testBlur() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nblur " +
            "colorImage colorImageBlur\nsave res/purpleBlur.ppm colorImageBlur\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(51, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(0));
    assertEquals(78, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(1));
    assertEquals(47, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(2));
    assertEquals( 54, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(0));
    assertEquals(55, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(1));
    assertEquals(72, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testBlurJPG() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nblur " +
            "colorImage colorImageBlur\nsave res/purpleBlur.jpg colorImageBlur\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(51, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(0));
    assertEquals(78, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(1));
    assertEquals(47, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(2));
    assertEquals( 54, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(0));
    assertEquals(55, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(1));
    assertEquals(72, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testBlurBMP() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nblur " +
            "colorImage colorImageBlur\nsave res/purpleBlur.bmp colorImageBlur\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(51, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(0));
    assertEquals(78, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(1));
    assertEquals(47, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(2));
    assertEquals( 54, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(0));
    assertEquals(55, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(1));
    assertEquals(72, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testBlurPNG() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nblur " +
            "colorImage colorImageBlur\nsave res/purpleBlur.png colorImageBlur\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(51, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(0));
    assertEquals(78, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(1));
    assertEquals(47, this.model1.getImage("colorImageBlur").
            get(0).get(0).getColorComp(2));
    assertEquals( 54, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(0));
    assertEquals(55, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(1));
    assertEquals(72, this.model1.getImage("colorImageBlur").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testSharpenPPM() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nsharpen " +
            "colorImage colorImageSharpen\nsave res/purpleSharpen.ppm colorImageSharpen\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(92, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(0));
    assertEquals(187, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(1));
    assertEquals(62, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(2));
    assertEquals( 103, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(0));
    assertEquals(87, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(1));
    assertEquals(165, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testSharpenJPG() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nsharpen " +
            "colorImage colorImageSharpen\nsave res/purpleSharpen.jpg colorImageSharpen\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(92, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(0));
    assertEquals(187, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(1));
    assertEquals(62, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(2));
    assertEquals( 103, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(0));
    assertEquals(87, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(1));
    assertEquals(165, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testSharpenPNG() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nsharpen " +
            "colorImage colorImageSharpen\nsave res/purpleSharpen.png colorImageSharpen\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(92, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(0));
    assertEquals(187, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(1));
    assertEquals(62, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(2));
    assertEquals( 103, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(0));
    assertEquals(87, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(1));
    assertEquals(165, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testSharpenBMP() {
    Readable rd1 = new StringReader("load res/purple.ppm colorImage\nsharpen " +
            "colorImage colorImageSharpen\nsave res/purpleSharpen.bmp colorImageSharpen\nq");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
    assertEquals( 86, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(65, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals( 89, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(0));
    assertEquals(82, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(1));
    assertEquals(141, this.model1.getImage("colorImage").
            get(2).get(2).getColorComp(2));
    assertEquals(92, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(0));
    assertEquals(187, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(1));
    assertEquals(62, this.model1.getImage("colorImageSharpen").
            get(0).get(0).getColorComp(2));
    assertEquals( 103, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(0));
    assertEquals(87, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(1));
    assertEquals(165, this.model1.getImage("colorImageSharpen").
            get(2).get(2).getColorComp(2));
  }

  @Test
  public void testBlurMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nblur colorImage " +
            "res/pastelMask.ppm colorImageBlurMask\nsave res/blurMask.ppm colorImageBlurMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testSepiaMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nsepia colorImage " +
            "res/pastelMask.ppm colorImageSepiaMask\nsave res/sepiaMask.ppm colorImageSepiaMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testRedCompMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nred-component " +
            "colorImage " +
            "res/pastelMask.ppm colorImageRedMask\nsave res/redMASK.jpg colorImageRedMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testBrightenMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nbrighten 100 " +
            "colorImage " +
            "res/pastelMask.ppm colorImageBrightMask\nsave res/brightMASK.png " +
            "colorImageBrightMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testGreenCompMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\ngreen-component " +
            "colorImage " +
            "res/pastelMask.ppm colorImageGreenMask\nsave res/greenMASK.png colorImageGreenMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testBlueCompMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nblue-component " +
            "colorImage " +
            "res/pastelMask.ppm colorImageBlueMask\nsave res/greenMASK.png colorImageBlueMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testSharpenMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nsharpen colorImage " +
            "res/pastelMask.ppm colorImageSharpenMask\nsave res/greenMASK.png " +
            "colorImageSharpenMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }


  @Test
  public void testGreyscaleMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\ngreyscale colorImage " +
            "res/pastelMask.ppm colorImageGreyMask\nsave res/greenMASK.png colorImageGreyMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }


  @Test
  public void testLumaComp() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nluma-component " +
            "colorImage " +
            "res/pastelMask.ppm colorImageLumaMask\nsave res/greenMASK.png colorImageLumaMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testValCompMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nvalue-component " +
            "colorImage " +
            "res/pastelMask.ppm colorImageValMask\nsave res/greenMASK.png colorImageValMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }

  @Test
  public void testIntensityCompMask() {
    Readable rd1 = new StringReader("load res/pastelOG.ppm colorImage\nintensity-component" +
            " colorImage " +
            "res/pastelMask.ppm colorImageIntMask\nsave res/greenMASK.png colorImageIntMask");
    ProcessorController controller1 = new ProcessorControllerImpl(rd1, model1, view1);
    controller1.control();
    assertEquals("Operation performed successfully.",
            this.resultView1.toString().split("\n")[12]);
  }





}