package com.dianping.cat.consumer.ip.model.entity;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_MINUTE;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_PERIOD;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.ip.model.BaseEntity;
import com.dianping.cat.consumer.ip.model.IVisitor;

public class Period extends BaseEntity<Period> {
   private Integer m_minute;

   private Map<String, Ip> m_ips = new LinkedHashMap<String, Ip>();

   public Period() {
   }

   public Period(Integer minute) {
      m_minute = minute;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitPeriod(this);
   }

   public Period addIp(Ip ip) {
      m_ips.put(ip.getAddress(), ip);
      return this;
   }

   public Ip findIp(String address) {
      return m_ips.get(address);
   }

   public Ip findOrCreateIp(String address) {
      Ip ip = m_ips.get(address);

      if (ip == null) {
         synchronized (m_ips) {
            ip = m_ips.get(address);

            if (ip == null) {
               ip = new Ip(address);
               m_ips.put(address, ip);
            }
         }
      }

      return ip;
   }

   public Map<String, Ip> getIps() {
      return m_ips;
   }

   public Integer getMinute() {
      return m_minute;
   }

   @Override
   public void mergeAttributes(Period other) {
      assertAttributeEquals(other, ENTITY_PERIOD, ATTR_MINUTE, m_minute, other.getMinute());

   }

   public boolean removeIp(String address) {
      if (m_ips.containsKey(address)) {
         m_ips.remove(address);
         return true;
      }

      return false;
   }

   public Period setMinute(Integer minute) {
      m_minute = minute;
      return this;
   }

}
