package com.dianping.cat.consumer.transaction.model.transform;

import com.dianping.cat.consumer.transaction.model.entity.AllDuration;
import com.dianping.cat.consumer.transaction.model.entity.Duration;
import com.dianping.cat.consumer.transaction.model.entity.Machine;
import com.dianping.cat.consumer.transaction.model.entity.Range;
import com.dianping.cat.consumer.transaction.model.entity.Range2;
import com.dianping.cat.consumer.transaction.model.entity.TransactionName;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.consumer.transaction.model.entity.TransactionType;

public interface IMaker<T> {

   public AllDuration buildAllDuration(T node);

   public String buildDomain(T node);

   public Duration buildDuration(T node);

   public String buildIp(T node);

   public Machine buildMachine(T node);

   public TransactionName buildName(T node);

   public Range buildRange(T node);

   public Range2 buildRange2(T node);

   public TransactionReport buildTransactionReport(T node);

   public TransactionType buildType(T node);
}
