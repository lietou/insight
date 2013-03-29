package com.dianping.cat.home.template.transform;

import com.dianping.cat.home.template.entity.Connection;
import com.dianping.cat.home.template.entity.Duration;
import com.dianping.cat.home.template.entity.Param;
import com.dianping.cat.home.template.entity.ThresholdTemplate;

public interface IParser<T> {
   public ThresholdTemplate parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForConnection(IMaker<T> maker, ILinker linker, Connection parent, T node);

   public void parseForDuration(IMaker<T> maker, ILinker linker, Duration parent, T node);

   public void parseForParam(IMaker<T> maker, ILinker linker, Param parent, T node);
}
