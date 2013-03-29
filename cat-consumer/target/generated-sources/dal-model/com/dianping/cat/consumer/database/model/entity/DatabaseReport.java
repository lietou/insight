package com.dianping.cat.consumer.database.model.entity;

import static com.dianping.cat.consumer.database.model.Constants.ATTR_DATABASE;
import static com.dianping.cat.consumer.database.model.Constants.ENTITY_DATABASE_REPORT;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.dianping.cat.consumer.database.model.BaseEntity;
import com.dianping.cat.consumer.database.model.IVisitor;

public class DatabaseReport extends BaseEntity<DatabaseReport> {
   private String m_database;

   private String m_connectUrl;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_databaseNames = new LinkedHashSet<String>();

   private Map<String, Domain> m_domains = new LinkedHashMap<String, Domain>();

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   public DatabaseReport() {
   }

   public DatabaseReport(String database) {
      m_database = database;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDatabaseReport(this);
   }

   public DatabaseReport addDatabaseName(String databaseName) {
      m_databaseNames.add(databaseName);
      return this;
   }

   public DatabaseReport addDomain(Domain domain) {
      m_domains.put(domain.getId(), domain);
      return this;
   }

   public DatabaseReport addDomainName(String domainName) {
      m_domainNames.add(domainName);
      return this;
   }

   public Domain findDomain(String id) {
      return m_domains.get(id);
   }

   public Domain findOrCreateDomain(String id) {
      Domain domain = m_domains.get(id);

      if (domain == null) {
         synchronized (m_domains) {
            domain = m_domains.get(id);

            if (domain == null) {
               domain = new Domain(id);
               m_domains.put(id, domain);
            }
         }
      }

      return domain;
   }

   public String getConnectUrl() {
      return m_connectUrl;
   }

   public String getDatabase() {
      return m_database;
   }

   public Set<String> getDatabaseNames() {
      return m_databaseNames;
   }

   public Set<String> getDomainNames() {
      return m_domainNames;
   }

   public Map<String, Domain> getDomains() {
      return m_domains;
   }

   public java.util.Date getEndTime() {
      return m_endTime;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   @Override
   public void mergeAttributes(DatabaseReport other) {
      assertAttributeEquals(other, ENTITY_DATABASE_REPORT, ATTR_DATABASE, m_database, other.getDatabase());

      if (other.getConnectUrl() != null) {
         m_connectUrl = other.getConnectUrl();
      }

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public boolean removeDomain(String id) {
      if (m_domains.containsKey(id)) {
         m_domains.remove(id);
         return true;
      }

      return false;
   }

   public DatabaseReport setConnectUrl(String connectUrl) {
      m_connectUrl = connectUrl;
      return this;
   }

   public DatabaseReport setDatabase(String database) {
      m_database = database;
      return this;
   }

   public DatabaseReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public DatabaseReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

}
