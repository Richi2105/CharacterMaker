/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.start;

import charmaker2.util.RSLogger;
import charmaker2.util.SavedSettings;
import charmaker2.control.ControlCharacterSet;
import charmaker2.control.ControlFileIO;
import charmaker2.control.ControlFileOperation;
import charmaker2.control.ControlFontSettings;
import charmaker2.control.ControlGrid;
import charmaker2.control.ControlHeaderWriter;
import charmaker2.control.ControlNewOpenWriteCharset;
import charmaker2.control.ControlPreview;
import charmaker2.control.ControlSaveLoadCharacterSet;
import charmaker2.control.ControlSetCharacter;
import charmaker2.util.RSLogger;
import charmaker2.util.SavedSettings;
import charmaker2.view.CharMakerWindow;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author richard
 */
public class CharMaker {
  
  public CharMaker()
  {
    try {
      CharMakerWindow window = new CharMakerWindow();
      SavedSettings settings = new SavedSettings("settings");
      ControlGrid gridController = new ControlGrid(window);
      gridController.setLabels();
      
      ControlFileIO fileIO = new ControlFileIO(window);
      fileIO.setDefaults(settings);
      
      ControlCharacterSet charListController = new ControlCharacterSet(window, gridController);
      charListController.setLabels();
      
      //ControlFileOperation fileController = new ControlFileOperation(window, settings);
      //fileController.addCharacterListController(charListController);
      ControlFontSettings fontController = new ControlFontSettings(window);
      fontController.setLabels();
      //outputController.setObservable(charListController).setObservable(fileController);
      
      ControlNewOpenWriteCharset charsetController = new ControlNewOpenWriteCharset(window, charListController, gridController, fileIO, fontController);
      charsetController.setLabels();
      
      ControlPreview preview = new ControlPreview(window, charListController);
      preview.setLabels();
      charListController.setPreviewController(preview);
      
      ControlSaveLoadCharacterSet io = new ControlSaveLoadCharacterSet(window, charListController, fileIO);
      io.setLabels();
      
      window.setVisible(true);
      
    } catch (IOException ex) {
      RSLogger.getLogger().log(Level.SEVERE, null, ex);
    }
    
  }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new CharMaker();
    }
    
}
