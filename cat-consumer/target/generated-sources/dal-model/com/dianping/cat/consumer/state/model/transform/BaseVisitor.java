package com.dianping.cat.consumer.state.model.transform;

import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitMachine(Machine machine) {
      for (ProcessDomain processDomain : machine.getProcessDomains().values()) {
         visitProcessDomain(processDomain);
      }

      for (Message message : machine.getMessages().values()) {
         visitMessage(message);
      }
   }

   @Override
   public void visitMessage(Message message) {
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      for (Machine machine : stateReport.getMachines().values()) {
         visitMachine(machine);
      }
   }
}
