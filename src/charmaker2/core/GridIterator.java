/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.core;

/**
 *
 * @author Richard
 */
public class GridIterator {
  
  public final int xBegin;
  public final int xEnd;
  public final int xDirection;
  public int x;
  
  public final int yBegin;
  public final int yEnd;
  public final int yDirection;
  public int y;
  
  private final int rotation;
  
  public GridIterator(DataGrid grid, int rotation, boolean mirror) throws Exception
  {
    this.rotation = rotation;
    switch (rotation) {
      case HeaderWriter.ROTATION_0: {
        xBegin = mirror ? grid.getXSize()-1 : 0;
        yBegin = 0;
        xEnd = mirror ? 0 : grid.getXSize();
        yEnd = grid.getYSize();
        xDirection = mirror ? -1 : 1;
        yDirection = 1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_90: {
        xBegin = 0;
        yBegin = mirror ? 0 : grid.getXSize()-1;
        xEnd = grid.getYSize();
        yEnd = mirror ? grid.getXSize() : 0;
        xDirection = 1;
        yDirection = mirror ? 1 : -1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_180: {
        xBegin = mirror ? 0 : grid.getXSize()-1;
        yBegin = grid.getYSize()-1;
        xEnd = mirror ? grid.getXSize() : 0;
        yEnd = 0;
        xDirection = mirror ? 1 : -1;
        yDirection = -1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_270: {
        xBegin = grid.getYSize()-1;
        yBegin = mirror ? grid.getXSize()-1 : 0;
        xEnd = 0;
        yEnd = mirror ? 0 : grid.getXSize();
        xDirection = -1;
        yDirection = mirror ? -1 : 1;
        x = xBegin;
        y = yBegin;
      } break;
      default: {
        throw new Exception("invalid parameter");
      }
    }
  }
  
  public int getColumnIterator()
  {
    if (this.rotation == HeaderWriter.ROTATION_0 || this.rotation == HeaderWriter.ROTATION_180)
      return this.x;
    else
      return this.y;
  }
  
  public int getRowIterator()
  {
    if (this.rotation == HeaderWriter.ROTATION_0 || this.rotation == HeaderWriter.ROTATION_180)
      return this.y;
    else
      return this.x;
  }
  
  public boolean conditionX()
  {
    boolean retVal = false;
    if (this.xDirection == -1)
    {
      if (this.x >= this.xEnd)
        retVal = true;
    }
    else // if (this.xDirection == 1)
    {
      if (this.x < this.xEnd)
        retVal = true;
    }    
    return retVal;
  }
  
  public boolean conditionY()
  {
    boolean retVal = false;
    if (this.yDirection == -1)
    {
      if (this.y >= this.yEnd)
        retVal = true;
    }
    else // if (this.yDirection == 1)
    {
      if (this.y < this.yEnd)
        retVal = true;
    }    
    return retVal;
  }
  
}
