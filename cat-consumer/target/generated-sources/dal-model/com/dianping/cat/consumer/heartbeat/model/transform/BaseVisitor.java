package com.dianping.cat.consumer.heartbeat.model.transform;

import com.dianping.cat.consumer.heartbeat.model.IVisitor;
import com.dianping.cat.consumer.heartbeat.model.entity.Disk;
import com.dianping.cat.consumer.heartbeat.model.entity.HeartbeatReport;
import com.dianping.cat.consumer.heartbeat.model.entity.Machine;
import com.dianping.cat.consumer.heartbeat.model.entity.Period;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDisk(Disk disk) {
   }

   @Override
   public void visitHeartbeatReport(HeartbeatReport heartbeatReport) {
      for (Machine machine : heartbeatReport.getMachines().values()) {
         visitMachine(machine);
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      for (Period period : machine.getPeriods()) {
         visitPeriod(period);
      }
   }

   @Override
   public void visitPeriod(Period period) {
      for (Disk disk : period.getDisks()) {
         visitDisk(disk);
      }
   }
}
