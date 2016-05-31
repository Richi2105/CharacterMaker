/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.start;

import charmaker2.util.RSLogger;
import charmaker2.util.SavedSettings;
import charmaker2.control.ControlCharacterSet;
import charmaker2.control.ControlFileOperation;
import charmaker2.control.ControlFontSettings;
import charmaker2.control.ControlGrid;
import charmaker2.control.ControlHeaderWriter;
import charmaker2.control.ControlNewCharset;
import charmaker2.control.ControlPreview;
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
      ControlCharacterSet charListController = new ControlCharacterSet(window, gridController);
      ControlFileOperation fileController = new ControlFileOperation(window, settings);
      fileController.addCharacterListController(charListController);
      ControlFontSettings fontController = new ControlFontSettings(window);
      ControlHeaderWriter outputController = new ControlHeaderWriter(window, fontController);
      outputController.setObservable(charListController).setObservable(fileController);
      ControlNewCharset charsetController = new ControlNewCharset(window, charListController);
      ControlSetCharacter setCharController = new ControlSetCharacter(window, charListController, gridController.getGridPane());
      ControlPreview preview = new ControlPreview(window, charListController);
      preview.setLabels();
      
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
