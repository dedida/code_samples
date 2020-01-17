package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * UndoCommand class lets the user go to an earlier image iteration.
 */
public class UndoCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public UndoCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes Undo command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      model.undo();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
