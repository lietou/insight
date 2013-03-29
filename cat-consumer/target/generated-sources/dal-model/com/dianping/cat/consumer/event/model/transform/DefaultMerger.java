package com.dianping.cat.consumer.event.model.transform;

import static com.dianping.cat.consumer.event.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_RANGE;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_TYPE;
import java.util.Stack;

import com.dianping.cat.consumer.event.model.IVisitor;
import com.dianping.cat.consumer.event.model.entity.EventReport;
import com.dianping.cat.consumer.event.model.entity.Machine;
import com.dianping.cat.consumer.event.model.entity.EventName;
import com.dianping.cat.consumer.event.model.entity.Range;
import com.dianping.cat.consumer.event.model.entity.EventType;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private EventReport m_eventReport;

   public DefaultMerger(EventReport eventReport) {
      m_eventReport = eventReport;
   }

   public EventReport getEventReport() {
      return m_eventReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeEventReport(EventReport old, EventReport eventReport) {
      old.mergeAttributes(eventReport);
   }

   protected void mergeMachine(Machine old, Machine machine) {
      old.mergeAttributes(machine);
   }

   protected void mergeName(EventName old, EventName name) {
      old.mergeAttributes(name);
   }

   protected void mergeRange(Range old, Range range) {
      old.mergeAttributes(range);
   }

   protected void mergeType(EventType old, EventType type) {
      old.mergeAttributes(type);
   }

   @Override
   public void visitEventReport(EventReport eventReport) {
      m_eventReport.mergeAttributes(eventReport);
      visitEventReportChildren(m_eventReport, eventReport);
   }

   protected void visitEventReportChildren(EventReport old, EventReport eventReport) {
      if (old != null) {
         m_objs.push(old);

         for (Machine machine : eventReport.getMachines().values()) {
            m_tags.push(ENTITY_MACHINE);
            visitMachine(machine);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitMachine(Machine machine) {
      Object parent = m_objs.peek();
      Machine old = null;

      if (parent instanceof EventReport) {
         EventReport eventReport = (EventReport) parent;

         old = eventReport.findMachine(machine.getIp());

         if (old == null) {
            old = new Machine(machine.getIp());
            eventReport.addMachine(old);
         }

         mergeMachine(old, machine);
      }

      visitMachineChildren(old, machine);
   }

   protected void visitMachineChildren(Machine old, Machine machine) {
      if (old != null) {
         m_objs.push(old);

         for (EventType type : machine.getTypes().values()) {
            m_tags.push(ENTITY_TYPE);
            visitType(type);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitName(EventName name) {
      Object parent = m_objs.peek();
      EventName old = null;

      if (parent instanceof EventType) {
         EventType type = (EventType) parent;

         old = type.findName(name.getId());

         if (old == null) {
            old = new EventName(name.getId());
            type.addName(old);
         }

         mergeName(old, name);
      }

      visitNameChildren(old, name);
   }

   protected void visitNameChildren(EventName old, EventName name) {
      if (old != null) {
         m_objs.push(old);

         for (Range range : name.getRanges()) {
            m_tags.push(ENTITY_RANGE);
            visitRange(range);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitRange(Range range) {
      Object parent = m_objs.peek();
      Range old = null;

      if (parent instanceof EventName) {
         EventName name = (EventName) parent;

         old = name.findRange(range.getValue());

         if (old == null) {
            old = new Range(range.getValue());
            name.addRange(old);
         }

         mergeRange(old, range);
      }

      visitRangeChildren(old, range);
   }

   protected void visitRangeChildren(Range old, Range range) {
   }

   @Override
   public void visitType(EventType type) {
      Object parent = m_objs.peek();
      EventType old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         old = machine.findType(type.getId());

         if (old == null) {
            old = new EventType(type.getId());
            machine.addType(old);
         }

         mergeType(old, type);
      }

      visitTypeChildren(old, type);
   }

   protected void visitTypeChildren(EventType old, EventType type) {
      if (old != null) {
         m_objs.push(old);

         for (EventName name : type.getNames().values()) {
            m_tags.push(ENTITY_NAME);
            visitName(name);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
