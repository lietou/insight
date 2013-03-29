package com.dianping.cat.consumer.event.model.entity;

import static com.dianping.cat.consumer.event.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.event.model.Constants.ENTITY_MACHINE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.event.model.BaseEntity;
import com.dianping.cat.consumer.event.model.IVisitor;

public class Machine extends BaseEntity<Machine> {
   private String m_ip;

   private Map<String, EventType> m_types = new LinkedHashMap<String, EventType>();

   public Machine() {
   }

   public Machine(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachine(this);
   }

   public Machine addType(EventType type) {
      m_types.put(type.getId(), type);
      return this;
   }

   public EventType findType(String id) {
      return m_types.get(id);
   }

   public EventType findOrCreateType(String id) {
      EventType type = m_types.get(id);

      if (type == null) {
         synchronized (m_types) {
            type = m_types.get(id);

            if (type == null) {
               type = new EventType(id);
               m_types.put(id, type);
            }
         }
      }

      return type;
   }

   public String getIp() {
      return m_ip;
   }

   public Map<String, EventType> getTypes() {
      return m_types;
   }

   @Override
   public void mergeAttributes(Machine other) {
      assertAttributeEquals(other, ENTITY_MACHINE, ATTR_IP, m_ip, other.getIp());

   }

   public boolean removeType(String id) {
      if (m_types.containsKey(id)) {
         m_types.remove(id);
         return true;
      }

      return false;
   }

   public Machine setIp(String ip) {
      m_ip = ip;
      return this;
   }

}
