package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * SharpenCommand class executes the sharpen filter on the image.
 */
public class SharpenCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public SharpenCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes sharpen filter command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.filterSharpen();
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}