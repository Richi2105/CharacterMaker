/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.core;

import java.awt.image.Raster;
import java.io.Serializable;

/**
 *
 * @author Richard
 */
public class DataGrid implements Serializable
{
  private boolean grid[][];
  protected int xSize;
  protected int ySize;
  
  public DataGrid(int xGrid, int yGrid)
  {
    this.xSize = xGrid;
    this.ySize = yGrid;
    this.changeGrid(xGrid, yGrid);
  }
  
  public void changeGrid(int xGrid, int yGrid)
  {
    this.xSize = xGrid;
    this.ySize = yGrid;
    grid = new boolean[xGrid][yGrid];
  }
  
  public int getXSize()
  {
    return xSize;
  }
  
  public int getYSize()
  {
    return ySize;
  }
  
  public boolean[][] getArray()
  {
    return grid;
  }
  
  public boolean isSetAt(int x, int y)
  {
    return grid[x][y];
  }
  
  public boolean isSetAt(DataGridPosition pos)
  {
    return grid[pos.getX()][pos.getY()];
  }
  
  public void setAt(int x, int y)
  {
    grid[x][y] = true;
  }
  
  public void setAt(DataGridPosition pos)
  {
    grid[pos.getX()][pos.getY()] = true;
  }
  
  public void unSetAt(int x, int y)
  {
    grid[x][y] = false;
  }
  
  public void unSetAt(DataGridPosition pos)
  {
    grid[pos.getX()][pos.getY()] = false;
  }
  
  public static DataGrid convert(Raster raster, int height)
  {
    return DataGrid.convert(raster, 0, 0, height);
  }
  
  public static DataGrid convert(Raster raster, int xOffset, int yOffset, int height)
  {
    int xSize = raster.getWidth()+xOffset+xOffset;
    int ySize;
    if (height == 0)
      ySize = raster.getHeight()+yOffset+yOffset;
    else
      ySize = height;
    DataGrid dgrid = new DataGrid(xSize < 5 ? 5 : xSize, ySize < 5 ? 5 : ySize);
//    RSLogger.getLogger().log(Level.INFO, String.format("Raster details: %d,%d; %d,%d; %d", raster.getMinX(),raster.getMinY(),raster.getWidth(),raster.getHeight(),raster.getNumBands()));
    
    int xStart = xOffset > 0 ? 0 : xOffset * (-1);
    int yStart = yOffset > 0 ? 0 : yOffset * (-1);
    
    for (int i=0; i<xSize; i+=1)
    {      
      for (int j=0; j<ySize; j+=1)
      {
        if (raster.getSample(i+raster.getMinX()+xStart, j+raster.getMinY()+yStart, 0) > 127)
        {
          dgrid.setAt(i, j);
        }
      }
      
    }
    
    return dgrid;
  }
  
  public void copy(DataGrid anotherGrid)
  {
    this.xSize = anotherGrid.xSize;
    this.ySize = anotherGrid.ySize;
    this.grid = new boolean[anotherGrid.xSize][anotherGrid.ySize];
    int x = 0;
    int y = 0;
    for (boolean arr[] : anotherGrid.getArray())
    {      
      for (boolean b : arr)
      {
        this.grid[x][y] = b;
        y += 1;
      }
      y = 0;
      x += 1;
    }
  }
}
