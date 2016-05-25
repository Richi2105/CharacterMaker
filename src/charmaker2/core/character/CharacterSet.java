/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.core.character;

import charmaker.core.DataGrid;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import charmaker.util.RSLogger;

/**
 *
 * @author Richard
 */
public class CharacterSet implements ListModel
{
  private ArrayList<CharacterDescriptor> characters;
  private int fontHeight;
  private int fontWidth;
  private String fontName;
  private ListDataListener listener;
  
  public CharacterSet(int width, int height, String fontName)
  {
    characters = new ArrayList<>();
    this.fontHeight = height;
    this.fontWidth = width;
    this.fontName = fontName;
    
    RSLogger.getLogger().log(Level.INFO, String.format("new Character set: %s, height: %d", fontName, height));
  }
  
  private void updateListDataListener()
  {
    if (listener != null)
    {
      listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, 0));
    }
  }
  
  public CharacterDescriptor getCharacterAt(int index)
  {
    return characters.get(index);
  }
  
  public String getFontName()
  {
    return fontName;
  }
  
  public int getFontHeight()
  {
    return fontHeight;
  }
  
  public int getFontWidth()
  {
    return fontWidth;
  }
  
  public void addCharacter(char character, Raster raster)
  {
    characters.add(new CharacterDescriptor(DataGrid.convert(raster), Char2Description.getDescription(character), character, fontWidth));
    this.updateListDataListener();
    RSLogger.getLogger().log(Level.INFO, String.format("new Character: %c", character));
  }
  
  public void addCharacter(char character, DataGrid grid)
  {
    characters.add(new CharacterDescriptor(grid, Char2Description.getDescription(character), character, fontWidth));
    this.updateListDataListener();
    RSLogger.getLogger().log(Level.INFO, String.format("new Character: %c", character));

  }

  @Override
  public int getSize()
  {
    return characters.size();
  }

  @Override
  public Object getElementAt(int index)
  {
    return characters.get(index).getDescriptor();
  }
  
  public ArrayList<CharacterDescriptor> getCharacters()
  {
    return characters;
  }

  @Override
  public void addListDataListener(ListDataListener l)
  {
    this.listener = l;
  }

  @Override
  public void removeListDataListener(ListDataListener l)
  {
    this.listener = null;
  }
}
