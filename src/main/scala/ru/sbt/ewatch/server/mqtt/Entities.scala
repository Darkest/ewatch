package ru.sbt.ewatch.server.mqtt

import java.sql.Timestamp

object Entities {
  case class Power(datetime: Timestamp, deviceId: String, value: Int)

}
