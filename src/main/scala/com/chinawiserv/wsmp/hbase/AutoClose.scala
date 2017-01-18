package com.chinawiserv.wsmp.hbase

import org.slf4j.{Logger, LoggerFactory}
import java.io.Closeable

/**
 * 自动关闭流
 */
object AutoClose {

  val logger : Logger = LoggerFactory.getLogger(this.getClass)

  /**
   * 传入一个可关闭的流
 * @param stream
 * @param f，匿名函数
 */
def using[A <: Closeable]( stream : A, f : (A) => Unit ): Unit = {
    try f(stream) 
    catch {case  e : Throwable => logger.error("Auto Close Error {}", e.getMessage)}
    finally stream.close();
  }
}
