package com.dianping.cat.consumer.top.model.transform;

import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public interface ILinker {

   public boolean onDomain(TopReport parent, Domain domain);

   public boolean onSegment(Domain parent, Segment segment);
}
