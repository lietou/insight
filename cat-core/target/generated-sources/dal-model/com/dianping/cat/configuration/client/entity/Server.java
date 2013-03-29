package com.dianping.cat.configuration.client.entity;

import static com.dianping.cat.configuration.client.Constants.ATTR_IP;
import static com.dianping.cat.configuration.client.Constants.ENTITY_SERVER;

import com.dianping.cat.configuration.client.BaseEntity;
import com.dianping.cat.configuration.client.IVisitor;

public class Server extends BaseEntity<Server> {
   private String m_ip;

   private Integer m_port;

   private Boolean m_enabled = true;

   public Server() {
   }

   public Server(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitServer(this);
   }

   public Boolean getEnabled() {
      return m_enabled;
   }

   public String getIp() {
      return m_ip;
   }

   public Integer getPort() {
      return m_port;
   }

   public boolean isEnabled() {
      return m_enabled != null && m_enabled.booleanValue();
   }

   @Override
   public void mergeAttributes(Server other) {
      assertAttributeEquals(other, ENTITY_SERVER, ATTR_IP, m_ip, other.getIp());

      if (other.getPort() != null) {
         m_port = other.getPort();
      }

      if (other.getEnabled() != null) {
         m_enabled = other.getEnabled();
      }
   }

   public Server setEnabled(Boolean enabled) {
      m_enabled = enabled;
      return this;
   }

   public Server setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public Server setPort(Integer port) {
      m_port = port;
      return this;
   }

}
