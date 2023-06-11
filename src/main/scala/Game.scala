import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane

object Game {
  val scene = new Scene(400, 300) {
    val label = new Label("hej")
    val rootPane = new StackPane()
    rootPane.children.add(label)

    root = rootPane
  }
}
