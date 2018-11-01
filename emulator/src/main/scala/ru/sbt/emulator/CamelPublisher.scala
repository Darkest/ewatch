package ru.sbt.emulator

import java.io.ByteArrayOutputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.model.dataformat.JsonLibrary
import org.apache.camel.{Exchange, Processor}

object CamelPublisher {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  type dataGen = Long => Status

  def publisher(gen: dataGen, deviceName: String) = {
    new RouteBuilder() {
      val procGen = new Processor {
        override def process(exchange: Exchange): Unit = {
          val n = exchange.getProperty(Exchange.TIMER_COUNTER).asInstanceOf[Long]
          val status = gen(n)
          println(s"Ready to send - $status")
          val os = new ByteArrayOutputStream()
          mapper.writeValue(os, status.copy(deviceName = deviceName))
          exchange.getIn.setBody(new String(os.toByteArray))
        }
      }

      override def configure(): Unit = {
        from(s"timer:timer_$deviceName?period=1000").process(procGen).marshal().json(JsonLibrary.Jackson).to(s"mqtt://power_$deviceName?host=tcp://138.68.155.35:1883&publishTopicName=$deviceName")
      }
    }
  }
}
