package assignment5;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

//import classwork15_javafx.Painter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.scene.layout.HBox;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;


import javafx.stage.Stage;

import javafx.scene.control.TextField;
//import startercode.Painter;

public class Main extends Application{

	static GridPane grid = new GridPane();

	static Scanner kb;	// scanner connected to keyboard input, or input file
	private static String inputFile;	// input file, used instead of keyboard input if specified
	static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
	private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
	private static boolean DEBUG = false; // Use it or not, as you wish!
	static PrintStream old = System.out;
	String critterStats = "Critter";// if you want to restore output to console
	static int givenSpeed = 1;
	int speedOfSteps = 1;
	Timeline tl;


	// Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}


	public static void main(String[] args) {
		launch(args);

	}


	public void start(Stage primaryStage) { // primaryStage is created by Java VM
		primaryStage.setTitle("Critters");

		TextField critter2Make = new TextField();
		critter2Make.setPromptText("Enter Critter Name to Make");
		critter2Make.prefHeightProperty().set(100);
		critter2Make.setFont(new Font(36));
		
		TextField num2Make = new TextField();
		num2Make.setPromptText("Enter Number to Make");
		num2Make.prefHeightProperty().set(100);
		num2Make.setFont(new Font(36));

		TextField steps = new TextField();
		steps.setPromptText("Enter Number of Steps");
		steps.prefHeightProperty().set(100);
		steps.setFont(new Font(36));

		TextField speed = new TextField();
		speed.setPromptText("Enter Speed of Steps");
		speed.prefHeightProperty().set(100);
		speed.setFont(new Font(36));

		Label statsLbl = new Label("Stats");
		statsLbl.prefHeightProperty().set(100);
		statsLbl.setFont(new Font(36));

		TextField critter = new TextField();
		critter.setPromptText("Enter Critter Name for Stats");
		critter.prefHeightProperty().set(100);
		critter.setFont(new Font(36));

		TextField seedNumber = new TextField();
		seedNumber.setPromptText("Enter Seed Number");
		seedNumber.prefHeightProperty().set(100);
		seedNumber.setFont(new Font(36));
		


        
		Button make = new Button();
		make.setText("Make Critter");
		make.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String critterName = critter2Make.getText();
				int num = Integer.parseInt(num2Make.getText());
				try {
					for(int i = 0; i < num; i++) {
						Critter.makeCritter(critterName);
					}

					Critter.displayWorld();
				}


				catch(Exception e){
					System.out.println("error processing: make " + critterName + " "  + num);
				}

				critter2Make.clear();
				num2Make.clear();
			}
		});
		
		make.prefHeightProperty().set(100);
		make.setFont(new Font(36));

		Button statsButton = new Button();
		statsButton.setText("Stats");
		statsButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {


				try {

					critterStats = critter.getText();
					String className = critterStats;
					List<Critter> stats = Critter.getInstances(className);
					Class newCritter = Class.forName("assignment5." + className);
					Class[] paramList = new Class[1];

					paramList[0] = java.util.List.class;				

					Method m = newCritter.getMethod("runStats", paramList);//creates a method object for the specified run stats method

					Object val = m.invoke(newCritter, stats);//invokes the run stats method through the method objecy
					String s = (String) val;


					statsLbl.setText(s);



				}


				catch(Exception e){
					System.out.println("error processing: stats " + critterStats);
				}

				critter2Make.clear();
				num2Make.clear();
			}
		});
		
		statsButton.prefHeightProperty().set(100);
		statsButton.setFont(new Font(36));

		
		
		Button step = new Button();
		step.setText("Step");
		step.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				
				int stepNum = Integer.parseInt(steps.getText());

				if(steps.getText().isEmpty()) {
					stepNum = 1;
				}


				else {
					stepNum = Integer.parseInt(steps.getText());
				}
				
				try {
					int display = 0;
					for(int i = 0; i < stepNum; i++) {
						Critter.worldTimeStep();
					}

					Critter.displayWorld();
				
					String className = critterStats;
					List<Critter> stats = Critter.getInstances(className);
					Class newCritter = Class.forName("assignment5." + className);
					Class[] paramList = new Class[1];

					paramList[0] = java.util.List.class;				

					Method m = newCritter.getMethod("runStats", paramList);//creates a method object for the specified run stats method

					Object val = m.invoke(newCritter, stats);//invokes the run stats method through the method objecy
					String s = (String) val;


					statsLbl.setText(s);
					steps.clear();

				}
				catch(Exception e){
					System.out.println("error processing: step " + stepNum);
				}
			}
			

		});
		
		step.prefHeightProperty().set(100);
		step.setFont(new Font(36));

		Button seed = new Button();
		seed.setText("Seed");
		seed.setOnAction(new EventHandler<ActionEvent>() {

			int seedNum = 0;
			@Override
			public void handle(ActionEvent event) {
				try {
					seedNum = Integer.parseInt(seedNumber.getText());
					Critter.setSeed(seedNum);
					seedNumber.clear();
				}
				
				catch(Exception e) {
					System.out.println("error processing: seed" + seedNum);
				}
				
			}

		});
		
		seed.prefHeightProperty().set(100);
		seed.setFont(new Font(36));
	

		
		Button start = new Button();
		start.setText("Start Animation");
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				if(speed.getText().isEmpty()) {
					speedOfSteps = 1;
				}
				
				else {
					speedOfSteps = Integer.parseInt(speed.getText());
				}
				
				tl = new Timeline();
		        tl.setCycleCount(Animation.INDEFINITE);
		        KeyFrame moveBall = new KeyFrame(Duration.millis((1/(double)speedOfSteps)*1000),
		                new EventHandler<ActionEvent>() {

		                    public void handle(ActionEvent event) {
		                    	
		                    	Critter.worldTimeStep();
		                        Critter.displayWorld();

		    				List<Critter> stats;
								try {
									stats = Critter.getInstances(critterStats);
									Class newCritter = Class.forName("assignment5." + critterStats);
			    					Class[] paramList = new Class[1];

			    					paramList[0] = java.util.List.class;				

			    					Method m = newCritter.getMethod("runStats", paramList);//creates a method object for the specified run stats method

			    					Object val = m.invoke(newCritter, stats);//invokes the run stats method through the method objecy
			    					String s = (String) val;


			    					statsLbl.setText(s);
			    					
								} catch (InvalidCritterException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    					


		                    }
		                });

		        tl.getKeyFrames().add(moveBall);
				
				tl.play();
			}

		});
		
		start.prefHeightProperty().set(100);
		start.setFont(new Font(36));
		
		Button end = new Button();
		end.setText("Stop Animation");
		end.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tl.stop();
				tl.getKeyFrames().clear();
			}

		});
		
		end.prefHeightProperty().set(100);
		end.setFont(new Font(36));



		HBox root1 = new HBox();



		VBox root = new VBox();
		root.setPadding(new Insets(10));
		root.setSpacing(8);
		VBox.setMargin(critter2Make, new Insets(0, 0, 0, 8));
		root.getChildren().add(critter2Make);
		VBox.setMargin(num2Make, new Insets(0, 0, 0, 8));
		root.getChildren().add(num2Make);
		VBox.setMargin(make, new Insets(0, 0, 0, 8));
		root.getChildren().add(make);


		
		root.prefWidthProperty().set(3840-2160);
		root.prefHeightProperty().set(2160);

		//root1.setPrefSize(2400, 1800);
		//root1.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		grid.setPrefSize(2160, 2160);
		grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);


		
		VBox.setMargin(steps, new Insets(50, 0, 0, 8));
		root.getChildren().add(steps);
		VBox.setMargin(speed, new Insets(0, 0, 0, 8));
		root.getChildren().add(speed);
		VBox.setMargin(step, new Insets(0, 0, 0, 8));
		root.getChildren().add(step);
		VBox.setMargin(start, new Insets(50, 0, 0, 8));
		root.getChildren().add(start);
		VBox.setMargin(end, new Insets(0, 0, 0, 8));
		root.getChildren().add(end);

		VBox.setMargin(seedNumber, new Insets(50, 0, 0, 8));
		root.getChildren().add(seedNumber);
		root.setSpacing(25);
		VBox.setMargin(seed, new Insets(0, 0, 0, 8));
		root.getChildren().add(seed);

		VBox.setMargin(critter, new Insets(50, 0, 0, 8));
		root.getChildren().add(critter);
		VBox.setMargin(statsButton, new Insets(0, 0, 0, 8));
		root.getChildren().add(statsButton);

		VBox.setMargin(statsLbl, new Insets(0, 0, 0, 8));
		root.getChildren().add(statsLbl);

		root1.getChildren().add(root);
		root1.getChildren().add(grid);

		//root.setAlignment(grid, Pos.TOP_RIGHT);
		primaryStage.setScene(new Scene(root1, 3840, 2160));
		primaryStage.show();


		Critter.displayWorld();

	}
}

/*
	@Override
	public void start(Stage arg0) throws Exception {
		try {			

			grid.setGridLinesVisible(false);

			Scene scene = new Scene(grid, 500, 500);
			arg0.setScene(scene);

			arg0.show();

			// Paints the icons.
			//Painter.paint();

		} catch(Exception e) {
			e.printStackTrace();		
		}

	}


 */

