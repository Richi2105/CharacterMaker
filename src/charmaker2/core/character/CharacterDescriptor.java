/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.core.character;

import charmaker2.core.DataGrid;

/**
 *
 * @author Richard
 */
public class CharacterDescriptor
{
  private DataGrid characterGrid;
  private String descriptor;
  private char character;
  private int width;
  public CharacterDescriptor(DataGrid grid, String description, char c, int width)
  {
    this.characterGrid = grid;
    this.descriptor = description;
    this.width = width;
    this.character = c;
  }
  
  public DataGrid getGrid()
  {
    return characterGrid;
  }
  
  public void setGrid(DataGrid grid)
  {
    this.characterGrid.copy(grid);
  }
  
  public int getWidth()
  {
    return characterGrid.getXSize();
  }
  
  public String getDescriptor()
  {
    return descriptor;
  }
  
  public char getCharacter()
  {
    return this.character;
  }
  
}
