package com.dianping.cat.consumer.health.model.entity;

import static com.dianping.cat.consumer.health.model.Constants.ATTR_DOMAIN;
import static com.dianping.cat.consumer.health.model.Constants.ENTITY_HEALTH_REPORT;

import java.util.LinkedHashSet;
import java.util.Set;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class HealthReport extends BaseEntity<HealthReport> {
   private String m_domain;

   private java.util.Date m_startTime;

   private java.util.Date m_endTime;

   private Set<String> m_domainNames = new LinkedHashSet<String>();

   private ProblemInfo m_problemInfo;

   private Url m_url;

   private Service m_service;

   private Call m_call;

   private Sql m_sql;

   private WebCache m_webCache;

   private KvdbCache m_kvdbCache;

   private MemCache m_memCache;

   private ClientService m_clientService;

   private MachineInfo m_machineInfo;

   public HealthReport() {
   }

   public HealthReport(String domain) {
      m_domain = domain;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitHealthReport(this);
   }

   public HealthReport addDomain(String domain) {
      m_domainNames.add(domain);
      return this;
   }

   public Call getCall() {
      return m_call;
   }

   public ClientService getClientService() {
      return m_clientService;
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

   public KvdbCache getKvdbCache() {
      return m_kvdbCache;
   }

   public MachineInfo getMachineInfo() {
      return m_machineInfo;
   }

   public MemCache getMemCache() {
      return m_memCache;
   }

   public ProblemInfo getProblemInfo() {
      return m_problemInfo;
   }

   public Service getService() {
      return m_service;
   }

   public Sql getSql() {
      return m_sql;
   }

   public java.util.Date getStartTime() {
      return m_startTime;
   }

   public Url getUrl() {
      return m_url;
   }

   public WebCache getWebCache() {
      return m_webCache;
   }

   @Override
   public void mergeAttributes(HealthReport other) {
      assertAttributeEquals(other, ENTITY_HEALTH_REPORT, ATTR_DOMAIN, m_domain, other.getDomain());

      if (other.getStartTime() != null) {
         m_startTime = other.getStartTime();
      }

      if (other.getEndTime() != null) {
         m_endTime = other.getEndTime();
      }
   }

   public HealthReport setCall(Call call) {
      m_call = call;
      return this;
   }

   public HealthReport setClientService(ClientService clientService) {
      m_clientService = clientService;
      return this;
   }

   public HealthReport setDomain(String domain) {
      m_domain = domain;
      return this;
   }

   public HealthReport setEndTime(java.util.Date endTime) {
      m_endTime = endTime;
      return this;
   }

   public HealthReport setKvdbCache(KvdbCache kvdbCache) {
      m_kvdbCache = kvdbCache;
      return this;
   }

   public HealthReport setMachineInfo(MachineInfo machineInfo) {
      m_machineInfo = machineInfo;
      return this;
   }

   public HealthReport setMemCache(MemCache memCache) {
      m_memCache = memCache;
      return this;
   }

   public HealthReport setProblemInfo(ProblemInfo problemInfo) {
      m_problemInfo = problemInfo;
      return this;
   }

   public HealthReport setService(Service service) {
      m_service = service;
      return this;
   }

   public HealthReport setSql(Sql sql) {
      m_sql = sql;
      return this;
   }

   public HealthReport setStartTime(java.util.Date startTime) {
      m_startTime = startTime;
      return this;
   }

   public HealthReport setUrl(Url url) {
      m_url = url;
      return this;
   }

   public HealthReport setWebCache(WebCache webCache) {
      m_webCache = webCache;
      return this;
   }

}
