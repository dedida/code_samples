package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * GreyScaleCommand class executes the greyscale filter on the image.
 */
public class GreyscaleCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public GreyscaleCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes GreyScale command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.filterGreyscale();
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
