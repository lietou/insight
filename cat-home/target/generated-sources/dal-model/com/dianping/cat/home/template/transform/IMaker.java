package com.dianping.cat.home.template.transform;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public interface IMaker<T> {

   public Connection buildConnection(T node);

   public Duration buildDuration(T node);

   public Param buildParam(T node);

   public ThresholdTemplate buildThresholdTemplate(T node);
}
