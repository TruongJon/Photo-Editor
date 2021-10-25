package model;

/**
 * Class to represent a type of image transformation in which the pixels color channels are adjusted
 * based on the values of neighboring pixels' color channels. New RGB channel values for each pixel
 * are calculated by first finding the neighboring pixels that overlap with the kernel given for
 * this filter.<p>The calculation for a pixel's new channel values are done by multiplying together
 * the value of the neighboring pixels channel with the corresponding value at the same position in
 * the kernel given. These values added together create the new channel value for this pixel.</p>
 * <p>If a pixel is near the edge of an image in which there are no neighboring pixels present on a
 * given side, the values for the neighboring pixels channels remain zero as to not be included
 * in the calculation of new RGB values.</p>
 */
public class Filter implements ITransform {

  /**
   * Constructs a filter object. Used in order to dynamically dispatch the application of image
   * transformations to this specific class.
   */
  public Filter() {
    // empty constructor: creates Filter object with no fields needed
  }

  @Override
  public IImage apply(IImage image, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException {
    if (image == null || kernel == null || color == null) {
      throw new IllegalArgumentException("Fields cannot be null.");
    }
    IPixel[][] newImagePixels = new IPixel[image.getPixels().length][image
        .getPixels()[0].length];

    // iterates through all values in 2D array
    for (int i = 0; i < image.getPixels()[0].length; i++) {
      for (int j = 0; j < image.getPixels().length; j++) {
        IPixel pixel = image.getPixels()[j][i];
        KernelMatrix pixelNeighbors = image
            .findNeighbors(pixel, color, kernel.getKernel().length);
        newImagePixels[j][i] = pixel.applyKernelFilter(kernel, pixelNeighbors, color);
      }
    }
    return image.getImage(newImagePixels);
  }
}
