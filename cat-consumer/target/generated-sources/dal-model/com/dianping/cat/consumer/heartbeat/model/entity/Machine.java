package com.dianping.cat.consumer.heartbeat.model.entity;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_MACHINE;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.heartbeat.model.BaseEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;

public class Machine extends BaseEntity<Machine> {
   private String m_ip;

   private List<Period> m_periods = new ArrayList<Period>();

   public Machine() {
   }

   public Machine(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachine(this);
   }

   public Machine addPeriod(Period period) {
      m_periods.add(period);
      return this;
   }

   public Period findPeriod(int minute) {
      for (Period period : m_periods) {
         if (period.getMinute() != minute) {
            continue;
         }

         return period;
      }

      return null;
   }

   public String getIp() {
      return m_ip;
   }

   public List<Period> getPeriods() {
      return m_periods;
   }

   @Override
   public void mergeAttributes(Machine other) {
      assertAttributeEquals(other, ENTITY_MACHINE, ATTR_IP, m_ip, other.getIp());

   }

   public boolean removePeriod(int minute) {
      int len = m_periods.size();

      for (int i = 0; i < len; i++) {
         Period period = m_periods.get(i);

         if (period.getMinute() != minute) {
            continue;
         }

         m_periods.remove(i);
         return true;
      }

      return false;
   }

   public Machine setIp(String ip) {
      m_ip = ip;
      return this;
   }

}
