package ru.sbt.ewatch.db.entities

import java.sql.Timestamp

import slick.jdbc.H2Profile.api._
import slick.lifted
import slick.lifted.{Rep => _, Tag => _, _}

object Entities {

  class Devices(tag: Tag)
    extends Table[Device](tag, "DEVICES") {

    def id: Rep[String] = column[String]("ID", O.PrimaryKey)

    def userId: Rep[Long] = column[Long]("USERID")

    def name: Rep[String] = column[String]("NAME")

    def * : ProvenShape[Device] = (id, userId, name).mapTo[Device]

    def user: ForeignKeyQuery[Users, User] =
      foreignKey("ID", userId, lifted.TableQuery[Users])(_.id)
  }

  val devices = lifted.TableQuery[Devices]

  class Users(tag: Tag)
    extends Table[User](tag, "USERS") {

    def id: Rep[Long] = column[Long]("ID", O.PrimaryKey)

    def name: Rep[String] = column[String]("NAME")

    def * : ProvenShape[User] = (id, name).mapTo[User]
  }

  val users = lifted.TableQuery[Users]

  case class User(id: Long, name: String)

  case class Device(id: String, userId: Long, name: String)

  case class Metric(datetime: Timestamp,
                    deviceId: String,
                    power: Double,
                    current: Double,
                    voltage: Double,
                    consumption: Double)

  class Metrics(tag: Tag) extends Table[Metric](tag, "METRICS") {
    def datetime = column[Timestamp]("DATE")
    def deviceId = column[String]("DEVICE_ID")
    def power = column[Double]("POWER")
    def current = column[Double]("CURRENT")
    def voltage = column[Double]("VOLTAGE")
    def consumption = column[Double]("CONSUMPTION")

    def * = (datetime, deviceId, power, current, voltage, consumption).mapTo[Metric]
  }

  val metrics = lifted.TableQuery[Metrics]

  val allTableNames = List(devices, users, metrics)

}