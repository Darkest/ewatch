package ru.sbt.ewatch.server.mqtt

import org.apache.camel.builder.RouteBuilder
import org.apache.camel.impl.DefaultMessage
import org.apache.camel.{Exchange, Processor}
import org.slf4j.LoggerFactory
import ru.sbt.ewatch.db.access.DB
import ru.sbt.ewatch.server.mqtt.Entities.Power
import ru.sbt.ewatch.util.JacksonUtils

object CamelRoutes {

  private val log = LoggerFactory.getLogger(CamelRoutes.getClass.getName)

  def powerRoute(topics: String, host: String = "138.68.155.35:1883"): RouteBuilder = {
    new RouteBuilder() {

      var addNewPowerValue = new Processor {
        override def process(exchange: Exchange): Unit = {
          val bytes = exchange.getIn.getBody(classOf[Array[Byte]])
          val power = JacksonUtils.readBytes(bytes, classOf[Power])
          val msg = new DefaultMessage(exchange.getContext)
          log.info(s"Message: ${power.toString}")
          msg.setBody(JacksonUtils.writeString(power))
          exchange.setOut(msg)
        }
      }

      override def configure(): Unit =
        from(s"mqtt://power?host=tcp://138.68.155.35:1883&subscribeTopicNames=$topics").process(addNewPowerValue).to("websocket://0.0.0.0:9112/power?sendToAll=true").autoStartup(true)
    }
  }

}
