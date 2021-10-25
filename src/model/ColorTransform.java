package model;

/**
 * Class to represent a type of image transformation in which the pixel's colors are adjusted using
 * a kernel. New RGB channel values for each pixel are calculated by multiplying each row in the
 * kernel to the corresponding RGB values.<p>Row 1 of the kernel calculation creates a new R
 * value.</p>
 * <p>Row 2 of the kernel calculations creates a new G value.</p><p>Row 3 of the kernel
 * calculations creates a new B value.</p>
 * <p>A single row's calculations are done by multiplying together the first value in a row by a
 * pixel's original r value, the second value in a row by a pixel's original g value, and the third
 * value in a row by a pixel's original b value.</p>
 * <p>Calculations across each individual row add up to represent the new channel value
 * corresponding to the row in which the calculations came from. (i.e. Channel 2 value comes from
 * row 2 calculations).</p>
 */
public class ColorTransform implements ITransform {

  /**
   * Constructs a color transformation object. Used in order to dynamically dispatch the application
   * of image transformations to this specific class.
   */
  public ColorTransform() {
    // empty constructor: creates ColorTransform object with no fields needed
  }

  @Override
  public IImage apply(IImage image, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException {
    if (image == null || kernel == null || color == null) {
      throw new IllegalArgumentException("Fields cannot be null");
    }

    // kernel must be of size 3x3 in order to apply a color transformation.
    if (kernel.getKernel().length != 3) {
      throw new IllegalArgumentException("Kernel must be 3x3 in size.");
    }

    IPixel[][] newImagePixels = new IPixel[image.getPixels().length][image
        .getPixels()[0].length];

    // iterates through all values in 2D array
    for (int i = 0; i < image.getPixels()[0].length; i++) {
      for (int j = 0; j < image.getPixels().length; j++) {
        IPixel pixel = image.getPixels()[j][i];
        newImagePixels[j][i] = pixel.applyKernelColor(kernel);
      }
    }
    return image.getImage(newImagePixels);
  }
}
