package com.dianping.cat.home.template.transform;

import com.dianping.cat.home.template.IVisitor;
import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public abstract class BaseVisitor implements IVisitor {
   @Override
   public void visitConnection(Connection connection) {
      for (Param param : connection.getParams().values()) {
         visitParam(param);
      }
   }

   @Override
   public void visitDuration(Duration duration) {
   }

   @Override
   public void visitParam(Param param) {
   }

   @Override
   public void visitThresholdTemplate(ThresholdTemplate thresholdTemplate) {
      if (thresholdTemplate.getConnection() != null) {
         visitConnection(thresholdTemplate.getConnection());
      }

      for (Duration duration : thresholdTemplate.getDurations().values()) {
         visitDuration(duration);
      }
   }
}
