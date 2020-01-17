package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * BlurCommand class executes the blur filter on the image.
 */
public class BlurCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public BlurCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes blur filter command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.filterBlur();
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
