package model;

/**
 * Class to represent a type of image transformation in which an image is properly "down-sized".
 * This is accomplished through calculating the proper ratio between the width and height of the
 * image being downsized, creating a new, smaller image with the same ratio of w/h, and then
 * populating the pixels within the downsized image.<p>The pixels' color channels in the downsized
 * image are calculated by first, finding the location of the corresponding "mapped" location in the
 * original image. There are two cases at this point:</p><p>Case 1: The mapped location falls
 * directly upon a specific pixel within the original image. The colors of the mapped pixel can be
 * directly populated to the color channel values of the new pixel in the downsized
 * image.</p><p>Case 2: The mapped location falls between specific pixels in the original image. The
 * color channels of the new pixel can be found by taking the average value for each channel across
 * each of the surrounding 4 pixels. For example, when calculating the value of the red color
 * channel, the pixels up, down, left, and right of the floating-point location are found before
 * executing the following functions:</p><p>Based on Pixel(x,y) floating point values:
 * m=Cb(x−⌊x⌋)+Ca(⌈x⌉−x) then,n=Cd(x−⌊x⌋)+Cc(⌈x⌉−x) and finally, Cp=n(y−⌊y⌋)+m(⌈y⌉−y) where Ca-Cd
 * are the colors of the 4 surrounding pixels and Cp is the resulting channel value for the new
 * pixel.</p>
 */
public class DownSize implements ITransform {

  private final double ratio;

  /**
   * Constructs a downsize object. Used in order to dynamically dispatch the application of image
   * transformations to this specific class.
   *
   * @param ratio the scale factor the image should be down-sized by (between 0 and 1 both
   *              non-inclusive)
   */
  public DownSize(double ratio) throws IllegalArgumentException {
    if (ratio <= 0 || ratio >= 1) {
      throw new IllegalArgumentException("ratio must be between 0 and 1, non-inclusive.");
    }
    this.ratio = ratio;
  }

  @Override
  public IImage apply(IImage image, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException {
    if (image == null || kernel == null || color == null) {
      throw new IllegalArgumentException("Fields cannot be null.");
    }

    // creating down-scaled image based on size of original image
    int originalWidth = image.getPixels().length;
    int originalHeight = image.getPixels()[0].length;
    int newWidth = (int) Math.round(originalWidth * this.ratio);
    int newHeight = (int) Math.round(originalHeight * this.ratio);
    IPixel[][] downSizeImagePixels = new IPixel[newWidth][newHeight];

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {

        // creates floating point ratios for x and y location within image
        double ratioX = (double) j / newWidth;
        double ratioY = (double) i / newHeight;

        double originalX = ratioX * originalWidth;
        double originalY = ratioY * originalHeight;

        // check if the mapped x and y coordinates in the 2d-array both equate to a specific
        // position (non-floating point)
        if ((originalX % 1 == 0 && originalY % 1 == 0) || (originalX == 0 || originalY == 0)) {

          // mapped location is a specific pixel, populate the new pixel channel values as the same
          IPixel mappedPixel = image.getPixels()[(int) originalX][(int) originalY];
          IPixel newPixel = new Pixel(new Posn(j, i), mappedPixel.getR(), mappedPixel.getG(),
              mappedPixel.getB());
          downSizeImagePixels[j][i] = newPixel;
        } else {

          IPixel pixelA = image.getPixels()[(int) Math.floor(originalX)][(int) Math
                .floor(originalY)];
          IPixel pixelB = image.getPixels()[(int) Math.ceil(originalX)][(int) Math
                .floor(originalY)];
          IPixel pixelC = image.getPixels()[(int) Math.floor(originalX)][(int) Math
                .ceil(originalY)];
          IPixel pixelD = image.getPixels()[(int) Math.ceil(originalX)][(int) Math
                .ceil(originalY)];

          // calculate each channel value
          int redChannel = getChannelVal(pixelA, pixelB, pixelC, pixelD, originalX, originalY,
              Channel.RED);
          int greenChannel = getChannelVal(pixelA, pixelB, pixelC, pixelD, originalX, originalY,
              Channel.GREEN);
          int blueChannel = getChannelVal(pixelA, pixelB, pixelC, pixelD, originalX, originalY,
              Channel.BLUE);

          IPixel newPixel = new Pixel(new Posn(j, i), redChannel, greenChannel, blueChannel);
          downSizeImagePixels[j][i] = newPixel;
        }
      }
    }
    return new PPMImage(downSizeImagePixels);
  }

  private static int getChannelVal(IPixel pixA, IPixel pixB, IPixel pixC, IPixel pixD, double x,
      double y, Channel val) {
    int colorA;
    int colorB;
    int colorC;
    int colorD;
    switch (val) {
      case RED:
        colorA = pixA.getR();
        colorB = pixB.getR();
        colorC = pixC.getR();
        colorD = pixD.getR();
        break;
      case GREEN:
        colorA = pixA.getG();
        colorB = pixB.getG();
        colorC = pixC.getG();
        colorD = pixD.getG();
        break;
      case BLUE:
        colorA = pixA.getB();
        colorB = pixB.getB();
        colorC = pixC.getB();
        colorD = pixD.getB();
        break;
      default:
        throw new IllegalArgumentException("Not a valid color channel.");
    }
    double m = (colorB * (x - Math.floor(x))) + (colorA * (Math.ceil(x) - x));
    double n = (colorD * (x - Math.floor(x))) + (colorC * (Math.ceil(x) - x));
    return (int) Math.round(n * (y - Math.floor(y)) + (m * (Math.ceil(y) - y)));
  }
}
