/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker.View;

import charmaker.core.DataGrid;
import charmaker.core.character.CharacterSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author richard
 */
public class ControlNewCharset implements ActionListener
{
    
    private FrameView view;
    private ControlCharacterList list;
    
    public ControlNewCharset(FrameView view, ControlCharacterList list)
    {
        this.view = view;
        this.list = list;
        view.getButtonNewCharSet().addActionListener(this);
        view.getButtonNewCharSet().setText("New Character Set");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int x = (int) view.getSpinnerColumns().getValue();
        int y = (int) view.getSpinnerRows().getValue();
        String name = view.getTextFieldFontName().getText();
        CharacterSet set = new CharacterSet(x, y, name);
        
        for (char c = ' '; c <= '~'; c += 1)
        {
            set.addCharacter(c, new DataGrid(x, y));
        }
        list.setCurrentCharacterSet(set);
    }
    
    
    
}
