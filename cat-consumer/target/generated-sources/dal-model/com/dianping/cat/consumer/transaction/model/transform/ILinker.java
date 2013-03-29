package com.dianping.cat.consumer.transaction.model.transform;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public interface ILinker {

   public boolean onAllDuration(TransactionType parent, AllDuration allDuration);

   public boolean onAllDuration(TransactionName parent, AllDuration allDuration);

   public boolean onDuration(TransactionName parent, Duration duration);

   public boolean onMachine(TransactionReport parent, Machine machine);

   public boolean onName(TransactionType parent, TransactionName name);

   public boolean onRange(TransactionName parent, Range range);

   public boolean onRange2(TransactionType parent, Range2 range2);

   public boolean onType(Machine parent, TransactionType type);
}
