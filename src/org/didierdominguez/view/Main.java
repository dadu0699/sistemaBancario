package org.didierdominguez.view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.didierdominguez.controller.ControllerUser;
import org.didierdominguez.util.Inserts;
import org.didierdominguez.util.ScreenSize;
import org.didierdominguez.view.admin.AdminPanel;
import org.didierdominguez.view.transaction.TransactionPanel;

public class Main extends Application {
    private Stage stage;
    private VBox root;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        root = new VBox();

        JFXDecorator decorator = new JFXDecorator(stage, root, false, false, true);
        Scene scene = new Scene(decorator, 460, 250);
        scene.getStylesheets().add("/org/didierdominguez/assets/stylesheets/style.css");
        stage.setTitle("Banco del Exterior");
        stage.setResizable(false);
        stage.setScene(scene);

        root.getChildren().addAll(mainUI());
        stage.centerOnScreen();
        stage.show();
        AdminPanel.getInstance().showWindow();
        TransactionPanel.getInstance().showWindow();
    }

    public HBox mainUI() {
        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();
        HBox hbox = new HBox();

        JFXButton buttonAdministrativeModule = new JFXButton("ADMINISTRADOR");
        buttonAdministrativeModule.setId("buttonAdministrativeModule");
        buttonAdministrativeModule.getStyleClass().addAll("customButton", "primaryButton");
        buttonAdministrativeModule.setButtonType(JFXButton.ButtonType.FLAT);
        buttonAdministrativeModule.setPrefSize((x / 2), y);
        buttonAdministrativeModule.setOnAction(event -> {
            root.getChildren().clear();
            stage.hide();
            root.getChildren().add(getLogin());
            stage.show();
        });

        JFXButton buttonTransactionsModule = new JFXButton("TRANSACCIONES");
        buttonTransactionsModule.setId("buttonTransactionsModule");
        buttonTransactionsModule.getStyleClass().addAll("customButton", "primaryButton");
        buttonTransactionsModule.setPrefSize((x / 2), y);
        buttonTransactionsModule.setButtonType(JFXButton.ButtonType.FLAT);
        buttonTransactionsModule.setOnAction(event -> {
            stage.hide();
            TransactionPanel.getInstance().showWindow();
        });

        hbox.getChildren().addAll(buttonAdministrativeModule, buttonTransactionsModule);

        return hbox;
    }

    public GridPane getLogin() {
        GridPane gridPane = new GridPane();
        RequiredFieldValidator validator = new RequiredFieldValidator();

        double x = ScreenSize.getInstance().getX();
        double y = ScreenSize.getInstance().getY();

        gridPane.setId("gridPaneAccessWindow");
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        // gridPane.setGridLinesVisible(true);

        Text textTitle = new Text("INICIAR SESIÓN");
        textTitle.getStyleClass().add("textTitle");
        textTitle.setFont(new Font(25));
        gridPane.add(textTitle, 0, 0);

        JFXTextField fieldUser = new JFXTextField();
        fieldUser.setPromptText("Usuario");
        fieldUser.setId("fieldUser");
        fieldUser.setPrefSize(x, y);
        fieldUser.getValidators().add(validator);
        fieldUser.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldUser.validate();
            }
        });
        gridPane.add(fieldUser, 0, 1, 2, 1);

        JFXPasswordField fieldPassword = new JFXPasswordField();
        fieldPassword.setPromptText("Contraseña");
        fieldPassword.setId("fieldPassword");
        fieldPassword.setPrefSize(x, y);
        fieldPassword.getValidators().add(validator);
        fieldPassword.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                fieldPassword.validate();
            }
        });
        gridPane.add(fieldPassword, 0, 2, 2, 1);

        JFXButton buttonSignIn = new JFXButton("Iniciar");
        buttonSignIn.setId("buttonSignIn");
        buttonSignIn.getStyleClass().addAll("customButton", "primaryButton");
        buttonSignIn.setButtonType(JFXButton.ButtonType.FLAT);
        buttonSignIn.setPrefSize(x, y);
        gridPane.add(buttonSignIn, 0, 3);
        buttonSignIn.setOnAction(event -> {
            if (ControllerUser.getInstance().authenticate(fieldUser.getText().trim(), fieldPassword.getText())) {
                stage.hide();
                AdminPanel.getInstance().showWindow();
            } else {
                Alert.getInstance().showAlert(gridPane, "ERROR",
                        "EL NOMBRE DE USUARIO Y LA "
                                + "CONTRASEÑA QUE INGRESASTE NO COINCIDEN CON NUESTROS REGISTROS. \nPOR FAVOR, "
                                + "REVISA E INTÉNTALO DE NUEVO.");
            }
        });

        JFXButton buttonCancel = new JFXButton("Cancelar");
        buttonCancel.setId("buttonCancel");
        buttonCancel.getStyleClass().addAll("customButton", "dangerButton");
        buttonCancel.setButtonType(JFXButton.ButtonType.FLAT);
        buttonCancel.setPrefSize(x, y);
        gridPane.add(buttonCancel, 1, 3);
        buttonCancel.setOnAction(event -> {
            root.getChildren().clear();
            stage.hide();
            root.getChildren().add(mainUI());
            stage.show();
        });

        return gridPane;
    }

    public static void main(String[] args) {
        Inserts.getInstance().insertData();
        launch(args);
    }
}
