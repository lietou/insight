package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class Call extends BaseEntity<Call> {
   private BaseInfo m_baseInfo;

   public Call() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitCall(this);
   }

   public BaseInfo getBaseInfo() {
      return m_baseInfo;
   }

   @Override
   public void mergeAttributes(Call other) {
   }

   public Call setBaseInfo(BaseInfo baseInfo) {
      m_baseInfo = baseInfo;
      return this;
   }

}
