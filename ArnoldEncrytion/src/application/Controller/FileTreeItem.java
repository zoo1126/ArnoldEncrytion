package application.Controller;




import static application.Controller.FileTreeCreator.listitem;
import static application.MainApp.ROOT_FILE;

import java.io.File;
import java.util.function.Function;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

 

public class FileTreeItem extends TreeItem<File> {
 
	
    
    private boolean notInitialized = true;
 
    private final File file;
    private final Function<File,File[]> supplier;
 
    
    public FileTreeItem(File file) {
        super(file, U.getFileIconToNode(file));
        this.file = file;
   
       supplier = (File f) -> {
	            if (((FileTreeItem) this.getParent()).getFile() == ROOT_FILE) {
	                String name = U.getFileName(f);
	                
	                if (name.equals("ÍøÂç") || name.equals("¼ÒÍ¥×é")) {
	                   return new File[0];
	                }
	            }
	            return f.listFiles();
	        };
        
    }
    public FileTreeItem(File file, Function<File,File[]> supplier) {
        super(file,U.getFileIconToNode(file));
        this.file=file;
        this.supplier=supplier;
    }
 
 
 
    @Override
    public ObservableList<TreeItem<File>> getChildren() {
 
        ObservableList<TreeItem<File>> children = super.getChildren();
        
        if (this.notInitialized && this.isExpanded()) {
            
            this.notInitialized = false;    
            listitem.add(this);
            if (this.getFile().isDirectory()) {
                for (File f : supplier.apply(this.getFile())) {
                   
                    if( (f.isDirectory()||f.getName().toUpperCase().endsWith(".JPG")||f.getName().toUpperCase().endsWith(".JPEG")||f.getName().toUpperCase().endsWith(".PNG"))&&!f.getName().contentEquals("WPSÍøÅÌ")&&!f.getName().contentEquals("3D Objects")) {
                    	children.add(new FileTreeItem(f));
                    }
                }
 
            }
        }
      
        return children;
    }
 
   
    @Override
    public boolean isLeaf() {
 
        return !file.isDirectory();
    }
 
 
    public File getFile() {
        return file;
    }
    
    
    public void fresh() {
    	
        if (!this.notInitialized && this.isExpanded()) {
        	 ObservableList<TreeItem<File>> children = super.getChildren();
        	 File[] realfiles=supplier.apply(this.getFile());
        	 if (this.getFile().isDirectory())
        	 {
                 for (File f : realfiles) 
                 {
                	 Boolean find=false;
                	 for(TreeItem<File> fi:children)
                	 {
                		 if(fi.getValue().getAbsolutePath().contentEquals(f.getAbsolutePath()))
                		 {
                			 find=true;
                			 break;
                		 }
                	 }
                     if( !find&&(f.isDirectory()||f.getName().toUpperCase().endsWith(".JPG")||f.getName().toUpperCase().endsWith(".JPEG")||f.getName().toUpperCase().endsWith(".PNG"))&&!f.getName().contentEquals("WPSÍøÅÌ")&&!f.getName().contentEquals("3D Objects")) {
                     	children.add(new FileTreeItem(f));
                     }
                 }
                 
                 for(int i=children.size()-1;i>=0;i--) 
                 {
                	 TreeItem<File> fi=children.get(i);
                	 Boolean find=false;
                	 for(File f : realfiles)
                	 {
                		 if(fi.getValue().getAbsolutePath().contentEquals(f.getAbsolutePath()))
                		 {
                			 find=true;
                			 break;
                		 }
                	 }
                     if(!find) {
                     	children.remove(i);
                     }
                 }
             }
         
        }
    
    }
  
}