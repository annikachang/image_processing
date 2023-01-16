
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.ImageProcessor;
import model.ImageProcessorImpl;
import model.Pixel;
import model.PixelRGB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Represents the testing class for the Image Processor model.
 */
public class ImageProcessorImplTest {
  List<List<Pixel>> pixelList1;
  Pixel p100;
  Pixel p101;
  Pixel p102;
  Pixel p103;
  List<Pixel> rowPixelList11;
  Pixel p110;
  Pixel p111;
  Pixel p112;
  Pixel p113;
  List<Pixel> rowPixelList12;
  Pixel p120;
  Pixel p121;
  Pixel p122;
  Pixel p123;
  List<Pixel> rowPixelList13;
  ImageProcessor model1;

  List<List<Pixel>> pixelList2;
  Pixel p200;
  Pixel p201;
  List<Pixel> rowPixeList21;
  Pixel p210;
  Pixel p211;
  List<Pixel> rowPixeList22;
  ImageProcessor model2;

  @Before
  public void testInit() {
    this.pixelList1 = new ArrayList<>();
    this.p100 = new PixelRGB(0, 0, 123, 74, 3);
    p101 = new PixelRGB(0, 1, 64, 0, 254);
    p102 = new PixelRGB(0, 2, 52, 255, 64);
    p103 = new PixelRGB(0, 3, 0, 12, 253);
    rowPixelList11 = new ArrayList<>();
    rowPixelList11.add(p100);
    rowPixelList11.add(p101);
    rowPixelList11.add(p102);
    rowPixelList11.add(p103);
    p110 = new PixelRGB(1, 0, 76, 192, 200);
    p111 = new PixelRGB(1, 1, 253, 254, 2);
    p112 = new PixelRGB(1, 2, 0, 17, 154);
    p113 = new PixelRGB(1, 3, 172, 36, 0);
    rowPixelList12 = new ArrayList<>();
    rowPixelList12.add(p110);
    rowPixelList12.add(p111);
    rowPixelList12.add(p112);
    rowPixelList12.add(p113);
    p120 = new PixelRGB(2, 0, 199, 67, 187);
    p121 = new PixelRGB(2, 1, 123, 74, 3);
    p122 = new PixelRGB(2, 2, 255, 4, 0);
    p123 = new PixelRGB(2, 3, 15, 252, 16);
    rowPixelList13 = new ArrayList<>();
    rowPixelList13.add(p120);
    rowPixelList13.add(p121);
    rowPixelList13.add(p122);
    rowPixelList13.add(p123);
    pixelList1.add(rowPixelList11);
    pixelList1.add(rowPixelList12);
    pixelList1.add(rowPixelList13);
    this.model1 = new ImageProcessorImpl();

    this.pixelList2 = new ArrayList<>();
    this.p200 = new PixelRGB(0, 0, 192, 0, 17);
    this.p201 = new PixelRGB(0, 1, 10, 162, 200);
    this.rowPixeList21 = new ArrayList<>();
    rowPixeList21.add(p200);
    rowPixeList21.add(p201);
    this.p210 = new PixelRGB(1, 0, 157, 64, 99);
    this.p211 = new PixelRGB(1, 1, 240, 232, 4);
    this.rowPixeList22 = new ArrayList<>();
    rowPixeList22.add(p210);
    rowPixeList22.add(p211);
    pixelList2.add(rowPixeList21);
    pixelList2.add(rowPixeList22);
  }

  @Test
  public void testLoad() {
    this.model1.load("colorImage", pixelList1);
    // tests that the height of the 2d array is 3
    assertEquals(3, this.model1.getImage("colorImage").size());
    // tests that the width of the 2d array is 4
    assertEquals(4, this.model1.getImage("colorImage").get(0).size());
    assertEquals(4, this.model1.getImage("colorImage").get(1).size());
    assertEquals(4, this.model1.getImage("colorImage").get(2).size());
    this.model1.load("colorImage2", pixelList2);
    assertEquals(2, this.model1.getImage("colorImage2").size());
    assertEquals(2, this.model1.getImage("colorImage2").get(0).size());
  }

  @Test
  public void testExceptionWhenPhotoNotLoaded() {
    this.model1.load("colorImage", pixelList1);
    try {
      this.model1.lumaComponent("colorImageLuma", "colorImageLuma2");
      fail("Operation performed successfully.");
    } catch (IllegalArgumentException e) {
      assertEquals("File not found.", e.getMessage());
    }
    // tests that file names are case-sensitive
    try {
      this.model1.lumaComponent("colorimage", "colorimage2");
      fail("Operation performed successfully.");
    } catch (IllegalArgumentException e) {
      assertEquals("File not found.", e.getMessage());
    }
  }


  @Test
  public void testRedComponent() {
    this.model1.load("colorImage", pixelList1);
    this.model1.redComponent("colorImage", "colorImageRed");
    assertEquals(3, this.model1.getImage("colorImageRed").size());
    assertEquals(4, this.model1.getImage("colorImageRed").get(0).size());
    // tests that the dest image changed red comps
    assertEquals(123, this.model1.getImage("colorImageRed").
            get(0).get(0).getColorComp(0));
    assertEquals(123, this.model1.getImage("colorImageRed").
            get(0).get(0).getColorComp(2));
    assertEquals(123, this.model1.getImage("colorImageRed").
            get(0).get(0).getColorComp(1));
    assertEquals(0, this.model1.getImage("colorImageRed").
            get(1).get(2).getColorComp(0));
    assertEquals(0, this.model1.getImage("colorImageRed").
            get(1).get(2).getColorComp(2));
    assertEquals(0, this.model1.getImage("colorImageRed").
            get(1).get(2).getColorComp(1));
    // tests that the org image remained the same
    assertEquals(123, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(3, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(74, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(0, this.model1.getImage("colorImage").
            get(1).get(2).getColorComp(0));
    assertEquals(154, this.model1.getImage("colorImage").
            get(1).get(2).getColorComp(2));
    assertEquals(17, this.model1.getImage("colorImage").
            get(1).get(2).getColorComp(1));
  }

  @Test
  public void testGreenComponent() {
    this.model1.load("colorImage", pixelList1);
    this.model1.greenComponent("colorImage", "colorImageGreen");
    assertEquals(3, this.model1.getImage("colorImageGreen").size());
    assertEquals(4, this.model1.getImage("colorImageGreen").
            get(1).size());
    assertEquals(74, this.model1.getImage("colorImageGreen").
            get(2).get(1).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImageGreen").
            get(2).get(1).getColorComp(2));
    assertEquals(74, this.model1.getImage("colorImageGreen").
            get(2).get(1).getColorComp(1));
    assertEquals(252, this.model1.getImage("colorImageGreen").
            get(2).get(3).getColorComp(0));
    assertEquals(252, this.model1.getImage("colorImageGreen").
            get(2).get(3).getColorComp(2));
    assertEquals(252, this.model1.getImage("colorImageGreen").
            get(2).get(3).getColorComp(1));
    // tests that the original photo remains unchanged
    assertEquals(123, this.model1.getImage("colorImage").
            get(2).get(1).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImage").
            get(2).get(1).getColorComp(1));
    assertEquals(3, this.model1.getImage("colorImage").
            get(2).get(1).getColorComp(2));

  }

  @Test
  public void testBlueComponent() {
    this.model1.load("colorImage", pixelList1);
    this.model1.blueComponent("colorImage", "colorImageBlue");
    assertEquals(3, this.model1.getImage("colorImageBlue").
            size());
    assertEquals(4, this.model1.getImage("colorImageBlue").
            get(1).size());
    assertEquals(2, this.model1.getImage("colorImageBlue").
            get(1).get(1).getColorComp(0));
    assertEquals(2, this.model1.getImage("colorImageBlue").
            get(1).get(1).getColorComp(2));
    assertEquals(2, this.model1.getImage("colorImageBlue").
            get(1).get(1).getColorComp(1));
    assertEquals(200, this.model1.getImage("colorImageBlue").
            get(1).get(0).getColorComp(0));
    assertEquals(200, this.model1.getImage("colorImageBlue").
            get(1).get(0).getColorComp(2));
    assertEquals(200, this.model1.getImage("colorImageBlue").
            get(1).get(0).getColorComp(1));
    // tests that the original photo remains unchanged
    assertEquals(76, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(1));
    assertEquals(200, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testValueComponent() {
    this.model1.load("colorImage", pixelList1);
    this.model1.valueComponent("colorImage", "colorImageValue");
    assertEquals(3, this.model1.getImage("colorImageValue").size());
    assertEquals(4, this.model1.getImage("colorImageValue").get(2).size());
    // tests for when blue is max
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(0).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(0).get(1).getColorComp(1));
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(0).get(1).getColorComp(2));
    //  tests for when red is max
    assertEquals(172, this.model1.getImage("colorImageValue").
            get(1).get(3).getColorComp(0));
    assertEquals(172, this.model1.getImage("colorImageValue").
            get(1).get(3).getColorComp(2));
    assertEquals(172, this.model1.getImage("colorImageValue").
            get(1).get(3).getColorComp(1));
    // test for when green is max
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(1).get(1).getColorComp(2));
    assertEquals(254, this.model1.getImage("colorImageValue").
            get(1).get(1).getColorComp(1));
    // tests that original image remained the same
    assertEquals(64, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(0));
    assertEquals(0, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(1));
    assertEquals(254, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(2));
    assertEquals(172, this.model1.getImage("colorImage").
            get(1).get(3).getColorComp(0));
    assertEquals(36, this.model1.getImage("colorImage").
            get(1).get(3).getColorComp(1));
    assertEquals(0, this.model1.getImage("colorImage").
            get(1).get(3).getColorComp(2));
    assertEquals(253, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
  }

  @Test
  public void testGetIntensityComp() {
    this.model1.load("colorImage", pixelList1);
    this.model1.intensityComponent("colorImage", "colorImageIntensity");
    assertEquals(94, this.model1.getImage("colorImageIntensity").
            get(2).get(3).getColorComp(0));
    assertEquals(94, this.model1.getImage("colorImageIntensity").
            get(2).get(3).getColorComp(1));
    assertEquals(94, this.model1.getImage("colorImageIntensity").
            get(2).get(3).getColorComp(2));
    // tests the og photo remains unchanged
    assertEquals(15, this.model1.getImage("colorImage").
            get(2).get(3).getColorComp(0));
    assertEquals(252, this.model1.getImage("colorImage").
            get(2).get(3).getColorComp(1));
    assertEquals(16, this.model1.getImage("colorImage").
            get(2).get(3).getColorComp(2));
  }

  @Test
  public void testGetLumaComp() {
    this.model1.load("colorImage", pixelList1);
    this.model1.lumaComponent("colorImage", "colorImageLuma");
    assertEquals(198, this.model1.getImage("colorImageLuma").
            get(0).get(2).getColorComp(0));
    assertEquals(198, this.model1.getImage("colorImageLuma").
            get(0).get(2).getColorComp(1));
    assertEquals(198, this.model1.getImage("colorImageLuma").
            get(0).get(2).getColorComp(2));
    // tests the og photo remains unchanged
    assertEquals(52, this.model1.getImage("colorImage").
            get(0).get(2).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImage").
            get(0).get(2).getColorComp(1));
    assertEquals(64, this.model1.getImage("colorImage").
            get(0).get(2).getColorComp(2));

  }

  @Test
  public void testVerticalFlip() {
    this.model1.load("colorImage", pixelList1);
    this.model1.verticalFlip("colorImage", "colorImageVertical");
    // tests that top side is flipped
    assertEquals(123, model1.getImage("colorImageVertical").
            get(2).get(0).getColorComp(0));
    assertEquals(74, model1.getImage("colorImageVertical").
            get(2).get(0).getColorComp(1));
    assertEquals(3, model1.getImage("colorImageVertical").
            get(2).get(0).getColorComp(2));
    // tests that the middle remains the same because it's 3 pixels high
    assertEquals(253, model1.getImage("colorImageVertical").
            get(1).get(1).getColorComp(0));
    assertEquals(254, model1.getImage("colorImageVertical").
            get(1).get(1).getColorComp(1));
    assertEquals(2, model1.getImage("colorImageVertical").
            get(1).get(1).getColorComp(2));
    // tests that the bottom row flips to the top
    assertEquals(0, model1.getImage("colorImageVertical").
            get(2).get(3).getColorComp(0));
    assertEquals(12, model1.getImage("colorImageVertical").
            get(2).get(3).getColorComp(1));
    assertEquals(253, model1.getImage("colorImageVertical").
            get(2).get(3).getColorComp(2));
    // tests that the original image remains un flipped
    assertEquals(199, model1.getImage("colorImage").
            get(2).get(0).getColorComp(0));
    assertEquals(67, model1.getImage("colorImage").
            get(2).get(0).getColorComp(1));
    assertEquals(187, model1.getImage("colorImage").
            get(2).get(0).getColorComp(2));
    assertEquals(123, model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(74, model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(3, model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));

  }

  @Test
  public void testTwoVerticalFlips() {
    this.model1.load("colorImage", pixelList1);
    this.model1.verticalFlip("colorImage", "colorImageV");
    this.model1.verticalFlip("colorImageV", "colorImageV2");
    // tests that the original photo is the same as the one veritically flipped twice
    assertEquals(76, model1.getImage("colorImageV2").
            get(1).get(0).getColorComp(0));
    assertEquals(192, model1.getImage("colorImageV2").
            get(1).get(0).getColorComp(1));
    assertEquals(200, model1.getImage("colorImageV2").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testHorizontalFlip() {
    this.model1.load("colorImage", pixelList1);
    this.model1.horizontalFlip("colorImage", "colorImageHorizontal");
    // tests that right side is flipped
    assertEquals(172, model1.getImage("colorImageHorizontal").
            get(1).get(0).getColorComp(0));
    assertEquals(36, model1.getImage("colorImageHorizontal").
            get(1).get(0).getColorComp(1));
    assertEquals(0, model1.getImage("colorImageHorizontal").
            get(1).get(0).getColorComp(2));

    // tests that the 2nd middle row is flipepd
    assertEquals(0, model1.getImage("colorImageHorizontal").
            get(1).get(1).getColorComp(0));
    assertEquals(17, model1.getImage("colorImageHorizontal").
            get(1).get(1).getColorComp(1));
    assertEquals(154, model1.getImage("colorImageHorizontal").
            get(1).get(1).getColorComp(2));

    // tests that the left row flips to the right
    assertEquals(199, model1.getImage("colorImageHorizontal").
            get(2).get(3).getColorComp(0));
    assertEquals(67, model1.getImage("colorImageHorizontal").
            get(2).get(3).getColorComp(1));
    assertEquals(187, model1.getImage("colorImageHorizontal").
            get(2).get(3).getColorComp(2));

    // tests that the original image remains un flipped
    assertEquals(253, model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
    assertEquals(123, model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(74, model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(3, model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));

  }

  @Test
  public void testHorizontalFlipTwice() {
    this.model1.load("colorImage", pixelList1);
    this.model1.horizontalFlip("colorImage", "colorImageH");
    this.model1.horizontalFlip("colorImageH", "colorImageH2");
    assertEquals(0, model1.getImage("colorImageH2").
            get(0).get(3).getColorComp(0));
    assertEquals(12, model1.getImage("colorImageH2").
            get(0).get(3).getColorComp(1));
    assertEquals(253, model1.getImage("colorImageH2").
            get(0).get(3).getColorComp(2));
  }

  @Test
  public void testBrightenPositive() {
    this.model1.load("colorImage", pixelList1);
    this.model1.brighten(100, "colorImage", "colorImageBright");
    // tests that the values go up by increment supplied
    assertEquals(100, model1.getImage("colorImageBright").
            get(1).get(2).getColorComp(0));
    assertEquals(117, model1.getImage("colorImageBright").
            get(1).get(2).getColorComp(1));
    assertEquals(254, model1.getImage("colorImageBright").
            get(1).get(2).getColorComp(2));
    // tests that 255 remains the max value
    assertEquals(255, model1.getImage("colorImageBright").
            get(1).get(1).getColorComp(0));
    assertEquals(255, model1.getImage("colorImageBright").
            get(1).get(0).getColorComp(1));
    assertEquals(255, model1.getImage("colorImageBright").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testBrightenNegative() {
    this.model1.load("colorImage", pixelList1);
    this.model1.brighten(-57, "colorImage", "colorImageDim");
    // tests that the values go down by an increment supplied
    assertEquals(142, model1.getImage("colorImageDim").
            get(2).get(0).getColorComp(0));
    assertEquals(10, model1.getImage("colorImageDim").
            get(2).get(0).getColorComp(1));
    assertEquals(130, model1.getImage("colorImageDim").
            get(2).get(0).getColorComp(2));
    // tests that 0 remains the min value
    assertEquals(0, model1.getImage("colorImageDim").
            get(0).get(3).getColorComp(0));
    assertEquals(0, model1.getImage("colorImageDim").
            get(1).get(2).getColorComp(1));
    assertEquals(0, model1.getImage("colorImageDim").
            get(2).get(1).getColorComp(2));
  }

  @Test
  public void testBrightenOriginal() {
    this.model1.load("colorImage", pixelList1);
    this.model1.brighten(19, "colorImage", "colorImageB");
    assertEquals(172, model1.getImage("colorImage").
            get(1).get(3).getColorComp(0));
    assertEquals(36, model1.getImage("colorImage").
            get(1).get(3).getColorComp(1));
    assertEquals(0, model1.getImage("colorImage").
            get(1).get(3).getColorComp(2));
  }

  @Test
  public void testGetImage() {
    this.model1.load("colorImage", pixelList1);
    assertEquals(0, this.model1.getImage("colorImage").get(0).get(0).getPosX());
    assertEquals(0, this.model1.getImage("colorImage").get(0).get(0).getPosY());
    assertEquals(123, this.model1.getImage("colorImage")
            .get(0).get(0).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImage")
            .get(0).get(0).getColorComp(1));
    assertEquals(3, this.model1.getImage("colorImage")
            .get(0).get(0).getColorComp(2));
    // checks that a mutation in the colorImage doesn't affect the actual image because
    // it returns a copy
    this.model1.getImage("colorImage").get(1).get(0).setColors(10);
    assertEquals(76, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(1));
    assertEquals(200, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testMultipleOps() {
    this.model1.load("colorImage", pixelList1);
    this.model1.brighten(30, "colorImage", "colorImageBright");
    this.model1.brighten(-20, "colorImageBright",
            "colorImageDim");
    this.model1.redComponent("colorImageDim",
            "colorImageFinalRed");
    assertEquals(86, this.model1.getImage("colorImageFinalRed").
            get(1).get(0).getColorComp(0));
    assertEquals(86, this.model1.getImage("colorImageFinalRed").
            get(1).get(0).getColorComp(1));
    assertEquals(86, this.model1.getImage("colorImageFinalRed").
            get(1).get(0).getColorComp(2));
    // test that og photo remains unchanged
    assertEquals(76, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(0));
    assertEquals(192, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(1));
    assertEquals(200, this.model1.getImage("colorImage").
            get(1).get(0).getColorComp(2));
  }

  @Test
  public void testWhenLoadedMoreFiles() {
    this.model1.load("colorImage", pixelList1);
    this.model1.load("colorImage2", pixelList2);
    this.model1.greenComponent("colorImage", "colorImageGreen");
    this.model1.blueComponent("colorImage2", "colorImageBlue2");
    assertEquals(255, this.model1.getImage("colorImageGreen").
            get(0).get(2).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageGreen").
            get(0).get(2).getColorComp(1));
    assertEquals(255, this.model1.getImage("colorImageGreen").
            get(0).get(2).getColorComp(2));

    assertEquals(200, this.model1.getImage("colorImageBlue2").
            get(0).get(1).getColorComp(0));
    assertEquals(200, this.model1.getImage("colorImageBlue2").
            get(0).get(1).getColorComp(1));
    assertEquals(200, this.model1.getImage("colorImageBlue2").
            get(0).get(1).getColorComp(2));
  }

  // tests that if two files are loaded under the same name, the first loaded one becomes
  // overrided
  @Test
  public void testLoadSameName() {
    this.model1.load("colorImage", pixelList1);
    this.model1.load("colorImage", pixelList2);
    assertEquals(200, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(2));
    assertEquals(162, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(1));
    assertEquals(10, this.model1.getImage("colorImage").
            get(0).get(1).getColorComp(0));
  }


}