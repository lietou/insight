package com.dianping.cat.consumer.top.model.entity;

import static com.dianping.cat.consumer.top.model.Constants.ATTR_NAME;
import static com.dianping.cat.consumer.top.model.Constants.ENTITY_DOMAIN;

import java.util.LinkedHashMap;
import java.util.Map;

import com.dianping.cat.consumer.top.model.BaseEntity;
import com.dianping.cat.consumer.top.model.IVisitor;

public class Domain extends BaseEntity<Domain> {
   private String m_name;

   private Map<Integer, Segment> m_segments = new LinkedHashMap<Integer, Segment>();

   public Domain() {
   }

   public Domain(String name) {
      m_name = name;
   }

   @Override
   public void accept(IVisitor visitor) {
      visitor.visitDomain(this);
   }

   public Domain addSegment(Segment segment) {
      m_segments.put(segment.getId(), segment);
      return this;
   }

   public Segment findSegment(Integer id) {
      return m_segments.get(id);
   }

   public Segment findOrCreateSegment(Integer id) {
      Segment segment = m_segments.get(id);

      if (segment == null) {
         synchronized (m_segments) {
            segment = m_segments.get(id);

            if (segment == null) {
               segment = new Segment(id);
               m_segments.put(id, segment);
            }
         }
      }

      return segment;
   }

   public String getName() {
      return m_name;
   }

   public Map<Integer, Segment> getSegments() {
      return m_segments;
   }

   @Override
   public void mergeAttributes(Domain other) {
      assertAttributeEquals(other, ENTITY_DOMAIN, ATTR_NAME, m_name, other.getName());

   }

   public boolean removeSegment(Integer id) {
      if (m_segments.containsKey(id)) {
         m_segments.remove(id);
         return true;
      }

      return false;
   }

   public Domain setName(String name) {
      m_name = name;
      return this;
   }

}
