package Gui

import Gui.BoardElementGui
import scalafx.scene.control.{Control, Label}

class LabelElement(label: Label) extends BoardElementGui {
  override def setText(text: String): Unit = label.setText(text)
  override def prefWidth(width: Int): Unit = label.prefWidth = width
  override def prefHeight(height: Int): Unit = label.prefHeight = height
  override def getNode: Control = label
}
