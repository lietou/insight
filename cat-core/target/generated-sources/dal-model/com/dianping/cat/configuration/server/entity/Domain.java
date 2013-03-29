package com.dianping.cat.configuration.server.entity;

import static com.dianping.cat.configuration.server.Constants.ATTR_NAME;
import static com.dianping.cat.configuration.server.Constants.ENTITY_DOMAIN;

import com.dianping.cat.configuration.server.BaseEntity;
import com.dianping.cat.configuration.server.IVisitor;

public class Domain extends BaseEntity<Domain> {
   private String m_name;

   private Integer m_urlThreshold;

   private Integer m_sqlThreshold;

   private Integer m_serviceThreshold;

   public Domain() {
   }

   public Domain(String name) {
      m_name = name;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDomain(this);
   }

   public String getName() {
      return m_name;
   }

   public Integer getServiceThreshold() {
      return m_serviceThreshold;
   }

   public Integer getSqlThreshold() {
      return m_sqlThreshold;
   }

   public Integer getUrlThreshold() {
      return m_urlThreshold;
   }

   @Override
   public void mergeAttributes(Domain other) {
      assertAttributeEquals(other, ENTITY_DOMAIN, ATTR_NAME, m_name, other.getName());

      if (other.getUrlThreshold() != null) {
         m_urlThreshold = other.getUrlThreshold();
      }

      if (other.getSqlThreshold() != null) {
         m_sqlThreshold = other.getSqlThreshold();
      }

      if (other.getServiceThreshold() != null) {
         m_serviceThreshold = other.getServiceThreshold();
      }
   }

   public Domain setName(String name) {
      m_name = name;
      return this;
   }

   public Domain setServiceThreshold(Integer serviceThreshold) {
      m_serviceThreshold = serviceThreshold;
      return this;
   }

   public Domain setSqlThreshold(Integer sqlThreshold) {
      m_sqlThreshold = sqlThreshold;
      return this;
   }

   public Domain setUrlThreshold(Integer urlThreshold) {
      m_urlThreshold = urlThreshold;
      return this;
   }

}
