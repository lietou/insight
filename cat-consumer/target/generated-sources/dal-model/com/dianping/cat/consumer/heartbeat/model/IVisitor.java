package com.dianping.cat.consumer.heartbeat.model;

import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public interface IVisitor {

   public void visitDisk(Disk disk);

   public void visitHeartbeatReport(HeartbeatReport heartbeatReport);

   public void visitMachine(Machine machine);

   public void visitPeriod(Period period);
}
