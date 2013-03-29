package com.dianping.cat.configuration.server.entity;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class ServerConfig extends BaseEntity<ServerConfig> {
   private Boolean m_localMode = true;

   private StorageConfig m_storage;

   private ConsumerConfig m_consumer;

   private ConsoleConfig m_console;

   private Boolean m_jobMachine = false;

   public ServerConfig() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitConfig(this);
   }

   public ConsoleConfig getConsole() {
      return m_console;
   }

   public ConsumerConfig getConsumer() {
      return m_consumer;
   }

   public Boolean getJobMachine() {
      return m_jobMachine;
   }

   public Boolean getLocalMode() {
      return m_localMode;
   }

   public StorageConfig getStorage() {
      return m_storage;
   }

   public boolean isJobMachine() {
      return m_jobMachine != null && m_jobMachine.booleanValue();
   }

   public boolean isLocalMode() {
      return m_localMode != null && m_localMode.booleanValue();
   }

   @Override
   public void mergeAttributes(ServerConfig other) {
      if (other.getLocalMode() != null) {
         m_localMode = other.getLocalMode();
      }

      if (other.getJobMachine() != null) {
         m_jobMachine = other.getJobMachine();
      }
   }

   public ServerConfig setConsole(ConsoleConfig console) {
      m_console = console;
      return this;
   }

   public ServerConfig setConsumer(ConsumerConfig consumer) {
      m_consumer = consumer;
      return this;
   }

   public ServerConfig setJobMachine(Boolean jobMachine) {
      m_jobMachine = jobMachine;
      return this;
   }

   public ServerConfig setLocalMode(Boolean localMode) {
      m_localMode = localMode;
      return this;
   }

   public ServerConfig setStorage(StorageConfig storage) {
      m_storage = storage;
      return this;
   }

}
