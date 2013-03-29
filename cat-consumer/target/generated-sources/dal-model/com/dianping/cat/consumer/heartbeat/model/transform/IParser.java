package com.dianping.cat.consumer.heartbeat.model.transform;

import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public interface IParser<T> {
   public HeartbeatReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForDisk(IMaker<T> maker, ILinker linker, Disk parent, T node);

   public void parseForMachine(IMaker<T> maker, ILinker linker, Machine parent, T node);

   public void parseForPeriod(IMaker<T> maker, ILinker linker, Period parent, T node);
}
