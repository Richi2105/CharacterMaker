/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.core.character;

import charmaker2.core.DataGrid;
import charmaker2.util.RSLogger;
import java.awt.image.Raster;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.swing.AbstractListModel;

/**
 *
 * @author Richard
 */
public class CharacterSet extends AbstractListModel<String> implements Serializable
{
  private final ArrayList<CharacterDescriptor> characters;
  private final int fontHeight;
  private final int fontWidth;
  private final String fontName;
  
  public CharacterSet(int width, int height, String fontName)
  {
    characters = new ArrayList<>();
    this.fontHeight = height;
    this.fontWidth = width;
    this.fontName = fontName;
    
    RSLogger.getLogger().log(Level.INFO, String.format("new Character set: %s, height: %d, width: %d", fontName, height, width));
  }
  
  public CharacterDescriptor getCharacterAt(int index)
  {
    return characters.get(index);
  }
  
  public CharacterDescriptor getCharacter(char c)
  {
    for (CharacterDescriptor ch: characters)
    {
      if (ch.getCharacter() == c)
        return ch;
    }
    return null;
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
    if (this.fontWidth == 0)
      characters.add(new CharacterDescriptor(DataGrid.convert(raster, fontHeight),
                                             Char2Description.getDescription(character),
                                             character));
    else
      characters.add(new CharacterDescriptor(DataGrid.convert(raster, fontHeight, fontWidth),
                                             Char2Description.getDescription(character),
                                             character));
    
    this.fireContentsChanged(this, this.characters.size()-1, this.characters.size());
    RSLogger.getLogger().log(Level.INFO, String.format("new Character: %c", character));
  }
  
  public void addCharacter(char character, String description) throws Exception
  {
    if (this.fontWidth != 0)
    {
      this.addCharacter(character, description, new DataGrid(this.fontWidth, this.fontHeight));
    }
    else
    {
      throw new Exception("Invalid Character: no width specified, width is variable");
    }
  }
  
  public void addCharacter(char character, String description, int width)
  {
    this.addCharacter(character, description, new DataGrid(width, this.fontHeight));
  }
  
  public void addCharacter(char character, DataGrid grid)
  {
    this.addCharacter(character, Char2Description.getDescription(character), grid);
  }
  
  public void addCharacter(char character, String description, DataGrid grid)
  {
    characters.add(new CharacterDescriptor(grid, description, character));
    this.fireContentsChanged(this, this.characters.size()-1, this.characters.size());
    RSLogger.getLogger().log(Level.INFO, String.format("new Character: %c", character));
  }
  
  public void removeCharacter(int index)
  {
    this.characters.remove(index);
    this.fireContentsChanged(this, index-1, index);
  }
  
  public void sort()
  {
    //TODO: implement
  }

  @Override
  public int getSize()
  {
    return characters.size();
  }

  @Override
  public String getElementAt(int index)
  {
    return characters.get(index).getDescriptor();
  }
  
  public ArrayList<CharacterDescriptor> getCharacters()
  {
    return characters;
  }
  
  public void update(int index)
  {
    this.fireContentsChanged(this, index-1, index);
  }
}
