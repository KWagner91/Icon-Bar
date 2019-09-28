package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    final String[] icons = {"firefox.png", "intellij icon.png", "mail.png", "textedit.png"};

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Scene und Stage
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setX(screenSize.getWidth() / 2 - 260);
        primaryStage.setY(screenSize.getHeight() - 210);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Docker
        ImageView ImageViewDocker = new ImageView(new Image(getClass().getResourceAsStream("/image/dock.png")));
        ImageViewDocker.setTranslateX(12);
        ImageViewDocker.setTranslateY(100);

        root.getChildren().add(ImageViewDocker);

        // Event Handler
        ImageViewDocker.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        Platform.exit();
                    }
                }
            }
        });


        // Icons hinzufügen
        int counter = 0;
        for (String element : icons) {
            final ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/image/" + element)));
            icon.setTranslateX(90 + 80 * counter);
            icon.setTranslateY(100);
            icon.setScaleX(0.8);
            icon.setScaleY(0.8);
            icon.setEffect(new Reflection());

            zoomIn(icon);
            zoomOut(icon);
            iconClicked(counter, icon);

            root.getChildren().add(icon);
            counter++;
        }

        primaryStage.show();
    }

    public void zoomIn(ImageView icon) {
        icon.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                icon.setScaleX(1.0);
                icon.setScaleY(1.0);
            }
        });
    }

    public void zoomOut(ImageView icon) {
        icon.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                icon.setScaleX(0.8);
                icon.setScaleY(0.8);
            }
        });
    }

    public void iconClicked (int index, ImageView icon) {
        icon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String path;
                switch(index) {
                    case 0:
                        path = "open C:\\Program Files\\Mozilla Firefox\\firefox.exe";
                        startProgram(path);
                        break;
                    case 1:
                        path = "open C:\\Program Files\\JetBrains\\IntelliJ IDEA 2019.2.1\\bin\\idea64.exe";
                        startProgram(path);
                        break;
                    case 2:
                        getHostServices().showDocument("https://www.gmx.at/");
                        break;
                    case 3:
                        path = "open \\%windir%\\system32\\notepad.exe";
                        startProgram(path);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void startProgram(String path) {
        String cmd = path;

        try {
            Process process = Runtime.getRuntime().exec(cmd);
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
