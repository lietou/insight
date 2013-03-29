package com.dianping.cat.consumer.transaction.model.transform;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public interface IParser<T> {
   public TransactionReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForAllDuration(IMaker<T> maker, ILinker linker, AllDuration parent, T node);

   public void parseForDuration(IMaker<T> maker, ILinker linker, Duration parent, T node);

   public void parseForMachine(IMaker<T> maker, ILinker linker, Machine parent, T node);

   public void parseForTransactionName(IMaker<T> maker, ILinker linker, TransactionName parent, T node);

   public void parseForRange(IMaker<T> maker, ILinker linker, Range parent, T node);

   public void parseForRange2(IMaker<T> maker, ILinker linker, Range2 parent, T node);

   public void parseForTransactionType(IMaker<T> maker, ILinker linker, TransactionType parent, T node);
}
