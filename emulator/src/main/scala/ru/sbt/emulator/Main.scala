package ru.sbt.emulator

import java.sql.Timestamp

import org.apache.camel.impl.DefaultCamelContext

object Main extends App {

  def plainPower(n: Long) = {
    val timestamp = new Timestamp(System.currentTimeMillis)
    val data = Status(timestamp, 60.0, 235.0, 0.5)
    data
  }

  val camelContext = new DefaultCamelContext()
  camelContext.addRoutes(CamelPublisher.publisher(plainPower, "Bulb60"))
  camelContext.start()

  println("Camel started")

  scala.io.StdIn.readLine()
}
