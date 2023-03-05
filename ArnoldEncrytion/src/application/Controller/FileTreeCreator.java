package application.Controller;


import static application.MainApp.TOOL_HEIGHT;
import static application.MainApp.TOOL_WIDTH;
import static application.MainApp.selectphoto;
import static application.MainApp.tabselectende;
import static application.MainApp.treeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
public class FileTreeCreator {
	public static File ROOT_FILE = FileSystemView.getFileSystemView().getRoots()[0];
	public static ArrayList<FileTreeItem> listitem=new ArrayList<FileTreeItem>();
	public TreeView<File> filetreecreator()
	{
		 TreeView<File> treeView = new TreeView<>();
		 treeView.setPrefWidth(TOOL_WIDTH*0.2);
		 treeView.setPrefHeight(TOOL_HEIGHT*0.7);
         FileTreeItem fileTreeItem = new FileTreeItem(ROOT_FILE, f -> {
             File[] allFiles = f.listFiles();
             
             File[] directorFiles = f.listFiles(File::isDirectory);
             
             List<File> list = new ArrayList<>(Arrays.asList(allFiles));
           
            
             for(int i=0;i<list.size();i++)
             {
             	
             	if(list.get(i).toString().contentEquals("网络"))
             		list.remove(i);
             }
             return list.toArray(new File[list.size()]);
         });
         
         treeView.setRoot(fileTreeItem);
         treeView.setCellFactory(new Callback<TreeView<File>, TreeCell<File>>() {

             public TreeCell<File> call(TreeView<File> tv) {
            	 TreeCell<File> tc=new TreeCell<File>() {

                     @Override
                     protected void updateItem(File item, boolean empty) {
                         super.updateItem(item, empty);
                         
                         setText((empty || item == null) ? null : U.getFileName(item));
              
                         setGraphic( (empty || item == null) ? null :U.getFileIconToNode(item));
                        
                     }
                    
                 };
                
                 return tc;
             }

         });
        

         treeView.setShowRoot(false);
       
         treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
             @Override
             public void changed(ObservableValue observable, Object oldValue, Object newValue) {
             	
                 TreeItem<File> currentSelectItem = (TreeItem<File>) newValue;
                 
             }
         });
         final ContextMenu cm = new ContextMenu(); 
         cm.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() { 
          @Override 
          public void handle(MouseEvent event) { 
           if (event.getButton() == MouseButton.SECONDARY) { 
            System.out.println("consuming right release button in cm filter"); 
            event.consume(); 
           } 
          } 
         }); 
         
         MenuItem menuItem2 = new MenuItem("刷新"); 
      
         cm.getItems().add(0, menuItem2);
         treeView.setOnMouseClicked(new EventHandler<MouseEvent>()
         {
             public void handle(MouseEvent mouseEvent)
             {
            	 if(mouseEvent.getButton()==MouseButton.PRIMARY&&mouseEvent.getClickCount() == 1)
            	 {
            		 if(mouseEvent.getTarget().toString().endsWith("'null'"))
            		 {
            			 treeView.getSelectionModel().clearSelection();
            		 }
            		 
            	 }
            	 else if(mouseEvent.getButton()==MouseButton.PRIMARY&&mouseEvent.getClickCount() == 2)
                 {
                     TreeItem<File> item = treeView.getSelectionModel().getSelectedItem();
                     if(item!=null&&item.getGraphic()!=null)
                     {
                    	
                    	 selectphoto.selectpho(item.getValue());
                     }
                     
                     treeView.getSelectionModel().clearSelection();
                    
                  }
                 else if(mouseEvent.getButton()==MouseButton.SECONDARY) {
                	 
                     cm.show(treeView, mouseEvent.getScreenX(), mouseEvent.getScreenY()); 
 //加入监听点击刷新和新建
                     cm.getItems().get(0).setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
						for(FileTreeItem f:listitem) {
							f.fresh();
						}
					        
						}
                     });
                 }
                 else
                 {
                	 cm.hide();
                 }
                	 
             }
         });
         
         return treeView;
	}

	public void freshtree(){
		
	}
}
