package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * RedoCommand class lets the user go to a later image iteration.
 */
public class RedoCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public RedoCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes Redo command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.redo();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}