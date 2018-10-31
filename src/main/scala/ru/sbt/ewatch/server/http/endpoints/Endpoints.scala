package ru.sbt.ewatch.server.http.endpoints


import io.finch.syntax.get
import io.finch.{Endpoint, param, Ok}
import ru.sbt.ewatch.db.access.DB
import ru.sbt.ewatch.db.entities.Entities.Device
import slick.jdbc.JdbcBackend.DatabaseDef

object Endpoints {
  def userDevicesEndpoint(implicit db: DatabaseDef): Endpoint[Seq[Device]] = get("devices" :: param("userId") ) { userId: String =>
    val userIdLong = userId.toLong
    DB.getDevicesForUser(userIdLong).map(Ok)
  }


}
