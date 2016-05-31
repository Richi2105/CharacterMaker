/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.core.BitmapReader;
import charmaker2.util.SavedSettings;
import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import javax.swing.JFileChooser;

/**
 *
 * @author Richard
 */
public class ControlFileOperation extends Observable implements ActionListener
{
  private CharMakerWindow view;
  private ControlCharacterSet characterListController;
  private File folderPath;
  private SavedSettings settings;
//  private BitmapReader reader;
  public ControlFileOperation(CharMakerWindow view, SavedSettings settings)
  {
    this.view = view;
    view.getButtonOpen().addActionListener(this);
    this.settings = settings;
    this.folderPath = new File(settings.getProperty("PATH"));
  }
  
  public void addCharacterListController(ControlCharacterSet characterListController)
  {
    this.characterListController = characterListController;
  }
  
  private void setFolderPath(String path)
  {
    this.folderPath = new File(path);
  }
  
  public File getFolderPath()
  {
    return folderPath;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
//    view.getFileChooser().setCurrentDirectory(folderPath);
//    view.getFileChooser().setDialogType(JFileChooser.OPEN_DIALOG);
//    view.getFileChooser().setVisible(true);
    
    System.out.println(folderPath.getAbsolutePath());
    view.getjFileChooser1().setCurrentDirectory(folderPath);
    int choice = view.getjFileChooser1().showOpenDialog(view);

    if (choice == JFileChooser.APPROVE_OPTION)
    {
      File file = view.getjFileChooser1().getSelectedFile();
      if (characterListController != null)
      {
        BitmapReader.readBitmap(file, characterListController);
      }
      else
      {
        BitmapReader.readBitmap(file, null);
      }
      
      folderPath = file.getParentFile();
      this.settings.setProperty("FILE", folderPath.getAbsolutePath());
      this.setChanged();
      this.notifyObservers();
    }
  }
}
