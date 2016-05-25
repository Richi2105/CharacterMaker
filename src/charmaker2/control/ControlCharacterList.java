/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker.core.character.CharacterDescriptor;
import charmaker.core.character.CharacterSet;
import java.util.Observable;
import java.util.logging.Level;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import charmaker.util.RSLogger;
import charmaker2.view.CharMakerWindow;
import charmaker2.view.GridPane;

/**
 *
 * @author Richard
 */
public class ControlCharacterList extends Observable implements ListSelectionListener
{
  private CharMakerWindow view;
  private CharacterSet charSet;
  private ControlGrid grid;
  private int currentSelection;
  
  public ControlCharacterList(CharMakerWindow view, ControlGrid grid)
  {
    this.view = view;
    this.grid = grid;
    view.getListChars().addListSelectionListener(this);
    view.getListChars().clearSelection();
    view.getListChars().removeAll();
    this.currentSelection = 0;
  }
  
  public void setCurrentCharacterSet(CharacterSet charset)
  {
    this.charSet = charset;
    this.currentSelection = 0;
    view.getListChars().clearSelection();
    view.getListChars().removeAll();
    view.getListChars().setModel(charset);
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
    this.currentSelection = view.getListChars().getSelectedIndex();
    grid.getGridPane().setGrid(charSet.getCharacterAt(this.currentSelection).getGrid());
  }
}
