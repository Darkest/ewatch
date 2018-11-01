package ru.sbt.emulator

import java.sql.Timestamp

case class Status(time: Timestamp,
                  power: Double,
                  voltage: Double,
                  current: Double)
