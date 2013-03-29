package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class BaseCacheInfo extends BaseEntity<BaseCacheInfo> {
   private long m_total;

   private double m_responseTime;

   private double m_hitPercent;

   public BaseCacheInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitBaseCacheInfo(this);
   }

   public double getHitPercent() {
      return m_hitPercent;
   }

   public double getResponseTime() {
      return m_responseTime;
   }

   public long getTotal() {
      return m_total;
   }

   @Override
   public void mergeAttributes(BaseCacheInfo other) {
      if (other.getTotal() != 0) {
         m_total = other.getTotal();
      }

      if (other.getResponseTime() - 1e6 < 0) {
         m_responseTime = other.getResponseTime();
      }

      if (other.getHitPercent() - 1e6 < 0) {
         m_hitPercent = other.getHitPercent();
      }
   }

   public BaseCacheInfo setHitPercent(double hitPercent) {
      m_hitPercent = hitPercent;
      return this;
   }

   public BaseCacheInfo setResponseTime(double responseTime) {
      m_responseTime = responseTime;
      return this;
   }

   public BaseCacheInfo setTotal(long total) {
      m_total = total;
      return this;
   }

}
