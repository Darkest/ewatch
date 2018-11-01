package ru.sbt.ewatch


import com.twitter.finagle.Http
import org.apache.camel.component.websocket.WebsocketComponent
import org.apache.camel.impl.DefaultCamelContext
import ru.sbt.ewatch.db.entities.Entities.{Device, User, devices, _}
import ru.sbt.ewatch.server.http.Services
import ru.sbt.ewatch.server.mqtt.CamelRoutes
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.DatabaseDef

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {
  val camelContext = new DefaultCamelContext()
  camelContext.addRoutes(CamelRoutes.powerRoute("testTopic"))
  camelContext.getComponent("websocket", classOf[WebsocketComponent]).setMaxThreads(50)
  camelContext.start()

  implicit val db:DatabaseDef = Database.forConfig("h2mem").asInstanceOf[DatabaseDef]

  val dbInit = DBIO.seq(
    (devices.schema ++ users.schema).create,
    users += User(1,"Ivan"),
    users += User(2, "Dmitry"),
    devices += Device("Bulb1", 1, "Kitchen"),
    devices += Device("Sin1", 1, "Room")
  )

  Await.result(db.run(dbInit), Duration.Inf)

  val server = Http.server.serve(":9111", Services.userDevicesService(db))

  scala.io.StdIn.readLine()
}
