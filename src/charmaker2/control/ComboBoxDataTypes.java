/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.View;

import charmaker.View.subsystem.VarBitdepthID;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Richard
 */
public class ComboBoxDataTypes implements ComboBoxModel
{
  private ListDataListener listener;
  private ArrayList<VarBitdepthID> dataTypes;
  private int selectedItem;
  
  public ComboBoxDataTypes()
  {
    dataTypes = new ArrayList<>();
    dataTypes.add(new VarBitdepthID("unsigned char", 8));
    dataTypes.add(new VarBitdepthID("uint8_t", 8));
    dataTypes.add(new VarBitdepthID("uint16_t", 16));
    dataTypes.add(new VarBitdepthID("uint32_t", 32));
    dataTypes.add(new VarBitdepthID("uint64_t", 64));
  }

  @Override
  public void setSelectedItem(Object anItem)
  {
    if (anItem instanceof VarBitdepthID)
    {
      if (dataTypes.contains(anItem))
      {
        selectedItem = dataTypes.indexOf(anItem);
      }
    }
  }

  @Override
  public Object getSelectedItem()
  {
    return dataTypes.get(selectedItem);
  }
  
  public int getSelectedBitDepth()
  {
    return dataTypes.get(selectedItem).getDepth();
  }
  
  public String getSelectedDatatypeName()
  {
    return dataTypes.get(selectedItem).getIdentifier();
  }

  @Override
  public int getSize()
  {
    return dataTypes.size();
  }
  
  public int getIndexOfSelectedItem()
  {
    return selectedItem;
  }
  
  public void updateList()
  {
    if (listener != null)
    {
      listener.contentsChanged(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, 0, dataTypes.size()-1));
    }
  }

  @Override
  public Object getElementAt(int index)
  {
    return dataTypes.get(index);
  }

  @Override
  public void addListDataListener(ListDataListener l)
  {
    this.listener = l;
  }

  @Override
  public void removeListDataListener(ListDataListener l)
  {
    this.listener = null;
  }
}
