package com.dianping.cat.configuration.client.transform;

import static com.dianping.cat.configuration.client.Constants.ENTITY_BIND;
import static com.dianping.cat.configuration.client.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.client.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.client.Constants.ENTITY_SERVERS;
import java.util.Stack;

import com.dianping.cat.configuration.client.IVisitor;
import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

public class DefaultMerger implements IVisitor {

   private Stack<Object> m_objs = new Stack<Object>();

   private Stack<String> m_tags = new Stack<String>();

   private ClientConfig m_config;

   public DefaultMerger(ClientConfig config) {
      m_config = config;
   }

   public ClientConfig getConfig() {
      return m_config;
   }

   protected Stack<Object> getObjects() {
      return m_objs;
   }

   protected Stack<String> getTags() {
      return m_tags;
   }

   protected void mergeBind(Bind old, Bind bind) {
      old.mergeAttributes(bind);
   }

   protected void mergeConfig(ClientConfig old, ClientConfig config) {
      old.mergeAttributes(config);
   }

   protected void mergeDomain(Domain old, Domain domain) {
      old.mergeAttributes(domain);
   }

   protected void mergeProperty(Property old, Property property) {
      old.mergeAttributes(property);
   }

   protected void mergeServer(Server old, Server server) {
      old.mergeAttributes(server);
   }

   @Override
   public void visitBind(Bind bind) {
      Object parent = m_objs.peek();
      Bind old = null;

      if (parent instanceof ClientConfig) {
         ClientConfig config = (ClientConfig) parent;

         old = config.getBind();

         if (old == null) {
            old = new Bind();
            config.setBind(old);
         }

         mergeBind(old, bind);
      }

      visitBindChildren(old, bind);
   }

   protected void visitBindChildren(Bind old, Bind bind) {
   }

   @Override
   public void visitConfig(ClientConfig config) {
      m_config.mergeAttributes(config);
      visitConfigChildren(m_config, config);
   }

   protected void visitConfigChildren(ClientConfig old, ClientConfig config) {
      if (old != null) {
         m_objs.push(old);

         for (Server server : config.getServers()) {
            m_tags.push(ENTITY_SERVERS);
            visitServer(server);
            m_tags.pop();
         }

         for (Domain domain : config.getDomains().values()) {
            m_tags.push(ENTITY_DOMAIN);
            visitDomain(domain);
            m_tags.pop();
         }

         if (config.getBind() != null) {
            m_tags.push(ENTITY_BIND);
            visitBind(config.getBind());
            m_tags.pop();
         }

         for (Property property : config.getProperties().values()) {
            m_tags.push(ENTITY_PROPERTIES);
            visitProperty(property);
            m_tags.pop();
         }

         m_objs.pop();
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      Object parent = m_objs.peek();
      Domain old = null;

      if (parent instanceof ClientConfig) {
         ClientConfig config = (ClientConfig) parent;

         old = config.findDomain(domain.getId());

         if (old == null) {
            old = new Domain(domain.getId());
            config.addDomain(old);
         }

         mergeDomain(old, domain);
      }

      visitDomainChildren(old, domain);
   }

   protected void visitDomainChildren(Domain old, Domain domain) {
   }

   @Override
   public void visitProperty(Property property) {
      Object parent = m_objs.peek();
      Property old = null;

      if (parent instanceof ClientConfig) {
         ClientConfig config = (ClientConfig) parent;

         old = config.findProperty(property.getName());

         if (old == null) {
            old = new Property(property.getName());
            config.addProperty(old);
         }

         mergeProperty(old, property);
      }

      visitPropertyChildren(old, property);
   }

   protected void visitPropertyChildren(Property old, Property property) {
   }

   @Override
   public void visitServer(Server server) {
      Object parent = m_objs.peek();
      Server old = null;

      if (parent instanceof ClientConfig) {
         ClientConfig config = (ClientConfig) parent;

         old = config.findServer(server.getIp());

         if (old == null) {
            old = new Server(server.getIp());
            config.addServer(old);
         }

         mergeServer(old, server);
      }

      visitServerChildren(old, server);
   }

   protected void visitServerChildren(Server old, Server server) {
   }
}
