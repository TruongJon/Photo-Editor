package model;

import java.util.ArrayList;

/**
 * The SimpleImageProcessor class is an implementation of the IImageProcessor interface. It supports
 * the ability to manipulate layers.
 */
public class SimpleImageProcessor implements IImageProcessor {

  private final ArrayList<IImage> layers;

  /**
   * Default constructor to construct a simple image processing model.
   */
  public SimpleImageProcessor() {
    this.layers = new ArrayList<>();
  }

  @Override
  public void addLayer(IImage layer) throws IllegalArgumentException {
    if (layer == null) {
      throw new IllegalArgumentException("Layers is null");
    }
    if (!this.layers.isEmpty() && layer.getPixels() != null) {
      IImage firstLayer = this.layers.get(0);
      int width = firstLayer.getPixels().length;
      int height = firstLayer.getPixels()[0].length;

      int width2 = layer.getPixels().length;
      int height2 = layer.getPixels()[0].length;

      if (width != width2 || height != height2) {
        throw new IllegalArgumentException("Layer is not of same dimensions.");
      }
    }
    this.layers.add(layer);
  }

  @Override
  public void removeLayer(IImage layer) throws IllegalArgumentException {
    if (layer == null) {
      throw new IllegalArgumentException("Layers is null");
    }
    this.layers.remove(layer);
  }

  @Override
  public void replaceLayer(IImage layerToRemove, IImage layerToAdd)
      throws IllegalArgumentException {
    if (layerToRemove == null || layerToAdd == null) {
      throw new IllegalArgumentException("Layer to remove or layer to add cannot be null");
    }
    int index = getIndex(layerToRemove);
    this.layers.set(index, layerToAdd);
  }

  @Override
  public void transformLayer(IImage layer, ITransform transformation, KernelMatrix kernel,
      boolean replace, String newName)
      throws IllegalArgumentException {
    if (layer == null || transformation == null || kernel == null) {
      throw new IllegalArgumentException("Arguments cannot be null");
    }
    int index = getIndex(layer);

    IImage targetLayer = this.layers.get(index);
    IImage transformedLayer = targetLayer.applyTransformation(transformation, kernel);
    transformedLayer.setName(newName);

    if (replace) {
      replaceLayer(layer, transformedLayer);

    } else {
      addLayer(transformedLayer);
    }
  }

  @Override
  public IImage findTopMostLayer() throws IllegalStateException {
    for (int i = this.layers.size() - 1; i >= 0; i--) {
      IImage image = this.layers.get(i);
      if (image.getVisibility()) {
        return image;
      }
    }
    throw new IllegalStateException("There are no visible layers.");
  }

  @Override
  public ArrayList<IImage> getAllImages() {
    return this.layers;
  }

  @Override
  public IImage getLayer(int index) throws IndexOutOfBoundsException {
    if (this.layers.size() <= index || index < 0) {
      throw new IndexOutOfBoundsException("Provided index not in bounds.");
    }
    return this.layers.get(index);
  }

  @Override
  public IImage getLayer(String name) throws IllegalArgumentException, IllegalStateException {
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null.");
    }
    for (IImage layer : this.layers) {
      if (layer.getName().equals(name)) {
        return layer;
      }
    }
    throw new IllegalStateException(
        "The requested layer does not exist in this multi-layered image.");
  }

  private int getIndex(IImage layer) throws IllegalArgumentException {
    int index = this.layers.indexOf(layer);
    if (index == -1) {
      throw new IllegalArgumentException("Layer not found");
    }
    return index;
  }
}
