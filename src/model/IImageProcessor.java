package model;

/**
 * Interface to represent the state of the image processor.
 */
public interface IImageProcessor extends IImageProcessorState {

  /**
   * Adds a layer to the multi-layer image.
   *
   * @param layer a layer within this multi-layer image
   * @throws IllegalArgumentException if the provided layer is null or is not of the same dimension
   *                                  as other layers within the multi-layer image
   */
  void addLayer(IImage layer) throws IllegalArgumentException;

  /**
   * Removes a layer from the multi-layer image.
   *
   * @param layer a layer within this multi-layer image
   * @throws IllegalArgumentException if the provided layer is null
   */
  void removeLayer(IImage layer) throws IllegalArgumentException;

  /**
   * Replaces a layer of the multi-layer image.
   *
   * @param layerToRemove a layer to be removed within this multi-layer image
   * @param layerToAdd    a layer to be replace the removed layer within this multi-layer image
   * @throws IllegalArgumentException if the provided layer is null
   */
  void replaceLayer(IImage layerToRemove, IImage layerToAdd)
      throws IllegalArgumentException;

  /**
   * Applies a transformation to a layer of the multi-layer image.
   *
   * @param layer          a layer within the multi-layer image
   * @param transformation the type of transformation to be applied to the layer
   * @param kernel         the kernel representing the transformation to be applied to the layer
   * @param replace        true if the transformed layer should replace the original layer, false
   *                       otherwise
   * @param newName        the name of the layer being transformed
   * @throws IllegalArgumentException if any of the parameters are null
   */
  void transformLayer(IImage layer, ITransform transformation, KernelMatrix kernel,
      boolean replace, String newName) throws IllegalArgumentException;

}
