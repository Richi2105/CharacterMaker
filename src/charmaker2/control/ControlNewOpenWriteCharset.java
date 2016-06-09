/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import charmaker2.core.BitmapReader;
import charmaker2.core.DataGrid;
import charmaker2.core.character.CharacterSet;
import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 *
 * @author richard
 */
public class ControlNewOpenWriteCharset implements ActionListener, Observer
{
    
    private CharMakerWindow view;
    private final ControlCharacterSet list;
    
    private int operation;
    private final int OPERATION_OPEN = 0;
    private final int OPERATION_SAVE = 1;
    private final int OPERATION_NEW = 2;
    
    private final JButton buttonNew;
    private final JButton buttonOpen;
    private final JButton buttonWrite;
    
    private final ControlHeaderWriter headerWriter;
    private final ControlGrid gridController;
    private final ControlFontSettings fontSettings;
    private final ControlFileIO fileController;
    
    public ControlNewOpenWriteCharset(CharMakerWindow view,
                             ControlCharacterSet list,
                             ControlGrid gridController,
                             ControlFileIO fileController,
                             ControlFontSettings settings)
    {
        this.view = view;
        this.list = list;
        this.buttonNew = view.getButtonNew();
        this.buttonOpen = view.getButtonOpen();
        this.buttonWrite = view.getButtonSave();
        
        this.buttonNew.addActionListener(this);
        this.buttonOpen.addActionListener(this);
        this.buttonWrite.addActionListener(this);
        
        this.headerWriter = new ControlHeaderWriter();
        
        this.gridController = gridController;
        this.fileController = fileController;
        this.fontSettings = settings;
    }
    
    public void setLabels()
    {
      this.buttonNew.setText("New Character Set");
      this.buttonOpen.setText("Open Bitmap Font");
      this.buttonWrite.setText("Write Header File");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource() == this.buttonNew)
      {
        this.operation = OPERATION_NEW;
        this.gridController.addObserver(this);
        this.gridController.showDialog(this.buttonNew.getLocationOnScreen());
      }
      else if (e.getSource() == this.buttonOpen)
      {
        this.operation = OPERATION_OPEN;
        this.fileController.addObserver(this);
        this.fileController.showOpenDialog();
      }
      else if (e.getSource() == this.buttonWrite)
      {
        this.operation = OPERATION_SAVE;
        this.fileController.setFile(String.format("FONT_%s.h", this.fontSettings.getFontName()));
        this.fileController.addObserver(this);
        this.fileController.showSaveDialog();
      }
    }    

  @Override
  public void update(Observable o, Object arg) {
    switch (this.operation) {
      case OPERATION_NEW: {
        if (this.gridController.getDialogReturn() == this.gridController.DIALOG_OK)
          {
          int x = this.gridController.getXDimension();
          int y = this.gridController.getYDimension();
          String name = view.getTextFieldFontName().getText();
          CharacterSet set = new CharacterSet(x, y, name);
          if (this.gridController.isCompleteCharacterSet())
          {
            for (char c = ' '; c <= '~'; c += 1)
            {
                set.addCharacter(c, new DataGrid(x, y));
            }
          }
          list.setCurrentCharacterSet(set);
        }
        this.gridController.deleteObserver(this);
      } break;
      
      case OPERATION_OPEN: {
        this.fileController.deleteObserver(this);
        if (this.fileController.getApproveOption() == JFileChooser.APPROVE_OPTION)
        {
          File f = new File(this.fileController.getFile());
          BitmapReader.readBitmap(f, this.list);
        }        
      } break;
      
      case OPERATION_SAVE: {
        this.fileController.deleteObserver(this);
        if (this.fileController.getApproveOption() == JFileChooser.APPROVE_OPTION)
        {
          this.headerWriter.writeOut(this.list.getCurrentCharacterSet(),
                                   this.fontSettings.getFontSettings(),
                                   this.fileController.getFile());
        }
      } break;
      default: break;
    }
    
  }
}
