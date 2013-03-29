package com.dianping.cat.home.template;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public interface IVisitor {

   public void visitConnection(Connection connection);

   public void visitDuration(Duration duration);

   public void visitParam(Param param);

   public void visitThresholdTemplate(ThresholdTemplate thresholdTemplate);
}
