package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class BaseInfo extends BaseEntity<BaseInfo> {
   private long m_total;

   private double m_responseTime;

   private long m_errorTotal;

   private double m_errorPercent;

   private double m_successPercent;

   private double m_tps;

   public BaseInfo() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitBaseInfo(this);
   }

   public double getErrorPercent() {
      return m_errorPercent;
   }

   public long getErrorTotal() {
      return m_errorTotal;
   }

   public double getResponseTime() {
      return m_responseTime;
   }

   public double getSuccessPercent() {
      return m_successPercent;
   }

   public long getTotal() {
      return m_total;
   }

   public double getTps() {
      return m_tps;
   }

   @Override
   public void mergeAttributes(BaseInfo other) {
      if (other.getTotal() != 0) {
         m_total = other.getTotal();
      }

      if (other.getResponseTime() - 1e6 < 0) {
         m_responseTime = other.getResponseTime();
      }

      if (other.getErrorTotal() != 0) {
         m_errorTotal = other.getErrorTotal();
      }

      if (other.getErrorPercent() - 1e6 < 0) {
         m_errorPercent = other.getErrorPercent();
      }

      if (other.getSuccessPercent() - 1e6 < 0) {
         m_successPercent = other.getSuccessPercent();
      }

      if (other.getTps() - 1e6 < 0) {
         m_tps = other.getTps();
      }
   }

   public BaseInfo setErrorPercent(double errorPercent) {
      m_errorPercent = errorPercent;
      return this;
   }

   public BaseInfo setErrorTotal(long errorTotal) {
      m_errorTotal = errorTotal;
      return this;
   }

   public BaseInfo setResponseTime(double responseTime) {
      m_responseTime = responseTime;
      return this;
   }

   public BaseInfo setSuccessPercent(double successPercent) {
      m_successPercent = successPercent;
      return this;
   }

   public BaseInfo setTotal(long total) {
      m_total = total;
      return this;
   }

   public BaseInfo setTps(double tps) {
      m_tps = tps;
      return this;
   }

}
