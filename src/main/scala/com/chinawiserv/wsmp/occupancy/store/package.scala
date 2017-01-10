package com.chinawiserv.wsmp.occupancy

import com.chinawiserv.wsmp.occupancy.store.disk.FlushDisk

/**
  * Created by Administrator on 2017/1/9.
  */
package object store {

  private[store] var NEED_FLUSH_DISK = false;

  FlushDisk.start;

}
