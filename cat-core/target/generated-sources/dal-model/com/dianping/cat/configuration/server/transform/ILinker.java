package com.dianping.cat.configuration.server.transform;

import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public interface ILinker {

   public boolean onConsole(ServerConfig parent, ConsoleConfig console);

   public boolean onConsumer(ServerConfig parent, ConsumerConfig consumer);

   public boolean onDomain(LongConfig parent, Domain domain);

   public boolean onHdfs(StorageConfig parent, HdfsConfig hdfs);

   public boolean onLongConfig(ConsumerConfig parent, LongConfig longConfig);

   public boolean onProperty(StorageConfig parent, Property property);

   public boolean onStorage(ServerConfig parent, StorageConfig storage);
}
