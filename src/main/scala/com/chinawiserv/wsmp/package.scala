package com.chinawiserv

import com.chinawiserv.wsmp.configuration.SpringContextManager._
import com.chinawiserv.wsmp.mongodb.MongoDB

/**
  * Created by zengpzh on 2017/1/13.
  */
package object wsmp {

  val mongoDB  = getBean(classOf[MongoDB]);

}
