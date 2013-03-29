package com.dianping.cat.consumer.sql.model.entity;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_DATABASE;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.sql.model.BaseEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;

public class Database extends BaseEntity<Database> {
   private String m_id;

   private String m_connectUrl;

   private Map<String, Table> m_tables = new LinkedHashMap<String, Table>();

   public Database() {
   }

   public Database(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDatabase(this);
   }

   public Database addTable(Table table) {
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

   public String getConnectUrl() {
      return m_connectUrl;
   }

   public String getId() {
      return m_id;
   }

   public Map<String, Table> getTables() {
      return m_tables;
   }

   @Override
   public void mergeAttributes(Database other) {
      assertAttributeEquals(other, ENTITY_DATABASE, ATTR_ID, m_id, other.getId());

      if (other.getConnectUrl() != null) {
         m_connectUrl = other.getConnectUrl();
      }
   }

   public boolean removeTable(String id) {
      if (m_tables.containsKey(id)) {
         m_tables.remove(id);
         return true;
      }

      return false;
   }

   public Database setConnectUrl(String connectUrl) {
      m_connectUrl = connectUrl;
      return this;
   }

   public Database setId(String id) {
      m_id = id;
      return this;
   }

}
