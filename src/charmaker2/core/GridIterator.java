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
  
  public int xBegin;
  public int xEnd;
  public int xDirection;
  public int x;
  
  public int yBegin;
  public int yEnd;
  public int yDirection;
  public int y;
  
  private int rotation;
  
  public GridIterator(DataGrid grid, int rotation, int mirror) throws Exception
  {
    boolean mirrorHorizontal = false;
    boolean mirrorVertical = false;
    switch (mirror) {
      case HeaderWriter.MIRROR_NONE: {
        mirrorHorizontal = false;
        mirrorVertical = false;
      } break;
      case HeaderWriter.MIRROR_HORIZONTAL: {
        mirrorHorizontal = true;
        mirrorVertical = false;
      } break;
      case HeaderWriter.MIRROR_VERTICAL: {
        mirrorHorizontal = false;
        mirrorVertical = true;
      } break;
      case HeaderWriter.MIRROR_HORIZONTAL_VERTICAL: {
        mirrorHorizontal = true;
        mirrorVertical = true;
      } break;
    }
    this.init(grid, rotation, mirrorHorizontal, mirrorVertical);
  }
  
  public GridIterator(DataGrid grid, int rotation, boolean mirrorHorizontal, boolean mirrorVertical) throws Exception
  {
    this.init(grid, rotation, mirrorHorizontal, mirrorVertical);
  }
  
  private void init(DataGrid grid, int rotation, boolean mirrorHorizontal, boolean mirrorVertical) throws Exception
  {
    this.rotation = rotation;
    switch (rotation) {
      case HeaderWriter.ROTATION_0: {
        xBegin = mirrorHorizontal ? grid.getXSize()-1 : 0;
        yBegin = mirrorVertical ? 0 : grid.getYSize()-1;
        xEnd = mirrorHorizontal ? 0 : grid.getXSize();
        yEnd = mirrorVertical ? grid.getYSize() : 0;
        xDirection = mirrorHorizontal ? -1 : 1;
        yDirection = mirrorVertical ? 1 : -1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_90: {
        xBegin = mirrorVertical ? grid.getYSize()-1 : 0;
        yBegin = mirrorHorizontal ? grid.getXSize()-1 : 0;
        xEnd = mirrorVertical ? 0 : grid.getYSize();
        yEnd = mirrorHorizontal ? 0 : grid.getXSize();
        xDirection = mirrorVertical ? -1 : 1;
        yDirection = mirrorHorizontal ? -1 : 1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_180: {
        xBegin = mirrorHorizontal ? 0 : grid.getXSize()-1;
        yBegin = mirrorVertical ? grid.getYSize()-1 : 0;
        xEnd = mirrorHorizontal ? grid.getXSize() : 0;
        yEnd = mirrorVertical ? 0 : grid.getYSize();
        xDirection = mirrorHorizontal ? 1 : -1;
        yDirection = mirrorVertical ? -1 : 1;
        x = xBegin;
        y = yBegin;
      } break;
      case HeaderWriter.ROTATION_270: {
        xBegin = mirrorVertical ? 0 : grid.getYSize()-1;
        yBegin = mirrorHorizontal ? 0 : grid.getXSize()-1;
        xEnd = mirrorVertical ? grid.getYSize() : 0;
        yEnd = mirrorHorizontal ? grid.getXSize() : 0;
        xDirection = mirrorVertical ? 1 : -1;
        yDirection = mirrorHorizontal ? 1 : -1;
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
