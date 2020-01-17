package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

/**
 * RainbowCommand class generates a rainbow either vertical or horizontal.
 */
public class RainbowCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;

  public RainbowCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Executes Rainbow command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel heightInput = new JLabel("height");
      heightInput.setText(JOptionPane.showInputDialog("Enter the height as an integer"));
      String numHeight = heightInput.getText().replaceAll("\\s", "");
      int height = Integer.parseInt(numHeight);

      JLabel widthInput = new JLabel("width");
      widthInput.setText(JOptionPane.showInputDialog("Enter the width as an integer"));
      String numWidth = widthInput.getText().replaceAll("\\s", "");
      int width = Integer.parseInt(numWidth);

      JLabel type = new JLabel("type");
      type.setText(JOptionPane.showInputDialog("Enter 'horizontal' or 'vertical' "));
      String rainbowType = type.getText().replaceAll("\\s", "");

      model.createRainbow(height, width, rainbowType);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (NumberFormatException n) {
      System.out.print("Cannot execute rainbow command with given width/height parameters");
      view.setStatus(false);
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }
  }

}