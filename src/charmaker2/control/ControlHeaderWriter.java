/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.control.models.FontSettings;
import charmaker2.core.HeaderWriter;
import charmaker2.core.character.CharacterSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import charmaker2.util.RSLogger;
import charmaker2.view.CharMakerWindow;

/**
 *
 * @author Richard
 */

//TODO: rewrite
public class ControlHeaderWriter implements Observer
{
  private final ControlFileIO fileController;
  

  
  private CharacterSet charaSet;
  private FontSettings settings;  
  
  //private ControlFileOperation fileController;
  
  public ControlHeaderWriter(ControlFileIO fileController)
  {
    this.fileController = fileController;
  }
  
  public ControlHeaderWriter setObservable(Observable o)
  {
    o.addObserver(this);
    return this;
  }
  
  private void writeHeader()
  {
    HeaderWriter writer = new HeaderWriter(settings.rotation, settings.bits, settings.mirrorVertical);
    
    String folderPath = this.fileController.getPath();
    
    try {
      writer.writeHeader(charaSet, new File(folderPath));
    }    
    catch (FileNotFoundException fnfe)
    {
      RSLogger.getLogger().log(Level.WARNING, "File not found", fnfe);
    }
    catch (IOException ioex)
    {
      RSLogger.getLogger().log(Level.SEVERE, "File not writeable", ioex);
    }
  }
  
  public void writeOut(CharacterSet charset, FontSettings settings, String file)
  {    
    HeaderWriter writer = new HeaderWriter(settings.rotation, settings.bits, settings.mirrorVertical);
     
    try {
      writer.writeHeader(charaSet, new File(file));
    }    
    catch (FileNotFoundException fnfe)
    {
      RSLogger.getLogger().log(Level.WARNING, "File not found", fnfe);
    }
    catch (IOException ioex)
    {
      RSLogger.getLogger().log(Level.SEVERE, "File not writeable", ioex);
    }
  }
/*
  @Override
  public void actionPerformed(ActionEvent e)
  {
    int rotation = fontSettings.getActiveButtonIndex();
    int dataType = this.dataTypes.getSelectedBitDepth();
    String dataTypeName = this.dataTypes.getSelectedDatatypeName();
    boolean mirror = view.getCheckBoxMirrorX().isSelected();
    writer = new HeaderWriter(rotation, dataType, dataTypeName, mirror);
    
    try
    {
      if (charaSet != null)
      {
        if (filePath != null)
        {
          writer.writeHeader(charaSet, filePath);
        }
        else 
        {
          writer.writeHeader(charaSet, new File(".//"));
        }
      }
    }
    catch (FileNotFoundException fnfe)
    {
      RSLogger.getLogger().log(Level.WARNING, "File not found", fnfe);
    }
    catch (IOException ioex)
    {
      RSLogger.getLogger().log(Level.SEVERE, "File not writeable", ioex);
    }
    
    
  }
*/
  @Override
  public void update(Observable o, Object arg)
  {
    this.fileController.deleteObserver(this);
    this.writeHeader();      
  }
}
