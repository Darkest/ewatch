package ru.sbt.ewatch.db.access

import com.twitter.util.Future
import ru.sbt.ewatch.db.entities.Entities.{Device, Devices}
import ru.sbt.ewatch.util.FuturesConversions._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.DatabaseDef

import scala.concurrent.ExecutionContext.Implicits.global


object DB {
  def getDevicesForUser(userId: Long)(implicit db: DatabaseDef): Future[Seq[Device]] ={
    val devices: TableQuery[Devices] = TableQuery[Devices]
    val devicesQuery = devices.filter(_.userId === userId)
    db.run(devicesQuery.result).asTwitter
  }


}
