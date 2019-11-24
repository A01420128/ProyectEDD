import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// TODO: Hacer tree pane escalable o ninguno Borderpane no escalable la ventana no escalable.
// TODO: Hacer animaciones del AVL.


public class MainFx extends Application {

	// Propiedades de layout
	final public String NAME_STAGE = "AVL Tree";
	final public double WIDTH_STAGE = 700;
	final public double HEIGHT_STAGE = 700;
	final public String MAIN_COLOR = "-fx-background-color: green";

	public int profundidad = 1; // TODO: Cambiar la profundidad a la profundidad del arbol

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// Configurar la ventana principal, Nombre y tamano
		primaryStage.setTitle(NAME_STAGE);
		primaryStage.setWidth(WIDTH_STAGE);
		primaryStage.setHeight(HEIGHT_STAGE);

		// El Pane que tiene a todos los otros panes
		BorderPane mainPane = new BorderPane();

		// El pane de control
		ControlsPane bottomPane = new ControlsPane(mainPane);

		// La escena primero para dar tamano a main pane
		// La escena
		Scene scene = new Scene(mainPane);

		// Establecer la escena.
		primaryStage.setScene(scene);

		// Mostrar el stage
		primaryStage.show();

		// El pane del arbol
		TreePane topPane = new TreePane(mainPane);

		// Preparar el estilo de los panes.
		preparePanes(mainPane, topPane, bottomPane);



		// FIXME: BORRAR?
		//primaryStage.setResizable(false);
		//Rectangle2D screen= Screen.getPrimary().getVisualBounds();
		//primaryStage.setHeight(screen.getHeight());
		//primaryStage.setWidth(screen.getWidth());

	}

	// region Prepare methods

	public void preparePanes(BorderPane mainPane, TreePane topPane, ControlsPane bottomPane) {

//		mainPane.setStyle("-fx-background-color: #ded9d9"); // FIXME: Remove this color?
		mainPane.setStyle(MAIN_COLOR);

		// TODO: Remove this.
//		LeafPane leafPane = new LeafPane(topPane, 33);

		// Anadir los eventos a los bottones del panel de control
		bottomPane.getBttnAdd().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent a) {
				// Anade numero en el text field al arbol
				addToTree(bottomPane, topPane);
			}
		});
		bottomPane.getBttnDelete().addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent a) {
				// Remueve el elemento en el panel de control del arbol.
				deleteFromTree(bottomPane);
			}
		});
	}

	// endregion

	// region Agregar/Remover Metodos

	// Agregar un elemento al arbol
	public void addToTree(ControlsPane controlsPane, TreePane treePane) {
		// TODO: Anadir al arbol
		System.out.println("adding...");
		int number = controlsPane.getNumber();
		treePane.addLeaf(number);
		controlsPane.getTxtNumber().setText("");
		//nodoCircle.smaller();
//		profundidad++; // TODO: Profundidad del arbol
		System.out.println("New depth: " + profundidad);
//		temporalPane.moveYUp(profundidad); // TODO: Este se va a llamar dentro de addLeafRec para todos los nodos.
	}

	// Remover un elemento del arbol
	public void deleteFromTree(ControlsPane controlsPane) {
		// TODO: Todo este metodo con el arbol
		System.out.println("deleting...");
		controlsPane.getTxtNumber().setText("");
	}

	// endregion
}
