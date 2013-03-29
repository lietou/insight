package com.dianping.cat.home.template.entity;

import static com.dianping.cat.home.template.Constants.ATTR_TYPE;
import static com.dianping.cat.home.template.Constants.ENTITY_PARAM;

import com.dianping.cat.home.template.BaseEntity;
import com.dianping.cat.home.template.IVisitor;

public class Param extends BaseEntity<Param> {
   private String m_type;

   private String m_value;

   public Param() {
   }

   public Param(String type) {
      m_type = type;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitParam(this);
   }

   public String getType() {
      return m_type;
   }

   public String getValue() {
      return m_value;
   }

   @Override
   public void mergeAttributes(Param other) {
      assertAttributeEquals(other, ENTITY_PARAM, ATTR_TYPE, m_type, other.getType());

      if (other.getValue() != null) {
         m_value = other.getValue();
      }
   }

   public Param setType(String type) {
      m_type = type;
      return this;
   }

   public Param setValue(String value) {
      m_value = value;
      return this;
   }

}
