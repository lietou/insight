package com.dianping.cat.consumer.ip.model.transform;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public interface IMaker<T> {

   public String buildDomain(T node);

   public Ip buildIp(T node);

   public IpReport buildIpReport(T node);

   public Period buildPeriod(T node);
}
