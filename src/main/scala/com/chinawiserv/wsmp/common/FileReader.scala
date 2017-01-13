package com.chinawiserv.wsmp.common

import java.io.{BufferedReader, File, FileReader}
import scala.collection.mutable.ListBuffer

object FileReader {
  /**
    * 扩展Java的File类的方法
    * 为Java的File类提供一个获得所有行数的方法
    */
  implicit class Files(file: File) {
    def lines: List[String] = {
      val fileReader = new FileReader(file);
      val reader = new BufferedReader(fileReader);
      try {
        var lines = ListBuffer[String]();
        var line = reader.readLine();
        while (line != null) {
          lines = lines :+ line;
          line = reader.readLine();
        }
        lines.toList;
      } finally {
        if (fileReader != null) {
          fileReader.close();
        }
        if (reader != null) {
          reader.close();
        }
      }
    }
  }
}
