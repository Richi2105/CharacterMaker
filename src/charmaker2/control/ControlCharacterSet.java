/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

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
import javax.swing.JButton;
import javax.swing.JList;

/**
 *
 * @author Richard
 */
public class ControlCharacterSet extends Observable implements ListSelectionListener, ActionListener
{
  private final JList<String> characterList;
  private CharacterSet charSet;
  private final ControlGrid grid;
  private int currentSelection;
  private final JButton buttonAdd;
  private final JButton buttonRemove;
  private final JButton buttonSort;
  
  public ControlCharacterSet(CharMakerWindow view, ControlGrid grid)
  {
    this.grid = grid;
    this.charSet = new CharacterSet(0, 0, "");
    this.characterList = view.getListChars();
    
    this.buttonAdd = view.getButtonAddChar();
    this.buttonRemove = view.getButtonRemoveChar();
    this.buttonSort = view.getButtonSortChars();
    
    this.buttonAdd.addActionListener(this);
    this.buttonRemove.addActionListener(this);
    this.buttonSort.addActionListener(this);
    
    this.characterList.addListSelectionListener(this);
    this.characterList.clearSelection();
    this.characterList.removeAll();
    this.characterList.setModel(charSet);
    this.currentSelection = 0;
  }
  
  public void setLabels()
  {
    this.buttonAdd.setText("add Character");
    this.buttonRemove.setText("remove Character");
    this.buttonSort.setText("Sort by ASCII value");
  }
  
  public void setCurrentCharacterSet(CharacterSet charset)
  {
    this.charSet = charset;
    this.currentSelection = 0;
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
  
  public CharacterDescriptor getSelectedCharacterDescriptor()
  {
    return this.charSet.getCharacterAt(currentSelection);
  }
  
  public void addCharacter(CharacterDescriptor character)
  {
    this.charSet.addCharacter(character.getCharacter(), character.getGrid());
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    this.currentSelection = this.characterList.getSelectedIndex();
    if (this.currentSelection >= 0)
    {
      grid.setDimensions(charSet.getCharacterAt(this.currentSelection).getGrid().getXSize(),
                         charSet.getCharacterAt(this.currentSelection).getGrid().getYSize());
      grid.getGridPane().setGrid(charSet.getCharacterAt(this.currentSelection).getGrid());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.buttonAdd)
    {
      //todo: show dialog
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
  }
}
