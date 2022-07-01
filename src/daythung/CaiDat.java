package daythung;
import java.io.File;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CaiDat extends Application 
{
	private final Canvas canvas = new Canvas();
	private final GridPane menu = new GridPane();
	private final BorderPane pane = new BorderPane();
	private final ComboBox<String> comboBox = new ComboBox<>();   
	private File mapFile = null;
	private Stage stage = null;
	private final ObservableList<String> stat = FXCollections.observableArrayList();
	private ThuatToan tree = null;
	private String solution = "";
	private int step = 0;
	private DieuKhien node;
	private String fileName;
	private boolean loadMap = false;
	private boolean computeSolution = false;
	private final Text filePath;
	

	public static void main(String[] args) 
        {

		launch(args);
	}

    public CaiDat() 
    {
        this.filePath = new Text(" ");
    }
	
        @Override
	public void start(Stage primaryStage) throws Exception 
        {
		Button btLoadMap = new Button("Bản Đồ");
		btLoadMap.setOnAction( e -> handleLoadMap());
		Text searchTypes = new Text("Thuật toán Leo Đồi :");                             		
		comboBox.setMaxWidth(100);
                comboBox.getItems().add("2) DFS");
                comboBox.setValue("Chọn");	

		Button btStart = new Button("Phân Tích:");
		btStart.setOnAction(e-> handleComputeResult());
		

		Button btNext = new Button("Đi tự động");
		btNext.setOnAction(e-> handleNextStep());
		

		ListView<String> statList = new ListView<>();
		statList.setItems(this.stat);
		
		btLoadMap.setFocusTraversable(false);
		searchTypes.setFocusTraversable(false);
		comboBox.setFocusTraversable(false);
		btStart.setFocusTraversable(false);
		statList.setFocusTraversable(false);
		btNext.setFocusTraversable(false);
		
		menu.setPadding(new Insets(11, 12, 13, 14));
		menu.setHgap(5);
		menu.setVgap(5);
		menu.add(btLoadMap, 0, 0);
		menu.add(filePath, 0, 1, 2, 1);
	
		menu.add(searchTypes, 0, 2);
		menu.add(comboBox, 1, 2);
		menu.add(btStart, 0, 3);
		menu.add(statList, 0, 4);
		menu.add(btNext, 0, 5);
		
		
		VBox containerCanvas = new VBox(50);		
		containerCanvas.setAlignment(Pos.CENTER);
		
		Text instruction = new Text("Các Phím"
				+ "\n Đi lên: ↑"
				+ "\n Đi xuống: ↓"
				+ "\n Đi qua trái: ←"
				+ "\n Đi qua phải: → ");
               
		
		pane.setCenter(containerCanvas);
                containerCanvas.getChildren().addAll(canvas,instruction);
		pane.setRight(menu);
                pane.setBackground(Background.EMPTY);               
	     
	     Scene scene = new Scene(pane, 900, 600, Color.SPRINGGREEN);
	     
	     scene.setOnKeyPressed(e->handleKeyPressed(e));
	     
	     stage = primaryStage;
	     stage.setTitle("Trò chơi đẩy thùng đến vị trí");             
	     stage.setScene(scene);
	     stage.show();
	}
	
	private void handleKeyPressed(KeyEvent keyEvent) 
        {
		if(keyEvent.getCode()== KeyCode.ENTER) 
                {
			this.handleNextStep();
		}
		if(this.loadMap ==true && this.computeSolution == false) 
                {
			switch (keyEvent.getCode())
                        {
            case UP:
                	this.handleMove('u');
                	break;
            case DOWN:
            		this.handleMove('d');
            		break;
            case LEFT:
	        	this.handleMove('l');
	        	break;
            case RIGHT:
	        	this.handleMove('r');
	        	break;
            case L:
	        	this.handleMove(' ');
	        	break;
		default:
			break;
			}
		}
	}
	private void handleMove(char dir) 
        {
		DieuKhien child = null;
		switch (dir) 
                {                    
        case 'u':
            if(this.node.isUpValid()) 
            {
            		child = new DieuKhien(node, 'u');
            		printState(child);
            		this.node = child;
            }
            	break;
                
        case 'd':
            if(this.node.isDownValid())
            {
            		child = new DieuKhien(node, 'd');
            		printState(child);
            		this.node = child;
            }
            	break;
                
        case 'l':
            if(this.node.isLeftValid()) 
            {
            		child = new DieuKhien(node, 'l');
            		printState(child);
            		this.node = child;
            }
            	break;
                
        case 'r':
            if(this.node.isRightValid()) 
            {
            		child = new DieuKhien(node, 'r');
            		printState(child);
            		this.node = child;
            }
            	break;
                
        case ' ':           
        		DieuKhien parent = node.getParent();
        		printState(parent);
        		this.node = parent;
            	break;
		default:
			break;
		}
	}
	private void handleNextStep() 
        {
		char[] solutionArray = solution.toCharArray();
		DieuKhien child = null;
		if(this.computeSolution==true && step<solutionArray.length) 
                {
			child = new DieuKhien(node, solutionArray[step]);
			printState(child);
			this.node = child;
			this.step++;
		}			
	}	
	private void handleComputeResult()
        {
		this.mapFile = null;
		this.stat.clear();
		this.tree = null;
		ThuatToan.JUSTKEEPSWIMMING = true;
		this.solution = "";
		this.step = 0;
		this.node = null;
		this.computeSolution = true;

		if(this.fileName != null) 
                {
			this.mapFile = new File(this.fileName);
			Map ll = new Map(this.mapFile);		
			this.tree = new ThuatToan(ll.init());
			this.node = this.tree.getRoot();
			printState(this.tree.getRoot());
		}
		String searchtype = ((String)comboBox.getValue()).substring(0, 1);

		switch(searchtype) 
                {
                    case "1":
                        break;
			case "2":
                            this.stat.addAll(Arrays.asList(tree.DFS()));
				solution =  this.stat.get(0);
				break;
                        }      
        }
	private void handleLoadMap() 
        {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		this.mapFile = fileChooser.showOpenDialog(this.stage);
		this.fileName = this.mapFile.getPath();
		Map ll = new Map(this.mapFile);		
		this.tree = new ThuatToan(ll.init());
		this.node = this.tree.getRoot();
		printState(this.tree.getRoot());
		this.stat.clear();
		this.loadMap = true;
		this.computeSolution = false;
		filePath.setText(this.fileName);
	}
	private void printState(DieuKhien state) 
        {
		GraphicsContext gc= canvas.getGraphicsContext2D();
		char[][] map = state.getState();		
		int height = map.length;
		int width = 0;
		for(char[] row : map)
                {
			int j = 0;
			for(char c : row) 
                        {
				j++;
			}
			if (j>=width) 
                        {
				width=j;
			}		
		}	
                
		Image Thung = new Image("hinhanh/Thung.png");
		Image NguoiChoi = new Image("hinhanh/NguoiChoi.png");
		Image Nen = new Image("hinhanh/Nen.png");
		Image Vung = new Image("hinhanh/Vung.png");
		Image Tuong = new Image("hinhanh/Tuong.png");
		Image ThanhCong = new Image("hinhanh/ThanhCong.png");	
                
		canvas.setWidth(width*20);
		canvas.setHeight(height*20);
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0,width*20, height*20);
		
		for(int h = 0; h< height;h++) 
                {
			for(int w = 0; w< width;w++) 
                        {
				gc.drawImage(Nen, w*20, h*20);
			}
		}
		
		for(int h = 0; h< map.length;h++) 
                {
			for(int w = 0; w< map[h].length;w++) 
                        {
				switch(state.level[h][w]) 
                                {
				case '#':
					gc.drawImage(Tuong, w*20, h*20);
					break;
				case '@':
					gc.drawImage(NguoiChoi, w*20, h*20);
					break;
				case '$':
					gc.drawImage(Thung, w*20, h*20);
					break;
				case ' ':
					gc.drawImage(Nen, w*20, h*20);
					break;
				case '.':
					gc.drawImage(Vung, w*20, h*20);
					break;
				case '*':
					gc.drawImage(ThanhCong, w*20, h*20);
					break;
				case '+':
					gc.drawImage(NguoiChoi, w*20, h*20);
					break;
				}
			}
		}
	}
}