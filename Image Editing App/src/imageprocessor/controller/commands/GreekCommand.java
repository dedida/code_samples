package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;


/**
 * GreekCommand class generates a Greek flag.
 */
public class GreekCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  /**
   * GreekCommand constructor.
   *
   * @param model (ImageModel)
   * @param view  (ImageView)
   */
  public GreekCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes Greek flag command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel input = new JLabel("Width");
      input.setText(JOptionPane.showInputDialog("Enter the width for the flag as an integer"));
      String numGreek = input.getText().replaceAll("\\s", "");
      int width = Integer.parseInt(numGreek);
      model.createGreekFlag(width);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
