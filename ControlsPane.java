/*
ControlPane:
Pane donde se introduce un nuevo numero, button de add para anadir
y button de Delete para remover
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


public class ControlsPane extends FlowPane {

	// region Class properties

	// Propiedades finales que definen la layout
	final private double CONTROLS_HEIGHT = 40;
	final private double PREF_BTN_WIDTH = 70;
	final private double PREF_MARGIN = 10;
	final private String ADD_BTN_STRING = "Add";
	final private String DEL_BTN_STRING = "Delete";
	final private String PROMT_STRING = "Write a number";
	final private int MAX_ACCEPTED_NUM_LENGTH = 15;

	// Componentes
	private Button bttnAdd, bttnDelete;
	private TextField txtNumber;
	private BorderPane mainPane;

	// endregion

	// region Constructor

	public ControlsPane(BorderPane mainPane){

		// Se establece el main pane y configura la altura del ControlPane
		this.mainPane = mainPane;
		this.setHeight(CONTROLS_HEIGHT);

		txtNumber=new TextField();
		txtNumber.setPromptText(PROMT_STRING);

		// Se crean los componentes, buttones y textField
		bttnAdd=new Button(ADD_BTN_STRING);
		bttnDelete=new Button(DEL_BTN_STRING);

		// Anadir componentes a el ControlPane
		getChildren().add(0, txtNumber);
		getChildren().add(1, bttnAdd);
		getChildren().add(2, bttnDelete);

		// Anadir el ControlPane al bottom del main pane
		mainPane.setBottom(this);

		// Configurar Style
		prepareStyle();

		// Configurar interaccion del texto.
		prepareInteractions();


	}

	// endregion

	// region Get/Set Methods

	public Button getBttnAdd() {
		return bttnAdd;
	}

	public Button getBttnDelete() {
		return bttnDelete;
	}

	public TextField getTxtNumber() { return txtNumber; }

	public int getNumber() {
		return Integer.parseInt(txtNumber.getText());
	}

	// endregion

	// region Prepare ControlPane Methods

	// Prepara el style de los componentes
	private void prepareStyle() {
		// Buttons
		bttnAdd.setPrefWidth(PREF_BTN_WIDTH);
		bttnDelete.setPrefWidth(PREF_BTN_WIDTH);

		// Text
		txtNumber.setStyle("-fx-prompt-text-fill: grey");

		// Margins de panes
		// FIXME: Hay alguna razon por la que se modifican las clases y no las instancias i.e. BorderPane vs mainpane, FlowPane vs this
		BorderPane.setMargin(this, new Insets(PREF_MARGIN,PREF_MARGIN,PREF_MARGIN,PREF_MARGIN));

		double flowMargin = PREF_MARGIN / 2;
		FlowPane.setMargin(txtNumber, new Insets(flowMargin, flowMargin, flowMargin, flowMargin));
		FlowPane.setMargin(bttnAdd, new Insets(flowMargin, flowMargin, flowMargin, flowMargin));
		FlowPane.setMargin(bttnDelete, new Insets(flowMargin, flowMargin, flowMargin, flowMargin));

		this.setStyle("-fx-background-color: #313030");
		this.setAlignment(Pos.CENTER);

		//txtNumber.setStyle("-fx-background-color: #313030");
		//bttnDelete.setStyle("-fx-background-color: #a09393");
		//bttnAdd.setStyle("-fx-background-color: #a09393");
	}

	// Prepara las interacciones de los inputs en espacial el text field. Limita el length y el input a solo numeros.
	private void prepareInteractions() {
		// Limita el numero de caracteres del text field
		txtNumber.lengthProperty().addListener(new ChangeListener<Number>() {
			// Cuando se cambia el texto en el input
			@Override
			public void changed(ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {
				// Revisar si el numero crecio.
				if (newValue.intValue() > oldValue.intValue()) {
					// Si la longitud es mayor a la longitud acceptada maxima
					if (txtNumber.getText().length() >= MAX_ACCEPTED_NUM_LENGTH) {
						// Substring sin el nuevo caracter, es decir como estaba antes
						txtNumber.setText(txtNumber.getText().substring(0, MAX_ACCEPTED_NUM_LENGTH));
					}
				}
			}
		});

		// Limita el text field a aceptar solo numeros
		txtNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			// Cuando cambia el texto del input
			public void changed(ObservableValue<? extends String> observable, String oldValue,
								String newValue) {
				// Si el nuevo input es algo mas que numeros
				if (!newValue.matches("\\d*")) {
					// Borra lo que no es numero
					txtNumber.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		}); //Limitar a solo numeros

	}

	// endregion

}
