package com.chinawiserv.wsmp.hbase

import org.slf4j.{Logger, LoggerFactory}

/**
 * 自动关闭流
 */
object AutoClose {

  val logger : Logger = LoggerFactory.getLogger(this.getClass)

  def using[A <: AutoCloseable]( stream : A, f : (A) => Unit ): Unit = {
    try{
        f(stream);
    }catch{
      case  e : Throwable => println("=====================" + e.getMessage)
    }
    finally {
      stream.close();
    }
  }
}
