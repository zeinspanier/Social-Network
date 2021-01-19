///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Main.java
// Semester:         Fall 2019
// Author:           A Team 38
// Instructor:       Debra Deppler
//////////////////////////// 80 columns wide //////////////////////////////////

package application;

import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class populates the stage with different javafx features for the GUI.
 * Provides controls for user to generate visual representation of Social Network.
 * @author A Team 38
 *
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	//sets up application window
	private static final int WINDOW_WIDTH = 450;
	private static final int WINDOW_HEIGHT = 450;
	private static final String APP_TITLE = "Social Network";

	//structure of graph data
	SocialNetwork friendNetwork = new SocialNetwork();
	
	//used to Central User's friend list
	ComboBox friendsList = new ComboBox();
	
	//version of file that user can save
	int saveVersion = 0;

	/**
	 * Takes in a stage to actually place visuals and create window / GUI
	 * 
	 * @param primaryStage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// MAIN SCENE
		Scene mainScene;

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #BBBBBB;");
		
		// String array for drop down menu of options
		String commands[] = { "Add User", "Add Friendship (2 user names)", 
				"Remove Friendship (2 user names)", "Load .txt File", 
				"Shortest Path Between Two Users" };

		Label introductionLabel = new Label("\n   CS400 Social Network Visualizer\n");

		//lists user's options for picking a command
		ComboBox commandsComboBox = new ComboBox(FXCollections.observableArrayList(commands));
		commandsComboBox.setMaxSize(200, 100);

		//button to execute user's selected command
		Button executeCommandButton = new Button("Execute");
		executeCommandButton.setMaxSize(175, 100);
		executeCommandButton.setStyle("-fx-border-color: #000000;-fx-background-color: #66ADEB;");

		//textbox for user input
		TextField entryTextField = new TextField();
		entryTextField.setPromptText("Enter text");

		//Displays # of users in friend network
		Label numPeople = new Label("#Users: ");

		//button that deletes all data about graph, frontend and backend
		Button clearGraphButton = new Button("Clear Network");
		clearGraphButton.setMaxSize(175, 100);
		clearGraphButton.setStyle("-fx-border-color: #000000;-fx-background-color: #66ADEB;");

		//button that exits program
		Button exitAppButton = new Button("Exit");
		exitAppButton.setMaxSize(175, 100);
		exitAppButton.setStyle("-fx-border-color: #000000;-fx-background-color: #66ADEB;");

		//button that displays number of connected components
		Button connectedComponents = new Button("Get Connected Components");
		connectedComponents.setMaxSize(175, 100);
		connectedComponents.setStyle("-fx-border-color: #000000;-fx-background-color: #66ADEB;");

		//prompts user to save/ not save before exiting program
		Alert exitAlert = new Alert(AlertType.INFORMATION);
		exitAlert.setTitle("Quitting Social Network Visualizer...");
		exitAlert.setHeaderText("Social Network Visualizer is closing...");
		exitAlert.setContentText("Would you like to save before exiting?");

		//adds exit buttons for exitAlert
		ButtonType exitButton = new ButtonType("Exit");
		ButtonType exitAndSaveButton = new ButtonType("Exit and Save");
		exitAlert.getButtonTypes().setAll(exitButton, exitAndSaveButton);

		//warns user that input is invalid
		Alert badInputAlert = new Alert(AlertType.INFORMATION);
		badInputAlert.setTitle("Bad Input");
		badInputAlert.setHeaderText("The social network cannot use this input.");
		badInputAlert.setContentText("Make sure to not use spacing, characters that are not"
				+ " letters, numbers, or underscores, and that the text isn't blank, and select"
				+ " a valid command from the drop down!");

		//button to save data as .txt file
		Button generateTxt = new Button("Save as .txt");

		//Displays # of users in friend network
		numPeople.setText("#Users " + friendNetwork.numPeople());

		//padding for application
		Label verticalSpacer = new Label("\n\n");
		Label verticalSpacer2 = new Label("\n");
		Label verticalSpacer3 = new Label("\n\n");
		Label horizontalSpacer = new Label("   ");
		Label horizontalSpacer2 = new Label("   ");
		Label horizontalSpacer3 = new Label("   ");
		Label centralUser = new Label();
		Label help = new Label();

		//label above central user's friends list
		Label friends = new Label("Friends: ");

		//Default when network has no central user set
		centralUser.setText("No Central User");
		centralUser.setStyle("-fx-font-size: 18;-fx-background-color: #66ADEB;-fx-border-color: #000000;");

		//right pane's vbox
		VBox rightMainVBox = new VBox();

		//button for option to change central user
		Button changeCentralUser = new Button("Change Central User");
		
		rightMainVBox.getChildren().add(changeCentralUser);
		rightMainVBox.getChildren().add(centralUser);

		//Hbox for user input and numPeople
		HBox adjustedEntryHBox = new HBox();

		adjustedEntryHBox.getChildren().add(horizontalSpacer);
		adjustedEntryHBox.getChildren().add(entryTextField);
		adjustedEntryHBox.getChildren().add(numPeople);

		//Hbox for commands and execute
		HBox adjustedCommandExecuteHBox = new HBox();

		adjustedCommandExecuteHBox.getChildren().add(horizontalSpacer2);
		adjustedCommandExecuteHBox.getChildren().add(commandsComboBox);
		adjustedCommandExecuteHBox.getChildren().add(horizontalSpacer3);
		adjustedCommandExecuteHBox.getChildren().add(executeCommandButton);

		//vbox for left pane
		VBox leftMainVBox = new VBox();

		leftMainVBox.getChildren().add(introductionLabel);
		leftMainVBox.getChildren().add(adjustedEntryHBox);
		leftMainVBox.getChildren().add(adjustedCommandExecuteHBox);
		leftMainVBox.getChildren().add(verticalSpacer);
		leftMainVBox.getChildren().add(clearGraphButton);
		leftMainVBox.getChildren().add(verticalSpacer2);
		leftMainVBox.getChildren().add(connectedComponents);
		leftMainVBox.getChildren().add(verticalSpacer3);
		leftMainVBox.getChildren().add(exitAppButton);

		//sets help/warning alerts to the bottom of the window
		root.setBottom(help);

		//generates .txt file of graph data action
		generateTxt.setOnAction((event) -> {
			File file = new File("save" + saveVersion + ".txt");

			//increments save version
			++saveVersion;

			if (friendNetwork.saveToFile(file)) {
				help.setText("Sucessfully created text file.");
			} else {
				help.setText("Failed to created text file.");
			}

		});

		//generates pop up alert for when user requests to get 
		//connected components
		connectedComponents.setOnAction((event) -> {
			int components = friendNetwork.getConnectedComponents();

			Alert displayComponents = new Alert(AlertType.INFORMATION);

			displayComponents.setTitle("Connected Components in Social Network");
			displayComponents.setContentText("Number of Connected Components: " 
					+ components);
			displayComponents.setHeaderText(null);

			displayComponents.showAndWait();
		});

		//removes all data from graph, and resets to no central user
		clearGraphButton.setOnAction((event) -> {
			friendNetwork = new SocialNetwork();

			centralUser.setText("No Central User");
			rightMainVBox.getChildren().remove(centralUser);
			rightMainVBox.getChildren().add(centralUser);

			//updates friend of central user 
			updateCentralUserFriends();
			rightMainVBox.getChildren().remove(friendsList);

		});

		//changes central user and updates friend list
		changeCentralUser.setOnAction((event) -> {

			//sets central user
			friendNetwork.setCentralUser(friendsList.getValue().toString());
			
			//refreshes display of central user and friend's list for the 
			//right pane ofthe GUI
			if (friendNetwork.numPeople() > 0) {

				if (!leftMainVBox.getChildren().contains(generateTxt)) {
					leftMainVBox.getChildren().add(generateTxt);
				}
				if (friendNetwork.getCentralUser() != null) {
					numPeople.setText("#Users " + friendNetwork.numPeople());
					centralUser.setText("Central User: " 
							+ friendNetwork.getCentralUser().getName());

					rightMainVBox.getChildren().remove(centralUser);
					rightMainVBox.getChildren().remove(friendsList);
					rightMainVBox.getChildren().remove(friends);

					updateCentralUserFriends();

					rightMainVBox.getChildren().add(centralUser);
					rightMainVBox.getChildren().add(friends);
					rightMainVBox.getChildren().add(friendsList);

				} else {
					centralUser.setText("No Central User");
					rightMainVBox.getChildren().remove(centralUser);
					rightMainVBox.getChildren().remove(friends);

					rightMainVBox.getChildren().add(centralUser);

				}

			} else {
				System.out.print(friendNetwork.numPeople());

				if (leftMainVBox.getChildren().contains(generateTxt)) {
					leftMainVBox.getChildren().remove(generateTxt);
				}

				numPeople.setText("#Users " + friendNetwork.numPeople());
			}
		});

		//Exits program either saving or not 
		exitAppButton.setOnAction((event) -> {
			Optional<ButtonType> result = exitAlert.showAndWait();
			if (result.get() == exitAndSaveButton) {
				System.out.println("SAVING");

				File saveEdition = new File("save" + saveVersion);

				friendNetwork.saveToFile(saveEdition);

				//increments save version file 
				saveVersion++;

				//exits program
				Platform.exit();
				System.exit(0);

			} else if (result.get() == exitButton) {
				//exits program
				Platform.exit();
				System.exit(0);
			}
		});

		//populates scene with features from vbox
		root.setLeft(leftMainVBox);
		root.setRight(rightMainVBox);

		// Sets up scene
		mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		//executes command user selected from combo box
		executeCommandButton.setOnAction((event) -> {
			if (entryTextField.getText() == null 
					|| entryTextField.getText().equals("")) {
				badInputAlert.showAndWait();

			} else {
				//adds user to network
				if (commandsComboBox.getValue().equals("Add User")) {

					String command = entryTextField.getText();

					if (!friendNetwork.duplicate(command)) {
						if (friendNetwork.addUser(command)) {
							help.setText("Successfully Added!");
						} else {
							help.setText("Failed to Add!");
						}
					} else {
						help.setText("Failed to add. Duplicate user is not allowed");
					}
				}
				//removes friendship of 2 users 
				else if (commandsComboBox.getValue().equals("Remove Friendship (2 user names)")) {
					String command = entryTextField.getText();

					String[] names = command.split(" ");

					if (names.length == 2) {
						if (friendNetwork.removeFriends(names[0], names[1])) {
							help.setText("Successfully removed friendship!");
						}
					} else {
						help.setText("Failed to remove friendship");
					}
					
				} 
				//adds 2 users to network and a friendship between them 
				else if (commandsComboBox.getValue().equals("Add Friendship (2 user names)")) {
					String command = entryTextField.getText();

					String[] names = command.split(" ");

					if (names.length == 2) {
						if (friendNetwork.addFriends(names[0], names[1])) {
							help.setText("Successfully added friendship!");
						}
					} else {
						help.setText("Failed to added friendship");
					}
				} 
				//loads .txt file and all users and friendships
				else if (commandsComboBox.getValue().equals("Load .txt File")) {
					String textFile = entryTextField.getText();
					if (textFile.contains(".txt")) {
						File readFromFile = new File(textFile);

						if (friendNetwork.loadFromFile(readFromFile)) {
							help.setText("Successfully loaded file into network");
						} else {
							help.setText("Could not read text file.");
						}
					} else {
						help.setText("Could not load file into network.");
					}

				} 
				//finds the shortest path between two users 
				else if (commandsComboBox.getValue().equals("Shortest Path Between Two Users")) {
					String command = entryTextField.getText();

					//splits user input into 2 names
					String[] names = command.split(" ");

					List<Person> shortestPath = friendNetwork.getShortestPath(names[0], names[1]);

					ArrayList<String> friendNames = new ArrayList<String>();

					if (shortestPath != null) {
						for (Person currFriend : shortestPath) {
							friendNames.add(currFriend.getName());
						}
					}
					
					//pop-up for alert that shows user the shortest path
					Alert shortestPathAlert = new Alert(AlertType.INFORMATION);

					shortestPathAlert.setTitle("Shortest Path Between " 
							+ names[0] + " and " + names[1]);
					shortestPathAlert.setContentText("Shortest Path is: " 
							+ friendNames.toString());
					shortestPathAlert.setHeaderText(null);

					shortestPathAlert.showAndWait();

				}

				//if there are any users then the right pane of GUI updates
				//to show central user and friend's list
				if (friendNetwork.numPeople() > 0) {

					if (!leftMainVBox.getChildren().contains(generateTxt)) {
						leftMainVBox.getChildren().add(generateTxt);
					}
					if (friendNetwork.getCentralUser() != null) {
						numPeople.setText("#Users " + friendNetwork.numPeople());
						centralUser.setText("Central User: None");
						if (centralUser != null) {
							centralUser.setText("Central User: " 
									+ friendNetwork.getCentralUser().getName());
						}

						rightMainVBox.getChildren().remove(friendsList);

						updateCentralUserFriends();

						if (!rightMainVBox.getChildren().contains(friends)) {
							rightMainVBox.getChildren().add(friends);
						}
						rightMainVBox.getChildren().add(friendsList);

						root.setRight(rightMainVBox);
					} else {
						centralUser.setText("No Central User");
						rightMainVBox.getChildren().remove(centralUser);
						rightMainVBox.getChildren().remove(friends);

						rightMainVBox.getChildren().add(centralUser);

					}

				} else {
					
					if (leftMainVBox.getChildren().contains(generateTxt)) {
						leftMainVBox.getChildren().remove(generateTxt);
					}

					numPeople.setText("#Users " + friendNetwork.numPeople());

				}

			}
		});

		//sets main stage displays of application
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();

	}

	/**
	 * updates data in friendslist comboBox based off central user
	 */
	public void updateCentralUserFriends() {

		if (friendNetwork.getCentralUser() == null) {
			return;
		}

		Set<Person> friends 
			= friendNetwork.getFriends(friendNetwork.getCentralUser().getName());
		ArrayList<String> friendNames = new ArrayList<String>();

		if (friends != null) {
			for (Person currFriend : friends) {
				friendNames.add(currFriend.getName());
			}
		}

		friendsList = new ComboBox(FXCollections.observableArrayList(friendNames));
	}

	/**
	 * Main method that actually creates and starts window
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}