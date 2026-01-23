package fr.efrei.alsi54;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class JavaFXApp extends Application {

    private final ActionsBDD actions = new ActionsBDDImpl();

    private BorderPane root;
    private TableView<Programmer> tableProgrammers;
    private ObservableList<Programmer> dataProgrammers;

    private SplitPane projectView;
    private TableView<Project> tableProjects;
    private TableView<Programmer> tableTeam;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Gestion Programmeurs 2025");

        root = new BorderPane();
        root.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-background-color: white;");

        VBox sideMenu = createSideMenu();
        root.setLeft(sideMenu);

        initProgrammerView();
        initProjectView();

        showProgrammersView();

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Database.getInstance().closeConnection();
        super.stop();
    }

    private VBox createSideMenu() {
        VBox menu = new VBox(15);
        menu.setPadding(new Insets(20));
        menu.setPrefWidth(200);
        menu.setStyle("-fx-background-color: #2C3E50;");

        Label lblTitle = new Label("DEV MANAGER");
        lblTitle.setTextFill(Color.WHITE);
        lblTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        lblTitle.setPadding(new Insets(0, 0, 20, 0));

        Button btnProgrammers = createStyledButton("👥  Programmeurs");
        Button btnProjects = createStyledButton("🚀  Projets");

        Separator sep = new Separator();
        sep.setOpacity(0.3);

        Button btnAdd = createStyledButton("➕  Ajouter Prog.");
        Button btnDelete = createStyledButton("🗑  Supprimer Prog.");
        Button btnSalary = createStyledButton("💰  Modifier Salaire");
        Separator sep2 = new Separator();
        sep2.setOpacity(0.3);

        Button btnAddProject = createStyledButton("➕  Ajouter Projet");
        Button btnDeleteProject = createStyledButton("🗑  Supprimer Projet");

        Separator sep3 = new Separator();
        sep3.setOpacity(0.3);

        Button btnAssignProgToProject = createStyledButton("➕  Ajouter au Projet");
        Button btnRemoveProgFromProject = createStyledButton("➖  Retirer du Projet");

        menu.getChildren().addAll(
                lblTitle,
                btnProgrammers,
                btnProjects,
                sep,
                btnAdd,
                btnDelete,
                btnSalary,
                sep2,
                btnAddProject,
                btnDeleteProject,
                sep3,
                btnAssignProgToProject,
                btnRemoveProgFromProject
        );

        btnProgrammers.setOnAction(e -> {
            loadProgrammers();
            root.setCenter(tableProgrammers);
        });

        btnProjects.setOnAction(e -> {
            loadProjects();
            root.setCenter(projectView);
        });

        btnAdd.setOnAction(e -> {
            root.setCenter(tableProgrammers);
            showAddDialog();
        });

        btnDelete.setOnAction(e -> {
            root.setCenter(tableProgrammers);
            deleteSelectedProgrammer();
        });

        btnSalary.setOnAction(e -> {
            root.setCenter(tableProgrammers);
            showUpdateSalaryDialog();
        });

        btnAddProject.setOnAction(e -> {
            root.setCenter(projectView);
            showAddProjectDialog();
        });

        btnDeleteProject.setOnAction(e -> {
            root.setCenter(projectView);
            deleteSelectedProject();
        });

        btnAssignProgToProject.setOnAction(e -> {
            root.setCenter(projectView);
            showAddProgrammerToProjectDialog();
        });

        btnRemoveProgFromProject.setOnAction(e -> {
            root.setCenter(projectView);
            removeSelectedProgrammerFromSelectedProject();
        });

        return menu;
    }

    private Button createStyledButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(10, 15, 10, 15));
        btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #ECF0F1; -fx-font-size: 14px; -fx-cursor: hand;");

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #34495E; -fx-text-fill: white; -fx-font-size: 14px; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #ECF0F1; -fx-font-size: 14px; -fx-cursor: hand;"));

        return btn;
    }

    private void showProgrammersView() {
        loadProgrammers();
        root.setCenter(tableProgrammers);
    }

    private void initProgrammerView() {
        tableProgrammers = new TableView<>();
        setupProgrammerColumns();
    }

    private void setupProgrammerColumns() {
        TableColumn<Programmer, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Programmer, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Programmer, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Programmer, String> colPseudo = new TableColumn<>("Pseudo");
        colPseudo.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Programmer, Float> colSalary = new TableColumn<>("Salaire");
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Programmer, Float> colPrime = new TableColumn<>("Prime");
        colPrime.setCellValueFactory(new PropertyValueFactory<>("bonus"));

        TableColumn<Programmer, String> colManager = new TableColumn<>("Responsable");
        colManager.setCellValueFactory(new PropertyValueFactory<>("manager"));

        tableProgrammers.getColumns().addAll(colId, colNom, colPrenom, colPseudo, colSalary, colPrime, colManager);
        tableProgrammers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    }

    private void loadProgrammers() {
        dataProgrammers = FXCollections.observableArrayList(actions.getAllProgrammers());
        tableProgrammers.setItems(dataProgrammers);
    }

    private void initProjectView() {
        tableProjects = new TableView<>();
        TableColumn<Project, Integer> pId = new TableColumn<>("ID");
        pId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Project, String> pName = new TableColumn<>("Nom du Projet");
        pName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Project, String> pState = new TableColumn<>("État");
        pState.setCellValueFactory(new PropertyValueFactory<>("state"));

        TableColumn<Project, String> pStart = new TableColumn<>("Début");
        pStart.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStartDate().toString()));

        TableColumn<Project, String> pEnd = new TableColumn<>("Fin");
        pEnd.setCellValueFactory(cell -> {
            var date = cell.getValue().getEndDate();
            return new SimpleStringProperty(date != null ? date.toString() : " - ");
        });

        tableProjects.getColumns().addAll(pId, pName, pState, pStart, pEnd);
        tableProjects.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        tableTeam = new TableView<>();
        TableColumn<Programmer, String> tNom = new TableColumn<>("Nom");
        tNom.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Programmer, String> tPrenom = new TableColumn<>("Prénom");
        tPrenom.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Programmer, String> tRole = new TableColumn<>("Pseudo");
        tRole.setCellValueFactory(new PropertyValueFactory<>("username"));

        tableTeam.getColumns().addAll(tNom, tPrenom, tRole);
        tableTeam.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        tableTeam.setPlaceholder(new Label("Sélectionnez un projet pour voir l'équipe"));

        tableProjects.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> refreshTeamForSelectedProject());

        VBox topBox = new VBox(5, new Label("LISTE DES PROJETS"), tableProjects);
        topBox.setPadding(new Insets(10));
        VBox.setVgrow(tableProjects, Priority.ALWAYS);

        VBox bottomBox = new VBox(5, new Label("ÉQUIPE DU PROJET SÉLECTIONNÉ"), tableTeam);
        bottomBox.setPadding(new Insets(10));
        VBox.setVgrow(tableTeam, Priority.ALWAYS);

        projectView = new SplitPane();
        projectView.setOrientation(javafx.geometry.Orientation.VERTICAL);
        projectView.getItems().addAll(topBox, bottomBox);
        projectView.setDividerPositions(0.5);
    }

    private void loadProjects() {
        tableProjects.setItems(FXCollections.observableArrayList(actions.getAllProjects()));
        tableTeam.getItems().clear();
    }

    private void deleteSelectedProgrammer() {
        Programmer selected = tableProgrammers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer " + selected.getFirstName() + " ?");
            confirm.setContentText("Cette action est irréversible.");

            if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                actions.deleteProgrammer(selected.getId());
                loadProgrammers();
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une ligne dans le tableau des programmeurs.");
        }
    }

    private void showAddDialog() {
        Dialog<Programmer> dialog = new Dialog<>();
        dialog.setTitle("Nouveau Programmeur");
        dialog.setHeaderText("Ajout d'un collaborateur");

        ButtonType okBtn = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        TextField nom = new TextField();
        nom.setPromptText("Nom");
        TextField prenom = new TextField();
        prenom.setPromptText("Prénom");
        TextField pseudo = new TextField();
        TextField adresse = new TextField();
        TextField manager = new TextField();
        TextField hobby = new TextField();
        TextField annee = new TextField();
        annee.setPromptText("YYYY");
        TextField salaire = new TextField();
        TextField prime = new TextField();

        grid.add(new Label("Nom *"), 0, 0);
        grid.add(nom, 1, 0);
        grid.add(new Label("Prénom *"), 0, 1);
        grid.add(prenom, 1, 1);
        grid.add(new Label("Pseudo *"), 0, 2);
        grid.add(pseudo, 1, 2);
        grid.add(new Label("Adresse"), 0, 3);
        grid.add(adresse, 1, 3);
        grid.add(new Label("Responsable"), 0, 4);
        grid.add(manager, 1, 4);
        grid.add(new Label("Hobby"), 0, 5);
        grid.add(hobby, 1, 5);
        grid.add(new Label("Année Naiss."), 0, 6);
        grid.add(annee, 1, 6);
        grid.add(new Label("Salaire"), 0, 7);
        grid.add(salaire, 1, 7);
        grid.add(new Label("Prime"), 0, 8);
        grid.add(prime, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == okBtn) {
                if (nom.getText().isBlank() || prenom.getText().isBlank() || pseudo.getText().isBlank()
                        || annee.getText().isBlank() || salaire.getText().isBlank() || prime.getText().isBlank()) {
                    showAlert("Champs manquants", "Les champs marqués par * et Salaire/Prime sont obligatoires.");
                    return null;
                }

                int year;
                try {
                    year = Integer.parseInt(annee.getText().trim());
                    int currentYear = java.time.Year.now().getValue();
                    if (year < 1800 || year > currentYear) {
                        showAlert("Année invalide", "Année de naissance doit être entre 1800 et " + currentYear + ".");
                        return null;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Année invalide", "Année de naissance doit être un entier (YYYY).");
                    return null;
                }

                String decimalPattern = "^\\d{1,8}(\\.\\d{1,2})?$";
                String sSalaire = salaire.getText().trim();
                String sPrime = prime.getText().trim();

                if (!sSalaire.matches(decimalPattern)) {
                    showAlert("Salaire invalide", "Salaire doit être numérique (max 8 chiffres avant la virgule, jusqu'à 2 décimales).");
                    return null;
                }
                if (!sPrime.matches(decimalPattern)) {
                    showAlert("Prime invalide", "Prime doit être numérique (max 8 chiffres avant la virgule, jusqu'à 2 décimales).");
                    return null;
                }

                try {
                    float fSalaire = Float.parseFloat(sSalaire);
                    float fPrime = Float.parseFloat(sPrime);
                    if (fSalaire < 0 || fSalaire > 99_999_999.99f || fPrime < 0 || fPrime > 99_999_999.99f) {
                        showAlert("Valeur trop grande", "Salaire/Prime dépassent la limite autorisée par la base de données.");
                        return null;
                    }

                    return new Programmer(
                            nom.getText(), prenom.getText(), adresse.getText(), pseudo.getText(),
                            manager.getText(), hobby.getText(),
                            year,
                            fSalaire,
                            fPrime);
                } catch (Exception e) {
                    showAlert("Format Invalide", "Vérifiez les champs numériques (Année, Salaire, Prime).");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(p -> {
            actions.addProgrammer(p);
            loadProgrammers();
        });
    }


    private void showUpdateSalaryDialog() {
        Programmer selected = tableProgrammers.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Selection requise", "Sélectionnez un programmeur dans la liste.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(String.valueOf(selected.getSalary()));
        dialog.setTitle("Mise à jour Salaire");
        dialog.setHeaderText("Nouveau salaire pour " + selected.getLastName().toUpperCase());
        dialog.setContentText("Montant :");

        dialog.showAndWait().ifPresent(val -> {
            try {
                actions.updateSalary(selected.getId(), Float.parseFloat(val));
                loadProgrammers();
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Montant invalide.");
            }
        });
    }

    private void showAddProjectDialog() {
        Dialog<Project> dialog = new Dialog<>();
        dialog.setTitle("Nouveau Projet");
        dialog.setHeaderText("Création d'un projet");

        ButtonType okBtn = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        TextField name = new TextField();
        name.setPromptText("Nom du projet");

        TextField state = new TextField();
        state.setPromptText("État (ex: En cours, Terminé, Planifié)");

        javafx.scene.control.DatePicker startDatePicker = new javafx.scene.control.DatePicker();
        startDatePicker.setPromptText("Date de début");

        javafx.scene.control.DatePicker endDatePicker = new javafx.scene.control.DatePicker();
        endDatePicker.setPromptText("Date de fin (optionnel)");

        grid.add(new Label("Nom *"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("État *"), 0, 1);
        grid.add(state, 1, 1);
        grid.add(new Label("Date début *"), 0, 2);
        grid.add(startDatePicker, 1, 2);
        grid.add(new Label("Date fin"), 0, 3);
        grid.add(endDatePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn != okBtn) return null;

            if (name.getText().isBlank() || state.getText().isBlank() || startDatePicker.getValue() == null) {
                showAlert("Champs manquants", "Le nom, l'état et la date de début sont obligatoires.");
                return null;
            }

            java.time.LocalDate startLd = startDatePicker.getValue();
            java.time.LocalDate endLd = endDatePicker.getValue();

            if (endLd != null && endLd.isBefore(startLd)) {
                showAlert("Dates invalides", "La date de fin ne peut pas être avant la date de début.");
                return null;
            }

            java.util.Date startUtil = java.util.Date.from(
                    startLd.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
            );
            java.util.Date endUtil = (endLd != null)
                    ? java.util.Date.from(endLd.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant())
                    : null;

            return new Project(
                    0,
                    name.getText().trim(),
                    startUtil,
                    endUtil,
                    state.getText().trim()
            );
        });

        dialog.showAndWait().ifPresent(p -> {
            actions.addProject(p);
            loadProjects();
        });
    }

    private void deleteSelectedProject() {
        Project selected = tableProjects.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Erreur", "Veuillez sélectionner un projet dans le tableau.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Supprimer le projet \"" + selected.getName() + "\" ?");
        confirm.setContentText("Cette action est irréversible.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            actions.deleteProject(selected.getId());
            loadProjects();
        }
    }

    private void refreshTeamForSelectedProject() {
        Project selected = tableProjects.getSelectionModel().getSelectedItem();
        if (selected == null) {
            tableTeam.getItems().clear();
            return;
        }
        var team = actions.getProgrammersByProject(selected.getId());
        tableTeam.setItems(FXCollections.observableArrayList(team));
    }

    private void showAddProgrammerToProjectDialog() {
        Project selectedProject = tableProjects.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showAlert("Erreur", "Veuillez sélectionner un projet.");
            return;
        }

        java.util.List<Programmer> all = actions.getAllProgrammers();
        if (all == null || all.isEmpty()) {
            showAlert("Erreur", "Aucun programmeur disponible.");
            return;
        }

        Dialog<Programmer> dialog = new Dialog<>();
        dialog.setTitle("Affecter un programmeur");
        dialog.setHeaderText("Ajouter un programmeur au projet : " + selectedProject.getName());

        ButtonType okBtn = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

        javafx.scene.control.ComboBox<Programmer> combo =
                new javafx.scene.control.ComboBox<>(FXCollections.observableArrayList(all));
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setPromptText("Sélectionnez un programmeur");

        combo.setCellFactory(list -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Programmer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLastName() + " " + item.getFirstName() + " (" + item.getUsername() + ")");
                }
            }
        });

        combo.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Programmer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getLastName() + " " + item.getFirstName() + " (" + item.getUsername() + ")");
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));
        grid.add(new Label("Programmeur *"), 0, 0);
        grid.add(combo, 1, 0);

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(btn -> btn == okBtn ? combo.getValue() : null);

        dialog.showAndWait().ifPresent(p -> {
            if (p == null) {
                showAlert("Champs manquants", "Veuillez sélectionner un programmeur.");
                return;
            }
            actions.addProgrammerToProject(p.getId(), selectedProject.getId());
            refreshTeamForSelectedProject();
        });
    }

    private void removeSelectedProgrammerFromSelectedProject() {
        Project selectedProject = tableProjects.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showAlert("Erreur", "Veuillez sélectionner un projet.");
            return;
        }

        Programmer selectedProgrammer = tableTeam.getSelectionModel().getSelectedItem();
        if (selectedProgrammer == null) {
            showAlert("Erreur", "Veuillez sélectionner un programmeur dans l'équipe du projet.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation");
        confirm.setHeaderText("Retirer " + selectedProgrammer.getFirstName() + " du projet \"" + selectedProject.getName() + "\" ?");
        confirm.setContentText("Cette action supprime l'affectation.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            actions.removeProgrammerFromProject(selectedProgrammer.getId(), selectedProject.getId());
            refreshTeamForSelectedProject();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}