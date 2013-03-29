package com.dianping.cat.consumer.heartbeat.model.entity;

import static com.dianping.cat.consumer.heartbeat.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.heartbeat.model.Constants.ENTITY_HEARTBEAT_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.heartbeat.model.BaseEntity;
import com.dianping.cat.consumer.heartbeat.model.IVisitor;

public class HeartbeatReport extends BaseEntity<HeartbeatReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Machine> m_machines = new LinkedHashMap<String, Machine>();

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Set<String> m_ips = new LinkedHashSet<String>();

   public HeartbeatReport() {
   }

   public HeartbeatReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitHeartbeatReport(this);
   }

   public HeartbeatReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public HeartbeatReport addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   public HeartbeatReport addMachine(Machine machine) {
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
   public void mergeAttributes(HeartbeatReport other) {
      assertAttributeEquals(other, ENTITY_HEARTBEAT_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

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

   public HeartbeatReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public HeartbeatReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public HeartbeatReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
