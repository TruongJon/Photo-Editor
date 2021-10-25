import static org.junit.Assert.assertEquals;

import model.Channel;
import model.DefaultKernel;
import model.IPixel;
import model.KernelMatrix;
import model.Pixel;
import model.Posn;
import org.junit.Test;

/**
 * To test the methods within the Pixel class.
 */
public class PixelTest {

  //to test the Pixel constructor.
  Pixel p1 = new Pixel(new Posn(0, 0), 255, 0, 0);
  Pixel p2 = new Pixel(new Posn(1, 0), 0, 255, 0);
  Pixel p3 = new Pixel(new Posn(2, 0), 0, 0, 255);
  Pixel p4 = new Pixel(new Posn(0, 1), 0, 0, 0);
  Pixel p5 = new Pixel(new Posn(1, 1), 0, 0, 0);
  Pixel p6 = new Pixel(new Posn(2, 1), 0, 0, 0);
  Pixel p7 = new Pixel(new Posn(0, 2), 0, 0, 255);
  Pixel p8 = new Pixel(new Posn(1, 2), 0, 255, 0);
  Pixel p9 = new Pixel(new Posn(2, 2), 255, 0, 0);

  //to test clamping in the Pixel constructor.
  @Test
  public void testPixelClamping() {
    Pixel lowR = new Pixel(new Posn(0, 0), -1, 0, 0);
    Pixel thresholdR = new Pixel(new Posn(0, 0), 255, 0, 0);
    Pixel highR = new Pixel(new Posn(0, 0), 256, 0, 0);

    assertEquals(0, lowR.getR());
    assertEquals(255, thresholdR.getR());
    assertEquals(255, highR.getR());

    Pixel lowG = new Pixel(new Posn(0, 0), 0, -1, 0);
    Pixel thresholdG = new Pixel(new Posn(0, 0), 0, 255, 0);
    Pixel highG = new Pixel(new Posn(0, 0), 0, 256, 0);

    assertEquals(0, lowG.getG());
    assertEquals(255, thresholdG.getG());
    assertEquals(255, highG.getG());

    Pixel lowB = new Pixel(new Posn(0, 0), 0, 0, -1);
    Pixel thresholdB = new Pixel(new Posn(0, 0), 0, 0, 255);
    Pixel highB = new Pixel(new Posn(0, 0), 0, 0, 256);

    assertEquals(0, lowB.getB());
    assertEquals(255, thresholdB.getB());
    assertEquals(255, highB.getB());

    Pixel multiClamp1 = new Pixel(new Posn(0, 0), -1, 0, 256);
    Pixel multiClamp2 = new Pixel(new Posn(0, 0), 0, -1, 256);
    Pixel multiClamp3 = new Pixel(new Posn(0, 0), -1, 256, 0);

    assertEquals(0, multiClamp1.getR());
    assertEquals(0, multiClamp1.getG());
    assertEquals(255, multiClamp1.getB());

    assertEquals(0, multiClamp2.getR());
    assertEquals(0, multiClamp2.getG());
    assertEquals(255, multiClamp2.getB());

    assertEquals(0, multiClamp3.getR());
    assertEquals(255, multiClamp3.getG());
    assertEquals(0, multiClamp3.getB());
  }

  //to test the applyKernelFilter with the Blur filter.
  @Test
  public void testApplyKernelFilterBlur() {
    KernelMatrix blur = DefaultKernel.BLUR.getDefaultKernel();

    KernelMatrix neighborPixelsR = new KernelMatrix(3);
    neighborPixelsR.setValue(p1.getPosition(), p1.getR());
    neighborPixelsR.setValue(p2.getPosition(), p2.getR());
    neighborPixelsR.setValue(p3.getPosition(), p3.getR());
    neighborPixelsR.setValue(p4.getPosition(), p4.getR());
    neighborPixelsR.setValue(p5.getPosition(), p5.getR());
    neighborPixelsR.setValue(p6.getPosition(), p6.getR());
    neighborPixelsR.setValue(p7.getPosition(), p7.getR());
    neighborPixelsR.setValue(p8.getPosition(), p8.getR());
    neighborPixelsR.setValue(p9.getPosition(), p9.getR());

    IPixel blurMiddlePixelR = p5.applyKernelFilter(blur, neighborPixelsR, Channel.RED);
    assertEquals(1, blurMiddlePixelR.getPosition().getX());
    assertEquals(1, blurMiddlePixelR.getPosition().getY());
    assertEquals(32, blurMiddlePixelR.getR());
    assertEquals(0, blurMiddlePixelR.getG());
    assertEquals(0, blurMiddlePixelR.getB());

    KernelMatrix neighborPixelsG = new KernelMatrix(3);
    neighborPixelsG.setValue(p1.getPosition(), p1.getG());
    neighborPixelsG.setValue(p2.getPosition(), p2.getG());
    neighborPixelsG.setValue(p3.getPosition(), p3.getG());
    neighborPixelsG.setValue(p4.getPosition(), p4.getG());
    neighborPixelsG.setValue(p5.getPosition(), p5.getG());
    neighborPixelsG.setValue(p6.getPosition(), p6.getG());
    neighborPixelsG.setValue(p7.getPosition(), p7.getG());
    neighborPixelsG.setValue(p8.getPosition(), p8.getG());
    neighborPixelsG.setValue(p9.getPosition(), p9.getG());

    IPixel blurMiddlePixelG = p5.applyKernelFilter(blur, neighborPixelsG, Channel.GREEN);
    assertEquals(1, blurMiddlePixelG.getPosition().getX());
    assertEquals(1, blurMiddlePixelG.getPosition().getY());
    assertEquals(0, blurMiddlePixelG.getR());
    assertEquals(64, blurMiddlePixelG.getG());
    assertEquals(0, blurMiddlePixelG.getB());

    KernelMatrix neighborPixelsB = new KernelMatrix(3);
    neighborPixelsB.setValue(p1.getPosition(), p1.getB());
    neighborPixelsB.setValue(p2.getPosition(), p2.getB());
    neighborPixelsB.setValue(p3.getPosition(), p3.getB());
    neighborPixelsB.setValue(p4.getPosition(), p4.getB());
    neighborPixelsB.setValue(p5.getPosition(), p5.getB());
    neighborPixelsB.setValue(p6.getPosition(), p6.getB());
    neighborPixelsB.setValue(p7.getPosition(), p7.getB());
    neighborPixelsB.setValue(p8.getPosition(), p8.getB());
    neighborPixelsB.setValue(p9.getPosition(), p9.getB());

    IPixel blurMiddlePixelB = p5.applyKernelFilter(blur, neighborPixelsB, Channel.BLUE);
    assertEquals(1, blurMiddlePixelB.getPosition().getX());
    assertEquals(1, blurMiddlePixelB.getPosition().getY());
    assertEquals(0, blurMiddlePixelB.getR());
    assertEquals(0, blurMiddlePixelB.getG());
    assertEquals(32, blurMiddlePixelB.getB());
  }

  //to test the applyKernelFilter with the Sharpen filter.
  @Test
  public void testApplyKernelFilterSharpen() {
    KernelMatrix sharpen = DefaultKernel.SHARPEN.getDefaultKernel();

    KernelMatrix neighborPixelsR = new KernelMatrix(5);

    neighborPixelsR.setValue(new Posn(0, 0), 0);
    neighborPixelsR.setValue(new Posn(1, 0), 0);
    neighborPixelsR.setValue(new Posn(2, 0), 0);
    neighborPixelsR.setValue(new Posn(3, 0), 0);
    neighborPixelsR.setValue(new Posn(4, 0), 0);
    neighborPixelsR.setValue(new Posn(0, 1), 0);
    neighborPixelsR.setValue(new Posn(1, 1), p1.getR());
    neighborPixelsR.setValue(new Posn(2, 1), p2.getR());
    neighborPixelsR.setValue(new Posn(3, 1), p3.getR());
    neighborPixelsR.setValue(new Posn(4, 1), 0);
    neighborPixelsR.setValue(new Posn(0, 2), 0);
    neighborPixelsR.setValue(new Posn(1, 2), p4.getR());
    neighborPixelsR.setValue(new Posn(2, 2), p5.getR());
    neighborPixelsR.setValue(new Posn(3, 2), p6.getR());
    neighborPixelsR.setValue(new Posn(4, 2), 0);
    neighborPixelsR.setValue(new Posn(0, 3), 0);
    neighborPixelsR.setValue(new Posn(1, 3), p7.getR());
    neighborPixelsR.setValue(new Posn(2, 3), p8.getR());
    neighborPixelsR.setValue(new Posn(3, 3), p9.getR());
    neighborPixelsR.setValue(new Posn(4, 3), 0);
    neighborPixelsR.setValue(new Posn(0, 4), 0);
    neighborPixelsR.setValue(new Posn(1, 4), 0);
    neighborPixelsR.setValue(new Posn(2, 4), 0);
    neighborPixelsR.setValue(new Posn(3, 4), 0);
    neighborPixelsR.setValue(new Posn(4, 4), 0);

    IPixel sharpenMiddlePixelR = p5.applyKernelFilter(sharpen, neighborPixelsR, Channel.RED);
    assertEquals(1, sharpenMiddlePixelR.getPosition().getX());
    assertEquals(1, sharpenMiddlePixelR.getPosition().getY());
    assertEquals(128, sharpenMiddlePixelR.getR());
    assertEquals(0, sharpenMiddlePixelR.getG());
    assertEquals(0, sharpenMiddlePixelR.getB());

    KernelMatrix neighborPixelsG = new KernelMatrix(5);
    neighborPixelsG.setValue(new Posn(0, 0), 0);
    neighborPixelsG.setValue(new Posn(1, 0), 0);
    neighborPixelsG.setValue(new Posn(2, 0), 0);
    neighborPixelsG.setValue(new Posn(3, 0), 0);
    neighborPixelsG.setValue(new Posn(4, 0), 0);
    neighborPixelsG.setValue(new Posn(0, 1), 0);
    neighborPixelsG.setValue(new Posn(1, 1), p1.getG());
    neighborPixelsG.setValue(new Posn(2, 1), p2.getG());
    neighborPixelsG.setValue(new Posn(3, 1), p3.getG());
    neighborPixelsG.setValue(new Posn(4, 1), 0);
    neighborPixelsG.setValue(new Posn(0, 2), 0);
    neighborPixelsG.setValue(new Posn(1, 2), p4.getG());
    neighborPixelsG.setValue(new Posn(2, 2), p5.getG());
    neighborPixelsG.setValue(new Posn(3, 2), p6.getG());
    neighborPixelsG.setValue(new Posn(4, 2), 0);
    neighborPixelsG.setValue(new Posn(0, 3), 0);
    neighborPixelsG.setValue(new Posn(1, 3), p7.getG());
    neighborPixelsG.setValue(new Posn(2, 3), p8.getG());
    neighborPixelsG.setValue(new Posn(3, 3), p9.getG());
    neighborPixelsG.setValue(new Posn(4, 3), 0);
    neighborPixelsG.setValue(new Posn(0, 4), 0);
    neighborPixelsG.setValue(new Posn(1, 4), 0);
    neighborPixelsG.setValue(new Posn(2, 4), 0);
    neighborPixelsG.setValue(new Posn(3, 4), 0);
    neighborPixelsG.setValue(new Posn(4, 4), 0);

    IPixel sharpenMiddlePixelG = p5.applyKernelFilter(sharpen, neighborPixelsG, Channel.GREEN);
    assertEquals(1, sharpenMiddlePixelG.getPosition().getX());
    assertEquals(1, sharpenMiddlePixelG.getPosition().getY());
    assertEquals(0, sharpenMiddlePixelG.getR());
    assertEquals(128, sharpenMiddlePixelG.getG());
    assertEquals(0, sharpenMiddlePixelG.getB());

    KernelMatrix neighborPixelsB = new KernelMatrix(5);
    neighborPixelsB.setValue(new Posn(0, 0), 0);
    neighborPixelsB.setValue(new Posn(1, 0), 0);
    neighborPixelsB.setValue(new Posn(2, 0), 0);
    neighborPixelsB.setValue(new Posn(3, 0), 0);
    neighborPixelsB.setValue(new Posn(4, 0), 0);
    neighborPixelsB.setValue(new Posn(0, 1), 0);
    neighborPixelsB.setValue(new Posn(1, 1), p1.getB());
    neighborPixelsB.setValue(new Posn(2, 1), p2.getB());
    neighborPixelsB.setValue(new Posn(3, 1), p3.getB());
    neighborPixelsB.setValue(new Posn(4, 1), 0);
    neighborPixelsB.setValue(new Posn(0, 2), 0);
    neighborPixelsB.setValue(new Posn(1, 2), p4.getB());
    neighborPixelsB.setValue(new Posn(2, 2), p5.getB());
    neighborPixelsB.setValue(new Posn(3, 2), p6.getB());
    neighborPixelsB.setValue(new Posn(4, 2), 0);
    neighborPixelsB.setValue(new Posn(0, 3), 0);
    neighborPixelsB.setValue(new Posn(1, 3), p7.getB());
    neighborPixelsB.setValue(new Posn(2, 3), p8.getB());
    neighborPixelsB.setValue(new Posn(3, 3), p9.getB());
    neighborPixelsB.setValue(new Posn(4, 3), 0);
    neighborPixelsB.setValue(new Posn(0, 4), 0);
    neighborPixelsB.setValue(new Posn(1, 4), 0);
    neighborPixelsB.setValue(new Posn(2, 4), 0);
    neighborPixelsB.setValue(new Posn(3, 4), 0);
    neighborPixelsB.setValue(new Posn(4, 4), 0);

    IPixel sharpenMiddlePixelB = p5.applyKernelFilter(sharpen, neighborPixelsB, Channel.BLUE);
    assertEquals(1, sharpenMiddlePixelB.getPosition().getX());
    assertEquals(1, sharpenMiddlePixelB.getPosition().getY());
    assertEquals(0, sharpenMiddlePixelB.getR());
    assertEquals(0, sharpenMiddlePixelB.getG());
    assertEquals(128, sharpenMiddlePixelB.getB());
  }

  //to test the applyKernelColor method with the Greyscale filter
  @Test
  public void testApplyKernelGreyscale() {
    KernelMatrix greyscale = DefaultKernel.GREYSCALE.getDefaultKernel();
    IPixel newPixel = new Pixel(new Posn(0, 0), 30, 50, 70);
    IPixel greyscalePixel = newPixel.applyKernelColor(greyscale);

    //(int) (Math.round((30 * 0.2126) + (50 * 0.7152) + (70 * 0.0722)) = 47
    assertEquals(47, greyscalePixel.getR());
    //(int) (Math.round((30 * 0.2126) + (50 * 0.7152) + (70 * 0.0722)) = 47
    assertEquals(47, greyscalePixel.getG());
    //(int) (Math.round((30 * 0.2126) + (50 * 0.7152) + (70 * 0.0722)) = 47
    assertEquals(47, greyscalePixel.getB());
  }

  //to test the applyKernelColor method with the Sepia filter
  @Test
  public void testApplyKernelSepia() {
    KernelMatrix sepia = DefaultKernel.SEPIA.getDefaultKernel();
    IPixel newPixel = new Pixel(new Posn(0, 0), 30, 50, 70);
    IPixel sepiaPixel = newPixel.applyKernelColor(sepia);

    //(int) (Math.round((30 * 0.393) + (50 * 0.769) + (70 * 0.189)) = 63
    assertEquals(63, sepiaPixel.getR());
    //(int) (Math.round((30 * 0.349) + (50 * 0.686) + (70 * 0.168)) = 57
    assertEquals(57, sepiaPixel.getG());
    //(int) (Math.round((30 * 0.272) + (50 * 0.534) + (70 * 0.131)) = 44
    assertEquals(44, sepiaPixel.getB());
  }


  //to test the getPosition method in the Pixel class.
  @Test
  public void testGetPosition() {
    assertEquals(0, p1.getPosition().getX());
    assertEquals(0, p1.getPosition().getY());
    assertEquals(2, p9.getPosition().getX());
    assertEquals(2, p9.getPosition().getY());
  }

  //to test the getR method in the Pixel class.
  @Test
  public void testGetR() {
    assertEquals(255, p1.getR());
    assertEquals(0, p5.getR());
    assertEquals(255, p9.getR());
  }

  //to test the getG method in the Pixel class.
  @Test
  public void testGetG() {
    assertEquals(255, p2.getG());
    assertEquals(0, p5.getG());
    assertEquals(255, p8.getG());
  }

  //to test the getB method in the Pixel class.
  @Test
  public void testGetB() {
    assertEquals(255, p3.getB());
    assertEquals(0, p5.getB());
    assertEquals(255, p7.getB());
  }
}
