package com.dianping.cat.configuration.server.transform;

import static com.dianping.cat.configuration.server.Constants.ATTR_ID;
import static com.dianping.cat.configuration.server.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_CONSUMER;
import static com.dianping.cat.configuration.server.Constants.ENTITY_DOMAIN;
import static com.dianping.cat.configuration.server.Constants.ENTITY_HDFS;
import static com.dianping.cat.configuration.server.Constants.ENTITY_LONG_CONFIG;
import static com.dianping.cat.configuration.server.Constants.ENTITY_PROPERTIES;
import static com.dianping.cat.configuration.server.Constants.ENTITY_PROPERTY;
import static com.dianping.cat.configuration.server.Constants.ENTITY_STORAGE;

import java.util.Stack;

import com.dianping.cat.configuration.server.IVisitor;
import com.dianping.cat.configuration.server.entity.ServerConfig;
import com.dianping.cat.configuration.server.entity.ConsoleConfig;
import com.dianping.cat.configuration.server.entity.ConsumerConfig;
import com.dianping.cat.configuration.server.entity.Domain;
import com.dianping.cat.configuration.server.entity.HdfsConfig;
import com.dianping.cat.configuration.server.entity.LongConfig;
import com.dianping.cat.configuration.server.entity.Property;
import com.dianping.cat.configuration.server.entity.StorageConfig;

public class DefaultValidator implements IVisitor {

   private Path m_path = new Path();
   
   protected void assertRequired(String name, Object value) {
      if (value == null) {
         throw new RuntimeException(String.format("%s at path(%s) is required!", name, m_path));
      }
   }

   @Override
   public void visitConfig(ServerConfig config) {
      m_path.down(ENTITY_CONFIG);

      visitConfigChildren(config);

      m_path.up(ENTITY_CONFIG);
   }

   protected void visitConfigChildren(ServerConfig config) {
      if (config.getStorage() != null) {
         visitStorage(config.getStorage());
      }

      if (config.getConsumer() != null) {
         visitConsumer(config.getConsumer());
      }

      if (config.getConsole() != null) {
         visitConsole(config.getConsole());
      }
   }

   @Override
   public void visitConsole(ConsoleConfig console) {
   }

   @Override
   public void visitConsumer(ConsumerConfig consumer) {
      m_path.down(ENTITY_CONSUMER);

      visitConsumerChildren(consumer);

      m_path.up(ENTITY_CONSUMER);
   }

   protected void visitConsumerChildren(ConsumerConfig consumer) {
      if (consumer.getLongConfig() != null) {
         visitLongConfig(consumer.getLongConfig());
      }
   }

   @Override
   public void visitDomain(Domain domain) {
      m_path.down(ENTITY_DOMAIN);

      assertRequired(ATTR_NAME, domain.getName());

      m_path.up(ENTITY_DOMAIN);
   }

   @Override
   public void visitHdfs(HdfsConfig hdfs) {
      m_path.down(ENTITY_HDFS);

      assertRequired(ATTR_ID, hdfs.getId());

      m_path.up(ENTITY_HDFS);
   }

   @Override
   public void visitLongConfig(LongConfig longConfig) {
      m_path.down(ENTITY_LONG_CONFIG);

      visitLongConfigChildren(longConfig);

      m_path.up(ENTITY_LONG_CONFIG);
   }

   protected void visitLongConfigChildren(LongConfig longConfig) {
      for (Domain domain : longConfig.getDomains().values()) {
         visitDomain(domain);
      }
   }

   @Override
   public void visitProperty(Property property) {
      m_path.down(ENTITY_PROPERTY);

      assertRequired(ATTR_NAME, property.getName());

      m_path.up(ENTITY_PROPERTY);
   }

   @Override
   public void visitStorage(StorageConfig storage) {
      m_path.down(ENTITY_STORAGE);

      visitStorageChildren(storage);

      m_path.up(ENTITY_STORAGE);
   }

   protected void visitStorageChildren(StorageConfig storage) {
      for (HdfsConfig hdfs : storage.getHdfses().values()) {
         visitHdfs(hdfs);
      }

      m_path.down(ENTITY_PROPERTIES);

      for (Property property : storage.getProperties().values()) {
         visitProperty(property);
      }

      m_path.up(ENTITY_PROPERTIES);
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
