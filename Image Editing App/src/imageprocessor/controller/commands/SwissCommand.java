package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * SwissCommand class generates a Swiss flag.
 */
public class SwissCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public SwissCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes Swiss flag command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel input = new JLabel("Width");
      input.setText(JOptionPane.showInputDialog("Enter the flag width as an integer"));
      String num = input.getText().replaceAll("\\s", "");
      int width = Integer.parseInt(num);
      model.createSwissFlag(width);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}
