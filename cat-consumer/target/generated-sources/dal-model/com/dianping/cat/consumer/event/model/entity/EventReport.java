package com.dianping.cat.consumer.event.model.entity;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_EVENT_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.event.model.BaseEntity;
import com.dianping.cat.consumer.event.model.IVisitor;

public class EventReport extends BaseEntity<EventReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Map<String, Machine> m_machines = new LinkedHashMap<String, Machine>();

   private Set<String> m_ips = new LinkedHashSet<String>();

   public EventReport() {
   }

   public EventReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitEventReport(this);
   }

   public EventReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public EventReport addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   public EventReport addMachine(Machine machine) {
      m_machines.put(machine.getIp(), machine);
      return this;
   }

   public Machine findMachine(String ip) {
      return m_machines.get(ip);
   }

   public Machine findOrCreateMachine(String ip) {
      Machine machine = m_machines.get(ip);

      if (machine == null) {
         synchronized (m_machines) {
            machine = m_machines.get(ip);

            if (machine == null) {
               machine = new Machine(ip);
               m_machines.put(ip, machine);
            }
         }
      }

      return machine;
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

   public Map<String, Machine> getMachines() {
      return m_machines;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(EventReport other) {
      assertAttributeEquals(other, ENTITY_EVENT_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeMachine(String ip) {
      if (m_machines.containsKey(ip)) {
         m_machines.remove(ip);
         return true;
      }

      return false;
   }

   public EventReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public EventReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public EventReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
