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
public class DataGridPosition
{
  private int x;
  private int y;
  
  public DataGridPosition()
  {
    this.x = 0;
    this.y = 0;
  }
  
  public DataGridPosition(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }
}
