package com.dianping.cat.consumer.transaction.model.transform;

import com.dianping.cat.consumer.transaction.model.IVisitor;
import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitAllDuration(AllDuration allDuration) {
   }

   @Override
   public void visitDuration(Duration duration) {
   }

   @Override
   public void visitMachine(Machine machine) {
      for (TransactionType type : machine.getTypes().values()) {
         visitType(type);
      }
   }

   @Override
   public void visitName(TransactionName name) {
      for (Range range : name.getRanges()) {
         visitRange(range);
      }

      for (Duration duration : name.getDurations()) {
         visitDuration(duration);
      }

      for (AllDuration allDuration : name.getAllDurations().values()) {
         visitAllDuration(allDuration);
      }
   }

   @Override
   public void visitRange(Range range) {
   }

   @Override
   public void visitRange2(Range2 range2) {
   }

   @Override
   public void visitTransactionReport(TransactionReport transactionReport) {
      for (Machine machine : transactionReport.getMachines().values()) {
         visitMachine(machine);
      }
   }

   @Override
   public void visitType(TransactionType type) {
      for (TransactionName name : type.getNames().values()) {
         visitName(name);
      }

      for (Range2 range2 : type.getRange2s()) {
         visitRange2(range2);
      }

      for (AllDuration allDuration : type.getAllDurations().values()) {
         visitAllDuration(allDuration);
      }
   }
}
