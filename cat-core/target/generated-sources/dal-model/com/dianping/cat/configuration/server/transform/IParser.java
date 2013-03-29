package com.dianping.cat.configuration.server.transform;

import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public interface IParser<T> {
   public ServerConfig parse(IMaker<T> maker, ILinker linker, T node);

   public void parseForConsoleConfig(IMaker<T> maker, ILinker linker, ConsoleConfig parent, T node);

   public void parseForConsumerConfig(IMaker<T> maker, ILinker linker, ConsumerConfig parent, T node);

   public void parseForDomain(IMaker<T> maker, ILinker linker, Domain parent, T node);

   public void parseForHdfsConfig(IMaker<T> maker, ILinker linker, HdfsConfig parent, T node);

   public void parseForLongConfig(IMaker<T> maker, ILinker linker, LongConfig parent, T node);

   public void parseForProperty(IMaker<T> maker, ILinker linker, Property parent, T node);

   public void parseForStorageConfig(IMaker<T> maker, ILinker linker, StorageConfig parent, T node);
}
