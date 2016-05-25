/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author richard
 */
public class SpinnerGridModel implements SpinnerModel {
  
  private int minVal;
  private int maxVal;
  private int currentVal;
  
  private ChangeListener changeListener;
  
  public SpinnerGridModel(int minValue, int maxValue)
  {    
    this.maxVal = maxValue;
    this.minVal = minValue;
    this.currentVal = minValue;
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
    
    if (changeListener != null)
      changeListener.stateChanged(new ChangeEvent(this));
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
    if (currentVal > maxVal)
    {
      currentVal -= 1;
    }
    return currentVal;
  }

  @Override
  public void addChangeListener(ChangeListener l) {
    this.changeListener = l;
  }

  @Override
  public void removeChangeListener(ChangeListener l) {
    this.changeListener = null;
  }
  
}
