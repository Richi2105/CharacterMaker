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
 * @author richard
 */
public class SpinnerDecimalModel extends AbstractSpinnerModel {
  
  private int minVal;
  private int maxVal;
  private int currentVal;
  
  public SpinnerDecimalModel(int minValue, int maxValue)
  {    
    this.maxVal = maxValue;
    this.minVal = minValue;
    this.currentVal = minValue;
  }
  
  public int getDecimalValue()
  {
    return this.currentVal;
  }
  
  public void setDecimalValue(int val)
  {
    if (val >= minVal && val <= maxVal)
      currentVal = val;
    
    this.fireStateChanged();
  }

  @Override
  public Object getValue() {
    return this.currentVal;
  }

  @Override
  public void setValue(Object value) {
    int val = (Integer) value;
    if (val >= minVal && val <= maxVal)
      currentVal = val;
    
    this.fireStateChanged();
  }

  @Override
  public Object getNextValue() {
    if (currentVal < maxVal)
    {
      currentVal += 1;
    }
    return currentVal;
  }

  @Override
  public Object getPreviousValue() {
    if (currentVal > minVal)
    {
      currentVal -= 1;
    }
    return currentVal;
  }  
}
