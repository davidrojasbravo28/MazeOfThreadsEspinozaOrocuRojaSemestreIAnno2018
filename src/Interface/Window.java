/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Domain.Grid;
import Domain.Mosaic;
import Data.MosaicFile;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class Window extends Application{
    //atributos
    private GridPane gridPane;
    private Scene scene;
    private MenuBar menuBarOpenSave;
    private Menu menuOpen;
    private Menu menuSave;
    private MenuItem menuItemOpenImage;
    private MenuItem menuItemOpenProject;
    private MenuItem menuItemSaveImage;
    private MenuItem menuItemSaveNewProject;
    private MenuItem menuItemSaveProjectChanges;
    private Canvas canvasLeftSide;//canvas del lado izquierdo
    private ScrollPane scrollpaneLeft;//scrollpane del lado izquierdo
    private Canvas canvasRightSide;//canvas del lado derecho
    private ScrollPane scrollpaneRight;//scrollpane del lado derecho
    private GraphicsContext graphicsContextLeftSide;//izquierdo
    private GraphicsContext graphicsContextRightSide;//derecho
    private FileChooser fileChooserImage;//filechooser para image
    private FileChooser fileChooserFile;//filechooser para file
    private Label labelWidthImageLeft;
    private Label labelHeightImageLeft;
    private int cellSize;//tamaño nxn
    private Label labelCellSize;
    private TextField textFieldCellSize;
    private Label labelWidthMosaic;
    private TextField textFieldWidthMosaic;
    private Label labelHeightMosaic;
    private TextField textFieldHeightMosaic;
    private Button buttonMakeMosaic;
    private ContextMenu contextMenuRightCanvas;
    private Menu menuRotate;
    private MenuItem menuItemRotateRight;
    private MenuItem menuItemRotateLeft;
    private MenuItem menuItemVerticalFlip;
    private MenuItem menuItemHorizontalFlip;
    private MenuItem menuItemDelete;
    private Grid makeGrid;
    private Image imageLeft;//Imagen en el canvasLeftSide
    private WritableImage writableImageRight;//Imagen copiada en el canvasRightSide
    private double xRight;//punto X de la cuadricula en el que dio click en el canvas derecho
    private double yRight;//punto Y de la cuadricula en el que se dio click en el canvas derecho
    private boolean modified;//bandera para saber si el mosaic ha sido modificado
    private boolean fileIsOpen;//bander para saber si hay o no un archivo abierto
    private MosaicFile mosaicFileTemp;//Mosaic File que recibira el archivo que se abra y permitira guardar los cambios en el mosaic
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Project");//título ventana
        initComponents(primaryStage);//inicializa componentes
        primaryStage.show();//muestra la ventana
    }//start
    //inicializa los componentes de la ventana
    private void initComponents(Stage primaryStage) {
        
        //menubar para abrir o guardar
        this.menuItemOpenImage= new MenuItem("Image");//Abre imagen
        this.menuItemOpenProject= new MenuItem("Project");//Abre archivo
        this.menuItemOpenImage.setOnAction(menuItemOpenImageAction);
        this.menuItemOpenProject.setOnAction(menuItemOpenProjectAction);
        this.menuOpen= new Menu("Open");//menu abrir
        this.menuOpen.getItems().addAll(menuItemOpenImage, menuItemOpenProject);
        this.menuItemSaveImage= new MenuItem("Image Mosaic");//Guarda Imagen
        this.menuItemSaveNewProject= new MenuItem("New Project");//Guarda nuevo archivo
        this.menuItemSaveProjectChanges= new MenuItem("Changes");//Guarda cambios
        this.menuItemSaveImage.setOnAction(menuItemSaveImageAction);
        this.menuItemSaveNewProject.setOnAction(menuItemSaveNewProjectAction);//guardar new project
        this.menuItemSaveProjectChanges.setOnAction(menuItemSaveProjectChangesAction);
        //menuItemSaveProjectChanges esta deshabilitado hasta que haya un archivo abierto y Mosaic sea modificado
        this.menuItemSaveProjectChanges.setDisable(true);
        //menuItemSaveNewProject y menuItemSaveImage deshabilitados hasta que se cree o cargue un Mosaic
        this.menuItemSaveNewProject.setDisable(true);
        this.menuItemSaveImage.setDisable(true);
        this.menuSave= new Menu("Save");//menu guardar
        this.menuSave.getItems().addAll(menuItemSaveImage, menuItemSaveProjectChanges, menuItemSaveNewProject);
        this.menuBarOpenSave= new MenuBar();//menu bar 
        this.menuBarOpenSave.getMenus().addAll(menuOpen, menuSave);
        
        int WIDTH = 800;//ancho de la ventana al abrir
        int HEIGHT = 600;//alto de la ventan al abrir 
        
        //crea los canvas para dibujar en ellos y los agrega a scrollpane
        this.canvasLeftSide= new Canvas(WIDTH/2, HEIGHT/2);
        this.canvasRightSide= new Canvas(WIDTH/2, HEIGHT/2);
        this.canvasLeftSide.setOnMouseClicked(canvasLeftMouse);
        this.canvasRightSide.setOnMouseClicked(canvasRightMouse);
        this.scrollpaneLeft= new ScrollPane(canvasLeftSide);
        this.scrollpaneRight= new ScrollPane(canvasRightSide);
        
        //x y y del cuadro en la cuadricula canvas derecho
        this.xRight=0;
        this.yRight=0;
        
        //crea boton makeMosaic
        this.buttonMakeMosaic= new Button("Make Mosaic");//hacer mosaic
        this.buttonMakeMosaic.setOnAction(buttonMakeMosaicAction);
        
        //label y textfield
        this.labelWidthImageLeft= new Label("Width:");
        this.labelHeightImageLeft= new Label("Height:");
        this.labelCellSize= new Label("Cell Size:");
        this.textFieldCellSize= new TextField();
        this.labelWidthMosaic= new Label("Width:");
        this.textFieldWidthMosaic= new TextField();
        this.labelHeightMosaic= new Label("Height:");
        this.textFieldHeightMosaic= new TextField();
        
        //root en el que se agrega el menubar para abrir imagen o archivo o guardar imagen o archivo
        Group root= new Group();
        root.getChildren().add(menuBarOpenSave);
           
        //gridpane
        this.gridPane= new GridPane();
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);
        this.gridPane.add(root, 0, 0);
        this.gridPane.add(scrollpaneLeft, 0, 2);
        this.gridPane.add(scrollpaneRight, 1, 2);
        this.gridPane.add(labelWidthImageLeft, 0, 3);
        this.gridPane.add(labelHeightImageLeft, 0, 7);
        this.gridPane.add(labelWidthMosaic, 1, 3);
        this.gridPane.add(textFieldWidthMosaic, 1, 5);
        this.gridPane.add(labelHeightMosaic, 1, 7);
        this.gridPane.add(textFieldHeightMosaic, 1, 9);
        this.gridPane.add(labelCellSize, 1, 11);
        this.gridPane.add(textFieldCellSize, 1, 13);
        this.gridPane.add(buttonMakeMosaic, 1, 14);
        
        //contextmenu en el que se agregan las acciones que se pueden realizar sobre la imagen del mosaic
        this.menuItemRotateRight= new MenuItem("↷");
        this.menuItemRotateLeft= new MenuItem("↶");
        this.menuItemRotateRight.setOnAction(menuItemRotateRightAction);
        this.menuItemRotateLeft.setOnAction(menuItemRotateLeftAction);
        this.menuRotate= new Menu("Rotate");
        this.menuRotate.getItems().add(menuItemRotateRight);
        this.menuRotate.getItems().add(menuItemRotateLeft);
        this.menuItemVerticalFlip= new MenuItem("Vertical Flip");
        this.menuItemHorizontalFlip= new MenuItem("Horizontal Flip");
        this.menuItemDelete= new MenuItem("Delete");
        this.menuItemVerticalFlip.setOnAction(menuItemVerticalFlipAction);
        this.menuItemHorizontalFlip.setOnAction(menuItemHorizontalFlipAction);
        this.menuItemDelete.setOnAction(menuItemDeleteAction);
        this.contextMenuRightCanvas= new ContextMenu();
        this.contextMenuRightCanvas.getItems().add(menuRotate);
        this.contextMenuRightCanvas.getItems().add(menuItemVerticalFlip);
        this.contextMenuRightCanvas.getItems().add(menuItemHorizontalFlip);
        this.contextMenuRightCanvas.getItems().add(menuItemDelete);
        
        //graphicsContext
        this.graphicsContextLeftSide= canvasLeftSide.getGraphicsContext2D();
        this.graphicsContextRightSide= canvasRightSide.getGraphicsContext2D();
        
        //crea el filechooser para cargar imagen
        this.fileChooserImage = new FileChooser();
        this.fileChooserImage.setTitle("Image");//titulo fileChooserImage
        this.fileChooserImage.getExtensionFilters().addAll(
             new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));//extensiones que leera el fileChooserImage
        //crea el FileChooser.ExtensionFilter para guardar imagen
        FileChooser.ExtensionFilter extensionFilterImage= new FileChooser.ExtensionFilter("png files (*.png)", "*.png");//guardara la imagen en formato png
        this.fileChooserImage.getExtensionFilters().add(extensionFilterImage);
        
        //filechooser para cargar archivo
        this.fileChooserFile= new FileChooser();
        this.fileChooserFile.setTitle("Project");//titulo fileChooserFile
        this.fileChooserFile.getExtensionFilters().add(new ExtensionFilter("text", "*.txt"));//extensiones que leera
        //crea FileChooser.ExtensionFilter para guardar archivo
        FileChooser.ExtensionFilter extensionFilterFile= new FileChooser.ExtensionFilter("text (*.txt)", "*.txt");
        this.fileChooserFile.getExtensionFilters().add(extensionFilterFile);
        
        //banderas
        this.modified= false;//Indica si hay cambios o un mosaic nuevo para guardar
        this.fileIsOpen= false;//Indica si el mosaic en el que se esta trabajando fue cargado desde un archivo
        
        //scene
        this.scene= new Scene(this.gridPane, WIDTH, HEIGHT);

        primaryStage.setScene(this.scene);//agrega contenedor a la ventana
        primaryStage.setOnCloseRequest(closeWindow);

    }//initComponents
    
    //METODOS
    
    //Se utiliza para mantener el tamano de los scrollpane al abrir imagen o crear mosaic
    private void scaleComponents(){
        this.scrollpaneLeft.setMaxSize(scene.getWidth()/2, scene.getHeight()/2);
        this.scrollpaneRight.setMaxSize(scene.getWidth()/2, scene.getHeight()/2); 
    }//scaleComponents
    
    /*Si hay un archivo abierto y el mosaic es modificado menuItemSaveprojectChanges es habilitado  
    entonces se pueden guardar los cambios, si mosaic no es modificado no se habilita ya que no habria cambios que guardar
    despues de guardar los cambios menuItemSaveprojectChanges vuelve a estar deshabilitado*/
    private void menuItemSaveProjectChangesActive(){
        if(fileIsOpen==true){
            if(modified==true){
                this.menuItemSaveProjectChanges.setDisable(false);
            }//if
            if(modified==false){
                this.menuItemSaveProjectChanges.setDisable(true);
            }//if
        }//if   
        if(fileIsOpen==false){
            this.menuItemSaveProjectChanges.setDisable(true);
        }//if
    }//menuItemSaveProjectChangesActive()
    
    /*Crea una WritableImage en la que se copia el contenido del canvasRightSide que es el canvas 
    en el que se encuentra el mosaic y guarda la imagen (WritableImage) en la misma direccion que el archivo
    en el que se guarda el project(mosaicFile)*/
    private void saveImageWithFile(Mosaic mosaic){
        try {
            WritableImage saveImage= new WritableImage((int)canvasRightSide.getWidth(), (int)canvasRightSide.getHeight());
            ImageView imageView= new ImageView(saveImage);
            imageView.resize(canvasRightSide.getWidth(), canvasRightSide.getHeight());
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasRightSide.snapshot(snapshotParameters, saveImage);
            RenderedImage renderedImage= SwingFXUtils.fromFXImage(saveImage, null);
            ImageIO.write(renderedImage, "png", new File(mosaic.getImageMosaic()));
        } //saveImageWithFile
        catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }//catch
    }//saveImageWithFile
    
    /*si fileIsOpen is true sustituye el archivo (mosaicFile) por un nuevo archivo con el mismo nombre y los cambios recientes
      si fileIsOpen es false crea un nuevo archivo (mosaicFile)*/
    private void saveProjectReplaceOrNew(){        
        try {
            Mosaic mosaic;//Mosaic para guardar los datos del mosaic que se desea guardar en el mosaicFile
            if(fileIsOpen==true){
                mosaicFileTemp= new MosaicFile(mosaicFileTemp.getMyFilePath());
                mosaic= new Mosaic((int)canvasRightSide.getWidth(), (int)canvasRightSide.getHeight(), cellSize, mosaicFileTemp.getMosaic().getImageMosaic());
                mosaicFileTemp.addMosaic(mosaic);
                modified=false;
                menuItemSaveProjectChangesActive();
            }//si hay un project abierto
            else{
                String path= fileChooserFile.showSaveDialog(scene.getWindow()).getAbsolutePath();
                mosaicFileTemp= new MosaicFile(path+".txt");
                mosaic= new Mosaic((int)canvasRightSide.getWidth(), (int)canvasRightSide.getHeight(), cellSize, path+".png");
                mosaicFileTemp.addMosaic(mosaic);
                fileIsOpen=true;
                modified=false;
            }//else
            saveImageWithFile(mosaic);
            mosaicFileTemp.close();
        }catch (FileNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
        }//catch
    }//saveProjectReplaceOrNew()
    
    //DRAW (metodos en los que se dibuja)
    
    /*Utiliza la variable global imageLeft en la que se encuentra la imagen dibujada en el canvasLeftSide 
     se utiliza para hacer cambios en el canvasLeftSide como cambiar la cuadricula*/
    private void reDrawLeft(){
        try {
            graphicsContextLeftSide.clearRect(0, 0, canvasLeftSide.getWidth(), canvasLeftSide.getHeight());
            //dibuja la imagen
            graphicsContextLeftSide.drawImage(imageLeft, 0, 0);//pinta una imagen
        }//try
        catch (Exception e) {
        }//catch        
        makeGrid.drawGrid(graphicsContextLeftSide, canvasLeftSide);   
   }//reDrawLeft()    
    
    //dibuja en el canvasRightSide 
    private void drawRight(WritableImage writableImage, double xRight, double yRight){   
        graphicsContextRightSide.clearRect(xRight, yRight, cellSize, cellSize);
        graphicsContextRightSide.drawImage(writableImage, xRight, yRight);
        //lineas horizontales
        graphicsContextRightSide.strokeLine(xRight, yRight, xRight+cellSize, yRight);
        graphicsContextRightSide.strokeLine(xRight, yRight+cellSize, xRight+cellSize, yRight+cellSize);
        //lineas verticales
        graphicsContextRightSide.strokeLine(xRight, yRight, xRight, yRight+cellSize);
        graphicsContextRightSide.strokeLine(xRight+ cellSize, yRight, xRight+cellSize, yRight+cellSize);
    }//drawRight
    
    //EVENTHANDLER
    
    //Abre imagen, se elige una imagen y se dibuja en el canvas izquierdo
    EventHandler<ActionEvent> menuItemOpenImageAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)  {
            try {
                imageLeft= new Image(new FileInputStream(fileChooserImage.showOpenDialog(scene.getWindow()).getAbsolutePath()));
                scaleComponents();//metodo scaleComponents()
                graphicsContextLeftSide.clearRect(0, 0, canvasLeftSide.getWidth(), canvasLeftSide.getHeight());
                //le da a canvas el tamaño de la imagen
                canvasLeftSide.setWidth(imageLeft.getWidth());
                canvasLeftSide.setHeight(imageLeft.getHeight());            
                graphicsContextLeftSide.drawImage(imageLeft, 0, 0);//pinta una imagen
                labelWidthImageLeft.setText("Width: "+imageLeft.getWidth());
                labelHeightImageLeft.setText("Height: "+imageLeft.getHeight());
                makeGrid.drawGrid(graphicsContextLeftSide, canvasLeftSide);//Dibuja cuadricula
            }//try
            catch (FileNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }catch (Exception e) {
            }//catch            
        }//handle
    };//menuItemOpenImageAction
        
    /*abre un proyect, si hay un archivo (mosaicFile) abierto con cambios sin guardar pregunta si desea guardar
      si la respuesta es positiva se guardan los cambios y se abre otro archivo
      si la respuesta es no se abre otro archivo sin guardar los cambios
      si la respuesta es cancelar no pasa nada*/
    EventHandler<ActionEvent> menuItemOpenProjectAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            boolean cancel=false;
            if(modified==true){
                int buttonDialog= JOptionPane.showConfirmDialog(null, "¿Desea guardar? \n(los cambios no guardados en el actual Project se perderan)");
                if(buttonDialog==0){
                    saveProjectReplaceOrNew();
                }//if si guardar
                if(buttonDialog==2){
                    cancel=true;
                }//if cancelar
            }//if
            if(cancel==false){
                try {
                mosaicFileTemp= new MosaicFile(fileChooserFile.showOpenDialog(scene.getWindow()));
                if(mosaicFileTemp.getMosaic()==null){                    
                    JOptionPane.showMessageDialog(null, "Invalid File");
                }//si el archivo no contiene un objeto Mosaic entonces no es válido
                canvasRightSide.setWidth(mosaicFileTemp.getMosaic().getWidth());
                canvasRightSide.setHeight(mosaicFileTemp.getMosaic().getHeight());
                cellSize= mosaicFileTemp.getMosaic().getCellSize();
                Image imageOpen= new Image(new FileInputStream(mosaicFileTemp.getMosaic().getImageMosaic()));
                textFieldWidthMosaic.setText("");
                textFieldHeightMosaic.setText("");
                textFieldCellSize.setText("");
                labelWidthMosaic.setText("Width: "+(int)canvasRightSide.getWidth());
                labelHeightMosaic.setText("Height: "+(int)canvasRightSide.getHeight());
                labelCellSize.setText("cell size: "+cellSize);
                fileIsOpen=true;
                graphicsContextRightSide.clearRect(0, 0, canvasRightSide.getWidth(), canvasRightSide.getHeight());
                graphicsContextRightSide.drawImage(imageOpen, 0, 0);
                makeGrid= new Grid(cellSize);//instancia un nuevo Objeto Grid el constructor recibe la variable global cellSize
                makeGrid.drawGrid(graphicsContextRightSide, canvasRightSide);
                reDrawLeft();
                mosaicFileTemp.close();
                scaleComponents();
                modified=false;
                menuItemSaveNewProject.setDisable(false);//habilita menuitemSaveNewProject
                menuItemSaveImage.setDisable(false);//Habilita menuItemSaveImage
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception e){
            }//exception e evita que al dar click al boton cancelar se intente cargar un archivo
            }
        }//handle
    };//menuItemOpenProjectAction
    
   //Guarda la imagen del mosaic, hace una copia del canvasRightSide y la guarda en un archivo de imagen formato png
   EventHandler<ActionEvent> menuItemSaveImageAction= new EventHandler<ActionEvent>() {       
        @Override
        public void handle(ActionEvent event) {
            try {
                WritableImage saveImage= new WritableImage((int)canvasRightSide.getWidth(), (int)canvasRightSide.getHeight());
                ImageView imageView= new ImageView(saveImage);
                imageView.resize(canvasRightSide.getWidth(), canvasRightSide.getHeight());
                SnapshotParameters snapshotParameters= new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                canvasRightSide.snapshot(snapshotParameters, saveImage);
                RenderedImage renderedImage= SwingFXUtils.fromFXImage(saveImage, null);
                ImageIO.write(renderedImage, "png", fileChooserImage.showSaveDialog(scene.getWindow()));
            }catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }catch (Exception e) {
                }//evita que se intente abrir imagen al dar click en cancelar o al cerrar filechooser
        }//handle
    };//menuItemSaveImageAction
   
    /*Guarda el mosaic en un nuevo archivo
      Si el mosaic a guardar fue creado desde la ventana y no cargado de un archivo se guarda 
      Si el mosaic a guardar se cargo desde un archivo y ha sido modificado 
      al guardar nuevo archivo la bandera modified sigue en true 
      hasta que se guarden los cambios en el archivo del que se cargo el mosaic*/
    EventHandler<ActionEvent> menuItemSaveNewProjectAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            //Si if(fileIsOpen==false) el mosaic a guardar fue creado desde la ventana y no cargado de un archivo
            if(fileIsOpen==false){
                saveProjectReplaceOrNew();
            }else{
                try {
                    String path= fileChooserFile.showSaveDialog(scene.getWindow()).getAbsolutePath();
                    MosaicFile mosaicFile= new MosaicFile(path+".txt");
                    Mosaic mosaic= new Mosaic((int)canvasRightSide.getWidth(), (int)canvasRightSide.getHeight(), cellSize, path+".png");
                    mosaicFile.addMosaic(mosaic);
                    saveImageWithFile(mosaic);
                }//try
                catch (FileNotFoundException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }catch(Exception e){                    
                }//catch
            }//else
        }//handle
    };//menuItemSaveNewProjectAction
    
    //reemplaza el archivo abierto por uno con los cambios realizados al proyecto
    EventHandler<ActionEvent> menuItemSaveProjectChangesAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            saveProjectReplaceOrNew();
        }//handle
    };//menuItemSaveProjectChangesAction
    
    /*Crea un nuevo mosaic, limpia el canvasRightSide y dibuja la nueva cuadricula 
      redibuja el canvasLeftSide(metodo reDrawLeft())*/ 
    EventHandler<ActionEvent> buttonMakeMosaicAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            boolean cancel= false;
            if(modified==true){     
                //pregunta si desea crear nuevo mosaico y guardar el actual
                int buttonDialog= JOptionPane.showConfirmDialog(null, "¿Desea guardar? \n(los cambios no guardados en el actual se perderan)");
                if(buttonDialog==0){
                    saveProjectReplaceOrNew();  
                }//if aceptar
                if(buttonDialog==2){
                    cancel=true;
                }//cancelar
            }//if
            if(cancel==false){
                try {
                int width= Integer.parseInt(textFieldWidthMosaic.getText());
                int height= Integer.parseInt(textFieldHeightMosaic.getText());
                int cell= Integer.parseInt(textFieldCellSize.getText());
                if(width<1 || height<1 || cell<1){
                    JOptionPane.showMessageDialog(null, "width, height y cell size no pueden tener un valor menor a uno");
                }//if
                else{
                    if(cell>width || cell>height){
                        JOptionPane.showMessageDialog(null, "cell size no puede ser menor que width o height");
                    }//if
                    else{
                        graphicsContextRightSide.clearRect(0, 0, canvasRightSide.getWidth(), canvasRightSide.getHeight());  
                        canvasRightSide.setWidth((width));
                        canvasRightSide.setHeight((height));
                        cellSize= cell;   
                        modified=true;
                        fileIsOpen=false;
                        menuItemSaveProjectChangesActive();
                        makeGrid= new Grid(cellSize);//instancia un nuevo Objeto Grid el constructor recibe la variable global cellSize
                        makeGrid.drawGrid(graphicsContextRightSide, canvasRightSide);
                        reDrawLeft();
                        scaleComponents();
                        menuItemSaveNewProject.setDisable(false);
                        menuItemSaveImage.setDisable(false);
                        textFieldWidthMosaic.setText("");
                        textFieldHeightMosaic.setText("");
                        textFieldCellSize.setText("");
                        labelWidthMosaic.setText("Width: "+width);
                        labelHeightMosaic.setText("Height: "+height);
                        labelCellSize.setText("cell size: "+cell);
                    }//else
                }//else
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe introducir solamente numeros");
        }//catch 
            }
        }//handler
    };//makeGrid
    
    //Copia la imagen en la celda de la cuadricula que recibio el click      
    EventHandler<MouseEvent> canvasLeftMouse= new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double xLeft=0;//x
            double yLeft=0;//y
            
            for(int i=1; i<makeGrid.getPointX(canvasLeftSide).length-1; i++){
                if(event.getX()<makeGrid.getPointX(canvasLeftSide)[i]){
                   xLeft= makeGrid.getPointX(canvasLeftSide)[i-1];//le da a xLeft el valor del punto x en la celda del punto x en el que se dio click
                   break;
                }//if
            }//for
            
            for(int j=1; j<makeGrid.getPointY(canvasLeftSide).length-1; j++){
                if(event.getY()<makeGrid.getPointY(canvasLeftSide)[j]){
                    yLeft= makeGrid.getPointY(canvasLeftSide)[j-1];//le da a yLeft el valor del punto Y en la celda del punto Y en el que se dio click
                    break;
                }//if
            }//for            
            writableImageRight= new WritableImage(cellSize, cellSize);
            Rectangle2D rectangle2D= new Rectangle2D(xLeft, yLeft, cellSize, cellSize);
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setViewport(rectangle2D);
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasLeftSide.snapshot(snapshotParameters, writableImageRight);
        }//handle
    };//copia la imagen de la celda de la cuadrícula en la que se dio el click
    
    EventHandler<MouseEvent> canvasRightMouse= new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            //Si se dio click izquierdo se pega la imagen en la celda que recibío el click
            if(event.getButton()==MouseButton.PRIMARY){
                for(int i=1; i<makeGrid.getPointX(canvasRightSide).length-1; i++){
                    if(event.getX()<makeGrid.getPointX(canvasRightSide)[i]){
                        xRight= makeGrid.getPointX(canvasRightSide)[i-1];
                        break;
                    }//if
                }//for i
                
                for(int j=1; j<makeGrid.getPointY(canvasRightSide).length-1; j++){
                    
                    if(event.getY()<makeGrid.getPointY(canvasRightSide)[j]){
                        yRight= makeGrid.getPointY(canvasRightSide)[j-1];
                        break;
                    }//if
                }//for j
                drawRight(writableImageRight, xRight, yRight);
                modified=true;
                menuItemSaveProjectChangesActive();
            }//if click izquierdo
         //click derecho
         //se muestra el menuContext con las acciones que se pueden realizar en la celda que recibio el click
            if(event.getButton()==MouseButton.SECONDARY){
                for(int i=1; i<makeGrid.getPointX(canvasRightSide).length-1; i++){
                    
                    if(event.getX()<makeGrid.getPointX(canvasRightSide)[i]){
                        xRight= makeGrid.getPointX(canvasRightSide)[i-1];
                        break;
                    }//if
                }//for i
                
                for(int j=1; j<makeGrid.getPointY(canvasRightSide).length-1; j++){
                    if(event.getY()<makeGrid.getPointY(canvasRightSide)[j]){
                        yRight= makeGrid.getPointY(canvasRightSide)[j-1];
                        break;
                    }//if
                }//for j
                 
                canvasRightSide.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent event) {
                        contextMenuRightCanvas.show(canvasRightSide, event.getScreenX(), event.getScreenY());
                    }//muestra el contextmenu al dar click dercho   
                });
            }//if click derecho
        }//handle
    };//canvasRightMouse
    
    //rota la imagen en la celda seleccionada 90 grados hacia el lado derecho
    EventHandler<ActionEvent> menuItemRotateRightAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            WritableImage writableImage= new WritableImage(cellSize, cellSize);
            Rectangle2D rectangle2D= new Rectangle2D(xRight, yRight, cellSize, cellSize);
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setViewport(rectangle2D);
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasRightSide.snapshot(snapshotParameters, writableImage);
            SnapshotParameters snap= new SnapshotParameters();
            snap.setFill(Color.TRANSPARENT);
            ImageView imageView= new ImageView(writableImage);
            imageView.setRotate(90);
            imageView.snapshot(snap, writableImage);            
            drawRight(writableImage, xRight, yRight);                
            modified=true;
            menuItemSaveProjectChangesActive();
        }//handle
    };//menuIteRotateRightAction
    
    //rota la imagen en la celda seleccionada 90 grados hacia el lado izquierdo
    EventHandler<ActionEvent> menuItemRotateLeftAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            WritableImage writableImage= new WritableImage(cellSize, cellSize);
            Rectangle2D rectangle2D= new Rectangle2D(xRight, yRight, cellSize, cellSize);
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setViewport(rectangle2D);
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasRightSide.snapshot(snapshotParameters, writableImage);
            SnapshotParameters snap= new SnapshotParameters();
            snap.setFill(Color.TRANSPARENT);
            ImageView imageView= new ImageView(writableImage);
            imageView.setRotate(-90);
            imageView.snapshot(snap, writableImage);
            drawRight(writableImage, xRight, yRight);
            modified=true;
            menuItemSaveProjectChangesActive();
        }//handle
    };//event handler menuItemRotatLeftAction
    
    //voltea la imagen verticalmente en la celda seleccionada
    EventHandler<ActionEvent> menuItemVerticalFlipAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            WritableImage writableImage= new WritableImage(cellSize, cellSize);
            Rectangle2D rectangle2D= new Rectangle2D(xRight, yRight, cellSize, cellSize);
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setViewport(rectangle2D);
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasRightSide.snapshot(snapshotParameters, writableImage);
            SnapshotParameters snap= new SnapshotParameters();
            snap.setFill(Color.TRANSPARENT);
            ImageView imageView= new ImageView(writableImage);
            imageView.setScaleX(-1);
            imageView.snapshot(snap, writableImage);
            drawRight(writableImage, xRight, yRight);
            modified=true;
            menuItemSaveProjectChangesActive();
        }//handle
    };//eventhandler menuItemVerticalFlipAction
    
    //voltea la imagen horizontalmente en la celda seleccionada
    EventHandler<ActionEvent> menuItemHorizontalFlipAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            WritableImage writableImage= new WritableImage(cellSize, cellSize);
            Rectangle2D rectangle2D= new Rectangle2D(xRight, yRight, cellSize, cellSize);
            SnapshotParameters snapshotParameters= new SnapshotParameters();
            snapshotParameters.setViewport(rectangle2D);
            snapshotParameters.setFill(Color.TRANSPARENT);
            canvasRightSide.snapshot(snapshotParameters, writableImage);
            SnapshotParameters snap= new SnapshotParameters();
            snap.setFill(Color.TRANSPARENT);
            ImageView imageView= new ImageView(writableImage);
            imageView.setScaleY(-1);
            imageView.snapshot(snap, writableImage);
            drawRight(writableImage, xRight, yRight);
            modified=true;
            menuItemSaveProjectChangesActive();
        }//handle
    };//eventhandler menuItemHorizontalFlipAction
    
    //borra la imagen en la celda seleccionada
    EventHandler<ActionEvent> menuItemDeleteAction= new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            graphicsContextRightSide.clearRect(xRight, yRight, cellSize, cellSize);
            //lineas horizontales
            graphicsContextRightSide.strokeLine(xRight, yRight, xRight+cellSize, yRight);
            graphicsContextRightSide.strokeLine(xRight, yRight+cellSize, xRight+cellSize, yRight+cellSize);
            //lineas verticales
            graphicsContextRightSide.strokeLine(xRight, yRight, xRight, yRight+cellSize);
            graphicsContextRightSide.strokeLine(xRight+ cellSize, yRight, xRight+cellSize, yRight+cellSize);
            modified=true;
            menuItemSaveProjectChangesActive();
        }//handle
    };//delete menuItemDeleteAction
    
    /*Al cerrar la ventana si hay cambios que no se han guardao pregunta si se desean guardar
    si la respuesta es si guarda los cambios y cierra la ventana
    si la respuesta es no cierra la ventana sin guardar
    si la respuesta es cancelar no hace nada
    si no hay cambios se pregunta si desea salir*/
    EventHandler<WindowEvent> closeWindow= new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {            
            Stage primaryStage= new Stage();
            int buttonDialog;
            //si hay cambios no guardados pregunta si se desean guardar
            if(modified==true){                    
                buttonDialog= JOptionPane.showConfirmDialog(null, "¿Desea guardar antes de salir? \n(los cambios no guardados se perderan)");
                if(buttonDialog==0){    
                    saveProjectReplaceOrNew();
                    primaryStage.setOnCloseRequest(this);
                }//if aceptar
                if(buttonDialog==1){
                    primaryStage.setOnCloseRequest(this);
                }//if no guardar cambios
                if(buttonDialog==2){
                    event.consume();
                }//if cancelar
            }//if
            else{
                buttonDialog= JOptionPane.showConfirmDialog(null, "¿Salir?", " ", 0);
                if(buttonDialog==0){   
                    primaryStage.setOnCloseRequest(this);
                }//if si
                if(buttonDialog==1){
                    event.consume();
                }//if no
            }//else
        }//handle
    };//closeWindow
    
}//fin clase
