package ru.sbt.emulator

import java.sql.Timestamp

import org.apache.camel.impl.DefaultCamelContext

object Main extends App {

  def plainPower(n: Long) = {
    val timestamp = new Timestamp(System.currentTimeMillis)
    val data = Status(timestamp, "", 60.0, 235.0, 0.5)
    data
  }

  def randomVoltageBetween220_240: Double = {
    220 + math.random()*20
  }

  def sinPower(n: Long) = {
    val timestamp = new Timestamp(System.currentTimeMillis)

    val P = math.sin(n.toDouble/180/math.Pi)
    val V = randomVoltageBetween220_240
    val A = P/V
    val data = Status(timestamp, "", P, V, A)
    data
  }

  val camelContext = new DefaultCamelContext()
  camelContext.addRoutes(CamelPublisher.publisher(plainPower, "Bulb60"))
  camelContext.addRoutes(CamelPublisher.publisher(sinPower, "Sin"))
  camelContext.start()

  println("Camel started")

  scala.io.StdIn.readLine()
}
