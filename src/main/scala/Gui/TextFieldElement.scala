package Gui

import Gui.BoardElementGui
import scalafx.scene.control.{Control, TextField}

class TextFieldElement(textField: TextField) extends BoardElementGui {
  override def setText(text: String): Unit =  textField.setText(text)
  override def prefWidth(width: Int): Unit = textField.prefWidth = width

  override def prefHeight(height: Int): Unit = textField.prefHeight = height

  override def getNode: Control = textField
}
