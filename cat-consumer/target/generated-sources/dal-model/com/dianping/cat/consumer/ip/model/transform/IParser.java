package com.dianping.cat.consumer.ip.model.transform;

import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public interface IParser<T> {
   public IpReport parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForIp(IMaker<T> maker, ILinker linker, Ip parent, T node);

   public void parseForPeriod(IMaker<T> maker, ILinker linker, Period parent, T node);
}
