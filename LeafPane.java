/*
Nodo Circle es un stack pane con el texto del numero insertado y un circulo como background.
Cambia su tamano y posicion de acuerdo a la profundidad del arbol.
 */
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LeafPane extends StackPane {

    // region Class properties

    final private String COLOR_BACKGROUND = "-fx-background-color: blue";
    final private String FONT_NAME = "roboto";
    final private Color FONT_COLOR = Color.WHITE;
    final private int ANIM_DURATION = 500; // En milis.

    private Circle circle;
    private Label lblNumero;
    private Color color;
    private TreePane treePane; // El Pane donde el NodeCircle va a estar

    private int number;
    private LeafPane left;
    private LeafPane right;
    private int depth;

    // endregion

    // region Constructor

    public LeafPane(TreePane treePane, int number, double xOff, double yOff) {

        this.number = number;

        // Establece cual es treePane donde esta
        this.treePane=treePane;

        // Circulo como background
        circle=new Circle();

        // Label con el numero
        lblNumero = new Label("" + number);

        // Prepara el estylo
        prepareStyle();

        this.getChildren().add(circle);
        this.getChildren().add(lblNumero);


        treePane.getChildren().add(this);

        // TODO: Console print here.
        System.out.println("Width: " + this.getWidth());
        System.out.println(circle.getRadius());
        System.out.print("xOff: " + xOff);
        System.out.print("yOff: " + yOff);

        // Trasladar el panel completo a la mitad.
        double xCenterOfTreePane = (treePane.getWidth()/2)-this.getWidth()/2;
        double yCenterOfTreePane = (treePane.getHeight()/2)-this.getHeight()/2;
        double widthTreePane = treePane.getWidth();
        double heightTreePane = treePane.getHeight();
        this.setTranslateX((widthTreePane * xOff) - this.getWidth() / 2); // TODO aqui se les da nuevas posiciones a las hojas que apens crecen.
        this.setTranslateY((heightTreePane - heightTreePane * (1 / (2 * yOff))) - this.getHeight() / 2);

    }

    // endregion

    // region Prepare Methods

    private void prepareStyle() {
        // TODO: Console print here.
        System.out.println("TreePane, height: " + treePane.getHeight());

        this.setStyle(COLOR_BACKGROUND);
        this.setStyle(COLOR_BACKGROUND);
        this.setWidth(treePane.getHeight()/10);
        this.setHeight(treePane.getHeight()/10);

        // Randomizar el color del circulo de background
        circle.setFill(Color.rgb((int) Math.floor(Math.random()*200), (int) Math.floor(Math.random()*200), (int) Math.floor(Math.random()*200)));
        circle.setRadius(this.getHeight()/2);
        circle.setCenterX(this.getWidth()/2);
        circle.setCenterY(this.getHeight()/2);

        //  Estilo para la label.
        lblNumero.setFont(Font.font(FONT_NAME, this.getHeight()/2));
        lblNumero.setTextFill(FONT_COLOR);

    }
    // endregion

    // region Get/Set method

    public int getNumber() { return this.number; }
    public void setNumber(int number) { this.number = number; }

    public LeafPane getLeft() { return this.left; }
    public void setLeft(LeafPane left) { this.left = left; }

    public LeafPane getRight() { return this.right; }
    public void setRight(LeafPane right) { this.right = right; }

    public int getDepth() { return this.depth; }
    public void setDepth(int depth) { this.depth = depth; }

    // endregion

    // region Translation animations

    // Mueve el nodo en Y dependiendo de la profundidad.
    public void moveYUp(int profundidad){

        // Cambiar el tamano
        makeSmaller(profundidad);

        // Crea animacion y configurala.
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this); // Que nodo es
        translateTransition.setDuration(Duration.millis(ANIM_DURATION)); // La duracion
        translateTransition.setCycleCount(1); // Cuantas veces lo hace.
        //translateTransition.setByY(-this.getHeight()/(int)Math.pow(2,profundidad-1));
        translateTransition.setByY(-this.getHeight()/(profundidad*(profundidad-1))); // Lo que se mueve en el eje Y.

        // Corre la animacion
        translateTransition.play();
    }

    public void yMoveUp(double rootDepth){

        // Cambiar el tamano
//        makeSmaller(profundidad);

        // Crea animacion y configurala.
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(this); // Que nodo es
        translateTransition.setDuration(Duration.millis(ANIM_DURATION)); // La duracion
        translateTransition.setCycleCount(1); // Cuantas veces lo hace.
        translateTransition.setByY(-1 / (rootDepth * (rootDepth + 1)));
//        translateTransition.setByY(-this.getHeight()/(profundidad*(profundidad-1))); // Lo que se mueve en el eje Y.

        // Corre la animacion
        translateTransition.play();
    }


    //
    public void makeSmaller(int profundidad) {

        // La profundidad debe ser double
        double fraction = (double) profundidad;

        // TODO: Console prints here.
        System.out.println("F: "+fraction);
        System.out.println("Previous width: "+this.getWidth());

        // Crea animacion y configura.
        ScaleTransition scaleTransition=new ScaleTransition();
        scaleTransition.setNode(this); // Que nodo es
        //scaleTransition.setByX((double) (-(1/Math.pow(2,fraction-1))));
        scaleTransition.setByX((double) (-(1/(fraction*(fraction-1))))); //  Mueve en x para compensar tamano

        // TODO: Console print here.
        System.out.println("X: "+ scaleTransition.getByX());
        //scaleTransition.setByY((double) (-(1/Math.pow(2,fraction-1))));
        scaleTransition.setByY((double) (-(1/(fraction*(fraction-1))))); // Mueve en y para compensar tamano
        //scaleTransition.setByX(-.5);
        //scaleTransition.setByY(-.5);
        scaleTransition.setDuration(Duration.millis(ANIM_DURATION)); // La duracion
        scaleTransition.setCycleCount(1); // La animacion se hace una sola vez.

        // Corre la animacion de cambiar de lugar para ajustar cuando es mas chico.
        scaleTransition.play();

        // FIXME: El tamano no es una transicion?
        // Cambia el tamano despues de establecer establecer la posicion donde debe de estar.
        setPrefWidth(this.getWidth()/2);
        setHeight(this.getHeight()/2);

        // TODO: Console print here.
        System.out.println("New width: "+this.getWidth());

    }

    // endregion
}
