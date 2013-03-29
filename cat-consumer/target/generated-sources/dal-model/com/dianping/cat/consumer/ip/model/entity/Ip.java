package com.dianping.cat.consumer.ip.model.entity;

import static com.dianping.cat.consumer.ip.model.Constants.ATTR_ADDRESS;
import static com.dianping.cat.consumer.ip.model.Constants.ENTITY_IP;

import com.dianping.cat.consumer.ip.model.BaseEntity;
import com.dianping.cat.consumer.ip.model.IVisitor;

public class Ip extends BaseEntity<Ip> {
   private String m_address;

   private int m_count;

   public Ip() {
   }

   public Ip(String address) {
      m_address = address;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitIp(this);
   }

   public String getAddress() {
      return m_address;
   }

   public int getCount() {
      return m_count;
   }

   public Ip incCount() {
      m_count++;
      return this;
   }

   @Override
   public void mergeAttributes(Ip other) {
      assertAttributeEquals(other, ENTITY_IP, ATTR_ADDRESS, m_address, other.getAddress());

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }
   }

   public Ip setAddress(String address) {
      m_address = address;
      return this;
   }

   public Ip setCount(int count) {
      m_count = count;
      return this;
   }

}
