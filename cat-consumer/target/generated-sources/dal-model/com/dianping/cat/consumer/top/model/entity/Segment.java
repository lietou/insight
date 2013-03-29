package com.dianping.cat.consumer.top.model.entity;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_SEGMENT;

import com.dianping.cat.consumer.top.model.BaseEntity;
import com.dianping.cat.consumer.top.model.IVisitor;

public class Segment extends BaseEntity<Segment> {
   private Integer m_id;

   private long m_error;

   private long m_url;

   private double m_urlDuration;

   private long m_service;

   private double m_serviceDuration;

   private long m_sql;

   private double m_sqlDuration;

   private long m_call;

   private double m_callDuration;

   private long m_cache;

   private double m_cacheDuration;

   private long m_callError;

   private double m_urlSum;

   private double m_serviceSum;

   private double m_sqlSum;

   private double m_callSum;

   private double m_cacheSum;

   public Segment() {
   }

   public Segment(Integer id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSegment(this);
   }

   public long getCache() {
      return m_cache;
   }

   public double getCacheDuration() {
      return m_cacheDuration;
   }

   public double getCacheSum() {
      return m_cacheSum;
   }

   public long getCall() {
      return m_call;
   }

   public double getCallDuration() {
      return m_callDuration;
   }

   public long getCallError() {
      return m_callError;
   }

   public double getCallSum() {
      return m_callSum;
   }

   public long getError() {
      return m_error;
   }

   public Integer getId() {
      return m_id;
   }

   public long getService() {
      return m_service;
   }

   public double getServiceDuration() {
      return m_serviceDuration;
   }

   public double getServiceSum() {
      return m_serviceSum;
   }

   public long getSql() {
      return m_sql;
   }

   public double getSqlDuration() {
      return m_sqlDuration;
   }

   public double getSqlSum() {
      return m_sqlSum;
   }

   public long getUrl() {
      return m_url;
   }

   public double getUrlDuration() {
      return m_urlDuration;
   }

   public double getUrlSum() {
      return m_urlSum;
   }

   @Override
   public void mergeAttributes(Segment other) {
      assertAttributeEquals(other, ENTITY_SEGMENT, ATTR_ID, m_id, other.getId());

      if (other.getError() != 0) {
         m_error = other.getError();
      }

      if (other.getUrl() != 0) {
         m_url = other.getUrl();
      }

      if (other.getUrlDuration() - 1e6 < 0) {
         m_urlDuration = other.getUrlDuration();
      }

      if (other.getService() != 0) {
         m_service = other.getService();
      }

      if (other.getServiceDuration() - 1e6 < 0) {
         m_serviceDuration = other.getServiceDuration();
      }

      if (other.getSql() != 0) {
         m_sql = other.getSql();
      }

      if (other.getSqlDuration() - 1e6 < 0) {
         m_sqlDuration = other.getSqlDuration();
      }

      if (other.getCall() != 0) {
         m_call = other.getCall();
      }

      if (other.getCallDuration() - 1e6 < 0) {
         m_callDuration = other.getCallDuration();
      }

      if (other.getCache() != 0) {
         m_cache = other.getCache();
      }

      if (other.getCacheDuration() - 1e6 < 0) {
         m_cacheDuration = other.getCacheDuration();
      }

      if (other.getCallError() != 0) {
         m_callError = other.getCallError();
      }

      if (other.getUrlSum() - 1e6 < 0) {
         m_urlSum = other.getUrlSum();
      }

      if (other.getServiceSum() - 1e6 < 0) {
         m_serviceSum = other.getServiceSum();
      }

      if (other.getSqlSum() - 1e6 < 0) {
         m_sqlSum = other.getSqlSum();
      }

      if (other.getCallSum() - 1e6 < 0) {
         m_callSum = other.getCallSum();
      }

      if (other.getCacheSum() - 1e6 < 0) {
         m_cacheSum = other.getCacheSum();
      }
   }

   public Segment setCache(long cache) {
      m_cache = cache;
      return this;
   }

   public Segment setCacheDuration(double cacheDuration) {
      m_cacheDuration = cacheDuration;
      return this;
   }

   public Segment setCacheSum(double cacheSum) {
      m_cacheSum = cacheSum;
      return this;
   }

   public Segment setCall(long call) {
      m_call = call;
      return this;
   }

   public Segment setCallDuration(double callDuration) {
      m_callDuration = callDuration;
      return this;
   }

   public Segment setCallError(long callError) {
      m_callError = callError;
      return this;
   }

   public Segment setCallSum(double callSum) {
      m_callSum = callSum;
      return this;
   }

   public Segment setError(long error) {
      m_error = error;
      return this;
   }

   public Segment setId(Integer id) {
      m_id = id;
      return this;
   }

   public Segment setService(long service) {
      m_service = service;
      return this;
   }

   public Segment setServiceDuration(double serviceDuration) {
      m_serviceDuration = serviceDuration;
      return this;
   }

   public Segment setServiceSum(double serviceSum) {
      m_serviceSum = serviceSum;
      return this;
   }

   public Segment setSql(long sql) {
      m_sql = sql;
      return this;
   }

   public Segment setSqlDuration(double sqlDuration) {
      m_sqlDuration = sqlDuration;
      return this;
   }

   public Segment setSqlSum(double sqlSum) {
      m_sqlSum = sqlSum;
      return this;
   }

   public Segment setUrl(long url) {
      m_url = url;
      return this;
   }

   public Segment setUrlDuration(double urlDuration) {
      m_urlDuration = urlDuration;
      return this;
   }

   public Segment setUrlSum(double urlSum) {
      m_urlSum = urlSum;
      return this;
   }

}
