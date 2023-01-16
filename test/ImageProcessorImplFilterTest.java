import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.ImageProcessorImplFilter;
import model.Pixel;
import model.PixelRGB;

import static org.junit.Assert.assertEquals;

/**
 * Represents the testing class for the ImageProcessorImplFilter class.
 */
public class ImageProcessorImplFilterTest {
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
  ImageProcessorImplFilter model1;

  List<List<Pixel>> pixelList2;
  Pixel p200;
  Pixel p201;
  Pixel p202;
  Pixel p203;
  List<Pixel> rowPixeList21;
  Pixel p210;
  Pixel p211;
  Pixel p212;
  Pixel p213;
  Pixel p220;
  Pixel p221;
  Pixel p222;
  Pixel p223;
  List<Pixel> rowPixeList22;
  List<Pixel> rowPixeList23;
  ImageProcessorImplFilter model2;

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
    this.model1 = new ImageProcessorImplFilter();



    this.pixelList2 = new ArrayList<>();
    this.p200 = new PixelRGB(0, 0, 0, 0, 0);
    this.p201 = new PixelRGB(0, 1, 0, 0, 0);
    this.p202 = new PixelRGB(0, 1, 52, 255, 64);
    this.p203 = new PixelRGB(0, 1, 0, 12, 253);
    this.rowPixeList21 = new ArrayList<>();
    rowPixeList21.add(p200);
    rowPixeList21.add(p201);
    rowPixeList21.add(p202);
    rowPixeList21.add(p203);
    this.p210 = new PixelRGB(1, 0, 0, 0, 0);
    this.p211 = new PixelRGB(1, 1, 0, 0, 0);
    this.p212 = new PixelRGB(1, 1, 0, 17, 154);
    this.p213 = new PixelRGB(1, 1, 172, 36, 0);
    this.rowPixeList22 = new ArrayList<>();
    rowPixeList22.add(p210);
    rowPixeList22.add(p211);
    rowPixeList22.add(p212);
    rowPixeList22.add(p213);
    this.p220 = new PixelRGB(1, 0, 0, 0, 0);
    this.p221 = new PixelRGB(1, 1, 0, 0, 0);
    this.p222 = new PixelRGB(1, 1, 255, 4, 0);
    this.p223 = new PixelRGB(1, 1, 15, 252, 16);
    rowPixeList23 = new ArrayList<>();
    rowPixeList23.add(p220);
    rowPixeList23.add(p221);
    rowPixeList23.add(p222);
    rowPixeList23.add(p223);
    pixelList2.add(rowPixeList21);
    pixelList2.add(rowPixeList22);
    pixelList2.add(rowPixeList23);

    this.model2 = new ImageProcessorImplFilter();
  }

  @Test
  public void testLoad() {
    this.model1.load("colorImage", pixelList1);
    assertEquals(3, this.model1.getImage("colorImage").size());
    assertEquals(4, this.model1.getImage("colorImage").get(0).size());
    assertEquals(123, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(3, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
  }

  @Test
  public void testRedComp() {
    this.model1.load("colorImage", pixelList1);
    this.model1.redComponent("colorImage", "colorImageR");
    assertEquals(253, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
    assertEquals(253, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(0));
    assertEquals(253, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(1));
    assertEquals(253, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(2));
  }

  @Test
  public void testLumaComp() {
    this.model1.load("colorImage", pixelList1);
    this.model1.lumaComponent("colorImage", "colorImageR");
    assertEquals(253, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
    assertEquals(236, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(0));
    assertEquals(236, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(1));
    assertEquals(236, this.model1.getImage("colorImageR").
            get(1).get(1).getColorComp(2));
  }

  @Test
  public void testHorizontal() {
    this.model1.load("colorImage", pixelList1);
    this.model1.horizontalFlip("colorImage", "colorImageH");
    assertEquals(253, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
    assertEquals(0, this.model1.getImage("colorImageH").
            get(1).get(1).getColorComp(0));
    assertEquals(17, this.model1.getImage("colorImageH").
            get(1).get(1).getColorComp(1));
    assertEquals(154, this.model1.getImage("colorImageH").
            get(1).get(1).getColorComp(2));

  }

  @Test
  public void testSepia() {
    this.model1.load("colorImage", pixelList1);
    this.model1.sepia("colorImage", "colorImageSepia", "colorImage");
    assertEquals(123, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(3, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(106, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(0));
    assertEquals(95, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(1));
    assertEquals(73, this.model1.getImage("colorImageSepia").
            get(0).get(0).getColorComp(2));
    // test for clamping color components
    assertEquals(253, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(0));
    assertEquals(254, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(1));
    assertEquals(2, this.model1.getImage("colorImage").
            get(1).get(1).getColorComp(2));
    assertEquals(255, this.model1.getImage("colorImageSepia").
            get(1).get(1).getColorComp(0));
    assertEquals(255, this.model1.getImage("colorImageSepia").
            get(1).get(1).getColorComp(1));
    assertEquals(205, this.model1.getImage("colorImageSepia").
            get(1).get(1).getColorComp(2));
  }

  @Test
  public void testGreyScale() {
    this.model1.load("colorImage", pixelList1);
    this.model1.greyscale("colorImage", "colorImageG", "colorImage");
    assertEquals(123, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(0));
    assertEquals(74, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(1));
    assertEquals(3, this.model1.getImage("colorImage").
            get(0).get(0).getColorComp(2));
    assertEquals(79, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(0));
    assertEquals(79, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(1));
    assertEquals(79, this.model1.getImage("colorImageG").
            get(0).get(0).getColorComp(2));

  }

  @Test
  public void testBlur() {
    this.model1.load("image", pixelList1);
    this.model1.blur("image", "imageBlur", "image");
    assertEquals(64, model1.getImage("imageBlur").
            get(0).get(0).getColorComp(0));
    assertEquals(58, model1.getImage("imageBlur").
            get(0).get(0).getColorComp(1));
    assertEquals(58, model1.getImage("imageBlur").
            get(0).get(0).getColorComp(2));
    assertEquals(57, model1.getImage("imageBlur").
            get(2).get(3).getColorComp(0));
    assertEquals(69, model1.getImage("imageBlur").
            get(2).get(3).getColorComp(1));
    assertEquals(14, model1.getImage("imageBlur").
            get(2).get(3).getColorComp(2));
    assertEquals(pixelList1.get(0).get(0).getColorComp(0),
            model1.getImage("image").get(0).get(0).getColorComp(0));
    assertEquals(pixelList1.get(0).get(0).getColorComp(1),
            model1.getImage("image").get(0).get(0).getColorComp(1));
    assertEquals(pixelList1.get(0).get(0).getColorComp(2),
            model1.getImage("image").get(0).get(0).getColorComp(2));
    assertEquals(pixelList1.get(2).get(3).getColorComp(0),
            model1.getImage("image").get(2).get(3).getColorComp(0));
    assertEquals(pixelList1.get(2).get(3).getColorComp(1),
            model1.getImage("image").get(2).get(3).getColorComp(1));
    assertEquals(pixelList1.get(2).get(3).getColorComp(2),
            model1.getImage("image").get(2).get(3).getColorComp(2));
  }

  @Test
  public void testSharpen() {
    this.model1.load("image", pixelList1);
    this.model1.sharpen("image", "imageSharpen", "image");
    assertEquals(143, model1.getImage("imageSharpen").
            get(0).get(0).getColorComp(0));
    assertEquals(133, model1.getImage("imageSharpen").
            get(0).get(0).getColorComp(1));
    assertEquals(66, model1.getImage("imageSharpen").
            get(0).get(0).getColorComp(2));
    assertEquals(60, model1.getImage("imageSharpen").
            get(2).get(3).getColorComp(0));
    assertEquals(192, model1.getImage("imageSharpen").
            get(2).get(3).getColorComp(1));
    assertEquals(0, model1.getImage("imageSharpen").
            get(2).get(3).getColorComp(2));
    assertEquals(pixelList1.get(0).get(0).getColorComp(0),
            model1.getImage("image").get(0).get(0).getColorComp(0));
    assertEquals(pixelList1.get(0).get(0).getColorComp(1),
            model1.getImage("image").get(0).get(0).getColorComp(1));
    assertEquals(pixelList1.get(0).get(0).getColorComp(2),
            model1.getImage("image").get(0).get(0).getColorComp(2));
    assertEquals(pixelList1.get(2).get(3).getColorComp(0),
            model1.getImage("image").get(2).get(3).getColorComp(0));
    assertEquals(pixelList1.get(2).get(3).getColorComp(1),
            model1.getImage("image").get(2).get(3).getColorComp(1));
    assertEquals(pixelList1.get(2).get(3).getColorComp(2),
            model1.getImage("image").get(2).get(3).getColorComp(2));
  }

  @Test
  public void testHistogram() {
    this.model1.load("colorImage", this.pixelList1);
    assertEquals(2, this.model1.getHistogram("colorImage")[0][0]);
    assertEquals(1, this.model1.getHistogram("colorImage")[1][0]);
    assertEquals(2, this.model1.getHistogram("colorImage")[2][0]);
    this.model1.brighten(-50, "colorImage", "colorImageDark");
    assertEquals(3, this.model1.getHistogram("colorImageDark")[0][0]);
    assertEquals(5, this.model1.getHistogram("colorImageDark")[1][0]);
    assertEquals(6, this.model1.getHistogram("colorImageDark")[2][0]);

  }


  /*
    void partialImageManipulation(String imageName, String destImageName,
                                List<List<Pixel>> mask, String command);
   */
  @Test
  public void testPartialImage() {
    this.model1.load("colorImage", this.pixelList1);
    this.model1.partialImageManipulation("green", "colorImage",
            this.pixelList2, "colorImagePartial");
    List<List<Pixel>> destImage = model1.getImage("PartialImage");
    assertEquals(123, destImage.get(0).get(0).getColorComp(0));
    assertEquals(74, destImage.get(0).get(0).getColorComp(1));
    assertEquals(3, destImage.get(0).get(0).getColorComp(2));
    assertEquals(64, destImage.get(0).get(1).getColorComp(0));
    assertEquals(0, destImage.get(0).get(1).getColorComp(1));
    assertEquals(254, destImage.get(0).get(1).getColorComp(2));
  }

  @Test
  public void testRecombine() {
    this.model1.load("colorImage", this.pixelList1);
    this.model1.partialImageManipulation("green", "colorImage",
            this.pixelList2, "colorImagePartial");
    this.model1.recombine(pixelList1, model1.getImage("PartialImage"),
            "colorImagePartial", this.pixelList2);
    List<List<Pixel>> destImage = model1.getImage("colorImagePartial");
    assertEquals(123, destImage.get(0).get(0).getColorComp(0));
    assertEquals(74, destImage.get(0).get(0).getColorComp(1));
    assertEquals(3, destImage.get(0).get(0).getColorComp(2));
    assertEquals(64, destImage.get(0).get(1).getColorComp(0));
    assertEquals(0, destImage.get(0).get(1).getColorComp(1));
    assertEquals(254, destImage.get(0).get(1).getColorComp(2));
  }








}