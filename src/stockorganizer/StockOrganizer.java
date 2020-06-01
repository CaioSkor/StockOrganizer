/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockorganizer;

import Scenes.Design;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class StockOrganizer extends Application {
    Design PAYSAGE;
    Stage MAINWINDOW;
    
    @Override
    public void start(Stage primaryStage) {
        MAINWINDOW = new Stage();
        PAYSAGE = new Design(MAINWINDOW);
        MAINWINDOW.setTitle("Stock Organizer Software");
        MAINWINDOW.setResizable(false);
        MAINWINDOW.setScene(PAYSAGE.getScreen());
        MAINWINDOW.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
