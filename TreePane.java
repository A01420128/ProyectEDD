/*
TreePane es un Pane donde van a estar los LeafPane que son las hojas del arbol
 */
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class TreePane extends Pane {

	// region Class properties

	// Propiedades finales de estilo.
	final private String PANE_BACKGROUND_STYLE = "-fx-background-color:red";
	final private double HEIGHT_OFFSET = 55;

	// Propiedades de la clase.
	private Pane parentPane; // El pane donde esta este treePane;
//	private ArrayList<LeafPane> leafs = new ArrayList<>();

	private LeafPane root;


	// endregion

	// region Constructor

	public TreePane(BorderPane parentPane){

		// Establce cual es el pane donde va a estar.
		this.parentPane = parentPane;

		// Preparar el estilo
		prepareStyle();

		// Anadir al pane donde debe de estar
		parentPane.getChildren().add(this);

		// TODO: Remove?
		//parentPane.setTop(this);
		//parentPane.setTop(this);
	}

	// endregion

	// region Prepare methods

	private void prepareStyle() {

		// TODO: Aqui hay outputs de consola.
		System.out.println(parentPane.getWidth());
		System.out.println(parentPane.getHeight());

		// Cambiar color de background
		this.setStyle(PANE_BACKGROUND_STYLE);

		// Establecer el tamano de este treePane
		setHeight(parentPane.getHeight() - HEIGHT_OFFSET); // El offset es para //TODO: ?Porque el tree pane es mas chico que su papa?, por el controlPane?
		setWidth(parentPane.getWidth());
	}

	// endregion

	// region Tree methods

	// Anade un nuevo LeafPane en el TreePane
	public void addLeaf(int number){
//		leafs.add(leafPane);
		this.root = addLeafRec(number, root, 0.5, 1);
		// aqui
		System.out.println("///");
		magicPrint();
	}

	private LeafPane addLeafRec(int number, LeafPane root, double xOffset, int yOffset) {
		if (root == null) {
			// TODO: Cade vez que se agregue una nueva hoja al arbol. se debe mover todos los nodos en el arbol hacia arriba de acuerdo a su profundidad
			// Llamar a moveYup aqui. para todos los nodos en el arbol.

			root = new LeafPane(this, number, xOffset, yOffset);

		} else {
			if (number > root.getNumber()) {
				root.setRight(addLeafRec(number, root.getRight(), xOffset + xOffset / 2, yOffset + 1));
//                this.magicPrint(element, xOffset, yOffset);
				if (calcDepthOf(root.getLeft()) - calcDepthOf(root.getRight()) == -2) {
					if (number > root.getRight().getNumber()) {
						root = simpleLeftRotation(root);
					} else {
						root = doubleLeftRotation(root);
					}
				}
			}
			if (number < root.getNumber()) {
				root.setLeft(addLeafRec(number, root.getLeft(), xOffset - xOffset / 2, yOffset + 1));
//                this.magicPrint(element, xOffset, yOffset);
				if (calcDepthOf(root.getLeft()) - calcDepthOf(root.getRight()) == 2) {
					if (number < root.getLeft().getNumber()) {
						root = simpleRightRotation(root);
					} else {
						root = doubleRightRotation(root);
					}
				}
			}
		}

		int depth = max(calcDepthOf(root.getLeft()), calcDepthOf(root.getRight())) + 1;
		root.setDepth((depth));

		return root;
	}

	private LeafPane simpleLeftRotation(LeafPane root) {
		LeafPane temp = root.getRight();

		root.setRight(temp.getLeft());
		temp.setLeft(root);

		root.setDepth(max(calcDepthOf(root.getLeft()), calcDepthOf(root.getRight())) + 1);
		temp.setDepth(max(calcDepthOf(root), calcDepthOf(temp.getRight())) + 1);

		return temp;
	}

	private LeafPane simpleRightRotation(LeafPane root) {
		LeafPane temp = root.getLeft();

		root.setLeft(root.getRight());
		temp.setRight(root);

		root.setDepth(max(calcDepthOf(root.getLeft()), calcDepthOf(root.getRight())) + 1);
		temp.setDepth(max(calcDepthOf(temp.getLeft()), calcDepthOf(root)) + 1);

		return temp;
	}

	private LeafPane doubleLeftRotation(LeafPane root) {
		root.setRight(simpleRightRotation(root.getRight()));
		return simpleLeftRotation(root);
	}

	private LeafPane doubleRightRotation(LeafPane root) {
		root.setLeft(simpleLeftRotation(root.getLeft()));
		return simpleRightRotation(root);
	}

	public String toStringPre() {
		return toStringPreRec(this.root);
	}

	private String toStringPreRec(LeafPane node) {
		String result = "";
		if (node != null) {
//			result = node.getElement().toString() + ", " ;
			// TODO: Aqui se llamma moveYUp para mover a todos los nodos.
			toStringPreRec(node.getLeft());
			toStringPreRec(node.getRight());
		}
		return result;
	}

    public String posPath() {
	    return toStringPreRec(this.root);
    }

    private String posPathRec(LeafPane leaf) {
        if (leaf != null) {
            toStringPreRec(node.getLeft()) +
                    toStringPreRec(node.getRight()) +
                    ", " + node.getElement().toString();
        }
        return result;
    }

	// Retira un leafPane
//	public LeafPane removeLeaft(int index){
//		return leafs.get(index);
//	}

	// region Tree helpers

	private int max(int a, int b) {
		int result = a > b ? a : b;
		return result;
	}

	private int calcDepthOf(LeafPane root) {
		if (root == null) return -1;
		return root.getDepth();
	}

	/*





	 */

	public void magicPrint() {
		if (this.root != null) {
			this.root.getDepth();
			int rootHeight = this.root.getDepth();
			int xSizeMax = (int) Math.pow(2, rootHeight);
			String[][] nodeArray = new String[rootHeight + 1][xSizeMax];
			sortNodesRec(nodeArray, this.root, 0, 0);
			printArray(nodeArray);
		}

	}

	private void sortNodesRec(String[][] array, LeafPane temp, int xOffset, int yOffset) {
		if (temp != null) {
			array[yOffset][xOffset] = "" + temp.getNumber();
			sortNodesRec(array, temp.getLeft(), xOffset * 2, yOffset + 1);
			sortNodesRec(array, temp.getRight(), xOffset * 2 + 1, yOffset + 1);
		}
	}

	private void printArray(String[][] array) {
		for (int i = 0; i < array.length; i++) {
			String result = "";
			result += initialSpaces(i, array.length);
			int xMaxNode = (int) Math.pow(2, i);
			for (int j = 0; j < xMaxNode; j++) {
				if (array[i][j] != null) {
					// Imprime si hay rama izquierda
					if (i + 1 < array.length) {
						boolean hasLeft = array[i + 1][j * 2] != null;
						result += branches(i, array.length, hasLeft);
					}
					// Imprime nodo
					result += array[i][j];
					// Imprime si hay rama derecha
					if (i + 1 < array.length) {
						boolean hasRight = array[i + 1][j * 2 +  1] != null;
						result += branches(i, array.length, hasRight);
					}
				} else {
					// Imprime espacio si no hay nodo
					result += branches(i, array.length, false) + "  " + branches(i, array.length, false);
				}
				// Imprime espacio libre.
				result += spacing(i, array.length);
			}
			System.out.println(result);
		}
	}

	private String initialSpaces(int yOffset, int yLength) {
		int spaceNum = (int) Math.pow(2, yLength - yOffset - 1) - 1;
		return chainOfChar(spaceNum, ' ');
	}

	private String branches(int yOffset, int yLength, boolean hasBranch) {
		int spaceNum = (int) Math.pow(2, yLength - yOffset - 1) - 1;
		if (hasBranch) {
			return chainOfChar(spaceNum, '_');
		} else {
			return  chainOfChar(spaceNum, ' ');
		}
	}

	private String spacing(int yOffset, int yLength) {
		int spaceNum = (int) Math.pow(2, yLength - yOffset);
		return chainOfChar(spaceNum, ' ');
	}

	private String chainOfChar(int length, char usingChar) {
		String result = "";
		for (int i = 0; i < length; i++) {
			result += usingChar;
		}
		return result;
	}

}
