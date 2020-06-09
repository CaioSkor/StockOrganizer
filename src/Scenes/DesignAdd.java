/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scenes;

import WindowManagement.GridPaneManagement;
import WindowManagement.TopManagement;
import com.intrinio.invoker.ApiException;
import controllers.InvestmentController;
import controllers.NewInvestmentProcessor;
import controllers.ToolsUse;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author caio
 */
public class DesignAdd {
    private Text CODETXT, PRICETXT, AMNTTXT, DATETXT, REATXT, TITLE, MESSAGE;
    private TextField PRC, AMNT, MYDATE, REASON;
    private String[] FIELDSINFO;
    private Button SUBBTN, BACKBTN;
    private RadioButton NYSE, NASDAQ, CONTINUE, ADDANOTHER, CANCEL;
    private ToggleGroup GROUP;
    private HBox EXCHANGES;
    private VBox CHOICES;
    private ComboBox COMBO;
    
    private InvestmentController INVESTCONTROL;
    private MyFont MYFONT;
    private ToolsUse TOOLS;
    private GridPaneManagement MANAGE;
    private TopManagement TOPMANAGE;
    private Design DESIGN;
    private DesignStock DSSTOCK;
    private NewInvestmentProcessor PROCESSOR;

    public GridPane DesignAdd(GridPane GRID, VBox TOP) throws IOException{
        DESIGN = new Design();
        INVESTCONTROL = new InvestmentController();
        MYFONT = new MyFont();
        MANAGE = new GridPaneManagement();
        PROCESSOR = new NewInvestmentProcessor();
        
        GRID.setVgap(30);
        GRID.setHgap(40);
        GRID.setPadding(new Insets(0, 50, 50, 75));
        
        EXCHANGES = new HBox(5);
        EXCHANGES.setPadding(new Insets(5, 0, 0, 0));
        
        CODETXT = new Text("Exchange");
        CODETXT.setFont(MYFONT.getOswaldRegular());
        
        PRICETXT = new Text("Price");
        PRICETXT.setFont(MYFONT.getOswaldRegular());
        
        AMNTTXT = new Text("Amount");
        AMNTTXT.setFont(MYFONT.getOswaldRegular());
        
        DATETXT = new Text("Date");
        DATETXT.setFont(MYFONT.getOswaldRegular());
        
        REATXT = new Text("Reason");
        REATXT.setFont(MYFONT.getOswaldRegular());
        
        PRC = new TextField();
        PRC.setFont(MYFONT.getOswaldButton());
        PRC.getStyleClass().add("submitTextfields");
        
        AMNT = new TextField();
        AMNT.setFont(MYFONT.getOswaldButton());
        AMNT.getStyleClass().add("submitTextfields");
        
        MYDATE = new TextField();
        MYDATE.setFont(MYFONT.getOswaldButton());
        MYDATE.getStyleClass().add("submitTextfields");
        
        REASON = new TextField();
        REASON.setFont(MYFONT.getOswaldButton());
        REASON.getStyleClass().add("submitTextfields");
        
        GRID.add(EXCHANGES, 1, 1);
        
        SUBBTN = new Button("Submit");
        SUBBTN.setFont(MYFONT.getOswaldButton());
        SUBBTN.getStyleClass().add("submitButton");
        SUBBTN.setOnAction(e->{
            String TICKER = COMBO.getSelectionModel().getSelectedItem().toString();
            FIELDSINFO[0] = TICKER;
            FIELDSINFO[1] = PRC.getText();
            FIELDSINFO[2] = AMNT.getText();
            FIELDSINFO[3] = MYDATE.getText();
            FIELDSINFO[4] = REASON.getText();
            GRID.getChildren().clear();
            try {
                PROCESSOR.createInvestment(
                        FIELDSINFO[0],
                        FIELDSINFO[1],
                        FIELDSINFO[2],
                        FIELDSINFO[3],
                        FIELDSINFO[4],
                        "000000",
                        GRID,
                        TOP
                        );
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        COMBO = new ComboBox();
               
        GROUP = new ToggleGroup();
        
        NYSE = new RadioButton("NYSE");
        NYSE.setFont(MYFONT.getOswaldButton());
        NYSE.setToggleGroup(GROUP);
        NYSE.setOnAction(e->{
           EXCHANGES.getChildren().clear();
           CODETXT.setText("Ticker    ");
            try {
                COMBO.setItems(FXCollections.observableList(INVESTCONTROL.readNyseTickers()));
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            GRID.add(SUBBTN, 2, 5);
            GRID.add(COMBO, 1, 1);
        });
        NYSE.getStyleClass().add("radioButtons");
        
        NASDAQ = new RadioButton("NASDAQ");
        NASDAQ.setFont(MYFONT.getOswaldButton());
        NASDAQ.setToggleGroup(GROUP);
        NASDAQ.setOnAction(e->{
           EXCHANGES.getChildren().clear();
           CODETXT.setText("Ticker    ");
            try {
                COMBO.setItems(FXCollections.observableList(INVESTCONTROL.readNasdaqTickers()));
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            GRID.add(SUBBTN, 2, 5);
            GRID.add(COMBO, 1, 1);
        });
        NASDAQ.getStyleClass().add("radioButtons");
        
        FIELDSINFO = new String[5];
       
        EXCHANGES.getChildren().addAll(NYSE, NASDAQ);
        
        GRID.add(CODETXT, 0, 1);
        GRID.add(PRICETXT, 0, 2);
        GRID.add(AMNTTXT, 0, 3);
        GRID.add(DATETXT, 0, 4);
        GRID.add(REATXT, 0, 5);
        
        GRID.add(PRC, 1, 2);
        GRID.add(AMNT, 1, 3);
        GRID.add(MYDATE, 1, 4);
        GRID.add(REASON, 1, 5);
        
        return GRID;
    }
    
    public GridPane DesignAddCreatedInvestment (GridPane GRID, String TICKER, VBox TOP){
        MYFONT = new MyFont();
        MANAGE = new GridPaneManagement();
        TOPMANAGE = new TopManagement();
        DSSTOCK = new DesignStock();
        
        String TEXT = "";
        TOPMANAGE.ChangeTop(TOP, TEXT);
        
        MESSAGE = new Text("Investment " + TICKER + " successfuly created.");
        MESSAGE.setFont(MYFONT.getOswaldRegular());
        
        GROUP = new ToggleGroup();
        
        CONTINUE = new RadioButton("Continue to investment");
        CONTINUE.setFont(MYFONT.getOswaldButton());
        CONTINUE.getStyleClass().add("radioButtons");
        CONTINUE.setToggleGroup(GROUP);
        CONTINUE.setOnAction(e ->{
            GRID.getChildren().clear();
            String TEXT2 = "Investment: "+ TICKER;
            TOPMANAGE.ChangeTop(TOP, TEXT2);
            try {
                try {
                    DSSTOCK.DesignStock(GRID, TICKER, TOP);
                } catch (ApiException ex) {
                    Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        ADDANOTHER = new RadioButton("Add another investment");
        ADDANOTHER.setFont(MYFONT.getOswaldButton());
        ADDANOTHER.getStyleClass().add("radioButtons");
        ADDANOTHER.setToggleGroup(GROUP);
        ADDANOTHER.setOnAction(e ->{
            GRID.getChildren().clear();
            try {
                MANAGE.DesignAddDefault(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        CHOICES = new VBox(10);
        CHOICES.getChildren().addAll(ADDANOTHER, CONTINUE);
        
        GRID.add(MESSAGE, 0, 0);
        GRID.add(CHOICES, 0, 1);
        
        return GRID;
    }
    
    public GridPane DesignAddWrongfulInputs (GridPane GRID, String[] BLANK, boolean[] CORRECT, Integer INDEX, VBox TOP){
        MYFONT = new MyFont();
        MANAGE = new GridPaneManagement();
        
        BACKBTN = new Button("Back");
        BACKBTN.setFont(MYFONT.getOswaldButton());
        BACKBTN.getStyleClass().add("submitButton");
        BACKBTN.setOnAction(e ->{
            GRID.getChildren().clear();
            try {
                MANAGE.DesignAddDefault(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        GRID.setPadding(new Insets(0, 50, 50, 70));
        
        Text MSG1 = new Text("Please input price as a double");
        MSG1.setFont(MYFONT.getOswaldRegular());
        Text MSG2 = new Text("Please input amount as an integer");
        MSG2.setFont(MYFONT.getOswaldRegular());
        
        Text MSG3 = new Text("Please don't leave" + Arrays.toString(BLANK).replace("[", " ").replace("]", " ") + "blank");
        MSG3.setFont(MYFONT.getOswaldRegular());
        System.out.println(BLANK[1]);
                
        System.out.println(BLANK[0]);
        
        if(BLANK.length > 0){
            for(int i =0; i < BLANK.length; i++){
                if(BLANK[i].equals("price")){
                    if(BLANK[i+1].equals("amount")){
                        GRID.add(MSG3, 0, 0);
                        break;
                    }
                    else if(CORRECT[1]){
                        GRID.add(MSG3, 0, 0);
                        GRID.add(MSG2, 0, 1);
                        break;
                    }else{
                        GRID.add(MSG3, 0, 0);
                        GRID.add(MSG1, 0, 1);
                        break;
                    }
                }else if(CORRECT[0]){
                    if(CORRECT[1]){
                        GRID.add(MSG3, 0, 0);
                        GRID.add(MSG1, 0, 1);
                        GRID.add(MSG2, 0, 2);
                        break;
                    }else{
                       GRID.add(MSG3, 0, 0);
                       GRID.add(MSG1, 0, 1);  
                       break;
                    }
                }else if(CORRECT[1]){
                    GRID.add(MSG3, 0, 0);
                    GRID.add(MSG2, 0, 1);
                    break;
                }else{
                    GRID.add(MSG3, 0, 0);
                    break;
                }
            }
        }else{
            if(CORRECT[0]){
                if(CORRECT[1]){
                    GRID.add(MSG1, 0, 0);
                    GRID.add(MSG2, 0, 1);
                }else{
                    GRID.add(MSG1, 0, 0);
                }
            }else{
                if(CORRECT[1]){
                    GRID.add(MSG2, 0, 0);
                }
            }
        }       
        GRID.add(BACKBTN, 0, 10);
        
        return GRID;
    }
    
    public GridPane DesignAddDeleted(GridPane GRID, VBox TOP, String TICKER, String OLDPRICE, String OLDAMOUNT, String OLDDATE, String OLDREASON, String OLDDELDATE, String NEWPRICE, String NEWAMOUNT, String NEWDATE, String NEWREASON ) throws IOException{
        INVESTCONTROL = new InvestmentController();
        MYFONT = new MyFont();
        
        GROUP = new ToggleGroup();
        
        CONTINUE = new RadioButton("Recover Investment");
        CONTINUE.setFont(MYFONT.getOswaldButton());
        CONTINUE.getStyleClass().add("radioButtons");
        CONTINUE.setToggleGroup(GROUP);
        CONTINUE.setOnAction(e ->{
            try {
                GRID.getChildren().clear();
                INVESTCONTROL.recoverDeletedInvestment(TICKER, OLDPRICE, OLDAMOUNT, OLDDATE, OLDREASON, OLDDELDATE, NEWPRICE, NEWAMOUNT, NEWDATE, NEWREASON);
                DesignAddCreatedInvestment(GRID, TICKER, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        CANCEL = new RadioButton("Cancel");
        CANCEL.setFont(MYFONT.getOswaldButton());
        CANCEL.getStyleClass().add("radioButtons");
        CANCEL.setToggleGroup(GROUP);
        CANCEL.setOnAction(e ->{
            GRID.getChildren().clear();
            try {
                DesignAdd(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        GRID.add(CONTINUE, 0, 0);
        GRID.add(CANCEL, 0, 1);
        
        return GRID;
    }
    
    public GridPane DesignAddUpdate(GridPane GRID, VBox TOP, String TICKER, String OLDPRICE,String  OLDAMOUNT,String OLDDATE,String OLDREASON,String NEWPRICE,String NEWAMOUNT,String NEWDATE,String NEWREASON) throws IOException{
        INVESTCONTROL = new InvestmentController();
        MYFONT = new MyFont();
        TOPMANAGE = new TopManagement();
        
        GROUP = new ToggleGroup();
        
        CONTINUE = new RadioButton("Update Investment");
        CONTINUE.setFont(MYFONT.getOswaldButton());
        CONTINUE.getStyleClass().add("radioButtons");
        CONTINUE.setToggleGroup(GROUP);
        CONTINUE.setOnAction(e ->{
            try {
                GRID.getChildren().clear();
                TOPMANAGE.ChangeTop(TOP, "Add Investment");
                INVESTCONTROL.updateInvestment(TICKER, OLDPRICE, OLDAMOUNT, OLDDATE, OLDREASON, NEWPRICE, NEWAMOUNT, NEWDATE, NEWREASON, "000000");
                DesignAddCreatedInvestment(GRID, TICKER, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        CANCEL = new RadioButton("Cancel");
        CANCEL.setFont(MYFONT.getOswaldButton());
        CANCEL.getStyleClass().add("radioButtons");
        CANCEL.setToggleGroup(GROUP);
        CANCEL.setOnAction(e ->{
            GRID.getChildren().clear();
            TOPMANAGE.ChangeTop(TOP, "Add Investment");
            try {
                DesignAdd(GRID, TOP);
            } catch (IOException ex) {
                Logger.getLogger(DesignAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        GRID.add(CONTINUE, 0, 0);
        GRID.add(CANCEL, 0, 1);
        return GRID;
    }
}