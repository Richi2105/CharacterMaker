/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control.models;

import javax.swing.AbstractSpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Richard
 */
public class SpinnerHexaModel extends AbstractSpinnerModel {
  
  private final int minVal;
  private final int maxVal;
  private int currentVal;
  private String currentValString;
  private final String FORMAT = "0x%02x";
  
  public SpinnerHexaModel(int minVal, int maxVal)
  {
    this.minVal = minVal;
    this.maxVal = maxVal;
    this.currentVal = minVal;
    this.currentValString = String.format(FORMAT, currentVal);
  }
  
  public int getDecimalValue()
  {
    return this.currentVal;
  }
  
  public void setDecimalValue(int val)
  {
    if (val >= minVal && val <= maxVal)
      currentVal = val;
    
    this.currentValString = String.format(FORMAT, currentVal);
  }


  @Override
  public Object getValue() {
    return this.currentValString;
  }

  @Override
  public void setValue(Object value) {
    int val = 0;
    if (value instanceof String)
      val = Integer.decode((String)value);
    else if (value instanceof Integer)
      val = (Integer)value;
    
    if (val >= minVal && val <= maxVal)
      currentVal = val;
    
    this.currentValString = String.format(FORMAT, currentVal);

    this.fireStateChanged();
  }  

  @Override
  public Object getNextValue() {
    if (currentVal < maxVal)
    {
      currentVal += 1;
      this.currentValString = String.format(FORMAT, currentVal);
    }
    return currentValString;
  }

  @Override
  public Object getPreviousValue() {
    if (currentVal > minVal)
    {
      currentVal -= 1;
      this.currentValString = String.format(FORMAT, currentVal);
    }
    return currentValString;
  } 
}
