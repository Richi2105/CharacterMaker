/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control.dialog;

import java.awt.Dialog;
import java.awt.Point;
import java.util.Observable;
import javax.swing.JDialog;

/**
 *
 * @author Richard
 */
public class DialogShowBaseClass extends Observable {
  
  protected final JDialog dialog;
  protected int dialogReturnValue;
  
  public final int DIALOG_OK = 1;
  public final int DIALOG_CANCEL = 0;

  public DialogShowBaseClass(JDialog dialog) {
    this.dialog = dialog;
    this.dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
  }
  
  public int getDialogReturn()
  {
    return this.dialogReturnValue;
  }
  
  public void showDialog(Point p)
  {
    if (p != null)
      this.dialog.setLocation(p);
    this.dialogReturnValue = DIALOG_CANCEL;
    this.dialog.setVisible(true);
    this.dialog.setEnabled(true);
  }
  
  public void showDialog()
  {
    this.showDialog(null);
  }
  
  protected void closeDialog(int dialogReturn)
  {
    this.dialogReturnValue = dialogReturn;
    this.dialog.setVisible(false);
    this.dialog.setEnabled(false);
    this.setChanged();
    this.notifyObservers();
  }
  
}
