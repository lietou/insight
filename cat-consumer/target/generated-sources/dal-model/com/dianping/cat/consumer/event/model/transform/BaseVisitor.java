package com.dianping.cat.consumer.event.model.transform;

import com.dianping.cat.consumer.event.model.IVisitor;
import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitEventReport(EventReport eventReport) {
      for (Machine machine : eventReport.getMachines().values()) {
         visitMachine(machine);
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      for (EventType type : machine.getTypes().values()) {
         visitType(type);
      }
   }

   @Override
   public void visitName(EventName name) {
      for (Range range : name.getRanges()) {
         visitRange(range);
      }
   }

   @Override
   public void visitRange(Range range) {
   }

   @Override
   public void visitType(EventType type) {
      for (EventName name : type.getNames().values()) {
         visitName(name);
      }
   }
}
