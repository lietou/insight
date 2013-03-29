package com.dianping.cat.consumer.cross.model.transform;

import com.dianping.cat.consumer.cross.model.entity.CrossReport;
import com.dianping.cat.consumer.cross.model.entity.Local;
import com.dianping.cat.consumer.cross.model.entity.Name;
import com.dianping.cat.consumer.cross.model.entity.Remote;
import com.dianping.cat.consumer.cross.model.entity.Type;

public interface IMaker<T> {

   public CrossReport buildCrossReport(T node);

   public String buildDomain(T node);

   public String buildIp(T node);

   public Local buildLocal(T node);

   public Name buildName(T node);

   public Remote buildRemote(T node);

   public Type buildType(T node);
}
