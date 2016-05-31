/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import charmaker2.control.models.CharacterData;
import charmaker2.control.models.SpinnerDecimalModel;
import charmaker2.control.models.SpinnerHexaModel;
import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javafx.beans.InvalidationListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Richard
 */
public class ControlAddCharacter extends Observable implements ActionListener, ChangeListener {
  
  private final JDialog dialog;
  private final Object semaphore;
  private int dialogReturn;
  
  private final JButton buttonOK;
  private final JButton buttonCancel;
  private final JSpinner spinnerInt;
  private final JSpinner spinnerHex;
  private final JTextField textFieldCharacter;
  private final JTextField textFieldDescription;
  
  private final SpinnerDecimalModel spinnerModelDecimal;
  private final SpinnerHexaModel spinnerModelHex;
  
  private final CharacterData characterData;
  
  private boolean wait;
  
  private InvalidationListener listener;
  
  public final int DIALOG_OK = 1;
  public final int DIALOG_CANCEL = 0;
  
  public ControlAddCharacter(CharMakerWindow view)
  {
    this.dialog = view.getDialogAddCharacter();
    
    view.getDialogButtonCancel().setText("Cancel");
    view.getDialogButtonOK().setText("OK");
    
    this.buttonCancel = view.getDialogButtonCancel();
    this.buttonOK = view.getDialogButtonOK();
    
    this.buttonCancel.addActionListener(this);
    this.buttonOK.addActionListener(this);
    
    view.getDialogLabelCharacter().setText("Character");
    view.getDialogLabelCharacterChar().setText("char");
    view.getDialogLabelCharacterHex().setText("Hex");
    view.getDialogLabelCharacterInt().setText("Dec");
    view.getDialogLabelDescription().setText("Description");
    
    this.spinnerInt = view.getDialogSpinnerCharacterInt();
    this.spinnerHex = view.getDialogSpinnerCharacterHex();
    
    this.spinnerModelDecimal = new SpinnerDecimalModel(0, 255);
    this.spinnerModelHex = new SpinnerHexaModel(0, 255);
    
    this.spinnerInt.setModel(spinnerModelDecimal);
    this.spinnerHex.setModel(spinnerModelHex);
    
    this.spinnerInt.addChangeListener(this);
    this.spinnerHex.addChangeListener(this);
    
    this.textFieldCharacter = view.getDialogTextFieldCharacterChar();
    this.textFieldDescription = view.getDialogTextFieldDescription();
    
    this.textFieldCharacter.addActionListener(this);
    this.textFieldCharacter.setColumns(1);
    
    this.characterData = new CharacterData();
    
    this.listener = null;
    
    this.semaphore = new Object();    
  }
  
  public CharacterData getCharacterData()
  {
    return this.characterData;
  }
  
  public int getDialogReturn()
  {
    return this.dialogReturn;
  }
  
  private void setCharacterChar(char c)
  {
    this.textFieldCharacter.setText(String.format("%c", c));
    this.spinnerModelDecimal.setValue((int) c);
    this.spinnerModelHex.setValue((int) c);
  }
  
  private void setCharacterDec(int d)
  {
    this.textFieldCharacter.setText(String.format("%c", (char) d));
    this.spinnerModelDecimal.setValue(d);
    this.spinnerModelHex.setValue(d);
  }
  
  public void showDialog()
  {
    this.dialogReturn = DIALOG_CANCEL;
    this.dialog.setVisible(true);
    this.dialog.setEnabled(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    
    if (e.getSource() == this.buttonCancel)
    {
      this.dialog.setVisible(false);
      this.dialog.setEnabled(false);
      this.dialogReturn = this.DIALOG_CANCEL;
      this.setChanged();
      this.notifyObservers();
    }
    else if (e.getSource() == this.buttonOK)
    {
      this.dialog.setVisible(false);
      this.dialog.setEnabled(false);
      this.dialogReturn = this.DIALOG_OK;
      this.characterData.character = this.textFieldCharacter.getText().charAt(0);
      this.characterData.description = this.textFieldDescription.getText();
      this.setChanged();
      this.notifyObservers();
    }
    else if (e.getSource() == this.textFieldCharacter)
    {
      this.characterData.character = this.textFieldCharacter.getText().charAt(0);
      this.setCharacterChar(this.characterData.character);
    }   
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (e.getSource() == this.spinnerHex)
    {
      this.spinnerHex.removeChangeListener(this);
      this.spinnerInt.removeChangeListener(this);
      this.setCharacterDec(this.spinnerModelHex.getDecimalValue());
      this.spinnerInt.addChangeListener(this);
      this.spinnerHex.addChangeListener(this);
    }
    else if (e.getSource() == this.spinnerInt)
    {
      this.spinnerHex.removeChangeListener(this);
      this.spinnerInt.removeChangeListener(this);
      this.setCharacterDec(this.spinnerModelDecimal.getDecimalValue());
      this.spinnerInt.addChangeListener(this);
      this.spinnerHex.addChangeListener(this);
    }
  }  
}
