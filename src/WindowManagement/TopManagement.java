/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WindowManagement;

import Scenes.MyFont;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author caio
 */
public class TopManagement {
    private Text TITLE;
    
    private MyFont MYFONT;
    
    public VBox ChangeTop (VBox TOP, String TEXT){
        MYFONT = new MyFont();
        TOP.getChildren().clear();
        
        TITLE = new Text(TEXT);
        TITLE.setFont(MYFONT.getOswaldBold());
        TITLE.setFill(MYFONT.getTitleColor());
        
        TOP.getChildren().add(TITLE);
    
        return TOP;
    }
}
