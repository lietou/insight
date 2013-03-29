package com.dianping.cat.consumer.state.model;

import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public interface IVisitor {

   public void visitMachine(Machine machine);

   public void visitMessage(Message message);

   public void visitProcessDomain(ProcessDomain processDomain);

   public void visitStateReport(StateReport stateReport);
}
