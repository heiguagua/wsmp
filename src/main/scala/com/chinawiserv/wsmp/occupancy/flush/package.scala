package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.occupancy.flush.disk.FlushDisk

/**
  * Created by Administrator on 2017/1/9.
  */
package object flush {

  private[flush] var NEED_FLUSH_DISK = false;

  FlushDisk.start;

}
