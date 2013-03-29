package com.dianping.cat.consumer.health.model.entity;

import com.dianping.cat.consumer.health.model.BaseEntity;
import com.dianping.cat.consumer.health.model.IVisitor;

public class ClientService extends BaseEntity<ClientService> {
   private BaseInfo m_baseInfo;

   public ClientService() {
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitClientService(this);
   }

   public BaseInfo getBaseInfo() {
      return m_baseInfo;
   }

   @Override
   public void mergeAttributes(ClientService other) {
   }

   public ClientService setBaseInfo(BaseInfo baseInfo) {
      m_baseInfo = baseInfo;
      return this;
   }

}
