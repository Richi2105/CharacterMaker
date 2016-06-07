/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

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
public class ControlFileIO extends Observable {
  
  private final JFileChooser fileChooser;
  private final CharMakerWindow view;
  private String currentPath;
  private String currentFile;
  private int approve;
  
  public ControlFileIO(CharMakerWindow view)
  {
    this.fileChooser = view.getjFileChooser1();
    this.view = view;
  }
  
  public void setDefaults(SavedSettings settings)
  {
    this.currentPath = settings.getProperty("PATH");
  }
  
  public void setPath(String path)
  {
    this.currentPath = path;
    this.currentFile = "";
  }
  
  public String getPath()
  {
    return this.currentPath;
  }
  
  public int getApproveOption()
  {
    return this.approve;
  }
  
  public String getFile()
  {
    return this.currentFile;
  }
  
  public void showOpenDialog()
  {
    this.fileChooser.setCurrentDirectory(new File(this.currentPath));
    this.approve = this.fileChooser.showOpenDialog(view);
    
    this.handleFileDialog();
  }
  
  public void showSaveDialog()
  {
    this.fileChooser.setCurrentDirectory(new File(this.currentPath));
    this.approve = this.fileChooser.showSaveDialog(view);
    
    this.handleFileDialog();
  }
  
  private void handleFileDialog()
  {
    if (approve == JFileChooser.APPROVE_OPTION)
    {
      this.currentFile = fileChooser.getSelectedFile().getAbsolutePath();
      this.currentPath = fileChooser.getSelectedFile().getParent();
    }
    this.setChanged();
    this.notifyObservers();
  }
  
}
