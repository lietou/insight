package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class ProblemInfo extends BaseEntity<ProblemInfo> {
   private long m_exceptions;

   private long m_longSqls;

   private double m_longSqlPercent;

   private long m_longUrls;

   private double m_longUrlPercent;

   private long m_longServices;

   private double m_longServicePercent;

   private long m_longCaches;

   private double m_longCachePercent;

   public ProblemInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitProblemInfo(this);
   }

   public long getExceptions() {
      return m_exceptions;
   }

   public double getLongCachePercent() {
      return m_longCachePercent;
   }

   public long getLongCaches() {
      return m_longCaches;
   }

   public double getLongServicePercent() {
      return m_longServicePercent;
   }

   public long getLongServices() {
      return m_longServices;
   }

   public double getLongSqlPercent() {
      return m_longSqlPercent;
   }

   public long getLongSqls() {
      return m_longSqls;
   }

   public double getLongUrlPercent() {
      return m_longUrlPercent;
   }

   public long getLongUrls() {
      return m_longUrls;
   }

   @Override
   public void mergeAttributes(ProblemInfo other) {
      if (other.getExceptions() != 0) {
         m_exceptions = other.getExceptions();
      }

      if (other.getLongSqls() != 0) {
         m_longSqls = other.getLongSqls();
      }

      if (other.getLongSqlPercent() - 1e6 < 0) {
         m_longSqlPercent = other.getLongSqlPercent();
      }

      if (other.getLongUrls() != 0) {
         m_longUrls = other.getLongUrls();
      }

      if (other.getLongUrlPercent() - 1e6 < 0) {
         m_longUrlPercent = other.getLongUrlPercent();
      }

      if (other.getLongServices() != 0) {
         m_longServices = other.getLongServices();
      }

      if (other.getLongServicePercent() - 1e6 < 0) {
         m_longServicePercent = other.getLongServicePercent();
      }

      if (other.getLongCaches() != 0) {
         m_longCaches = other.getLongCaches();
      }

      if (other.getLongCachePercent() - 1e6 < 0) {
         m_longCachePercent = other.getLongCachePercent();
      }
   }

   public ProblemInfo setExceptions(long exceptions) {
      m_exceptions = exceptions;
      return this;
   }

   public ProblemInfo setLongCachePercent(double longCachePercent) {
      m_longCachePercent = longCachePercent;
      return this;
   }

   public ProblemInfo setLongCaches(long longCaches) {
      m_longCaches = longCaches;
      return this;
   }

   public ProblemInfo setLongServicePercent(double longServicePercent) {
      m_longServicePercent = longServicePercent;
      return this;
   }

   public ProblemInfo setLongServices(long longServices) {
      m_longServices = longServices;
      return this;
   }

   public ProblemInfo setLongSqlPercent(double longSqlPercent) {
      m_longSqlPercent = longSqlPercent;
      return this;
   }

   public ProblemInfo setLongSqls(long longSqls) {
      m_longSqls = longSqls;
      return this;
   }

   public ProblemInfo setLongUrlPercent(double longUrlPercent) {
      m_longUrlPercent = longUrlPercent;
      return this;
   }

   public ProblemInfo setLongUrls(long longUrls) {
      m_longUrls = longUrls;
      return this;
   }

}
