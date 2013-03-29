package com.dianping.cat.consumer.transaction.model.entity;

import static com.dianping.cat.consumer.transaction.model.Constants.ATTR_IP;
import static com.dianping.cat.consumer.transaction.model.Constants.ENTITY_MACHINE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.transaction.model.BaseEntity;
import com.dianping.cat.consumer.transaction.model.IVisitor;

public class Machine extends BaseEntity<Machine> {
   private String m_ip;

   private Map<String, TransactionType> m_types = new LinkedHashMap<String, TransactionType>();

   public Machine() {
   }

   public Machine(String ip) {
      m_ip = ip;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitMachine(this);
   }

   public Machine addType(TransactionType type) {
      m_types.put(type.getId(), type);
      return this;
   }

   public TransactionType findType(String id) {
      return m_types.get(id);
   }

   public TransactionType findOrCreateType(String id) {
      TransactionType type = m_types.get(id);

      if (type == null) {
         synchronized (m_types) {
            type = m_types.get(id);

            if (type == null) {
               type = new TransactionType(id);
               m_types.put(id, type);
            }
         }
      }

      return type;
   }

   public String getIp() {
      return m_ip;
   }

   public Map<String, TransactionType> getTypes() {
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
