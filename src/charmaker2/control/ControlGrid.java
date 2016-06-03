/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.control.dialog.DialogShowBaseClass;
import charmaker2.control.models.SpinnerDecimalModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import charmaker2.view.CharMakerWindow;
import charmaker2.view.GridPane;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Richard
 */
public class ControlGrid extends DialogShowBaseClass implements ActionListener, MouseListener, ChangeListener
{
  private int gridXNumber;
  private int gridYNumber;
  private final GridPane grid;
  private boolean varColumnNumber;
  
  private final JSpinner spinnerColumns;
  private final JSpinner spinnerRows;
  private final SpinnerDecimalModel spinnerModelColumns;
  private final SpinnerDecimalModel spinnerModelRows;
  private final JCheckBox checkBoxVariableColumns;
  
  private final JButton buttonSetGridOK;
  private final JButton buttonSetGridCancel;
  
  private final JLabel labelColumns;
  private final JLabel labelRows;
  
  public ControlGrid(CharMakerWindow view)
  {    
    super(view.getDialogNewCharSet());
    
    gridXNumber = 8;
    gridYNumber = 8;
    this.varColumnNumber = false;

    grid = new GridPane();
    grid.setGrid(gridXNumber, gridYNumber);
    view.getPanelEditor().add(grid);
    grid.setVisible(true);
    
    this.buttonSetGridOK = view.getDialogButtonGridOK();
    this.buttonSetGridOK.addActionListener(this);
    this.buttonSetGridCancel = view.getDialogButtonGridCancel();
    this.buttonSetGridCancel.addActionListener(this);
    
    this.spinnerModelColumns = new SpinnerDecimalModel(5, 32);
    this.spinnerModelRows = new SpinnerDecimalModel(5, 32);
    this.spinnerColumns = view.getSpinnerColumns();
    this.spinnerRows = view.getSpinnerRows();
    this.spinnerColumns.setModel(spinnerModelColumns);
    this.spinnerRows.setModel(spinnerModelRows);
    
    this.spinnerColumns.setValue(gridXNumber);
    this.spinnerRows.setValue(gridYNumber);
    
    this.spinnerColumns.addChangeListener(this);
    this.spinnerRows.addChangeListener(this);
    
    this.checkBoxVariableColumns = view.getCheckBoxColumns();
    this.checkBoxVariableColumns.addActionListener(this);
    this.checkBoxVariableColumns.setSelected(varColumnNumber);
    
    grid.addMouseListener(this);
    
    this.labelColumns = view.getLabelColumns();
    this.labelRows = view.getLabelRows();
  }
  
  public void setLabels()
  {
    this.labelColumns.setText("Columns");
    this.labelRows.setText("Rows");
    this.buttonSetGridOK.setText("Set Grid");
    this.buttonSetGridCancel.setText("Cancel");
    this.checkBoxVariableColumns.setText("Variable Number of Columns");
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
    if (this.varColumnNumber)
      return 0;
    else
      return this.gridXNumber;
  }
  
  public int getYDimension()
  {
    return this.gridYNumber;
  }
  
  public boolean isVariableColumnNumber()
  {
    return this.varColumnNumber;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == this.buttonSetGridCancel)
    {
      this.closeDialog(this.DIALOG_CANCEL);
    }
    else if (e.getSource() == this.buttonSetGridOK)
    {
      this.varColumnNumber = this.checkBoxVariableColumns.isSelected();
      
      if (varColumnNumber)
        this.gridXNumber = 0;
      else
        this.gridXNumber = this.spinnerModelRows.getDecimalValue();
      
      this.gridYNumber = this.spinnerModelColumns.getDecimalValue();
      grid.setGrid(gridXNumber, gridYNumber);
      this.grid.repaint();
      this.closeDialog(this.DIALOG_OK);
    }
    else if (e.getSource() == this.checkBoxVariableColumns)
    {
      if (this.checkBoxVariableColumns.isSelected())
      {
        this.spinnerColumns.setEnabled(false);
      }
      else
      {
        this.spinnerColumns.setEnabled(true);
      }
    }

  }
  
  public GridPane getGridPane()
  {
    return this.grid;
  }

  @Override
  public void mouseClicked(MouseEvent e)
  {
//    grid.fillOneGrid(e.getPoint());
  }

  @Override
  public void mousePressed(MouseEvent e)
  {
    grid.fillOneGrid(e.getPoint());
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

  @Override
  public void stateChanged(ChangeEvent e) {
  /*
    if (e.getSource() == this.spinnerColumns)
    {
      this.gridXNumber = (int) this.spinnerColumns.getValue();
    }
    else if (e.getSource() == this.spinnerRows)
      this.gridYNumber = (int) this.spinnerRows.getValue();
  */
  }
}
