package com.dianping.cat.configuration.client.transform;

import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

public interface ILinker {

   public boolean onBind(ClientConfig parent, Bind bind);

   public boolean onDomain(ClientConfig parent, Domain domain);

   public boolean onProperty(ClientConfig parent, Property property);

   public boolean onServer(ClientConfig parent, Server server);
}
