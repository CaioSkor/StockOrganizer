/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.TopManagement;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
/**
 *
 * @author caio
 */
public class Design {
    private Scene SCENE;
    private VBox BOTTOM, NAV, MAIN, TOP;
    private HBox SECTIONS;
    private GridPane SUBMAIN;
    private BorderPane LAYOUT;
    private Button HOMEBTN, ADDBTN, PERFBTN, INVESTBTN;
    private Text TITLE;
    
    private MyFont MYFONT;
    private DesignAdd DSADD;
    private TopManagement TOPMANAGE;
    private DesignInv DSINV;
    
    public Design(){
    }
    
    public Design(Stage MAINWINDOW){
        MYFONT = new MyFont();
        DSADD = new DesignAdd();
        TOPMANAGE = new TopManagement();
        DSINV = new DesignInv();
        
        SUBMAIN = new GridPane();
        
        TOP = new VBox();
        
        TITLE = new Text();
        TITLE.setText("Stock Organizer Software");
        TITLE.setTextAlignment(TextAlignment.CENTER);
        TITLE.setFill(MYFONT.getTitleColor());
        TITLE.setFont(MYFONT.getOswaldBold());
        
        HOMEBTN = new Button();
        HOMEBTN.setMinWidth(300);
        HOMEBTN.setText("Home");
        HOMEBTN.getStyleClass().add("navBarButtons");
        HOMEBTN.setMinHeight(100);
        HOMEBTN.setFont(MYFONT.getOswaldNavBarButton());
        HOMEBTN.setOnAction(e ->{
            SUBMAIN.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Stock Organizer Software");
        });
        
        ADDBTN = new Button();
        ADDBTN.setMinWidth(300);
        ADDBTN.setText("Add Investment");
        ADDBTN.getStyleClass().add("navBarButtons");
        ADDBTN.setMinHeight(100);
        ADDBTN.setFont(MYFONT.getOswaldNavBarButton());
        ADDBTN.setOnAction(e ->{
            SUBMAIN.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Add investment");
            try {
                DSADD.DesignAdd(SUBMAIN, MAINWINDOW, TOP);
            } catch (IOException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
        PERFBTN = new Button();
        PERFBTN.setMinWidth(300);
        PERFBTN.setText("Portfolio performance");
        PERFBTN.getStyleClass().add("navBarButtons");
        PERFBTN.setMinHeight(100);
        PERFBTN.setFont(MYFONT.getOswaldNavBarButton());
        
        INVESTBTN = new Button();
        INVESTBTN.setMinWidth(300);
        INVESTBTN.setText("Investments");
        INVESTBTN.getStyleClass().add("navBarButtons");
        INVESTBTN.setMinHeight(100);
        INVESTBTN.setFont(MYFONT.getOswaldNavBarButton());
        INVESTBTN.setOnAction(e->{
            SUBMAIN.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Investments");
            try {
                DSINV.DesignInv(SUBMAIN, TOP, MAINWINDOW);
            } catch (IOException ex) {
                Logger.getLogger(Design.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        NAV = new VBox(0);
        NAV.setMinWidth(200);
        NAV.getChildren().addAll(HOMEBTN, ADDBTN, PERFBTN, INVESTBTN);
        NAV.setAlignment(Pos.CENTER);
        NAV.setPadding(new Insets(0, 0, 0, 0));
        NAV.getStyleClass().add("navBar");
        
        TOP.getChildren().add(TITLE);
        TOP.setPadding(new Insets(20, 20, 100, 65));
        
        MAIN = new VBox();
        MAIN.getChildren().add(TOP);
        MAIN.getChildren().add(SUBMAIN);
        
        SECTIONS = new HBox();
        SECTIONS.getChildren().add(NAV);
        SECTIONS.getChildren().add(MAIN);
 
        BOTTOM = new VBox();
        
        LAYOUT = new BorderPane();
        //LAYOUT.setTop(TOP);
        LAYOUT.setCenter(SECTIONS);
        LAYOUT.setBottom(BOTTOM);
        
        SCENE = new Scene(LAYOUT, 800, 600);
        SCENE.getStylesheets().add("style.css");
    }
    
    public Scene getScreen(){
        return SCENE;
    }

}
 