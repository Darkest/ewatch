package ru.sbt.emulator

import java.sql.Timestamp

case class Status(time: Timestamp,
                  deviceName: String,
                  power: Double,
                  voltage: Double,
                  current: Double)
