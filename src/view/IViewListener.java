package view;

import java.awt.image.BufferedImage;
import model.IImage;

public interface IViewListener {

  void handleLoadEvent(String filepath) throws IllegalStateException;

  void handleSaveEvent(String filepath);

  void handleSaveAllEvent(String filepath, String fileType);

  void handleVisibilityEvent(String layer, boolean visible);

  void handleTransformationEvent();

  BufferedImage handleImageConversionEvent(IImage image);

  void setFields(String ... fields);
}
