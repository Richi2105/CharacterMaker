/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.view;

import charmaker.core.DataGrid;
import charmaker.core.DataGridPosition;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Richard
 */
public class GridPane extends JPanel
{
  private Line2D.Double line;
  private Rectangle2D.Double rectangle;
  
  private DataGrid grid;
  
  private int rows;
  private int columns;
  private int xSize;
  private int ySize;
  private double stepSize;
  
  public GridPane()
  {
    line = new Line2D.Double();
    rectangle = new Rectangle2D.Double();
    columns = 5;
    rows = 5;
    this.setBackground(Color.white);
    this.setOpaque(true);
    
    grid = new DataGrid(columns, rows);
  }
  
  private void calculateDimensions()
  {
    xSize = this.getWidth();
    stepSize = xSize / columns;
    ySize = (int) (stepSize * rows);
    if (ySize > this.getHeight())
    {
      ySize = this.getHeight();
      stepSize = ySize / rows;
      xSize = (int) (stepSize * columns);
    }
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    this.calculateDimensions();
    this.fillAllGrid((Graphics2D) g);
    this.paintGrid((Graphics2D) g);    
  }
  
  public void setGrid(int columns, int rows)
  {
    if (columns != 0)
    {
      this.columns = columns;
    }
    else
    {
      this.columns = 32;
    }
    this.rows = rows;
    grid.changeGrid(columns, rows);
    
    this.repaint();
    
  }
  
  public DataGrid getGrid()
  {
    return this.grid;
  }
  
  public void setGrid(DataGrid dGrid)
  {
    this.grid.copy(dGrid);
    
    this.repaint();
  }
  
  public DataGridPosition fillOneGrid(Point p)
  {
//    this.calculateDimensions();
    int xPos = (int) (p.getX() / stepSize);
    int yPos = (int) (p.getY() / stepSize);
    DataGridPosition pos = new DataGridPosition(xPos, yPos);
    
    rectangle.setFrame(xPos*stepSize, yPos*stepSize, stepSize, stepSize);
    Graphics2D g2 = (Graphics2D) this.getGraphics();
    if (grid.isSetAt(pos))
    {
      g2.setColor(Color.white);
      grid.unSetAt(pos);
    }
    else
    {
      g2.setColor(Color.black);
      grid.setAt(pos);
    }
    g2.fill(rectangle);
    g2.draw(rectangle);
    this.paintGrid(g2);
    g2.dispose();
    
    return pos;    
  }
  
  private void fillAllGrid(Graphics2D g2)
  {
    int xPos = 0;
    int yPos = 0;
    for (boolean arr[] : grid.getArray())
    {      
      for (boolean b : arr)
      {
        if (b)
        {
          g2.setColor(Color.black);
        }
        else
        {
          g2.setColor(Color.white);          
        }
        rectangle.setFrame(stepSize*xPos, stepSize*yPos, stepSize, stepSize);
        g2.fill(rectangle);
        g2.draw(rectangle);
        yPos += 1;
      }
      xPos += 1;
      yPos = 0;
    }
  }
  
  private void paintGrid(Graphics2D g2)
  {    
    g2.setColor(Color.black);
    int i;
    for (i=1; i<columns; i+=1)
    {
      line.setLine(stepSize*i, 0, stepSize*i, ySize);
      g2.draw(line);
    }
    for (i=1; i<rows; i+=1)
    {
      line.setLine(0, stepSize*i, xSize, stepSize*i);
      g2.draw(line);
    }
    this.setSize(xSize, ySize);
  }
}
