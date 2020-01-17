package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

import javax.swing.JFrame;

/**
 * DitherCommand class executes the dither filter on the image.
 */
public class DitherCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public DitherCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }


  /**
   * Executes Dither command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.filterDither();
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
