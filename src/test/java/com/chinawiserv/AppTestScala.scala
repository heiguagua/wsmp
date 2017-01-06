package com.chinawiserv

import scala.collection.mutable.ListBuffer

object AppTestScala {

  def main(args: Array[String]): Unit = {
    val temp = new ListBuffer[String];
    temp += "1";
    temp += "2";
    temp += "3";
    temp += "4";
    temp += "5";
    val list = temp.sliding(2, 2).toList;
    list.foreach(x => println(x))

  }

}
