/*
Branch es una linea que calcula las dos esquinas que tiene que unir
 */
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Branch extends Line {

	// region Class Properties

	final private double LINE_WIDTH = 2;

	// endregion

	// region Constructor

	// FIXME: A lo mejor aqui van a ser LeafNode y de ahi se hace leafNode.getCircle() no?
	public Branch(Circle circleTop, Circle circleBottom){

		// Mueve la linea a la back de los otros nodos
		// FIXME: Los nodos estan en un grupo? por la documentacion
		//  Moves this Node to the back of its sibling nodes in terms of z-order.
		//  This is accomplished by moving this Node to the first position in its parent's content ObservableList.
		//  This function has no effect if this Node is not part of a group.
		this.toBack();

		// Set el ancho de la linea
		setStrokeWidth(LINE_WIDTH);

		// Posicionar la linea
		preparePosition(circleTop, circleBottom);

	}

	// endregion

	// region Prepare Components

	private void preparePosition(Circle circleTop, Circle circleBottom) {
		setStartX(circleTop.getCenterX());
		setStartY(circleTop.getCenterY());
		setEndX(circleBottom.getCenterX());
		setStartY(circleBottom.getCenterY());
	}

	// endregion
}
