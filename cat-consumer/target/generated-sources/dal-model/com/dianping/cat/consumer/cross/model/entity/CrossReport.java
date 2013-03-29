package com.dianping.cat.consumer.cross.model.entity;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_CROSS_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.cross.model.BaseEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;

public class CrossReport extends BaseEntity<CrossReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Set<String> m_ips = new LinkedHashSet<String>();

   private Map<String, Local> m_locals = new LinkedHashMap<String, Local>();

   public CrossReport() {
   }

   public CrossReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitCrossReport(this);
   }

   public CrossReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public CrossReport addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   public CrossReport addLocal(Local local) {
      m_locals.put(local.getId(), local);
      return this;
   }

   public Local findLocal(String id) {
      return m_locals.get(id);
   }

   public Local findOrCreateLocal(String id) {
      Local local = m_locals.get(id);

      if (local == null) {
         synchronized (m_locals) {
            local = m_locals.get(id);

            if (local == null) {
               local = new Local(id);
               m_locals.put(id, local);
            }
         }
      }

      return local;
   }

   public String getDomain() {
      return m_domain;
   }

   public Set<String> getDomainNames() {
      return m_domainNames;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public Set<String> getIps() {
      return m_ips;
   }

   public Map<String, Local> getLocals() {
      return m_locals;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(CrossReport other) {
      assertAttributeEquals(other, ENTITY_CROSS_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeLocal(String id) {
      if (m_locals.containsKey(id)) {
         m_locals.remove(id);
         return true;
      }

      return false;
   }

   public CrossReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public CrossReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public CrossReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
