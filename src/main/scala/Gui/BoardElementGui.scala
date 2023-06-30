package Gui

import scalafx.scene.control.Control

trait BoardElementGui {
  def setText(text: String): Unit
  def prefWidth(width: Int): Unit
  def prefHeight(height: Int): Unit
  def getNode:Control
}
