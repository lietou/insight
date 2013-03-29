package com.dianping.cat.consumer.sql.model.entity;

import static com.dianping.cat.consumer.sql.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.sql.model.Constants.ENTITY_SQL_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.sql.model.BaseEntity;
import com.dianping.cat.consumer.sql.model.IVisitor;

public class SqlReport extends BaseEntity<SqlReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Map<String, Database> m_databases = new LinkedHashMap<String, Database>();

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private Set<String> m_databaseNames = new LinkedHashSet<String>();

   public SqlReport() {
   }

   public SqlReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSqlReport(this);
   }

   public SqlReport addDatabase(Database database) {
      m_databases.put(database.getId(), database);
      return this;
   }

   public SqlReport addDatabaseName(String databaseName) {
      m_databaseNames.add(databaseName);
      return this;
   }

   public SqlReport addDomainName(String domainName) {
      m_domainNames.add(domainName);
      return this;
   }

   public Database findDatabase(String id) {
      return m_databases.get(id);
   }

   public Database findOrCreateDatabase(String id) {
      Database database = m_databases.get(id);

      if (database == null) {
         synchronized (m_databases) {
            database = m_databases.get(id);

            if (database == null) {
               database = new Database(id);
               m_databases.put(id, database);
            }
         }
      }

      return database;
   }

   public Set<String> getDatabaseNames() {
      return m_databaseNames;
   }

   public Map<String, Database> getDatabases() {
      return m_databases;
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

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(SqlReport other) {
      assertAttributeEquals(other, ENTITY_SQL_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeDatabase(String id) {
      if (m_databases.containsKey(id)) {
         m_databases.remove(id);
         return true;
      }

      return false;
   }

   public SqlReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public SqlReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public SqlReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
