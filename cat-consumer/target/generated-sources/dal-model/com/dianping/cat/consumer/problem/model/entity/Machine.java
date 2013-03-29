package com.dianping.cat.consumer.problem.model.entity;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_MACHINE;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.problem.model.BaseEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;

public class Machine extends BaseEntity<Machine> {
   private String m_ip;

   private List<Entry> m_entries = new ArrayList<Entry>();

   public Machine() {
   }

   public Machine(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachine(this);
   }

   public Machine addEntry(Entry entry) {
      m_entries.add(entry);
      return this;
   }

   public List<Entry> getEntries() {
      return m_entries;
   }

   public String getIp() {
      return m_ip;
   }

   @Override
   public void mergeAttributes(Machine other) {
      assertAttributeEquals(other, ENTITY_MACHINE, ATTR_IP, m_ip, other.getIp());

   }

   public Machine setIp(String ip) {
      m_ip = ip;
      return this;
   }

}
