package com.dianping.cat.consumer.top.model.entity;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_TOP_REPORT;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.top.model.BaseEntity;
import com.dianping.cat.consumer.top.model.IVisitor;

public class TopReport extends BaseEntity<TopReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Domain> m_domains = new LinkedHashMap<String, Domain>();

   public TopReport() {
   }

   public TopReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitTopReport(this);
   }

   public TopReport addDomain(Domain domain) {
      m_domains.put(domain.getName(), domain);
      return this;
   }

   public Domain findDomain(String name) {
      return m_domains.get(name);
   }

   public Domain findOrCreateDomain(String name) {
      Domain domain = m_domains.get(name);

      if (domain == null) {
         synchronized (m_domains) {
            domain = m_domains.get(name);

            if (domain == null) {
               domain = new Domain(name);
               m_domains.put(name, domain);
            }
         }
      }

      return domain;
   }

   public String getDomain() {
      return m_domain;
   }

   public Map<String, Domain> getDomains() {
      return m_domains;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(TopReport other) {
      assertAttributeEquals(other, ENTITY_TOP_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeDomain(String name) {
      if (m_domains.containsKey(name)) {
         m_domains.remove(name);
         return true;
      }

      return false;
   }

   public TopReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public TopReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public TopReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
