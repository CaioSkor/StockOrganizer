/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author caio
 */
public class MyFont {
    private final Color TITLECOLOR;
    private final Font OSWALDBOLD;
    private final Font OSWALDREGULAR;
    private final Font OSWALDBUTTON;
    private final Font OSWALDNAVBARBUTTON;
    
    public MyFont(){
        TITLECOLOR = Color.web("#cc0000");
        OSWALDBOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Bold.ttf"), 35);
        OSWALDREGULAR = Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Regular.ttf"), 20);
        OSWALDBUTTON = Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Regular.ttf"), 15);
        OSWALDNAVBARBUTTON = Font.loadFont(getClass().getResourceAsStream("/fonts/Oswald-Regular.ttf"), 25);
    }
    
    public Color getTitleColor(){
        return TITLECOLOR;
    }
    
    public Font getOswaldBold(){
        return OSWALDBOLD;
    }
    
    public Font getOswaldRegular(){
        return OSWALDREGULAR;
    }
    
    public Font getOswaldButton(){
        return OSWALDBUTTON;
    }
    
    public Font getOswaldNavBarButton(){
        return OSWALDNAVBARBUTTON;
    }
}
