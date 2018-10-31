package ru.sbt.ewatch.server.mqtt

import org.apache.camel.{Exchange, Processor}
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import ru.sbt.ewatch.server.mqtt.Entities.Power

object CamelRoutes {

  def powerRoute(topics: String, host: String = "138.68.155.35:1883"): RouteBuilder = {
    new RouteBuilder() {

      var addNewPowerValue = new Processor {
        override def process(exchange: Exchange): Unit = {
          val power = exchange.getIn.getBody(classOf[Power])

        }
      }

      override def configure(): Unit =
        from(s"mqtt://power?subscribeTopicNames=$topics").unmarshal().json(JsonLibrary.Jackson).process(addNewPowerValue)
    }
  }

}
