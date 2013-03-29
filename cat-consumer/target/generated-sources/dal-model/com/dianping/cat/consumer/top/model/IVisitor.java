package com.dianping.cat.consumer.top.model;

import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public interface IVisitor {

   public void visitDomain(Domain domain);

   public void visitSegment(Segment segment);

   public void visitTopReport(TopReport topReport);
}
