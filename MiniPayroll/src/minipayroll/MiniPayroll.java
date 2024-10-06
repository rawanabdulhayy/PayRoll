/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package minipayroll;

import java.math.BigDecimal;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

class ErrorHandler {

    int severity;
    String message;

    public void start() {
        AlertType type;
        switch (severity) {
            case 3:
                type = AlertType.ERROR;
                break;
            case 2:
                type = AlertType.WARNING;
                break;
            default:
                type = AlertType.INFORMATION;
                break;
        }
        Alert alert = new Alert(type);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    ErrorHandler(String message, int severity) {
        this.severity = severity;
        this.message = message;
    }

}

public class MiniPayroll extends Application {

    public Administration admin = new Administration();

    Engineer selectedEng;
    Trainee selectedTra;

    String adminUsername = "admin";
    String adminPassword = "admin";

    private Tab getEngineersTab() {
        // List view of engineers
        ListView listView = new ListView();
        listView.getItems().clear();
        if (admin.engineers != null) {
            for (Engineer e : admin.engineers) {
                listView.getItems().add(e.getID() + " - " + e.getName());
            }
        }
        listView.setLayoutX(390);
        listView.setLayoutY(22);
        listView.setPrefHeight(216);
        listView.setPrefWidth(236);

        // Pay Rate text field
        TextField payField = new TextField();
        payField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        payField.setPromptText("Pay rate per hour...");
        payField.setLayoutX(81);
        payField.setLayoutY(243);
        payField.setPrefHeight(26);
        payField.setPrefWidth(289);
        payField.setDisable(true);

        // Tax Rate text field
        TextField taxField = new TextField();
        taxField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        taxField.setPromptText("Tax rate...");
        taxField.setLayoutX(81);
        taxField.setLayoutY(275);
        taxField.setPrefHeight(26);
        taxField.setPrefWidth(289);
        taxField.setDisable(true);

        // Name text field
        TextField nameField = new TextField();
        nameField.setPromptText("Name...");
        nameField.setLayoutX(81);
        nameField.setLayoutY(35);
        nameField.setPrefHeight(26);
        nameField.setPrefWidth(289);

        // Age text field
        TextField ageField = new TextField();
        ageField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        ageField.setPromptText("Age...");
        ageField.setLayoutX(81);
        ageField.setLayoutY(76);
        ageField.setPrefHeight(26);
        ageField.setPrefWidth(289);

        // Hours text field
        TextField hrsField = new TextField();
        hrsField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        hrsField.setPromptText("Hours...");
        hrsField.setLayoutX(81);
        hrsField.setLayoutY(121);
        hrsField.setPrefHeight(26);
        hrsField.setPrefWidth(289);

        // Position text field
        TextField posField = new TextField();
        posField.setPromptText("Manager, Team leader, Team member, ...");
        posField.setLayoutX(81);
        posField.setLayoutY(161);
        posField.setPrefHeight(26);
        posField.setPrefWidth(289);
        posField.textProperty().addListener((final ObservableValue<? extends String> observable, final String oldValue, final String newValue) -> {
            boolean cond = newValue.isEmpty() || newValue.equals("Manager") || newValue.equals("Team leader") || newValue.equals("Team member");
            payField.setDisable(cond);
            taxField.setDisable(cond);
        });

        // Add button
        Button addBtn = new Button("Add");
        addBtn.setLayoutX(81);
        addBtn.setLayoutY(201);
        addBtn.setOnAction((ActionEvent event) -> {
            String name = nameField.getText();
            String age = ageField.getText().replace(",", "");
            String hrs = hrsField.getText().replace(",", "");
            String pos = posField.getText();
            String pay = payField.getText().replace(",", "");
            String tax = taxField.getText().replace(",", "");
            try {
                if (name.isEmpty()) {
                    throw new EmptyInputException("Name");
                }
                if (age.isEmpty()) {
                    throw new EmptyInputException("Age");
                }
                if (Integer.valueOf(age) > 60 || Integer.valueOf(age) < 18) {
                    throw new InvalidInputException("Age", "Age should be within [18, 60]");
                }
                if (hrs.isEmpty()) {
                    throw new EmptyInputException("Working hours");
                }
                if (pos.isEmpty()) {
                    throw new EmptyInputException("Position");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && pay.isEmpty()) {
                    throw new EmptyInputException("Pay rate");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && tax.isEmpty()) {
                    throw new EmptyInputException("Tax rate");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && Double.valueOf(tax) >= 1) {
                    throw new InvalidInputException("Tax rate", "Tax rate should not be equal to or more than %100");
                }
                admin.addNewEngineer(name, Integer.parseInt(age), Integer.parseInt(hrs), pos, pay.isEmpty() ? 0.0 : Double.parseDouble(pay), tax.isEmpty() ? 0.0 : Double.parseDouble(tax));
                listView.getItems().clear();
                if (admin.engineers != null) {
                    for (Engineer e : admin.engineers) {
                        listView.getItems().add(e.getID() + " - " + e.getName());
                    }
                }
                listView.getSelectionModel().clearSelection();
                nameField.clear();
                ageField.clear();
                hrsField.clear();
                posField.clear();
                payField.clear();
                taxField.clear();
                payField.setDisable(true);
                taxField.setDisable(true);
            } catch (CustomException e) {
                ErrorHandler err = new ErrorHandler(e.getMessage(), e.severity);
                err.start();
            }
        });
        // Edit button
        Button editBtn = new Button("Edit");
        editBtn.setLayoutX(272);
        editBtn.setLayoutY(201);
        editBtn.setDisable(true);
        editBtn.setOnAction((ActionEvent event) -> {
            String name = nameField.getText();
            String age = ageField.getText().replace(",", "");
            String hrs = hrsField.getText().replace(",", "");
            String pos = posField.getText();
            String pay = payField.getText().replace(",", "");
            String tax = taxField.getText().replace(",", "");
            try {
                if (name.isEmpty()) {
                    throw new EmptyInputException("Name");
                }
                if (age.isEmpty()) {
                    throw new EmptyInputException("Age");
                }
                if (Integer.valueOf(age) > 60 || Integer.valueOf(age) < 18) {
                    throw new InvalidInputException("Age", "Age should be within [18, 60]");
                }
                if (hrs.isEmpty()) {
                    throw new EmptyInputException("Working hours");
                }
                if (pos.isEmpty()) {
                    throw new EmptyInputException("Position");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && pay.isEmpty()) {
                    throw new EmptyInputException("Pay rate");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && tax.isEmpty()) {
                    throw new EmptyInputException("Tax rate");
                }
                if (!pos.equals("Manager") && !pos.equals("Team leader") && !pos.equals("Team member") && Double.valueOf(tax) >= 1) {
                    throw new InvalidInputException("Tax rate", "Tax rate should not be equal to or more than %100");
                }
                admin.editEngineer(selectedEng.getID(), name, Integer.parseInt(age), Integer.parseInt(hrs), pos, pay.isEmpty() ? 0.0 : Double.parseDouble(pay), tax.isEmpty() ? 0.0 : Double.parseDouble(tax));
                listView.getItems().clear();
                if (admin.engineers != null) {
                    for (Engineer e : admin.engineers) {
                        listView.getItems().add(e.getID() + " - " + e.getName());
                    }
                }
                listView.getSelectionModel().clearSelection();
                nameField.clear();
                ageField.clear();
                hrsField.clear();
                posField.clear();
                payField.clear();
                taxField.clear();
                payField.setDisable(true);
                taxField.setDisable(true);
            } catch (CustomException e) {
                ErrorHandler err = new ErrorHandler(e.getMessage(), e.severity);
                err.start();
            }
        });
        // Delete button
        Button delBtn = new Button("Delete");
        delBtn.setLayoutX(320);
        delBtn.setLayoutY(201);
        delBtn.setDisable(true);
        delBtn.setOnAction((ActionEvent event) -> {
            admin.deleteEngineer(selectedEng.getID());
            listView.getSelectionModel().clearSelection();
            listView.getItems().clear();
            if (admin.engineers != null) {
                for (Engineer e : admin.engineers) {
                    listView.getItems().add(e.getID() + " - " + e.getName());
                }
            }
            nameField.clear();
            ageField.clear();
            hrsField.clear();
            posField.clear();
            payField.clear();
            taxField.clear();
            payField.setDisable(true);
            taxField.setDisable(true);
        });
        // Salary button
        Button salBtn = new Button("Salary");
        salBtn.setLayoutX(210);
        salBtn.setLayoutY(201);
        salBtn.setDisable(true);
        salBtn.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Salary");
            alert.setHeaderText("The calculted salary of engineer");
            alert.setContentText(String.valueOf(BigDecimal.valueOf(selectedEng.getSalary())));
            alert.showAndWait();
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (((String) newValue) == null || ((String) newValue).isEmpty()) {
                editBtn.setDisable(true);
                delBtn.setDisable(true);
                salBtn.setDisable(true);
                return;
            }
            int id = Integer.parseInt(((String) newValue).split(" ")[0]);
            selectedEng = admin.getEngineer(id);
            nameField.setText(selectedEng.getName());
            ageField.setText(String.valueOf(selectedEng.getAge()));
            hrsField.setText(String.valueOf(selectedEng.getWorkingHours()));
            posField.setText(selectedEng.getGrade().position);
            payField.setText(String.valueOf(selectedEng.getGrade().payRate));
            taxField.setText(String.valueOf(selectedEng.getGrade().taxRate));
            editBtn.setDisable(false);
            salBtn.setDisable(false);
            delBtn.setDisable(false);
            if (!posField.getText().equals("Manager") && !posField.getText().equals("Team leader") && !posField.getText().equals("Team member")) {
                payField.setDisable(false);
                taxField.setDisable(false);
            }
        });

        Label nameLabel = new Label("Name");
        nameLabel.setLayoutX(14);
        nameLabel.setLayoutY(39);

        Label ageLabel = new Label("Age");
        ageLabel.setLayoutX(14);
        ageLabel.setLayoutY(80);

        Label hrsLabel = new Label("Hours");
        hrsLabel.setLayoutX(14);
        hrsLabel.setLayoutY(114);

        Label posLabel = new Label("Position");
        posLabel.setLayoutX(14);
        posLabel.setLayoutY(154);

        Label payLabel = new Label("Pay Rate");
        payLabel.setLayoutX(14);
        payLabel.setLayoutY(247);

        Label taxLabel = new Label("Tax Rate");
        taxLabel.setLayoutX(14);
        taxLabel.setLayoutY(279);

        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(listView);
        pane.getChildren().add(addBtn);
        pane.getChildren().add(editBtn);
        pane.getChildren().add(delBtn);
        pane.getChildren().add(salBtn);
        pane.getChildren().add(nameField);
        pane.getChildren().add(ageField);
        pane.getChildren().add(hrsField);
        pane.getChildren().add(posField);
        pane.getChildren().add(nameLabel);
        pane.getChildren().add(ageLabel);
        pane.getChildren().add(hrsLabel);
        pane.getChildren().add(posLabel);
        pane.getChildren().add(payField);
        pane.getChildren().add(taxField);
        pane.getChildren().add(payLabel);
        pane.getChildren().add(taxLabel);
        pane.setMinHeight(0);
        pane.setMinWidth(0);
        pane.setPrefHeight(232);
        pane.setPrefWidth(640);

        Tab tab1 = new Tab("Egnineers", pane);
        tab1.setClosable(false);

        return tab1;
    }

    private Tab getTraineesTab() {
        // List view of trainees
        ListView listView = new ListView();
        listView.getItems().clear();
        if (admin.trainees != null) {
            for (Trainee t : admin.trainees) {
                listView.getItems().add(t.getID() + " - " + t.getName());
            }
        }
        listView.setLayoutX(390);
        listView.setLayoutY(22);
        listView.setPrefHeight(216);
        listView.setPrefWidth(236);

        // Name text field
        TextField nameField = new TextField();
        nameField.setPromptText("Name...");
        nameField.setLayoutX(81);
        nameField.setLayoutY(35);
        nameField.setPrefHeight(26);
        nameField.setPrefWidth(289);

        // Age text field
        TextField ageField = new TextField();
        ageField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        ageField.setPromptText("Age...");
        ageField.setLayoutX(81);
        ageField.setLayoutY(76);
        ageField.setPrefHeight(26);
        ageField.setPrefWidth(289);

        // GPA text field
        TextField gpaField = new TextField();
        gpaField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        gpaField.setPromptText("GPA...");
        gpaField.setLayoutX(81);
        gpaField.setLayoutY(121);
        gpaField.setPrefHeight(26);
        gpaField.setPrefWidth(289);

        // University text field
        TextField uniField = new TextField();
        uniField.setPromptText("University");
        uniField.setLayoutX(81);
        uniField.setLayoutY(161);
        uniField.setPrefHeight(26);
        uniField.setPrefWidth(289);

        // University text field
        TextField yrField = new TextField();
        yrField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        yrField.setPromptText("Academic year");
        yrField.setLayoutX(81);
        yrField.setLayoutY(200);
        yrField.setPrefHeight(26);
        yrField.setPrefWidth(289);

        // Add button
        Button addBtn = new Button("Add");
        addBtn.setLayoutX(81);
        addBtn.setLayoutY(242);
        addBtn.setOnAction((ActionEvent event) -> {
            String name = nameField.getText();
            String age = ageField.getText().replace(",", "");
            String gpa = gpaField.getText().replace(",", "");
            String uni = uniField.getText();
            String yr = yrField.getText().replace(",", "");

            try {
                if (name.isEmpty()) {
                    throw new EmptyInputException("Name");
                }
                if (age.isEmpty()) {
                    throw new EmptyInputException("Age");
                }
                if (Integer.valueOf(age) > 60 || Integer.valueOf(age) < 18) {
                    throw new InvalidInputException("Age", "Age should be within [18, 60]");
                }
                if (gpa.isEmpty()) {
                    throw new EmptyInputException("GPA");
                }
                if (Double.valueOf(gpa) > 4 || Double.valueOf(gpa) < 0) {
                    throw new InvalidInputException("GPA", "GPA should be within [0, 4]");
                }
                if (uni.isEmpty()) {
                    throw new EmptyInputException("University name");
                }
                if (yr.isEmpty()) {
                    throw new EmptyInputException("Academic year");
                }
                if (Integer.valueOf(yr) > 2030 || Integer.valueOf(yr) < 1950) {
                    throw new InvalidInputException("Academic year", "Academic year should be within [1950, 2030]");
                }

                admin.addNewTrainee(name, Integer.parseInt(age), Float.parseFloat(gpa), uni, yr);
                listView.getItems().clear();
                if (admin.trainees != null) {
                    for (Trainee t : admin.trainees) {
                        listView.getItems().add(t.getID() + " - " + t.getName());
                    }
                }
                listView.getSelectionModel().clearSelection();
                nameField.clear();
                ageField.clear();
                gpaField.clear();
                uniField.clear();
                yrField.clear();
            } catch (CustomException e) {
                ErrorHandler err = new ErrorHandler(e.getMessage(), e.severity);
                err.start();
            }
        });
        // Edit button
        Button editBtn = new Button("Edit");
        editBtn.setLayoutX(272);
        editBtn.setLayoutY(242);
        editBtn.setDisable(true);
        editBtn.setOnAction((ActionEvent event) -> {
            String name = nameField.getText();
            String age = ageField.getText().replace(",", "");
            String gpa = gpaField.getText().replace(",", "");
            String uni = uniField.getText();
            String yr = yrField.getText().replace(",", "");

            try {
                if (name.isEmpty()) {
                    throw new EmptyInputException("Name");
                }
                if (age.isEmpty()) {
                    throw new EmptyInputException("Age");
                }
                if (Integer.valueOf(age) > 60 || Integer.valueOf(age) < 18) {
                    throw new InvalidInputException("Age", "Age should be within [18, 60]");
                }
                if (gpa.isEmpty()) {
                    throw new EmptyInputException("GPA");
                }
                if (Double.valueOf(age) > 4 || Double.valueOf(age) < 0) {
                    throw new InvalidInputException("GPA", "GPA should be within [0, 4]");
                }
                if (uni.isEmpty()) {
                    throw new EmptyInputException("Univeristy name");
                }
                if (yr.isEmpty()) {
                    throw new EmptyInputException("Academic year");
                }
                if (Integer.valueOf(yr) > 2030 || Integer.valueOf(yr) < 1950) {
                    throw new InvalidInputException("Academic year", "Academic year should be within [1950, 2030]");
                }

                admin.editTrainee(selectedTra.getID(), name, Integer.parseInt(age), uni, yr, Float.parseFloat(gpa));
                listView.getItems().clear();
                if (admin.trainees != null) {
                    for (Trainee t : admin.trainees) {
                        listView.getItems().add(t.getID() + " - " + t.getName());
                    }
                }
                listView.getSelectionModel().clearSelection();
                nameField.clear();
                ageField.clear();
                gpaField.clear();
                uniField.clear();
                yrField.clear();
            } catch (CustomException e) {
                ErrorHandler err = new ErrorHandler(e.getMessage(), e.severity);
                err.start();
            }
        });
        // Delete button
        Button delBtn = new Button("Delete");
        delBtn.setLayoutX(320);
        delBtn.setLayoutY(242);
        delBtn.setDisable(true);
        delBtn.setOnAction((ActionEvent event) -> {
            admin.deleteTrainee(selectedTra.getID());
            listView.getSelectionModel().clearSelection();
            listView.getItems().clear();
            if (admin.trainees != null) {
                for (Trainee t : admin.trainees) {
                    listView.getItems().add(t.getID() + " - " + t.getName());
                }
            }
            listView.getSelectionModel().clearSelection();
            nameField.clear();
            ageField.clear();
            gpaField.clear();
            uniField.clear();
            yrField.clear();
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (((String) newValue) == null || ((String) newValue).isEmpty()) {
                editBtn.setDisable(true);
                delBtn.setDisable(true);
                return;
            }
            int id = Integer.parseInt(((String) newValue).split(" ")[0]);
            selectedTra = admin.getTrainee(id);
            nameField.setText(selectedTra.getName());
            ageField.setText(String.valueOf(selectedTra.getAge()));
            gpaField.setText(String.valueOf(selectedTra.GPA));
            uniField.setText(selectedTra.university);
            yrField.setText(String.valueOf(selectedTra.academicYear));
            editBtn.setDisable(false);
            delBtn.setDisable(false);
        });

        Label nameLabel = new Label("Name");
        nameLabel.setLayoutX(14);
        nameLabel.setLayoutY(39);

        Label ageLabel = new Label("Age");
        ageLabel.setLayoutX(14);
        ageLabel.setLayoutY(80);

        Label gpaLabel = new Label("GPA");
        gpaLabel.setLayoutX(14);
        gpaLabel.setLayoutY(114);

        Label uniLabel = new Label("University");
        uniLabel.setLayoutX(14);
        uniLabel.setLayoutY(154);

        Label yrLabel = new Label("Year");
        yrLabel.setLayoutX(14);
        yrLabel.setLayoutY(193);

        AnchorPane pane = new AnchorPane();
        pane.getChildren().add(listView);
        pane.getChildren().add(addBtn);
        pane.getChildren().add(editBtn);
        pane.getChildren().add(delBtn);
        pane.getChildren().add(nameField);
        pane.getChildren().add(ageField);
        pane.getChildren().add(gpaField);
        pane.getChildren().add(uniField);
        pane.getChildren().add(yrField);
        pane.getChildren().add(nameLabel);
        pane.getChildren().add(ageLabel);
        pane.getChildren().add(gpaLabel);
        pane.getChildren().add(uniLabel);
        pane.getChildren().add(yrLabel);
        pane.setMinHeight(0);
        pane.setMinWidth(0);
        pane.setPrefHeight(232);
        pane.setPrefWidth(640);

        Tab tab1 = new Tab("Trainees", pane);
        tab1.setClosable(false);

        return tab1;
    }

    private MenuBar getMenuBar() {
        Menu menu1 = new Menu("Porgram");
        MenuItem menu1i1 = new MenuItem("Logout");
        menu1i1.setOnAction((ActionEvent event) -> {
            mainScene.setRoot(getLogin());
        });
        SeparatorMenuItem menu1i2 = new SeparatorMenuItem();
        MenuItem menu1i3 = new MenuItem("Exit");
        menu1i3.setOnAction((ActionEvent event) -> {
            Platform.exit();
            System.exit(0);
        });
        menu1.getItems().add(menu1i1);
        menu1.getItems().add(menu1i2);
        menu1.getItems().add(menu1i3);

        Menu menu2 = new Menu("About");
        MenuItem menu2i1 = new MenuItem("Version: 0.0.1");
        menu2i1.setDisable(true);
        SeparatorMenuItem menu2i2 = new SeparatorMenuItem();
        MenuItem menu2i3 = new MenuItem("License: MIT");
        menu2i3.setDisable(true);
        menu2.getItems().add(menu2i1);
        menu2.getItems().add(menu2i2);
        menu2.getItems().add(menu2i3);

        MenuBar mb = new MenuBar();
        mb.getMenus().add(menu1);
        mb.getMenus().add(menu2);

        return mb;
    }

    private TabPane getTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.getTabs().add(getEngineersTab());
        tabPane.getTabs().add(getTraineesTab());
        tabPane.setPrefHeight(375);
        tabPane.setPrefWidth(640);
        return tabPane;
    }

    private Pane getLogin() {

        TextField usrField = new TextField();
        usrField.setLayoutX(238);
        usrField.setLayoutY(149);
        usrField.setPrefHeight(25);
        usrField.setPrefWidth(217);

        PasswordField pwdField = new PasswordField();
        pwdField.setLayoutX(238);
        pwdField.setLayoutY(189);
        pwdField.setPrefHeight(25);
        pwdField.setPrefWidth(217);

        Label usrLabel = new Label("Username");
        usrLabel.setLayoutX(146);
        usrLabel.setLayoutY(153);

        Label pwdLabel = new Label("Password");
        pwdLabel.setLayoutX(146);
        pwdLabel.setLayoutY(193);

        Button loginBtn = new Button("Login");
        loginBtn.setLayoutX(403);
        loginBtn.setLayoutY(227);
        loginBtn.setOnAction((ActionEvent event) -> {
            try {
                if (usrField.getText().equals(adminUsername) && pwdField.getText().equals(adminPassword)) {
                    mainScene.setRoot(getAdminDashboard());
                } else {
                    System.out.println("Incorrect"); // Throw exception
                    throw new InvalidCredentialsException();
                }
            } catch (CustomException e) {
                ErrorHandler err = new ErrorHandler(e.getMessage(), e.severity);
                err.start();
            }
        });

        Pane loginPane = new Pane();
        loginPane.getChildren().add(usrField);
        loginPane.getChildren().add(pwdField);
        loginPane.getChildren().add(usrLabel);
        loginPane.getChildren().add(pwdLabel);
        loginPane.getChildren().add(loginBtn);
        loginPane.setPrefHeight(400);
        loginPane.setPrefWidth(600);
        loginPane.setCenterShape(true);

        VBox vb = new VBox(loginPane);
        vb.setPrefHeight(400);
        vb.setPrefWidth(640);
        return new Pane(vb);
    }

    private Pane getAdminDashboard() {
        VBox vb = new VBox();
        vb.getChildren().add(getMenuBar());
        vb.getChildren().add(getTabPane());
        vb.setPrefHeight(400);
        vb.setPrefWidth(640);
        return new Pane(vb);

    }
    Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainScene = new Scene(getLogin());
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Mini Payroll");
        primaryStage.setIconified(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println("Exception caught:");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
