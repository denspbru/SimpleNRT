package org.denspbru.generators

object IdIncrementor {
  private var counter: Long = 0
  val label: String = "CounterLabel"

  def get(): Long = {
    counter += 1
    return counter
  }

}
