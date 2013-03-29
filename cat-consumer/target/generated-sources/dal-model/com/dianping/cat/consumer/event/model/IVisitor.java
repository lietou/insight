package com.dianping.cat.consumer.event.model;

import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.EventType;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.Range;

public interface IVisitor {

   public void visitEventReport(EventReport eventReport);

   public void visitMachine(Machine machine);

   public void visitName(EventName name);

   public void visitRange(Range range);

   public void visitType(EventType type);
}
