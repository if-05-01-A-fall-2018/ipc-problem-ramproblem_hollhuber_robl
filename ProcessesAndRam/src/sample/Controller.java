package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {



    private Label label;
    private Circle process;
    @FXML
    private ImageView ivProcessOne;
    @FXML
    private ImageView ivProcessTwo;
    @FXML
    private ImageView ivProcessFive;
    @FXML
    private ImageView ivProcessThree;
    @FXML
    private ImageView ivProcessFour;


    @FXML
    private ImageView ivRamFive;
    @FXML
    private ImageView ivRamOne;
    @FXML
    private ImageView ivRamTwo;
    @FXML
    private ImageView ivRamFour;
    @FXML
    private ImageView ivRamThree;
    @FXML
    private ImageView ivProcessOneSlotOne;
    @FXML
    private ImageView ivProcessOneSlotTwo;
    @FXML
    private ImageView ivProcessTwoSlotOne;
    @FXML
    private ImageView ivProcessThreeSlotOne;
    @FXML
    private ImageView ivProcessFourSlotOne;
    @FXML
    private ImageView ivProcessFiveSlotOne;
    @FXML
    private ImageView ivProcessTwoSlotTwo;
    @FXML
    private ImageView ivProcessThreeSlotTwo;
    @FXML
    private ImageView ivProcessFourSlotTwo;
    @FXML
    private ImageView ivProcessFiveSlotTwo;

    private ImageView[][] processSlot;
    private ImageView[] initRams;
    private ImageView[] processes;

    Image imageDirtyRam;
    Image imageRam;
    Image imageRamWhite;
    Image imageBlack;
    Image imageProcess;
    Image imageProcessDone;
    Image imageProcessWorking;

    static ThreadController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            imageRam = new Image(Paths.get("src/sample/images/ram.png").toUri().toURL().toString());
            imageDirtyRam = new Image(Paths.get("src/sample/images/dirt_ram.png").toUri().toURL().toString());
            imageBlack = new Image(Paths.get("src/sample/images/black.png").toUri().toURL().toString());
            imageProcess = new Image(Paths.get("src/sample/images/process.png").toUri().toURL().toString());
            imageProcessDone = new Image(Paths.get("src/sample/images/process_done.jpg").toUri().toURL().toString());
            imageRamWhite = new Image(Paths.get("src/sample/images/ram_white.png").toUri().toURL().toString());
            imageProcessWorking = new Image(Paths.get("src/sample/images/process_occupied.jpg").toUri().toURL().toString());
        }catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        processSlot = new ImageView[][] {
                {ivProcessOneSlotOne , ivProcessOneSlotTwo},
                {ivProcessTwoSlotOne , ivProcessTwoSlotTwo},
                {ivProcessThreeSlotOne , ivProcessThreeSlotTwo},
                {ivProcessFourSlotOne , ivProcessFourSlotTwo},
                {ivProcessFiveSlotOne , ivProcessFiveSlotTwo},};
        initRams = new ImageView[] {ivRamOne , ivRamTwo , ivRamThree , ivRamFour , ivRamFive};
        processes = new ImageView[] {ivProcessOne,ivProcessTwo,ivProcessThree,ivProcessFour,ivProcessFive};

        controller = new ThreadController(this);
        controller.setDaemon(true);
        controller.start();
    }



    public void updateProcessView(MyProcess p){

        Platform.runLater(() ->
        {
            Integer id = p.getMyId();
            String[] string = (p.infos()).split(";");
            for (int i = 0; i < 2; i++) {
                ImageView current = processSlot[id][i];
                switch(string[i]){
                    case "0":
                        current.setImage(imageRamWhite);
                        break;
                    case "1":
                        current.setImage(imageDirtyRam);
                        break;
                    case "2":
                        current.setImage(imageRam);
                        break;
                }
            }
            if(string[2].equals("1"))
                processes[id].setImage(imageProcessDone);
            else
                processes[id].setImage(imageProcessWorking);
        });
    }


}
