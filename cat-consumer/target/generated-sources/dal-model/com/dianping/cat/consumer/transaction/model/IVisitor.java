package com.dianping.cat.consumer.transaction.model;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public interface IVisitor {

   public void visitAllDuration(AllDuration allDuration);

   public void visitDuration(Duration duration);

   public void visitMachine(Machine machine);

   public void visitName(TransactionName name);

   public void visitRange(Range range);

   public void visitRange2(Range2 range2);

   public void visitTransactionReport(TransactionReport transactionReport);

   public void visitType(TransactionType type);
}
