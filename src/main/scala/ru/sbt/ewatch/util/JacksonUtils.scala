package ru.sbt.ewatch.util

import java.io.ByteArrayOutputStream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object JacksonUtils {

  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  def readBytes(bytes: Array[Byte], clazz: Class[_]): Any = {
    mapper.readValue(bytes,clazz)
  }

  def readString(s: String, clazz: Class[_]): Any = {
    readBytes(s.getBytes, clazz)
  }

  def writeBytes(obj: Any): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    mapper.writeValue(baos, obj)
    baos.toByteArray
  }

  def writeString(obj: Any): String = {
    new String(writeBytes(obj))
  }
}
