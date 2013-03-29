package com.dianping.cat.configuration.client.entity;

import static com.dianping.cat.configuration.client.Constants.ATTR_ID;
import static com.dianping.cat.configuration.client.Constants.ENTITY_DOMAIN;

import com.dianping.cat.configuration.client.BaseEntity;
import com.dianping.cat.configuration.client.IVisitor;

public class Domain extends BaseEntity<Domain> {
   private String m_id;

   private String m_ip;

   private int m_maxThreads = 500;

   private Boolean m_enabled;

   public Domain() {
   }

   public Domain(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDomain(this);
   }

   public Boolean getEnabled() {
      return m_enabled;
   }

   public String getId() {
      return m_id;
   }

   public String getIp() {
      return m_ip;
   }

   public int getMaxThreads() {
      return m_maxThreads;
   }

   public boolean isEnabled() {
      return m_enabled != null && m_enabled.booleanValue();
   }

   @Override
   public void mergeAttributes(Domain other) {
      assertAttributeEquals(other, ENTITY_DOMAIN, ATTR_ID, m_id, other.getId());

      if (other.getIp() != null) {
         m_ip = other.getIp();
      }

      if (other.getMaxThreads() != 0) {
         m_maxThreads = other.getMaxThreads();
      }

      if (other.getEnabled() != null) {
         m_enabled = other.getEnabled();
      }
   }

   public Domain setEnabled(Boolean enabled) {
      m_enabled = enabled;
      return this;
   }

   public Domain setId(String id) {
      m_id = id;
      return this;
   }

   public Domain setIp(String ip) {
      m_ip = ip;
      return this;
   }

   public Domain setMaxThreads(int maxThreads) {
      m_maxThreads = maxThreads;
      return this;
   }

}
