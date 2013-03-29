package com.dianping.cat.configuration.client.transform;

import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

public interface IMaker<T> {

   public Bind buildBind(T node);

   public ClientConfig buildConfig(T node);

   public Domain buildDomain(T node);

   public Property buildProperty(T node);

   public Server buildServer(T node);
}
