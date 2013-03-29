package com.dianping.cat.configuration.server;

import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public interface IVisitor {

   public void visitConfig(ServerConfig config);

   public void visitConsole(ConsoleConfig console);

   public void visitConsumer(ConsumerConfig consumer);

   public void visitDomain(Domain domain);

   public void visitHdfs(HdfsConfig hdfs);

   public void visitLongConfig(LongConfig longConfig);

   public void visitProperty(Property property);

   public void visitStorage(StorageConfig storage);
}
