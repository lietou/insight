package com.dianping.cat.consumer.ip.model.transform;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public interface ILinker {

   public boolean onIp(Period parent, Ip ip);

   public boolean onPeriod(IpReport parent, Period period);
}
