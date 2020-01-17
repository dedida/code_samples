package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocessor.model.ImageModel;
import imageprocessor.view.ImageView;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

/**
 * Class that takes care of loading in an image from user.
 */
public class LoadImageCommand extends JFrame implements Commands {

  private ImageModel model;
  private ImageView view;
  private String path;

  /**
   * LoadImage constructor.
   * @param model (ImageModel)
   * @param view (ImageView)
   */
  public LoadImageCommand(ImageModel model, ImageView view) {
    this.model = model;
    this.view = view;
    this.path = "";
  }

  /**
   * Executes LoadImage command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel fileOpenDisplay = new JLabel();
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Images", "jpg", "gif", "jpeg", "png");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(LoadImageCommand.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        fileOpenDisplay.setText(f.getAbsolutePath());
        this.path = fileOpenDisplay.getText();
      }
      model.setImageName(path);
      model.tempSave();
      view.updateImage(model.getRGB(), model.getWidth(), model.getHeight());
    } catch (IOException e) {
      e.getMessage();
      view.setStatus(false);
    } catch (ArrayIndexOutOfBoundsException a) {
      System.out.print("Cannot load given image");
      view.setStatus(false);
    }
  }

  public String getPath() {
    return this.path;
  }

}