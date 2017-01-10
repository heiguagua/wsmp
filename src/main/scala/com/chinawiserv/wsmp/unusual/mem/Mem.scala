package com.chinawiserv.wsmp.unusual.mem

import scala.collection.mutable.ArrayBuffer

case class Mem(
                //uint32	m_uiFlag	开始标志位,固定为:0xEEEEEEEE
                //uiFlag: Long,

                //uint32	m_uiLength	数据帧的长度（含帧头长度), 4字节浮整形
                //uiLength: Long,

                //uint32	m_uiLength	本类型数据长度,包括m_uiLength的字节长度
                //dataUiLength: Long,

                //char[20]	m_cDataType	表示数据类型, 20字节字符型；
                //cdataType: String,

                //char[40]	Id	传感器编号（Int类型字符串）
                id: Int,

                //int64	scanOverTime	本地的格林威治时间的秒数
                scanOverTime: Long,

                //int64	StartFreq	扫描的开始频率
                startFreq: Long,

                //int64	StopFreq	扫描的结束频率
                stopFreq: Long,

                //int64	StepFreq	扫描步进
                stepFreq: Long,

                //double	fLon	传感器经度
                flon: Long,

                //double	fLat	传感器纬度
                flat: Long,

                //char[9]	compressInfo	忽略
                //compressInfo: String,

                //uint32	numOfTraceItems	数据个数
                numOfTraceItems: Long,

                //int16	selectorFlags	数据类型
                //selectorFlags: Short,

                //uInt16	attCount	值固定为0
                //attCount: Int,

                //uInt32	dataLen	数据长度
                //dataLen: Long,

                //sbyte[numOfTraceItems]	level	能量数据，8位有符号整数，取值[-128, 127]
                levels: ArrayBuffer[Byte]

                //byte[numOfTraceItems]	Stddev	统计值，8位无符号整数,取值[0-255]
                //stddevs: ArrayBuffer[Short]
              );

