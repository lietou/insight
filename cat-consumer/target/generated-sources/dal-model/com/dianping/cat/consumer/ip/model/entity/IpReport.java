package com.dianping.cat.consumer.ip.model.entity;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.ip.model.BaseEntity;
import com.dianping.cat.consumer.ip.model.IVisitor;

public class IpReport extends BaseEntity<IpReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Map<Integer, Period> m_periods = new LinkedHashMap<Integer, Period>();

   public IpReport() {
   }

   public IpReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitIpReport(this);
   }

   public IpReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public IpReport addPeriod(Period period) {
      m_periods.put(period.getMinute(), period);
      return this;
   }

   public Period findPeriod(Integer minute) {
      return m_periods.get(minute);
   }

   public Period findOrCreatePeriod(Integer minute) {
      Period period = m_periods.get(minute);

      if (period == null) {
         synchronized (m_periods) {
            period = m_periods.get(minute);

            if (period == null) {
               period = new Period(minute);
               m_periods.put(minute, period);
            }
         }
      }

      return period;
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

   public Map<Integer, Period> getPeriods() {
      return m_periods;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(IpReport other) {
      assertAttributeEquals(other, ENTITY_IP_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removePeriod(Integer minute) {
      if (m_periods.containsKey(minute)) {
         m_periods.remove(minute);
         return true;
      }

      return false;
   }

   public IpReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public IpReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public IpReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
