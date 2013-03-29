package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class Url extends BaseEntity<Url> {
   private BaseInfo m_baseInfo;

   public Url() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitUrl(this);
   }

   public BaseInfo getBaseInfo() {
      return m_baseInfo;
   }

   @Override
   public void mergeAttributes(Url other) {
   }

   public Url setBaseInfo(BaseInfo baseInfo) {
      m_baseInfo = baseInfo;
      return this;
   }

}
