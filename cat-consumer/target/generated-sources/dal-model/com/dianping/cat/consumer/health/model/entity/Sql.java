package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class Sql extends BaseEntity<Sql> {
   private BaseInfo m_baseInfo;

   public Sql() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSql(this);
   }

   public BaseInfo getBaseInfo() {
      return m_baseInfo;
   }

   @Override
   public void mergeAttributes(Sql other) {
   }

   public Sql setBaseInfo(BaseInfo baseInfo) {
      m_baseInfo = baseInfo;
      return this;
   }

}
