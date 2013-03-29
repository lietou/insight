package com.dianping.cat.consumer.top.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.top.model.IVisitor;
import com.dianping.cat.consumer.top.model.entity.Domain;
import com.dianping.cat.consumer.top.model.entity.Segment;
import com.dianping.cat.consumer.top.model.entity.TopReport;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static TopReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static TopReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      TopReport topReport = new TopReport();

      try {
         topReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return topReport;
   }

   @Override
   public void visitDomain(Domain domain) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitDomainChildren(domain, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitDomainChildren(Domain domain, int _field, int _type) {
      switch (_field) {
         case 1:
            domain.setName(readString());
            break;
         case 33:
            if (_type == 1) {
              Segment segments = new Segment();

              visitSegment(segments);
              m_linker.onSegment(domain, segments);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Segment segments = new Segment();

                 visitSegment(segments);
                 m_linker.onSegment(domain, segments);
               }
            }
            break;
      }
   }

   @Override
   public void visitSegment(Segment segment) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitSegmentChildren(segment, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitSegmentChildren(Segment segment, int _field, int _type) {
      switch (_field) {
         case 1:
            segment.setId(readInt());
            break;
         case 2:
            segment.setError(readLong());
            break;
         case 3:
            segment.setUrl(readLong());
            break;
         case 4:
            segment.setUrlDuration(readDouble());
            break;
         case 5:
            segment.setService(readLong());
            break;
         case 6:
            segment.setServiceDuration(readDouble());
            break;
         case 7:
            segment.setSql(readLong());
            break;
         case 8:
            segment.setSqlDuration(readDouble());
            break;
         case 9:
            segment.setCall(readLong());
            break;
         case 10:
            segment.setCallDuration(readDouble());
            break;
         case 11:
            segment.setCache(readLong());
            break;
         case 12:
            segment.setCacheDuration(readDouble());
            break;
         case 13:
            segment.setCallError(readLong());
            break;
         case 14:
            segment.setUrlSum(readDouble());
            break;
         case 15:
            segment.setServiceSum(readDouble());
            break;
         case 16:
            segment.setSqlSum(readDouble());
            break;
         case 17:
            segment.setCallSum(readDouble());
            break;
         case 18:
            segment.setCacheSum(readDouble());
            break;
      }
   }

   @Override
   public void visitTopReport(TopReport topReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitTopReportChildren(topReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitTopReportChildren(TopReport topReport, int _field, int _type) {
      switch (_field) {
         case 1:
            topReport.setDomain(readString());
            break;
         case 2:
            topReport.setStartTime(readDate());
            break;
         case 3:
            topReport.setEndTime(readDate());
            break;
         case 33:
            if (_type == 1) {
              Domain domains = new Domain();

              visitDomain(domains);
              m_linker.onDomain(topReport, domains);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Domain domains = new Domain();

                 visitDomain(domains);
                 m_linker.onDomain(topReport, domains);
               }
            }
            break;
      }
   }

   private java.util.Date readDate() {
      try {
         return new java.util.Date(readVarint(64));
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private double readDouble() {
      try {
         return m_in.readDouble();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private int readInt() {
      try {
         return (int) readVarint(32);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private long readLong() {
      try {
         return readVarint(64);
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private String readString() {
      try {
         return m_in.readUTF();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   private byte readTag() {
      try {
         return m_in.readByte();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   protected long readVarint(final int length) throws IOException {
      int shift = 0;
      long result = 0;

      while (shift < length) {
         final byte b = m_in.readByte();
         result |= (long) (b & 0x7F) << shift;
         if ((b & 0x80) == 0) {
            return result;
         }
         shift += 7;
      }

      throw new RuntimeException("Malformed variable int " + length + "!");
   }
}
