package com.dianping.cat.consumer.top.model.transform;

import com.dianping.cat.consumer.top.model.IVisitor;
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitDomain(Domain domain) {
      for (Segment segment : domain.getSegments().values()) {
         visitSegment(segment);
      }
   }

   @Override
   public void visitSegment(Segment segment) {
   }

   @Override
   public void visitTopReport(TopReport topReport) {
      for (Domain domain : topReport.getDomains().values()) {
         visitDomain(domain);
      }
   }
}
