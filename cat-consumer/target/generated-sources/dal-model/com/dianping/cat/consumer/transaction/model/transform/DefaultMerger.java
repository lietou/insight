package com.dianping.cat.consumer.transaction.model.transform;

import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_DURATION;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_MACHINE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_NAME;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_RANGE;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_TYPE;
import java.util.Stack;

import com.dianping.cat.consumer.transaction.model.IVisitor;
import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private TransactionReport m_transactionReport;

   public DefaultMerger(TransactionReport transactionReport) {
      m_transactionReport = transactionReport;
   }

   public TransactionReport getTransactionReport() {
      return m_transactionReport;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeAllDuration(AllDuration old, AllDuration allDuration) {
      old.mergeAttributes(allDuration);
   }

   protected void mergeDuration(Duration old, Duration duration) {
      old.mergeAttributes(duration);
   }

   protected void mergeMachine(Machine old, Machine machine) {
      old.mergeAttributes(machine);
   }

   protected void mergeName(TransactionName old, TransactionName name) {
      old.mergeAttributes(name);
   }

   protected void mergeRange(Range old, Range range) {
      old.mergeAttributes(range);
   }

   protected void mergeRange2(Range2 old, Range2 range2) {
      old.mergeAttributes(range2);
   }

   protected void mergeTransactionReport(TransactionReport old, TransactionReport transactionReport) {
      old.mergeAttributes(transactionReport);
   }

   protected void mergeType(TransactionType old, TransactionType type) {
      old.mergeAttributes(type);
   }

   @Override
   public void visitAllDuration(AllDuration allDuration) {
   }

   protected void visitAllDurationChildren(AllDuration old, AllDuration allDuration) {
   }

   @Override
   public void visitDuration(Duration duration) {
      Object parent = m_objs.peek();
      Duration old = null;

      if (parent instanceof TransactionName) {
         TransactionName name = (TransactionName) parent;

         old = name.findDuration(duration.getValue());

         if (old == null) {
            old = new Duration(duration.getValue());
            name.addDuration(old);
         }

         mergeDuration(old, duration);
      }

      visitDurationChildren(old, duration);
   }

   protected void visitDurationChildren(Duration old, Duration duration) {
   }

   @Override
   public void visitMachine(Machine machine) {
      Object parent = m_objs.peek();
      Machine old = null;

      if (parent instanceof TransactionReport) {
         TransactionReport transactionReport = (TransactionReport) parent;

         old = transactionReport.findMachine(machine.getIp());

         if (old == null) {
            old = new Machine(machine.getIp());
            transactionReport.addMachine(old);
         }

         mergeMachine(old, machine);
      }

      visitMachineChildren(old, machine);
   }

   protected void visitMachineChildren(Machine old, Machine machine) {
      if (old != null) {
         m_objs.push(old);

         for (TransactionType type : machine.getTypes().values()) {
            m_tags.push(ENTITY_TYPE);
            visitType(type);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitName(TransactionName name) {
      Object parent = m_objs.peek();
      TransactionName old = null;

      if (parent instanceof TransactionType) {
         TransactionType type = (TransactionType) parent;

         old = type.findName(name.getId());

         if (old == null) {
            old = new TransactionName(name.getId());
            type.addName(old);
         }

         mergeName(old, name);
      }

      visitNameChildren(old, name);
   }

   protected void visitNameChildren(TransactionName old, TransactionName name) {
      if (old != null) {
         m_objs.push(old);

         for (Range range : name.getRanges()) {
            m_tags.push(ENTITY_RANGE);
            visitRange(range);
            m_tags.pop();
         }

         for (Duration duration : name.getDurations()) {
            m_tags.push(ENTITY_DURATION);
            visitDuration(duration);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitRange(Range range) {
      Object parent = m_objs.peek();
      Range old = null;

      if (parent instanceof TransactionName) {
         TransactionName name = (TransactionName) parent;

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
   public void visitRange2(Range2 range2) {
   }

   protected void visitRange2Children(Range2 old, Range2 range2) {
   }

   @Override
   public void visitTransactionReport(TransactionReport transactionReport) {
      m_transactionReport.mergeAttributes(transactionReport);
      visitTransactionReportChildren(m_transactionReport, transactionReport);
   }

   protected void visitTransactionReportChildren(TransactionReport old, TransactionReport transactionReport) {
      if (old != null) {
         m_objs.push(old);

         for (Machine machine : transactionReport.getMachines().values()) {
            m_tags.push(ENTITY_MACHINE);
            visitMachine(machine);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitType(TransactionType type) {
      Object parent = m_objs.peek();
      TransactionType old = null;

      if (parent instanceof Machine) {
         Machine machine = (Machine) parent;

         old = machine.findType(type.getId());

         if (old == null) {
            old = new TransactionType(type.getId());
            machine.addType(old);
         }

         mergeType(old, type);
      }

      visitTypeChildren(old, type);
   }

   protected void visitTypeChildren(TransactionType old, TransactionType type) {
      if (old != null) {
         m_objs.push(old);

         for (TransactionName name : type.getNames().values()) {
            m_tags.push(ENTITY_NAME);
            visitName(name);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }
}
