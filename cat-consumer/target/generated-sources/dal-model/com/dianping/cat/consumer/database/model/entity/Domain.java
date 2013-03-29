package com.dianping.cat.consumer.database.model.entity;

import static com.dianping.cat.consumer.database.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DOMAIN;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.database.model.BaseEntity;
import com.dianping.cat.consumer.database.model.IVisitor;

public class Domain extends BaseEntity<Domain> {
   private String m_id;

   private Map<String, Table> m_tables = new LinkedHashMap<String, Table>();

   public Domain() {
   }

   public Domain(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDomain(this);
   }

   public Domain addTable(Table table) {
      m_tables.put(table.getId(), table);
      return this;
   }

   public Table findTable(String id) {
      return m_tables.get(id);
   }

   public Table findOrCreateTable(String id) {
      Table table = m_tables.get(id);

      if (table == null) {
         synchronized (m_tables) {
            table = m_tables.get(id);

            if (table == null) {
               table = new Table(id);
               m_tables.put(id, table);
            }
         }
      }

      return table;
   }

   public String getId() {
      return m_id;
   }

   public Map<String, Table> getTables() {
      return m_tables;
   }

   @Override
   public void mergeAttributes(Domain other) {
      assertAttributeEquals(other, ENTITY_DOMAIN, ATTR_ID, m_id, other.getId());

   }

   public boolean removeTable(String id) {
      if (m_tables.containsKey(id)) {
         m_tables.remove(id);
         return true;
      }

      return false;
   }

   public Domain setId(String id) {
      m_id = id;
      return this;
   }

}
