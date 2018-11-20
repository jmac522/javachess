import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;

public class GameDriver extends Application {
    public static BoardSquare selectedPieceSquare = null;
    public static Board activeGameBoard = Board.createInitialPosition(false);
    public static Side sideToMove = activeGameBoard.getCurrentPlayer().getSide();
    public static GridPane pane;
    public static ScrollPane movesPaneScroller;
    public static GridPane movesPane = new GridPane();
    public static VBox whiteMovesStack = new VBox();
    public static VBox blackMovesStack = new VBox();
    public static GridPane capturedPiecePane = new GridPane();
    public static int capturedWhite = 0;
    public static int capturedBlack = 0;
    public static int moveCounter = 1;
    public static Text bottomText;
    public static Alert mateAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert newGameConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert playerInCheckMessage = new Alert(Alert.AlertType.WARNING);
    public static Alert getPromotionChoice = new Alert(Alert.AlertType.CONFIRMATION);
    public static RadioMenuItem selectedMode;
    public static RadioMenuItem selectedDifficulty;
    public static ToggleGroup difficultyToggleGroup;
    public static ToggleGroup modeToggleGroup;
    public static boolean isVsComputer = false;
    public static boolean isHard = false;
    public static BenchmarkTracker benchmarkTracker = new BenchmarkTracker(new Text(), 0);

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        setup();
        // Grid pane holding the active gameboard
        pane = new GridPane();
        for (int i = 0; i < 64; i++) {
            pane.add(activeGameBoard.getBoardSquare(i), (i % 8), (i / 8));
        }
        pane.setPadding(new Insets(5));


        // Grid pane which will hold the list of moves that have been executed so far
        movesPane.setPadding(new Insets(5, 5, 5, 5));
        movesPane.setId("move-container");

        // New Test setup for move list
        HBox testBox = new HBox();
        testBox.setSpacing(5);
        testBox.setPadding(new Insets(5));
        whiteMovesStack.setPadding(new Insets(5));
        whiteMovesStack.getStyleClass().add("white-move-panel");
        blackMovesStack.setPadding(new Insets(5));
        blackMovesStack.getStyleClass().add("black-move-panel");
        testBox.getChildren().addAll(whiteMovesStack, blackMovesStack);


        // Change back to movesPane if needed
        movesPaneScroller = new ScrollPane(testBox);
        movesPaneScroller.setPadding(new Insets(5));
        movesPaneScroller.setMinWidth(190);
        movesPaneScroller.setMaxHeight(600);
        movesPaneScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        // Border pane to hold board in center, moves on left, options on top, text info on bottom
        BorderPane borderPane = new BorderPane();
        // Center will hold the game board
        borderPane.setCenter(pane);


        // left will hold the list of moves
        movesPaneScroller.getStyleClass().add("move-table-scroller");
        borderPane.setLeft(movesPaneScroller);

        // right will hold captured piece icons
        capturedPiecePane.setMinWidth(90);
        borderPane.setRight(capturedPiecePane);


        // top will have menu items for new game, etc.
        MenuBar menuBar = new MenuBar();
        Menu mainMenu = new Menu("Menu");
        // submenu holding game mode
        Menu modeSubMenu = new Menu("New Game");
        // submenu holding difficulty
        Menu difficultySubMenu = new Menu("Computer Difficulty");
        // menu item to start a new game
        //  MenuItem newGame = new MenuItem("New Game");
        //newGame.setOnAction(e -> newGame());


        // Mode Selection items and Toggle group

        RadioMenuItem humanVsHuman = new RadioMenuItem("Human vs Human");
        humanVsHuman.setSelected(true);
        humanVsHuman.setOnAction(e -> modeSelected(humanVsHuman));
        selectedMode = humanVsHuman;
        RadioMenuItem humanVsComputer = new RadioMenuItem("Human vs Computer");
        humanVsComputer.setOnAction(e -> modeSelected(humanVsComputer));
//        RadioMenuItem computerVsComputer = new RadioMenuItem("Computer vs Computer");
//        computerVsComputer.setOnAction(event -> modeSelected(computerVsComputer));

        modeToggleGroup = new ToggleGroup();
        modeToggleGroup.getToggles().add(humanVsHuman);
        modeToggleGroup.selectToggle(humanVsHuman);
        modeToggleGroup.getToggles().add(humanVsComputer);
//        modeToggleGroup.getToggles().add(computerVsComputer);


        // Difficulty selection menu items and toggle group
        RadioMenuItem lowDifficulty = new RadioMenuItem("Low");
        lowDifficulty.setSelected(true);
        lowDifficulty.setOnAction(e -> difficultySelected(lowDifficulty));
        selectedDifficulty = lowDifficulty;
        // RadioMenuItem mediumDifficulty = new RadioMenuItem("Medium");
        RadioMenuItem highDifficulty = new RadioMenuItem("High");
        highDifficulty.setOnAction(event -> difficultySelected(highDifficulty));

        difficultyToggleGroup = new ToggleGroup();
        difficultyToggleGroup.getToggles().add(lowDifficulty);
        //difficultyToggleGroup.getToggles().add(mediumDifficulty);
        difficultyToggleGroup.getToggles().add(highDifficulty);

        modeSubMenu.getItems().addAll(humanVsHuman, humanVsComputer);
        difficultySubMenu.getItems().addAll(lowDifficulty, highDifficulty);
        mainMenu.getItems().addAll( modeSubMenu, difficultySubMenu);
        menuBar.getMenus().add(mainMenu);
        menuBar.getStyleClass().add("top-menu");
        borderPane.setTop(menuBar);




        // Bottom box will hold text info on side to move, etc.
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        bottomBox.setPadding(new Insets(10));
        bottomText = new Text();
        bottomText.getStyleClass().add("bottom-text-content");
        setBottomText(Side.WHITE.turnMessage());
        Region spacingRegion = new Region();
        spacingRegion.setMinWidth(500);
        bottomBox.setHgrow(spacingRegion, Priority.ALWAYS);
        bottomBox.getChildren().addAll(bottomText, spacingRegion, benchmarkTracker.getBenchmarkText());
        bottomBox.getStyleClass().add("bottom-text");
        borderPane.setBottom(bottomBox);

        borderPane.setId("main-pane");

        Scene scene = new Scene(borderPane, 900, 650);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Java Chess");
        primaryStage.getIcons().add(new Image(GameDriver.class.getResourceAsStream("img_assets/white_knight.png")));
        primaryStage.show();
    }

    private void modeSelected(RadioMenuItem selectedItem) {
        String selectedString = selectedItem.getText();
        if (selectedString == "Human vs Human") {
            throwNewGameConfirmation();
            isVsComputer = false;
            newGame();
        } else if (selectedString == "Human vs Computer") {
            throwNewGameConfirmation();
            isVsComputer = true;
            newGame();
        } else {
            throwNewGameConfirmation();
            isVsComputer = true;
            newGame();
        }
    }

    private void difficultySelected(RadioMenuItem selectedItem) {
        String selectedString = selectedItem.getText();
        if (selectedString == "Low") {
            isHard = false;
        } else {
            isHard = true;
        }
    }

    private void throwNewGameConfirmation() {
        Optional<ButtonType> result = newGameConfirmation.showAndWait();
        if (result.get() == ButtonType.OK){
//            newGame(true);
        } else {
            // ... user chose CANCEL or closed the dialog
            System.out.println("cancelled");
        }
    }

    public static void throwGetPromotionChoice() {
        Optional<ButtonType> result = getPromotionChoice.showAndWait();

        if (result.get().getText() == "Queen") {
            activeGameBoard.getCurrentPlayer().setPromotionChoice(Piece.PieceType.QUEEN);
        } else if (result.get().getText() == "Rook") {
            activeGameBoard.getCurrentPlayer().setPromotionChoice(Piece.PieceType.ROOK);
        } else if (result.get().getText() == "Bishop") {
            activeGameBoard.getCurrentPlayer().setPromotionChoice(Piece.PieceType.BISHOP);
        } else {
            activeGameBoard.getCurrentPlayer().setPromotionChoice(Piece.PieceType.KNIGHT);
        }
    }

    private void setupNewGameConfirmation() {
        newGameConfirmation.setTitle("Are You Sure?");
        newGameConfirmation.setHeaderText("This action will start a new game.");
        newGameConfirmation.setContentText("Do you want to continue?");
    }

    private void setupGetPromotionChoice() {
        getPromotionChoice.setTitle("Promotion Selection");
        getPromotionChoice.setHeaderText("You have promoted a pawn!");
        getPromotionChoice.setContentText("What would you like to promote to?");

        ButtonType queenButton = new ButtonType("Queen");
        ButtonType rookButton = new ButtonType("Rook");
        ButtonType bishopButton = new ButtonType("Bishop");
        ButtonType knightButton = new ButtonType("Knight");

        getPromotionChoice.getButtonTypes().setAll(queenButton, rookButton, bishopButton, knightButton);
    }

    public static void throwPlayerInCheckMessage() {
        playerInCheckMessage.showAndWait();
    }

    private void setupPlayerInCheckMessage() {
        playerInCheckMessage.setTitle("Warning");
        playerInCheckMessage.setHeaderText("Illegal Move!");
        playerInCheckMessage.setContentText("You're in Check!");
    }

    // General setup method for preparing certain elements
    private void setup() {
        GameUtilities.setUpMateAlert(mateAlert);
        setupNewGameConfirmation();
        setupPlayerInCheckMessage();
        setupGetPromotionChoice();
        setupMovesPane();
    }


    public static void drawUpdatedBoard() {
        pane.getChildren().clear();
        for (int i = 0; i < 64; i++) {
            pane.add(activeGameBoard.getBoardSquare(i), (i % 8), (i / 8));
        }
    }

    public static void newGame() {
        activeGameBoard = Board.createInitialPosition(isVsComputer);
        drawUpdatedBoard();
        sideToMove = Side.WHITE;
        bottomText.setText("White to Move!");
        moveCounter = 1;
        movesPane.getChildren().clear();
        capturedPiecePane.getChildren().clear();
        capturedWhite = 0;
        capturedBlack = 0;
        blackMovesStack.getChildren().clear();
        whiteMovesStack.getChildren().clear();
        setupMovesPane();
    }

    private static void setupMovesPane() {
        MovesTableCell whiteCell = new MovesTableCell();
        whiteCell.setText("White");
        whiteCell.underlineProperty().setValue(true);
       whiteCell.setStyle("-fx-font: 14 verdana;");

        MovesTableCell blackCell = new MovesTableCell();
        blackCell.setText("Black");
        blackCell.underlineProperty().setValue(true);
        blackCell.setStyle("-fx-font: 14 verdana;");

        movesPane.add(whiteCell, 0, 0);
        movesPane.add(blackCell, 1, 0);

        whiteMovesStack.getChildren().add(whiteCell);
        blackMovesStack.getChildren().add(blackCell);
    }

    public static void setBottomText(String text) {
        bottomText.setText(text);
        bottomText.setTextAlignment(TextAlignment.CENTER);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
