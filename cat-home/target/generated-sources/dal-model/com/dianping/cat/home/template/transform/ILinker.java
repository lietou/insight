package com.dianping.cat.home.template.transform;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public interface ILinker {

   public boolean onConnection(ThresholdTemplate parent, Connection connection);

   public boolean onDuration(ThresholdTemplate parent, Duration duration);

   public boolean onParam(Connection parent, Param param);
}
