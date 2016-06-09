/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.control.models.CharacterData;
import charmaker2.core.DataGrid;
import charmaker2.core.character.CharacterSet;
import java.util.Observable;
import java.util.logging.Level;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import charmaker2.core.character.CharacterDescriptor;
import charmaker2.util.RSLogger;
import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observer;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Richard
 */
public class ControlCharacterSet extends Observable implements ListSelectionListener, ActionListener, MouseListener, Observer
{
  private int mode;
  private final int MODE_ADD = 1;
  private final int MODE_EDIT = 2;
  
  private final JList<String> characterList;
  private CharacterSet charSet;
  private final ControlGrid grid;
  private int currentSelection;
  
  private final JButton buttonAdd;
  private final JButton buttonRemove;
  private final JButton buttonSort;
  private final JButton buttonSet;
  private final JButton buttonPreview;
  private final JButton buttonEdit;
  
  private ControlPreview previewController;
  
  private final JTabbedPane tabPane;
  
  private final ControlAddCharacter addCharacterController;
  
  public ControlCharacterSet(CharMakerWindow view, ControlGrid grid)
  {
    this.mode = 0;
    
    this.grid = grid;
    this.charSet = new CharacterSet(8, 8, "");
    this.characterList = view.getListChars();
    
    this.buttonAdd = view.getButtonAddChar();
    this.buttonRemove = view.getButtonRemoveChar();
    this.buttonSort = view.getButtonSortChars();
    this.buttonSet = view.getButtonSetChar();
    this.buttonPreview = view.getButtonShowPreview();
    this.buttonEdit = view.getButtonEditChar();
    
    this.buttonAdd.addActionListener(this);
    this.buttonRemove.addActionListener(this);
    this.buttonSort.addActionListener(this);
    this.buttonSet.addActionListener(this);
    this.buttonPreview.addActionListener(this);
    this.buttonEdit.addActionListener(this);
    
    this.characterList.addListSelectionListener(this);
//    this.characterList.addMouseListener(this);  //todo!
    this.characterList.clearSelection();
    this.characterList.removeAll();
    this.characterList.setModel(charSet);
    this.currentSelection = -1;
    
    this.addCharacterController = new ControlAddCharacter(view);
    this.addCharacterController.setLabels();
    this.previewController = null;
    
    this.tabPane = view.getTabbedPaneFont();
  }
  
  public void setPreviewController(ControlPreview previewController)
  {
    this.previewController = previewController;
  }
  
  public void setLabels()
  {
    this.buttonAdd.setText("add Character");
    this.buttonRemove.setText("remove Character");
    this.buttonSort.setText("Sort by ASCII value");
    this.buttonPreview.setText("Show Preview");
    this.buttonSet.setText("Set Character");
    this.buttonEdit.setText("Edit Character");
    
    this.tabPane.setTitleAt(0, "Output Format");
    this.tabPane.setTitleAt(1, "Character List");
  }
  
  public void setCurrentCharacterSet(CharacterSet charset)
  {
    this.charSet = charset;
    this.currentSelection = -1;
    this.characterList.clearSelection();
    this.characterList.removeAll();
    this.characterList.setModel(charset);
    grid.setDimensions(charset.getFontWidth(), charset.getFontHeight());
    this.setChanged();
    this.notifyObservers();
    RSLogger.getLogger().log(Level.INFO, String.format("set a new CharacterSet %s", charset.getFontName()));
  }
  
  public CharacterSet getCurrentCharacterSet()
  {
    return charSet;
  }
  
  public CharacterDescriptor getSelectedCharacterDescriptor() throws Exception
  {
    if (this.currentSelection != -1)
      return this.charSet.getCharacterAt(currentSelection);
    
    return null;
  }
  
  public void addCharacter(CharacterDescriptor character)
  {
    this.charSet.addCharacter(character.getCharacter(), character.getGrid());
    this.setChanged();
    this.notifyObservers();
  }
  
  public void addCharacter(char c, String description)
  {
    this.charSet.addCharacter(c, description, new DataGrid(charSet.getFontWidth(), charSet.getFontHeight()));
    this.setChanged();
    this.notifyObservers();
  }
  
  public void addCharacter(char c, String description, int width)
  {
    if (this.charSet.getFontWidth() == 0)
      this.charSet.addCharacter(c, description, width);
    else
    {
      try {
        this.charSet.addCharacter(c, description);
      } catch (Exception ex) {
        RSLogger.getLogger().log(Level.SEVERE, null, ex);
      }
    }
    this.setChanged();
    this.notifyObservers();
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    this.currentSelection = this.characterList.getSelectedIndex();
    if (this.currentSelection >= 0)
    {
      try {
        grid.setDimensions(charSet.getCharacterAt(this.currentSelection).getGrid().getXSize(),
                charSet.getCharacterAt(this.currentSelection).getGrid().getYSize());
        grid.getGridPane().setGrid(charSet.getCharacterAt(this.currentSelection).getGrid());
      } catch (Exception ex) {
        RSLogger.getLogger().log(Level.SEVERE, null, ex);
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.buttonAdd)
    {
      this.mode = MODE_ADD;
      this.addCharacterController.addObserver(this);
      this.addCharacterController.showDialog(this.buttonAdd.getLocationOnScreen(),
                                             this.grid.isVariableColumnNumber(),
                                             this.charSet.getFontWidth());
    }
    else if (e.getSource() == this.buttonRemove)
    {
      if (this.characterList.getSelectedIndex() != -1)
      {
        int selection = this.characterList.getSelectedIndex();
        this.characterList.clearSelection();
        this.charSet.removeCharacter(selection);
        this.characterList.setSelectedIndex(selection);
      }
    }
    else if (e.getSource() == this.buttonSort)
    {
      this.charSet.sort();
    }
    else if (e.getSource() == this.buttonPreview)
    {
      if (this.currentSelection != -1 && this.previewController != null)
      {
        try {
          CharacterDescriptor selection = this.charSet.getCharacterAt(this.currentSelection);
          this.previewController.addCharacter(selection.getGrid());
        } catch (Exception ex) {
          RSLogger.getLogger().log(Level.SEVERE, null, ex);
        }
      }
    }
    else if (e.getSource() == this.buttonSet)
    {
      if (this.currentSelection != -1)
      {
        try {
          CharacterDescriptor selection = this.charSet.getCharacterAt(this.currentSelection);
          selection.setGrid(grid.getGridPane().getGrid());
        } catch (Exception ex) {
          RSLogger.getLogger().log(Level.SEVERE, null, ex);
        }
      }
    }
    else if (e.getSource() == this.buttonEdit)
    {
      if (this.currentSelection != -1)
      {
        try {
          this.mode = MODE_EDIT;
          this.addCharacterController.addObserver(this);
          CharacterDescriptor selection = this.charSet.getCharacterAt(this.currentSelection);
          this.addCharacterController.showDialog(selection,
                  this.buttonEdit.getLocationOnScreen(),
                  this.grid.isVariableColumnNumber(),
                  this.charSet.getFontWidth());
        } catch (Exception ex) {
          RSLogger.getLogger().log(Level.SEVERE, null, ex);
        }
      }
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    int decicion = this.addCharacterController.getDialogReturn();
    if (decicion == this.addCharacterController.DIALOG_OK)
    {
      CharacterData d = this.addCharacterController.getCharacterData();
      switch (mode) {
        case MODE_ADD: {          
          this.addCharacter(d.character, d.description, d.width);
          this.currentSelection = this.charSet.getSize()-1;
          this.characterList.setSelectedIndex(this.currentSelection);
          this.characterList.ensureIndexIsVisible(this.currentSelection);
          this.valueChanged(null);
          break;
        }
        case MODE_EDIT: {
        try {
          this.getSelectedCharacterDescriptor().setCharacter(d.character);
          this.getSelectedCharacterDescriptor().setDescription(d.description);
          this.getSelectedCharacterDescriptor().setWidth(d.width);
          this.charSet.update(this.characterList.getSelectedIndex());
          this.characterList.setSelectedIndex(this.currentSelection);
          this.characterList.ensureIndexIsVisible(this.currentSelection);
          this.valueChanged(null);
          break;
        } catch (Exception ex) {
          RSLogger.getLogger().log(Level.SEVERE, null, ex);
        }
        }
        default: break;
      }
      
    }
    this.addCharacterController.deleteObserver(this);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void mousePressed(MouseEvent e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
