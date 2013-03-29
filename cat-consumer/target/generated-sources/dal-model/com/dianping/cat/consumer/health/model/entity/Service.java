package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class Service extends BaseEntity<Service> {
   private BaseInfo m_baseInfo;

   public Service() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitService(this);
   }

   public BaseInfo getBaseInfo() {
      return m_baseInfo;
   }

   @Override
   public void mergeAttributes(Service other) {
   }

   public Service setBaseInfo(BaseInfo baseInfo) {
      m_baseInfo = baseInfo;
      return this;
   }

}
