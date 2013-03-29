package com.dianping.cat.configuration.client.transform;

import static com.dianping.cat.configuration.client.Constants.ATTR_ID;
import static com.dianping.cat.configuration.client.Constants.ATTR_IP;
import static com.dianping.cat.configuration.client.Constants.ATTR_MODE;
import static com.dianping.cat.configuration.client.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.client.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.client.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.client.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.client.Constants.ENTITY_PROPERTY;
import static com.dianping.cat.configuration.client.Constants.ENTITY_SERVER;
import static com.dianping.cat.configuration.client.Constants.ENTITY_SERVERS;

import java.util.Stack;

import com.dianping.cat.configuration.client.IVisitor;
import com.dianping.cat.configuration.client.entity.Bind;
import com.dianping.cat.configuration.client.entity.ClientConfig;
import com.dianping.cat.configuration.client.entity.Domain;
import com.dianping.cat.configuration.client.entity.Property;
import com.dianping.cat.configuration.client.entity.Server;

public class DefaultValidator implements IVisitor {

   private Path m_path = new Path();
   
   protected void assertRequired(String name, Object value) {
      if (value == null) {
         throw new RuntimeException(String.format("%s at path(%s) is required!", name, m_path));
      }
   }

   @Override
   public void visitBind(Bind bind) {
   }

   @Override
   public void visitConfig(ClientConfig config) {
      m_path.down(ENTITY_CONFIG);

      assertRequired(ATTR_MODE, config.getMode());

      visitConfigChildren(config);

      m_path.up(ENTITY_CONFIG);
   }

   protected void visitConfigChildren(ClientConfig config) {
      m_path.down(ENTITY_SERVERS);

      for (Server server : config.getServers()) {
         visitServer(server);
      }

      m_path.up(ENTITY_SERVERS);

      for (Domain domain : config.getDomains().values()) {
         visitDomain(domain);
      }

      if (config.getBind() != null) {
         visitBind(config.getBind());
      }

      m_path.down(ENTITY_PROPERTIES);

      for (Property property : config.getProperties().values()) {
         visitProperty(property);
      }

      m_path.up(ENTITY_PROPERTIES);
   }

   @Override
   public void visitDomain(Domain domain) {
      m_path.down(ENTITY_DOMAIN);

      assertRequired(ATTR_ID, domain.getId());

      m_path.up(ENTITY_DOMAIN);
   }

   @Override
   public void visitProperty(Property property) {
      m_path.down(ENTITY_PROPERTY);

      assertRequired(ATTR_NAME, property.getName());

      m_path.up(ENTITY_PROPERTY);
   }

   @Override
   public void visitServer(Server server) {
      m_path.down(ENTITY_SERVER);

      assertRequired(ATTR_IP, server.getIp());

      m_path.up(ENTITY_SERVER);
   }

   static class Path {
      private Stack<String> m_sections = new Stack<String>();

      public Path down(String nextSection) {
         m_sections.push(nextSection);

         return this;
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();

         for (String section : m_sections) {
            sb.append('/').append(section);
         }

         return sb.toString();
      }

      public Path up(String currentSection) {
         if (m_sections.isEmpty() || !m_sections.peek().equals(currentSection)) {
            throw new RuntimeException("INTERNAL ERROR: stack mismatched!");
         }

         m_sections.pop();
         return this;
      }
   }
}
