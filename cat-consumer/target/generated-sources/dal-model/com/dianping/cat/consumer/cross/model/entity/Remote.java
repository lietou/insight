package com.dianping.cat.consumer.cross.model.entity;

import static com.dianping.cat.consumer.cross.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.cross.model.Constants.ENTITY_REMOTE;

import com.dianping.cat.consumer.cross.model.BaseEntity;
import com.dianping.cat.consumer.cross.model.IVisitor;

public class Remote extends BaseEntity<Remote> {
   private String m_id;

   private String m_role;

   private Type m_type;

   public Remote() {
   }

   public Remote(String id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitRemote(this);
   }

   public String getId() {
      return m_id;
   }

   public String getRole() {
      return m_role;
   }

   public Type getType() {
      return m_type;
   }

   @Override
   public void mergeAttributes(Remote other) {
      assertAttributeEquals(other, ENTITY_REMOTE, ATTR_ID, m_id, other.getId());

      if (other.getRole() != null) {
         m_role = other.getRole();
      }
   }

   public Remote setId(String id) {
      m_id = id;
      return this;
   }

   public Remote setRole(String role) {
      m_role = role;
      return this;
   }

   public Remote setType(Type type) {
      m_type = type;
      return this;
   }

}
