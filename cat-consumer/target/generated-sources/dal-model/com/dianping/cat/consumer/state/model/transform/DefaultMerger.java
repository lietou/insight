package com.dianping.cat.consumer.state.model.transform;

import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_MESSAGE;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAINS;
import java.util.Stack;

import com.dianping.cat.consumer.state.model.IVisitor;
import com.dianping.cat.consumer.state.model.entity.Machine;
import com.dianping.cat.consumer.state.model.entity.Message;
import com.dianping.cat.consumer.state.model.entity.ProcessDomain;
import com.dianping.cat.consumer.state.model.entity.StateReport;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private StateReport m_stateReport;

   public DefaultMerger(StateReport stateReport) {
      m_stateReport = stateReport;
   }

   public StateReport getStateReport() {
      return m_stateReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeMachine(Machine old, Machine machine) {
      old.mergeAttributes(machine);
   }

   protected void mergeMessage(Message old, Message message) {
      old.mergeAttributes(message);
   }

   protected void mergeProcessDomain(ProcessDomain old, ProcessDomain processDomain) {
      old.mergeAttributes(processDomain);
   }

   protected void mergeStateReport(StateReport old, StateReport stateReport) {
      old.mergeAttributes(stateReport);
   }

   @Override
   public void visitMachine(Machine machine) {
      Object parent = m_objs.peek();
      Machine old = null;

      if (parent instanceof StateReport) {
         StateReport stateReport = (StateReport) parent;

         old = stateReport.findMachine(machine.getIp());

         if (old == null) {
            old = new Machine(machine.getIp());
            stateReport.addMachine(old);
         }

         mergeMachine(old, machine);
      }

      visitMachineChildren(old, machine);
   }

   protected void visitMachineChildren(Machine old, Machine machine) {
      if (old != null) {
         m_objs.push(old);

         for (ProcessDomain processDomain : machine.getProcessDomains().values()) {
            m_tags.push(ENTITY_PROCESSDOMAINS);
            visitProcessDomain(processDomain);
            m_tags.pop();
         }

         for (Message message : machine.getMessages().values()) {
            m_tags.push(ENTITY_MESSAGE);
            visitMessage(message);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMessage(Message message) {
      Object parent = m_objs.peek();
      Message old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         old = machine.findMessage(message.getId());

         if (old == null) {
            old = new Message(message.getId());
            machine.addMessage(old);
         }

         mergeMessage(old, message);
      }

      visitMessageChildren(old, message);
   }

   protected void visitMessageChildren(Message old, Message message) {
   }

   @Override
   public void visitProcessDomain(ProcessDomain processDomain) {
      Object parent = m_objs.peek();
      ProcessDomain old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         old = machine.findProcessDomain(processDomain.getName());

         if (old == null) {
            old = new ProcessDomain(processDomain.getName());
            machine.addProcessDomain(old);
         }

         mergeProcessDomain(old, processDomain);
      }

      visitProcessDomainChildren(old, processDomain);
   }

   protected void visitProcessDomainChildren(ProcessDomain old, ProcessDomain processDomain) {
   }

   @Override
   public void visitStateReport(StateReport stateReport) {
      m_stateReport.mergeAttributes(stateReport);
      visitStateReportChildren(m_stateReport, stateReport);
   }

   protected void visitStateReportChildren(StateReport old, StateReport stateReport) {
      if (old != null) {
         m_objs.push(old);

         for (Machine machine : stateReport.getMachines().values()) {
            m_tags.push(ENTITY_MACHINE);
            visitMachine(machine);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
