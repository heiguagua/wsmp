package com.chinawiserv.wsmp.hbase

/**
 * 自动关闭流
 */
object AutoClose {

  def using[A <: AutoCloseable]( stream : A, f : (A) => Unit ): Unit = {
    try{
        f(stream);
    }finally {
      stream.close();
    }
  }
}
