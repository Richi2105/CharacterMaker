/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import charmaker.util.RSLogger;
import charmaker2.view.CharMakerWindow;
import charmaker2.view.GridPane;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;

/**
 *
 * @author Richard
 */
public class ControlGrid implements ActionListener, MouseListener
{
  private int gridXNumber;
  private int gridYNumber;
  private final GridPane grid;
  
  private final JSpinner spinnerColumns;
  private final JSpinner spinnerRows;
  private final JCheckBox checkBoxVariableColumns;
  
  public ControlGrid(CharMakerWindow view)
  {    
    gridXNumber = 8;
    gridYNumber = 8;

    grid = new GridPane();
    grid.setGrid(gridXNumber, gridYNumber);
    view.getButtonSetGrid().addActionListener(this);
    SpinnerGridModel sp1 = new SpinnerGridModel(5, 32);
    SpinnerGridModel sp2 = new SpinnerGridModel(5, 32);
    this.spinnerColumns = view.getSpinnerColumns();
    this.spinnerRows = view.getSpinnerRows();
    this.spinnerColumns.setModel(sp1);
    this.spinnerRows.setModel(sp2);
    this.checkBoxVariableColumns = view.getCheckBoxColumns();
    grid.addMouseListener(this);
  }
  
  public void setDimensions(int xSize, int ySize)
  {
    this.gridXNumber = xSize;
    this.gridYNumber = ySize;
    
    if (xSize == 0)
      checkBoxVariableColumns.setSelected(true);
    else
    {
      checkBoxVariableColumns.setSelected(false);
      this.spinnerColumns.setValue(xSize);
    }
    
    this.spinnerRows.setValue(ySize);
    
    this.grid.setGrid(xSize, ySize);
    this.grid.repaint();
  }
  
  public int getXDimension()
  {
    if (checkBoxVariableColumns.isSelected())
      return 0;
    else
      return this.gridXNumber;
  }
  
  public int getYDimension()
  {
    return this.gridYNumber;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    //view.getEditorPane().removeAll();
    //this.grid.repaint();

    //view.getPanelEditorSettings().repaint();
    try
    {
      this.gridYNumber = Integer.decode(this.spinnerRows.getValue().toString());
      this.gridXNumber = Integer.decode(this.spinnerColumns.getValue().toString());
      grid.setGrid(gridXNumber, gridYNumber);
      this.grid.repaint();
    }

    catch (Exception ex)
    {
      RSLogger.getLogger().log(Level.WARNING, "Could not decode Integer", ex);
    }
  }
  
  public GridPane getGridPane()
  {
    return this.grid;
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
    grid.fillOneGrid(e.getPoint());
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
  }

  @Override
  public void mouseReleased(MouseEvent e)
  {
  }

  @Override
  public void mouseEntered(MouseEvent e)
  {
  }

  @Override
  public void mouseExited(MouseEvent e)
  {
  }
}
