package imageprocessor.controller.commands;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;

import imageprocessor.controller.ImageController;
import imageprocessor.view.ImageView;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

/**
 * This command class enables the user to upload a file.
 */
public class UploadFileCommand extends JFrame implements Commands {

  private ImageController controller;
  private ImageView view;

  /**
   * UploadFile constructor.

   * @param controller (ImageController)
   * @param view (ImageView)
   */
  public UploadFileCommand( ImageController controller, ImageView view) {
    this.controller = controller;
    this.view = view;
  }

  /**
   * Executes UploadFile command.
   *
   * @param event (ActionEvent)
   */
  public void actionPerformed(ActionEvent event) {
    try {
      JLabel openBatchDisplay = new JLabel();
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "Text File", "txt");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(UploadFileCommand.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        openBatchDisplay.setText(f.getAbsolutePath());
        String path = openBatchDisplay.getText();
        controller.executeFile(path);
      }
    } catch (IOException ex) {
      ex.getMessage();
      view.setStatus(false);
    }

  }

}
