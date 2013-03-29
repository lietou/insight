package com.dianping.cat.consumer.state.model.entity;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_STATE_REPORT;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.state.model.BaseEntity;
import com.dianping.cat.consumer.state.model.IVisitor;

public class StateReport extends BaseEntity<StateReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Machine> m_machines = new LinkedHashMap<String, Machine>();

   public StateReport() {
   }

   public StateReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitStateReport(this);
   }

   public StateReport addMachine(Machine machine) {
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

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public Map<String, Machine> getMachines() {
      return m_machines;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(StateReport other) {
      assertAttributeEquals(other, ENTITY_STATE_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

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

   public StateReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public StateReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public StateReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
