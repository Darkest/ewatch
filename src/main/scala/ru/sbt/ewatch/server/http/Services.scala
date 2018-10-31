package ru.sbt.ewatch.server.http

import ru.sbt.ewatch.server.http.endpoints.Endpoints
import slick.jdbc.JdbcBackend.DatabaseDef
import io.circe.generic.auto._
import io.finch.circe._

object Services {
  def userDevicesService(implicit db: DatabaseDef) = Endpoints.userDevicesEndpoint(db).toService
}
