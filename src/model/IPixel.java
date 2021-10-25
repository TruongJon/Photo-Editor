package model;

/**
 * Represents a pixel within an image.
 */
public interface IPixel {

  /**
   * Applies the given kernel to the specified channel of the pixel. This is done through
   * multiplying the values of each overlapping value between the kernel and the pixel's channel
   * values. After multiplying the overlapping values between the neighboring pixels and kernel, the
   * results are added together and assigned to this target pixel's channel value.
   *
   * @param kernel    the filter to be applied to this pixel in the image
   * @param neighbors the values of the neighbors of this pixel calculate the filter result.
   *                  The neighbors matrix should always be the same size as kernel. If no
   *                  neighboring pixel is present relative to the target pixel, the value at the
   *                  corresponding position in neighbors remains 0.0
   * @param color     the channel of the pixel to apply the filter to
   * @return the new pixel after filtering its channel values
   */
  IPixel applyKernelFilter(KernelMatrix kernel, KernelMatrix neighbors, Channel color);

  /**
   * Applies the given kernel to this pixel in the image. This is done through by multiplying each
   * row in the kernel to the corresponding RGB values.<p>Row 1 of the kernel calculation creates a
   * new R value.</p>
   * <p>Row 2 of the kernel calculations creates a new G value.</p><p>Row 3 of the kernel
   * calculations creates a new B value.</p>
   *
   * @param kernel the kernel matrix to be applied to this pixel in the image
   * @return the new pixel after applying the color transformation kernel to its channel values
   */
  IPixel applyKernelColor(KernelMatrix kernel);

  /**
   * Gets the position object of this pixel.
   *
   * @return the pixels x and y position
   */
  Posn getPosition();

  /**
   * Gets this pixels "red" channel value.
   *
   * @return the value in the pixels red channel
   */
  int getR();

  /**
   * Gets this pixels "green" channel value.
   *
   * @return the value in the pixels green channel
   */
  int getG();

  /**
   * Gets this pixels "blue" channel value.
   *
   * @return the value in the pixels blue channel
   */
  int getB();
}
