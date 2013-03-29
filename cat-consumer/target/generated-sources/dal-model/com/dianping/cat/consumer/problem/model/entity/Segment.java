package com.dianping.cat.consumer.problem.model.entity;

import static com.dianping.cat.consumer.problem.model.Constants.ATTR_ID;
import static com.dianping.cat.consumer.problem.model.Constants.ENTITY_SEGMENT;

import java.util.ArrayList;
import java.util.List;

import com.dianping.cat.consumer.problem.model.BaseEntity;
import com.dianping.cat.consumer.problem.model.IVisitor;

public class Segment extends BaseEntity<Segment> {
   private Integer m_id;

   private int m_count;

   private List<String> m_messages = new ArrayList<String>();

   public Segment() {
   }

   public Segment(Integer id) {
      m_id = id;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitSegment(this);
   }

   public Segment addMessage(String message) {
      m_messages.add(message);
      return this;
   }

   public int getCount() {
      return m_count;
   }

   public Integer getId() {
      return m_id;
   }

   public List<String> getMessages() {
      return m_messages;
   }

   public Segment incCount() {
      m_count++;
      return this;
   }

   @Override
   public void mergeAttributes(Segment other) {
      assertAttributeEquals(other, ENTITY_SEGMENT, ATTR_ID, m_id, other.getId());

      if (other.getCount() != 0) {
         m_count = other.getCount();
      }
   }

   public Segment setCount(int count) {
      m_count = count;
      return this;
   }

   public Segment setId(Integer id) {
      m_id = id;
      return this;
   }

}
