package com.dianping.cat.consumer.state.model.entity;

import static com.dianping.cat.consumer.state.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.state.model.Constants.ENTITY_PROCESSDOMAIN;

import java.util.LinkedHashSet;
import java.util.Set;

import com.dianping.cat.consumer.state.model.BaseEntity;
import com.dianping.cat.consumer.state.model.IVisitor;

public class ProcessDomain extends BaseEntity<ProcessDomain> {
   private String m_name;

   private Set<String> m_ips = new LinkedHashSet<String>();

   public ProcessDomain() {
   }

   public ProcessDomain(String name) {
      m_name = name;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitProcessDomain(this);
   }

   public ProcessDomain addIp(String ip) {
      m_ips.add(ip);
      return this;
   }

   public Set<String> getIps() {
      return m_ips;
   }

   public String getName() {
      return m_name;
   }

   @Override
   public void mergeAttributes(ProcessDomain other) {
      assertAttributeEquals(other, ENTITY_PROCESSDOMAIN, ATTR_NAME, m_name, other.getName());

   }

   public ProcessDomain setName(String name) {
      m_name = name;
      return this;
   }

}
