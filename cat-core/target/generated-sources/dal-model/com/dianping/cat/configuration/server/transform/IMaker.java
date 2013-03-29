package com.dianping.cat.configuration.server.transform;

import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public interface IMaker<T> {

   public ServerConfig buildConfig(T node);

   public ConsoleConfig buildConsole(T node);

   public ConsumerConfig buildConsumer(T node);

   public Domain buildDomain(T node);

   public HdfsConfig buildHdfs(T node);

   public LongConfig buildLongConfig(T node);

   public Property buildProperty(T node);

   public StorageConfig buildStorage(T node);
}
