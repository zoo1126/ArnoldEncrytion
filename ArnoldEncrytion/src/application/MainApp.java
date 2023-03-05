package application;


import static application.MainApp.selectphoto;
import static application.MainApp.tabselectende;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import javax.imageio.ImageIO;

import javax.swing.filechooser.FileSystemView;

import application.Controller.Decrytion;
import application.Controller.Encrytion;
import application.Controller.FileTreeCreator;
import application.Controller.FileTreeItem;
import application.Controller.ImageArray;
import application.Controller.U;
import application.Controller.selectPhoto;

public class MainApp extends Application {
	public static File ROOT_FILE;
	public static double TOOL_WIDTH;
	public static double TOOL_HEIGHT;
	public static TreeView<File> treeView;
	public static LinkedHashMap<File,StackPane> ImageList;//加密界面
	public static LinkedHashMap<File,StackPane> ImageList2;//解密界面的

	public static ArrayList<File> selected;
	public static ArrayList<File> selected2;
	public static selectPhoto selectphoto;
	public static Stage nowwindow;
   
	private  Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane filetreeview;
    private BorderPane photoPane;
    private AnchorPane Attributes;
    private AnchorPane Menutop;
    private AnchorPane AdditionalTool;
    public static Boolean tabselectende;
    private static TextArea console;
    private static TextArea console2;
    private TextField inputtext=new TextField();
    private TextField location=new TextField();
    public static ListView<ImageArray> readyarray;
    public static ListView<ImageArray> readyarray2;
    private int changetime=0;
    private  Thread thread;
    private  Thread thread2;
    public static Boolean in;
    public static Boolean in2;
    public static File corrent;
    public static File corrent2;

    public static RadioButton radiobutton;
    @Override
    public void start(Stage primaryStage) {
    	ROOT_FILE = FileSystemView.getFileSystemView().getRoots()[0];
    	TOOL_WIDTH=Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.85;
    	TOOL_HEIGHT=Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.85;
    	 treeView=new FileTreeCreator().filetreecreator();
    	ImageList=new LinkedHashMap<File,StackPane>();//加密界面
    	ImageList2=new LinkedHashMap<File,StackPane>();//解密界面的
    	tabselectende=true;
    	in=false;
    	in2=false;
    	readyarray=new ListView<ImageArray>();
    	readyarray2=new ListView<ImageArray>();
    	selected=new ArrayList<File>();
    	selected2=new ArrayList<File>();
    	selectphoto=new selectPhoto();
        this.primaryStage = primaryStage;
        thread=new Thread();
        thread2=new Thread();
        this.primaryStage.setTitle("Arnold变换图像加密工具");
        this.primaryStage.getIcons().add(new Image("/images/ArnoldEncrytion.jpg"));
		initRootLayout();
		showMenutop();
        showfiletreeview();
        showphotoprocess();
        showattributes();
        showAdditionalTool();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            rootLayout.setPrefWidth(TOOL_WIDTH);
            rootLayout.setPrefHeight(TOOL_HEIGHT);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
         
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  
     */
    @SuppressWarnings("unchecked")
	public void showfiletreeview() {
       
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("View/FileTree.fxml"));
         
		try {
			filetreeview = (AnchorPane) loader.load();
			
            filetreeview.getChildren().add(treeView);
            
            rootLayout.setLeft(filetreeview);
           
           
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 
     */
    public void showphotoprocess() {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("View/Photoprocess.fxml"));
        try {
			photoPane=(BorderPane)loader.load();
			photoPane.setPrefHeight(TOOL_HEIGHT*0.7);
			photoPane.setPrefWidth(TOOL_WIDTH*0.6);
		
			ScrollPane scrollPaneencry=new ScrollPane();
			scrollPaneencry.setContent(selectphoto.getenflowPane());
			ScrollPane scrollPanedecry=new ScrollPane();
			scrollPanedecry.setContent(selectphoto.getdeflowPane());
			TabPane tabpane=(TabPane)photoPane.getCenter();
			tabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {

				@Override
				public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
					// TODO Auto-generated method stub
					if(newValue==null)
						System.out.println("错误");
					else if(newValue!=null&&newValue.getId().contentEquals("encry"))
					{
						tabselectende=true;
						inputtext.setEditable(true);
						radiobutton.setSelected(selectphoto.enall);
					
						
					}
					else if(newValue!=null&&newValue.getId().contentEquals("decry"))
					{
						tabselectende=false;
						inputtext.setEditable(false);
						radiobutton.setSelected(selectphoto.deall);

					}
				}
			});
			
			tabpane.getTabs().get(0).setContent(scrollPaneencry);
			tabpane.getTabs().get(1).setContent(scrollPanedecry);
			ButtonBar bbr=(ButtonBar) photoPane.getBottom();
			Button button=null;
			Button surebutton=null;
			for(int i=0;i<bbr.getButtons().size();i++)
			{
				if(bbr.getButtons().get(i).getId().contentEquals("delete"))
				{
					button=(Button) bbr.getButtons().get(i);
					button.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							ArrayList<File> selectlist;
							if(tabselectende) selectlist=selected;
							else selectlist=selected2;
							if(selectlist!=null&&selectlist.size()!=0)
							{
								selectphoto.freshflow();
								for(int j=0;j<bbr.getButtons().size();j++)
								{
									if(bbr.getButtons().get(j).getId().contentEquals("all")) 
									{
										RadioButton rb=(RadioButton)bbr.getButtons().get(j);
										rb.setSelected(false);
									}
								}
							}
						}
						
					});
				
				}
				if(bbr.getButtons().get(i).getId().contentEquals("sure")) 
				{
					surebutton=(Button) bbr.getButtons().get(i);
					surebutton.setOnAction(new EventHandler<ActionEvent>() {
					
						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
						Boolean choose=tabselectende;
						if(changetime==0&&choose&&selected!=null&&selected.size()!=0) {
							Alert alert=new Alert(Alert.AlertType.INFORMATION,"未输入加密参数！请在下方属性面板设置参数！");
							alert.initOwner(primaryStage);
							alert.initModality(Modality.WINDOW_MODAL);
							alert.show();
							
								
								
							}
						else if((changetime>0&&changetime<256&&choose)||!choose)
						{
							if((!in&&changetime>0&&changetime<256&&choose)||(!in2&&!choose))
							{
								if(choose)
									readyarray.getItems().clear();
								else
									readyarray2.getItems().clear();
								ArrayList<File> selectlist;
								if(choose) selectlist=selected;
								else selectlist=selected2;
								
								if(selectlist!=null&&selectlist.size()!=0)
								{
									if(Attributes.getChildren()!=null&&Attributes.getChildren().get(0).getId().contentEquals("tabpane"))
									{
										TabPane tabpane=(TabPane) Attributes.getChildren().get(0);
										TabPane tabarray=(TabPane) AdditionalTool.getChildren().get(0);
										if(choose)
										{
											tabpane.getSelectionModel().select(1);
											tabarray.getSelectionModel().select(0);
											
										}
										else
										{
											tabpane.getSelectionModel().select(2);
											tabarray.getSelectionModel().select(1);
										}
									
									}//切换到控制台
									
									if(!location.getText().contentEquals("默认为原图片所在文件夹"))
									{
										if(choose)
										{
											for(int i=0;i<selectlist.size();i++)
											{	
												readyarray.getItems().add(new ImageArray(selectlist.get(i),choose,location.getText(),changetime));
											}
										}
										else
										{
											for(int i=0;i<selectlist.size();i++)
											{	
												readyarray2.getItems().add(new ImageArray(selectlist.get(i),choose,location.getText(),changetime));
											}
										}
									}
									else
									{
										if(choose)
										{
											for(int i=0;i<selectlist.size();i++)
											{	
												readyarray.getItems().add(new ImageArray(selectlist.get(i),choose,selectlist.get(i).getParentFile().getAbsolutePath(),changetime));
											}
										}
										else
										{
											for(int i=0;i<selectlist.size();i++)
											{	
												readyarray2.getItems().add(new ImageArray(selectlist.get(i),choose,selectlist.get(i).getParentFile().getAbsolutePath(),changetime));
											}
										}
									}
									
									if(choose)
									{
										
										Encrytion encry=new Encrytion(changetime);
										if(!location.getText().contentEquals("默认为原图片所在文件夹"))
											encry.setEnpath(location.getText());
									
								      in=true;
								
								      thread=new Thread(encry);
								      thread.start();
									
										
										selectphoto.clearflow();
								    
									}
									else
									{
										try {
										Decrytion decry=new Decrytion();
										if(!location.getText().contentEquals("默认为原图片所在文件夹"))
											decry.setDepath(location.getText());
										
								
								      in2=true;
								      thread2=new Thread(decry);
								      thread2.start();
								      selectphoto.clearflow();
										}catch(Exception e) {
											
											Alert a=new Alert(Alert.AlertType.ERROR,e.getMessage()+"  请稍后再试！");
											e.printStackTrace();
										}
										
										
									}
	//加密在这里！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
									for(int j=0;j<bbr.getButtons().size();j++)
									{
										if(bbr.getButtons().get(j).getId().contentEquals("all")) 
										{
											RadioButton rb=(RadioButton)bbr.getButtons().get(j);
											rb.setSelected(false);
										}
									}
									
								}
							}
							else
							{
								Alert alert=new Alert(Alert.AlertType.INFORMATION,"当前选择队列仍有任务!请任务完成后重试！");
								alert.initOwner(primaryStage);
								alert.initModality(Modality.WINDOW_MODAL);
								alert.show();
								
								
							}
						
						}
						
					}	
					});
					
				}
				if(bbr.getButtons().get(i).getId().contentEquals("all")) 
				{
					radiobutton=(RadioButton) bbr.getButtons().get(i);
					radiobutton.selectedProperty().addListener(new ChangeListener<Boolean>() {

						@Override
						public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
								Boolean newValue) {
							// TODO Auto-generated method stub
							if(newValue&&newValue!=oldValue)
							{
								
								selectphoto.selectall();
								
							}
							else
							{
								selectphoto.undoselectall();
							}
						}
						
					});
				}
			}
			rootLayout.setCenter(photoPane);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
	
    
    public void showattributes() {
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("View/Attributes.fxml"));
         try {
        	 Attributes=(AnchorPane)loader.load();
        	 
        	 TabPane tabpane=new TabPane();
        	 tabpane.setId("tabpane");
        	 
        	 tabpane.setPrefHeight(TOOL_HEIGHT*0.27);
        	 tabpane.setPrefWidth(TOOL_WIDTH);
        	 tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        	 Tab tab1=new Tab("属性");
        	 Tab tab2=new Tab("加密控制台");
        	 tab2.setId("tab2");
        	 Tab tab3=new Tab("解密控制台");
        	 tab3.setId("tab3");
        	 tabpane.getTabs().add(0,tab1);
        	 tabpane.getTabs().add(1,tab2);
        	 tabpane.getTabs().add(2, tab3);
        	 Attributes.getChildren().add(0,tabpane);
        	 HBox hbox=new HBox();
        	 hbox.setAlignment(Pos.CENTER_LEFT);
        	
        	 Label inputlabel=new Label("输入加密变换次数");
        	 
        	 inputtext.setId("inputtext");
        	 inputtext.setTextFormatter(new TextFormatter<String>(new UnaryOperator<TextFormatter.Change>() {
                 @Override
                 public TextFormatter.Change apply(TextFormatter.Change change) {
                     String value = change.getText();
                     if (value.matches("[0-9]*")) {
                         return change;
                     }
                     return null;
                 }
             }));
   
        	 hbox.getChildren().addAll(inputlabel,inputtext);
        	 hbox.setMargin(inputlabel, new Insets(5,5,5,5));
        	 hbox.setMargin(inputtext, new Insets(5,5,5,5));
        	 inputtext.focusedProperty().addListener(new ChangeListener<Boolean>() {
//失去焦点即改变值
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					// TODO Auto-generated method stub
					if(!newValue&&inputtext.getText()!=null&&!inputtext.getText().contentEquals("")) {
						if(Integer.parseInt(inputtext.getText())<=255)
						{ 
							changetime=Integer.parseInt(inputtext.getText());
						}
						else
						{	
							changetime=255;
							inputtext.setText("255");
						}
					}
					else
						changetime=0;
				}
        		 
        	 });
        	 inputtext.setOnKeyPressed(new EventHandler<KeyEvent>() {
//键入enter即改变值
				@Override
				public void handle(KeyEvent event) {
					// TODO Auto-generated method stub
					if(event.getCode()==KeyCode.ENTER)
					{
						if(inputtext.getText()!=null&&!inputtext.getText().contentEquals("")) {
							if(Integer.parseInt(inputtext.getText())<=255)
							{ 
								changetime=Integer.parseInt(inputtext.getText());
							}
							else
							{	
								changetime=255;
								inputtext.setText("255");
							}
						}
						else
							changetime=0;
						
					}
				}
        	}	 );
        	 
        	 Label l2=new Label("输出位置");
        	 location=new TextField();
        	 location.setText("默认为原图片所在文件夹");
        	 location.setStyle("-fx-text-fill:#778899;");
        	 location.setEditable(false);
        	 Button b2=new Button("选择位置");
        	 Button b3=new Button("默认位置");
        	 b2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					
					Stage fcstage=new Stage();
					DirectoryChooser filechooser=new DirectoryChooser();
					fcstage.initOwner(primaryStage);
					fcstage.initModality(Modality.WINDOW_MODAL);
					filechooser.setTitle("选择文件保存位置");
					File getfile=filechooser.showDialog(fcstage);
					location.setText(getfile.getAbsolutePath());
				
				}
        		 
        	 });
        	 b3.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					location.setText("默认为原图片所在文件夹");
				}
        		 
        	 });
        	 hbox.getChildren().addAll(l2,location,b2,b3);
        	 tab1.setContent(hbox);
        	 
        	 ScrollPane textscroll=new ScrollPane();
        	 textscroll.setId("textscroll");
        	 ScrollPane textscroll2=new ScrollPane();
        	 textscroll2.setId("textscroll2");
        	 console=new TextArea();//运行结果在这儿显示
        	 console.setId("console");
        	 console.setWrapText(true);
        	 textscroll.setContent(console);
        	 console.setPrefWidth(TOOL_WIDTH);
        	 console.insertText(0, "运行");
        	 console2=new TextArea();//运行结果在这儿显示
        	 console2.setId("console2");
        	 console2.setWrapText(true);
        	 textscroll2.setContent(console2);
        	 console2.setPrefWidth(TOOL_WIDTH);
        	 console2.insertText(0, "运行");
        	 tab2.setContent(textscroll);
        	 tab3.setContent(textscroll2);
        	 rootLayout.setBottom(Attributes);
         }catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
    
    public void showMenutop() {
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("View/Menutop.fxml"));
         try {
        	 Menutop=(AnchorPane)loader.load();
      
        	 double size=Menutop.getHeight();
        	 MenuBar bar=(MenuBar) Menutop.getChildren().get(0);
        	 bar.setPrefWidth(TOOL_WIDTH);
        	 bar.setPrefHeight(TOOL_HEIGHT*0.03);
        	
        	 rootLayout.setTop(Menutop);
         }catch(Exception e){
        	 e.printStackTrace();
         }
         
    }
    
    public void showAdditionalTool() {
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("View/AdditionalTool.fxml"));
         try {
        	 AdditionalTool=(AnchorPane)loader.load();
        	ScrollPane scrollpane=new ScrollPane();
        	ScrollPane scrollpane2=new ScrollPane();
        	TabPane list=new TabPane();
        	Tab t1=new Tab("加密等待队列");
        	Tab t2=new Tab("解密等待队列");
        	list.getTabs().add(t1);
        	list.getTabs().add(t2);
        	t1.setContent(scrollpane);
        	t1.setClosable(false);
        	t2.setContent(scrollpane2);
        	t2.setClosable(false);
        	list.setPrefSize(TOOL_WIDTH*0.2, TOOL_HEIGHT*0.7);
        	readyarray.setPrefHeight(TOOL_HEIGHT*0.7);
			readyarray.setPrefWidth(TOOL_WIDTH*0.2);
			readyarray2.setPrefHeight(TOOL_HEIGHT*0.7);
			readyarray2.setPrefWidth(TOOL_WIDTH*0.2);
        	scrollpane.setContent(readyarray);
        	scrollpane2.setContent(readyarray2);

        	 AdditionalTool.getChildren().add(list);
        	 rootLayout.setRight(AdditionalTool);
         }catch(Exception e){
        	 e.printStackTrace();
         }
         
    }
    public static TextArea getconsole() {
    	return console;
    }
    public static void setconsole(String message) {
    	
    	console.appendText(message);
    
    }
    public static void clearconsole() {
    	console.clear();
    	
    }
    public static TextArea getconsole2() {
    	return console2;
    }
    public static void setconsole2(String message) {
    	
    	console2.appendText(message);
    
    }
    public static void clearconsole2() {
    	console2.clear();
    	
    }
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	
	
    public static void main(String[] args) {

        launch(args);
      
    }
    public static void close() {
    	System.exit(0);
    	
    }

	
}
