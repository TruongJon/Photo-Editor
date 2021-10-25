package model;

import java.util.ArrayList;

/**
 * Interface to represent the state of the image processor.
 */
public interface IImageProcessorState {

  ArrayList<IImage> getAllImages();

  IImage getLayer(int index) throws IndexOutOfBoundsException;

  /**
   * Gets the layer with the corresponding given name from this image processor.
   *
   * @param name the name of the desired layer
   * @return the image with the desired name
   * @throws IllegalArgumentException if the given name is null
   * @throws IllegalStateException    if the name cannot be found in the multi-layered image
   */
  IImage getLayer(String name) throws IllegalArgumentException, IllegalStateException;

  /**
   * Finds the top most visible layer within the layers of images.
   *
   * @return the image on the top layer, must be visible
   * @throws IllegalStateException when there are no visible layers in the image processor
   */
  IImage findTopMostLayer() throws IllegalStateException;
}
