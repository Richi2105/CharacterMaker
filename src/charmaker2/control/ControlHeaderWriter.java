/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

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
public class ControlHeaderWriter implements ActionListener, Observer
{
  private CharMakerWindow view;
  private HeaderWriter writer;
  private ControlFontSettings fontSettings;
  private ComboBoxDataTypes dataTypes;
  
  private CharacterSet charaSet;
  private File filePath;
  
  private ControlCharacterSet charaListController;
  private ControlFileOperation fileController;
  
  public ControlHeaderWriter(CharMakerWindow view, ControlFontSettings fontSettings)
  {
    this.view = view;
    view.getButtonSave().addActionListener(this);
    view.getComboBoxDatatype().removeAllItems();
    
    this.dataTypes = new ComboBoxDataTypes();
    view.getComboBoxDatatype().setModel(dataTypes);
    dataTypes.updateList();
    
/*    JRadioButton buttons[] = {view.getRadioButtonRotate0(),
                              view.getRadioButtonRotate90(),
                              view.getRadioButtonRotate180(),
                              view.getRadioButtonRotate270()};
    
    radioButtonController = new ControlFontSettings(buttons, 4);
*/
    this.fontSettings = fontSettings;
  }
  
  public ControlHeaderWriter setObservable(Observable o)
  {
    o.addObserver(this);
    return this;
  }

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

  @Override
  public void update(Observable o, Object arg)
  {
    if (o.getClass().equals(ControlFileOperation.class))
    {
      filePath = ((ControlFileOperation)o).getFolderPath();
    }
    else if (o.getClass().equals(ControlCharacterSet.class))
    {
      charaSet = ((ControlCharacterSet)o).getCurrentCharacterSet();
    }
      
  }
}
