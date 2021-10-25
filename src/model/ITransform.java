package model;

/**
 * Represents a transformation that can be done to an image. Some examples of such a transformation
 * include "filtering" the image or applying a "color transformation".
 */
public interface ITransform {

  /**
   * Factory method to apply a given kernel to an image. Method will be invoked by an implemented
   * ITransform object which will then dynamically dispatch to the type of transformation that must
   * be applied to the image. A new image is created using the original image and kernel passed in.
   *
   * @param image  the image to apply the filter to
   * @param kernel the 2D kernel matrix to be applied to the image
   * @param color  the channel of the pixel to apply the kernel to
   * @return a new image created by the kernel being applied to the original image
   * @throws IllegalArgumentException if any of image, kernel or color are null
   */
  IImage apply(IImage image, KernelMatrix kernel, Channel color)
      throws IllegalArgumentException;
}
