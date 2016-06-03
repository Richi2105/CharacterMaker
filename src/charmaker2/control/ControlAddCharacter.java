/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import charmaker2.control.dialog.DialogShowBaseClass;
import charmaker2.control.models.CharacterData;
import charmaker2.control.models.SpinnerDecimalModel;
import charmaker2.control.models.SpinnerHexaModel;
import charmaker2.core.character.CharacterDescriptor;
import charmaker2.view.CharMakerWindow;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Richard
 */
public class ControlAddCharacter extends DialogShowBaseClass implements ActionListener, ChangeListener {
    
  private final JButton buttonOK;
  private final JButton buttonCancel;
  private final JSpinner spinnerInt;
  private final JSpinner spinnerHex;
  private final JSpinner spinnerColumns;
  private final JTextField textFieldCharacter;
  private final JTextField textFieldDescription;
  
  private final JLabel labelCharacter;
  private final JLabel labelCharacterChar;
  private final JLabel labelCharacterDec;
  private final JLabel labelCharacterHex;
  private final JLabel labelDescription;
  private final JLabel labelColumns;
  
  private final SpinnerDecimalModel spinnerModelDecimal;
  private final SpinnerHexaModel spinnerModelHex;
  private final SpinnerDecimalModel spinnerModelColumns;
  
  private final CharacterData characterData;
  
  public final int DIALOG_OK = 1;
  public final int DIALOG_CANCEL = 0;
  
  public ControlAddCharacter(CharMakerWindow view)
  {
    super(view.getDialogAddCharacter());
    
    view.getDialogButtonCancel().setText("Cancel");
    view.getDialogButtonOK().setText("OK");
    
    this.buttonCancel = view.getDialogButtonCancel();
    this.buttonOK = view.getDialogButtonOK();
    
    this.buttonCancel.addActionListener(this);
    this.buttonOK.addActionListener(this);
    
    this.labelCharacter = view.getDialogLabelCharacter();
    this.labelCharacterChar = view.getDialogLabelCharacterChar();
    this.labelCharacterHex = view.getDialogLabelCharacterHex();
    this.labelCharacterDec = view.getDialogLabelCharacterInt();
    this.labelDescription = view.getDialogLabelDescription();
    this.labelColumns = view.getDialogLabelColumns();
    
    this.spinnerInt = view.getDialogSpinnerCharacterInt();
    this.spinnerHex = view.getDialogSpinnerCharacterHex();
    this.spinnerColumns = view.getDialogSpinnerColumns();
    
    this.spinnerModelDecimal = new SpinnerDecimalModel(0, 255);
    this.spinnerModelHex = new SpinnerHexaModel(0, 255);
    this.spinnerModelColumns = new SpinnerDecimalModel(1, 32);
    
    this.spinnerInt.setModel(spinnerModelDecimal);
    this.spinnerHex.setModel(spinnerModelHex);
    this.spinnerColumns.setModel(spinnerModelColumns);
    
    this.spinnerInt.addChangeListener(this);
    this.spinnerHex.addChangeListener(this);
    
    this.textFieldCharacter = view.getDialogTextFieldCharacterChar();
    this.textFieldDescription = view.getDialogTextFieldDescription();
    
    this.textFieldCharacter.addActionListener(this);
    this.textFieldCharacter.setColumns(1);
    
    this.characterData = new CharacterData();
    
    this.setCharacterChar('a');
  }
  
  public void setLabels()
  {
    this.labelCharacter.setText("Character");
    this.labelCharacterChar.setText("char");
    this.labelCharacterHex.setText("Hex");
    this.labelCharacterDec.setText("Dec");
    this.labelDescription.setText("Description");
    this.labelColumns.setText("Columns");
  }
  
  public CharacterData getCharacterData()
  {
    return this.characterData;
  }
  
  private void setCharacterChar(char c)
  {
    this.spinnerHex.removeChangeListener(this);
    this.spinnerInt.removeChangeListener(this);
    
    this.textFieldCharacter.setText(String.format("%c", c));
    this.spinnerModelDecimal.setValue((int) c);
    this.spinnerModelHex.setValue((int) c);
    
    this.spinnerInt.addChangeListener(this);
    this.spinnerHex.addChangeListener(this);
  }
  
  private void setCharacterDec(int d)
  {
    this.spinnerHex.removeChangeListener(this);
    this.spinnerInt.removeChangeListener(this);
    
    this.textFieldCharacter.setText(String.format("%c", (char) d));
    this.spinnerModelDecimal.setValue(d);
    this.spinnerModelHex.setValue(d);
    
    this.spinnerInt.addChangeListener(this);
    this.spinnerHex.addChangeListener(this);
  }
  
  public void showDialog(CharacterDescriptor desc, Point p, boolean varColumnWidth)
  {
    this.spinnerColumns.setEnabled(varColumnWidth);
    if (desc != null)
    {
      this.setCharacterChar(desc.getCharacter());
      this.textFieldDescription.setText(desc.getDescriptor());
      this.spinnerModelColumns.setDecimalValue(desc.getWidth());
    }    
    this.showDialog(p);
  }
  
  public void showDialog(Point p, boolean varColumnWidth)
  {
    this.showDialog(null, p, varColumnWidth);
  }

  public void showDialog(CharacterDescriptor desc, boolean varColumnWidth)
  {
    this.showDialog(desc, null, varColumnWidth);
  }
  
  public void showDialog(boolean varColumnWidth)
  {
    this.showDialog(null, null, varColumnWidth);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    
    if (e.getSource() == this.buttonCancel)
    {
      this.closeDialog(this.DIALOG_CANCEL);
    }
    else if (e.getSource() == this.buttonOK)
    {
      this.characterData.character = this.textFieldCharacter.getText().charAt(0);
      this.characterData.description = this.textFieldDescription.getText();
      this.characterData.width = this.spinnerModelColumns.getDecimalValue();
      this.closeDialog(this.DIALOG_OK);
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
      this.setCharacterDec(this.spinnerModelHex.getDecimalValue());      
    }
    else if (e.getSource() == this.spinnerInt)
    {
      this.setCharacterDec(this.spinnerModelDecimal.getDecimalValue());
    }
  }  
}
