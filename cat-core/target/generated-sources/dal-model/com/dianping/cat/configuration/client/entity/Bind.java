package com.dianping.cat.configuration.client.entity;

import com.dianping.cat.configuration.client.BaseEntity;
import com.dianping.cat.configuration.client.IVisitor;

public class Bind extends BaseEntity<Bind> {
   private String m_ip;

   private int m_port = 2280;

   public Bind() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitBind(this);
   }

   public String getIp() {
      return m_ip;
   }

   public int getPort() {
      return m_port;
   }

   @Override
   public void mergeAttributes(Bind other) {
      if (other.getIp() != null) {
         m_ip = other.getIp();
      }

      if (other.getPort() != 0) {
         m_port = other.getPort();
      }
   }

   public Bind setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public Bind setPort(int port) {
      m_port = port;
      return this;
   }

}
