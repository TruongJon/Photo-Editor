import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import model.Channel;
import model.ColorTransform;
import model.DefaultKernel;
import model.Filter;
import model.IImage;
import model.IPixel;
import model.ITransform;
import model.KernelMatrix;
import model.PPMImage;
import model.Pixel;
import model.Posn;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for PPMImage: Unit tests to ensure the accuracy of methods and constructors within the
 * PPMImage class.
 */
public class PPMImageTest {

  IImage checkerboard;
  ITransform filterObject;
  ITransform colorTransformObject;
  KernelMatrix blurKernel;
  KernelMatrix sharpKernel;
  KernelMatrix greyKernel;
  KernelMatrix sepiaKernel;
  IPixel[][] neighborsMatrix;
  KernelMatrix neighborsMatrixR;
  KernelMatrix neighborsMatrixG;
  KernelMatrix neighborsMatrixB;
  IImage neighborsImage;
  Pixel p1;
  Pixel p2;
  Pixel p3;
  Pixel p4;
  Pixel p5;
  Pixel p6;
  Pixel p7;
  Pixel p8;
  Pixel p9;

  @Before
  public void init() {
    checkerboard = new PPMImage(2, 2, Color.CYAN, Color.MAGENTA);
    filterObject = new Filter();
    colorTransformObject = new ColorTransform();
    blurKernel = DefaultKernel.BLUR.getDefaultKernel();
    sharpKernel = DefaultKernel.SHARPEN.getDefaultKernel();
    greyKernel = DefaultKernel.GREYSCALE.getDefaultKernel();
    sepiaKernel = DefaultKernel.SEPIA.getDefaultKernel();

    p1 = new Pixel(new Posn(0, 0), 212, 89, 83);
    p2 = new Pixel(new Posn(1, 0), 83, 25, 179);
    p3 = new Pixel(new Posn(2, 0), 90, 2, 243);
    p4 = new Pixel(new Posn(0, 1), 123, 146, 200);
    p5 = new Pixel(new Posn(1, 1), 8, 24, 77);
    p6 = new Pixel(new Posn(2, 1), 128, 1, 0);
    p7 = new Pixel(new Posn(0, 2), 241, 176, 162);
    p8 = new Pixel(new Posn(1, 2), 49, 71, 100);
    p9 = new Pixel(new Posn(2, 2), 150, 38, 0);

    neighborsMatrix = new IPixel[3][3];
    neighborsMatrix[0][0] = p1;
    neighborsMatrix[1][0] = p2;
    neighborsMatrix[2][0] = p3;
    neighborsMatrix[0][1] = p4;
    neighborsMatrix[1][1] = p5;
    neighborsMatrix[2][1] = p6;
    neighborsMatrix[0][2] = p7;
    neighborsMatrix[1][2] = p8;
    neighborsMatrix[2][2] = p9;

    neighborsImage = new PPMImage(neighborsMatrix);

    neighborsMatrixR = new KernelMatrix(3);
    neighborsMatrixG = new KernelMatrix(3);
    neighborsMatrixB = new KernelMatrix(3);

    neighborsMatrixR.setValue(p1.getPosition(), p1.getR());
    neighborsMatrixR.setValue(p2.getPosition(), p2.getR());
    neighborsMatrixR.setValue(p3.getPosition(), p3.getR());
    neighborsMatrixR.setValue(p4.getPosition(), p4.getR());
    neighborsMatrixR.setValue(p5.getPosition(), p5.getR());
    neighborsMatrixR.setValue(p6.getPosition(), p6.getR());
    neighborsMatrixR.setValue(p7.getPosition(), p7.getR());
    neighborsMatrixR.setValue(p8.getPosition(), p8.getR());
    neighborsMatrixR.setValue(p9.getPosition(), p9.getR());

    neighborsMatrixG.setValue(p1.getPosition(), p1.getG());
    neighborsMatrixG.setValue(p2.getPosition(), p2.getG());
    neighborsMatrixG.setValue(p3.getPosition(), p3.getG());
    neighborsMatrixG.setValue(p4.getPosition(), p4.getG());
    neighborsMatrixG.setValue(p5.getPosition(), p5.getG());
    neighborsMatrixG.setValue(p6.getPosition(), p6.getG());
    neighborsMatrixG.setValue(p7.getPosition(), p7.getG());
    neighborsMatrixG.setValue(p8.getPosition(), p8.getG());
    neighborsMatrixG.setValue(p9.getPosition(), p9.getG());

    neighborsMatrixB.setValue(p1.getPosition(), p1.getB());
    neighborsMatrixB.setValue(p2.getPosition(), p2.getB());
    neighborsMatrixB.setValue(p3.getPosition(), p3.getB());
    neighborsMatrixB.setValue(p4.getPosition(), p4.getB());
    neighborsMatrixB.setValue(p5.getPosition(), p5.getB());
    neighborsMatrixB.setValue(p6.getPosition(), p6.getB());
    neighborsMatrixB.setValue(p7.getPosition(), p7.getB());
    neighborsMatrixB.setValue(p8.getPosition(), p8.getB());
    neighborsMatrixB.setValue(p9.getPosition(), p9.getB());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFileName() {
    new PPMImage((String) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullPixels() {
    new PPMImage((IPixel[][]) null);
  }

  // test valid creation of a single tile checkerboard image
  @Test
  public void singleSquareCheckerboard() {
    IImage singleSquareCheckerboard = new PPMImage(10, 1, Color.BLACK, Color.RED);
    IPixel[][] pixels = singleSquareCheckerboard.getPixels();

    assertEquals(255, pixels[0][0].getR());
    assertEquals(255, pixels[5][0].getR());
    assertEquals(255, pixels[9][0].getR());
    assertEquals(255, pixels[5][5].getR());
    assertEquals(255, pixels[9][9].getR());
    assertEquals(0, pixels[0][0].getG());
    assertEquals(0, pixels[5][0].getG());
    assertEquals(0, pixels[9][0].getG());
    assertEquals(0, pixels[5][5].getG());
    assertEquals(0, pixels[9][9].getG());
    assertEquals(0, pixels[0][0].getB());
    assertEquals(0, pixels[5][0].getB());
    assertEquals(0, pixels[9][0].getB());
    assertEquals(0, pixels[5][5].getB());
    assertEquals(0, pixels[9][9].getB());
  }

  // test for when creating a checkerboard image with non-positive tile size is called
  @Test(expected = IllegalArgumentException.class)
  public void testNotPositiveTileSize() {
    new PPMImage(0, 3, Color.GREEN, Color.BLUE);
  }

  // test for when creating a checkerboard image with non-positive number of tiles is called
  @Test(expected = IllegalArgumentException.class)
  public void testNotPositiveNumTiles() {
    new PPMImage(50, 0, Color.GREEN, Color.BLUE);
  }

  // test for when creating a checkerboard image with color 1 being null is called
  @Test(expected = IllegalArgumentException.class)
  public void testNullCheckerboardColor1() {
    new PPMImage(5, 3, null, Color.BLUE);
  }

  // test for when creating a checkerboard image with color 2 being null is called
  @Test(expected = IllegalArgumentException.class)
  public void testNullCheckerboardColor2() {
    new PPMImage(10, 3, Color.GREEN, null);
  }

  // test valid creation of an odd sized checkerboard image
  @Test
  public void testOddCheckerboard() {
    IImage oddCheckerboard = new PPMImage(3, 3, Color.GREEN, Color.BLUE);
    IPixel[][] pixels = oddCheckerboard.getPixels();

    assertEquals(255, pixels[0][0].getB());
    assertEquals(255, pixels[1][2].getB());
    assertEquals(255, pixels[2][2].getB());
    assertEquals(0, pixels[2][2].getG());
    assertEquals(255, pixels[3][0].getG());
    assertEquals(255, pixels[3][1].getG());
    assertEquals(255, pixels[3][2].getG());
    assertEquals(0, pixels[3][2].getB());
    assertEquals(255, pixels[0][4].getG());
    assertEquals(0, pixels[0][4].getB());
    assertEquals(255, pixels[4][4].getB());
    assertEquals(0, pixels[4][4].getG());
    assertEquals(0, pixels[0][8].getG());
    assertEquals(255, pixels[0][8].getB());
    assertEquals(255, pixels[8][4].getG());
    assertEquals(0, pixels[8][4].getB());
    assertEquals(255, pixels[8][8].getB());
    assertEquals(0, pixels[8][8].getG());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTransform() {
    init();
    checkerboard.applyTransformation(null, blurKernel, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullKernel() {
    init();
    checkerboard.applyTransformation(filterObject, null, Channel.RED);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullChannel() {
    init();
    checkerboard.applyTransformation(filterObject, blurKernel, null);
  }

  // applies the blur filter through each applyTransformation method, then compares equality in a
  // separate helper method
  @Test
  public void testApplyTransformationWithBlur() {
    IImage newRedBoard = checkerboard.applyTransformation(filterObject, blurKernel, Channel.RED);
    IImage newGreenBoard = newRedBoard
        .applyTransformation(filterObject, blurKernel, Channel.GREEN);
    IImage newBlueBoard = newGreenBoard
        .applyTransformation(filterObject, blurKernel, Channel.BLUE);

    IImage blurredImage = checkerboard.applyTransformation(filterObject, blurKernel);

    assertTrue(imageEqualityCheck(newBlueBoard.getPixels(), blurredImage.getPixels()));
  }

  // applies the sharpen filter through each applyTransformation method, then compares equality in a
  // separate helper method
  @Test
  public void testApplyTransformationWithSharpen() {
    IImage newRedBoard = checkerboard.applyTransformation(filterObject, sharpKernel, Channel.RED);
    IImage newGreenBoard = newRedBoard
        .applyTransformation(filterObject, sharpKernel, Channel.GREEN);
    IImage newBlueBoard = newGreenBoard
        .applyTransformation(filterObject, sharpKernel, Channel.BLUE);

    IImage sharpenedImage = checkerboard.applyTransformation(filterObject, sharpKernel);

    assertTrue(imageEqualityCheck(newBlueBoard.getPixels(), sharpenedImage.getPixels()));
  }

  // applies the greyscale transformation through each applyTransformation method, then compares
  // equality in a separate helper method
  @Test
  public void testApplyTransformationWithGreyscale() {
    IImage newRedBoard = checkerboard
        .applyTransformation(colorTransformObject, greyKernel, Channel.RED);
    IImage newGreenBoard = newRedBoard
        .applyTransformation(colorTransformObject, greyKernel, Channel.GREEN);
    IImage newBlueBoard = newGreenBoard
        .applyTransformation(colorTransformObject, greyKernel, Channel.BLUE);

    IImage greyImage = checkerboard.applyTransformation(colorTransformObject, greyKernel);

    assertTrue(imageEqualityCheck(newBlueBoard.getPixels(), greyImage.getPixels()));
  }

  // applies the sepia transformation through each applyTransformation method, then compares
  // equality in a separate helper method
  @Test
  public void testApplyTransformationWithSepia() {
    IImage newBoard = checkerboard
        .applyTransformation(colorTransformObject, sepiaKernel, Channel.RED);

    IImage sepiaImage = checkerboard.applyTransformation(colorTransformObject, sepiaKernel);

    assertTrue(imageEqualityCheck(newBoard.getPixels(), sepiaImage.getPixels()));
  }

  // helper method to check for the equality between every pixel in two separate images
  private boolean imageEqualityCheck(IPixel[][] image1, IPixel[][] image2) {

    int image1Width = image1.length;
    int image1Height = image1[0].length;
    int image2Width = image2.length;
    int image2Height = image2[0].length;

    // ensure that the images are the same size
    if (image1Width != image2Width || image2Height != image1Height) {
      return false;
    }

    // iterate through all pixels within each image
    for (int i = 0; i < image1Width; i++) {
      for (int j = 0; j < image1Height; j++) {
        int img1R = image1[j][i].getR();
        int img1G = image1[j][i].getG();
        int img1B = image1[j][i].getB();
        int img2R = image2[j][i].getR();
        int img2G = image2[j][i].getG();
        int img2B = image2[j][i].getB();

        // ensure that all color channels for pixels at same location across each image are same
        if (img1R != img2R || img1G != img2G || img1B != img2B) {
          return false;
        }
      }
    }
    return true;
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFindNeighborsNullPixel() {
    checkerboard.findNeighbors(null, Channel.RED, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFindNeighborsNullColor() {
    checkerboard.findNeighbors(p1, null, 3);
  }

  @Test
  public void testFindNeighborsMiddlePixel() {
    assertTrue(checkNeighborsEquality(
        neighborsMatrixR, neighborsImage.findNeighbors(p5, Channel.RED, 3)));
    assertTrue(checkNeighborsEquality(
        neighborsMatrixG, neighborsImage.findNeighbors(p5, Channel.GREEN, 3)));
    assertTrue(checkNeighborsEquality(
        neighborsMatrixB, neighborsImage.findNeighbors(p5, Channel.BLUE, 3)));
  }

  @Test
  public void testFindNeighborsTopCornerPixel() {
    KernelMatrix topCornerRed = new KernelMatrix(3);
    topCornerRed.setValue(new Posn(1, 1), 212);
    topCornerRed.setValue(new Posn(2, 1), 83);
    topCornerRed.setValue(new Posn(1, 2), 123);
    topCornerRed.setValue(new Posn(2, 2), 8);

    KernelMatrix topCornerGreen = new KernelMatrix(3);
    topCornerGreen.setValue(new Posn(1, 1), 89);
    topCornerGreen.setValue(new Posn(2, 1), 25);
    topCornerGreen.setValue(new Posn(1, 2), 146);
    topCornerGreen.setValue(new Posn(2, 2), 24);

    KernelMatrix topCornerBlue = new KernelMatrix(3);
    topCornerBlue.setValue(new Posn(1, 1), 83);
    topCornerBlue.setValue(new Posn(2, 1), 179);
    topCornerBlue.setValue(new Posn(1, 2), 200);
    topCornerBlue.setValue(new Posn(2, 2), 77);

    assertTrue(
        checkNeighborsEquality(topCornerRed, neighborsImage.findNeighbors(p1, Channel.RED, 3)));
    assertTrue(
        checkNeighborsEquality(topCornerGreen, neighborsImage.findNeighbors(p1, Channel.GREEN, 3)));
    assertTrue(
        checkNeighborsEquality(topCornerBlue, neighborsImage.findNeighbors(p1, Channel.BLUE, 3)));
  }

  @Test
  public void testFindNeighborsBottomCornerPixel() {
    KernelMatrix bottomCornerRed = new KernelMatrix(3);
    bottomCornerRed.setValue(new Posn(1, 1), 150); // p9
    bottomCornerRed.setValue(new Posn(0, 1), 49); // p8
    bottomCornerRed.setValue(new Posn(1, 0), 128); // p6
    bottomCornerRed.setValue(new Posn(0, 0), 8); // p5

    KernelMatrix bottomCornerGreen = new KernelMatrix(3);
    bottomCornerGreen.setValue(new Posn(1, 1), 38);
    bottomCornerGreen.setValue(new Posn(0, 1), 71);
    bottomCornerGreen.setValue(new Posn(1, 0), 1);
    bottomCornerGreen.setValue(new Posn(0, 0), 24);

    KernelMatrix bottomCornerBlue = new KernelMatrix(3);
    bottomCornerBlue.setValue(new Posn(1, 1), 0);
    bottomCornerBlue.setValue(new Posn(0, 1), 100);
    bottomCornerBlue.setValue(new Posn(1, 0), 0);
    bottomCornerBlue.setValue(new Posn(0, 0), 77);

    assertTrue(
        checkNeighborsEquality(bottomCornerRed, neighborsImage.findNeighbors(p9, Channel.RED, 3)));
    assertTrue(
        checkNeighborsEquality(bottomCornerGreen,
            neighborsImage.findNeighbors(p9, Channel.GREEN, 3)));
    assertTrue(
        checkNeighborsEquality(bottomCornerBlue,
            neighborsImage.findNeighbors(p9, Channel.BLUE, 3)));
  }

  // helper method to check the equality between two kernel matrices
  private boolean checkNeighborsEquality(KernelMatrix matrix1, KernelMatrix matrix2) {
    double[][] m1 = matrix1.getKernel();
    double[][] m2 = matrix2.getKernel();
    int m1Width = m1.length;
    int m1Height = m1[0].length;
    int m2Width = m2.length;
    int m2Height = m2[0].length;

    // check to make sure both 2D arrays are the same size
    if (m1Width != m2Width || m2Height != m1Height) {
      return false;
    }

    for (int i = 0; i < m1Width; i++) {
      for (int j = 0; j < m1Height; j++) {

        // check to make sure values in both positions are the same
        if (m1[j][i] != m2[j][i]) {
          return false;
        }
      }
    }
    return true;
  }

  @Test
  public void testGetPixels() {
    IPixel[][] pixels = new IPixel[3][3];
    pixels[0][0] = p1;
    pixels[1][0] = p2;
    pixels[2][0] = p3;
    pixels[0][1] = p4;
    pixels[1][1] = p5;
    pixels[2][1] = p6;
    pixels[0][2] = p7;
    pixels[1][2] = p8;
    pixels[2][2] = p9;

    // create new image through the constructor
    IImage pixelsImage = new PPMImage(pixels);
    assertTrue(imageEqualityCheck(pixels, pixelsImage.getPixels()));
  }

  @Test
  public void testGetImage() {
    IPixel[][] pixels = new IPixel[3][3];
    pixels[0][0] = p1;
    pixels[1][0] = p2;
    pixels[2][0] = p3;
    pixels[0][1] = p4;
    pixels[1][1] = p5;
    pixels[2][1] = p6;
    pixels[0][2] = p7;
    pixels[1][2] = p8;
    pixels[2][2] = p9;

    // create new image through the getImage method
    IImage pixelsImage = checkerboard.getImage(pixels);

    assertTrue(imageEqualityCheck(pixels, pixelsImage.getPixels()));
  }

  //TODO: test sendToString method.
}
