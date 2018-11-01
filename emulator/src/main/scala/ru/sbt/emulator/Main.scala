package ru.sbt.emulator

import java.sql.Timestamp

import org.apache.camel.impl.DefaultCamelContext

object Main extends App {

  def WsToKWh(ws: Double): Double = {
    ws/1000/3600
  }

  def bulb(n: Long) = {
    val timestamp = new Timestamp(System.currentTimeMillis)
    val P = 60
    val V = randomVoltageBetween220_240
    val A = P/V
    val data = Status(timestamp, "", P, V, A, WsToKWh(V*A))
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
    val data = Status(timestamp, "", P, V, A, WsToKWh(V*A))
    data
  }

  val camelContext = new DefaultCamelContext()
  camelContext.addRoutes(CamelPublisher.publisher(bulb, "Bulb1"))
  camelContext.addRoutes(CamelPublisher.publisher(bulb, "Bulb2"))
  camelContext.addRoutes(CamelPublisher.publisher(bulb, "Bulb3"))
  camelContext.addRoutes(CamelPublisher.publisher(sinPower, "Sin1"))
  camelContext.addRoutes(CamelPublisher.publisher(sinPower, "Sin2"))
  camelContext.addRoutes(CamelPublisher.publisher(sinPower, "Sin3"))
  camelContext.start()

  println("Camel started")

  scala.io.StdIn.readLine()
}
