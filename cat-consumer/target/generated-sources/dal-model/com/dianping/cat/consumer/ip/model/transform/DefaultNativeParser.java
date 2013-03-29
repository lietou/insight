package com.dianping.cat.consumer.ip.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.ip.model.IVisitor;
import com.dianping.cat.consumer.ip.model.entity.Ip;
import com.dianping.cat.consumer.ip.model.entity.IpReport;
import com.dianping.cat.consumer.ip.model.entity.Period;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static IpReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static IpReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      IpReport ipReport = new IpReport();

      try {
         ipReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return ipReport;
   }

   @Override
   public void visitIp(Ip ip) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitIpChildren(ip, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitIpChildren(Ip ip, int _field, int _type) {
      switch (_field) {
         case 1:
            ip.setAddress(readString());
            break;
         case 2:
            ip.setCount(readInt());
            break;
      }
   }

   @Override
   public void visitIpReport(IpReport ipReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitIpReportChildren(ipReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitIpReportChildren(IpReport ipReport, int _field, int _type) {
      switch (_field) {
         case 1:
            ipReport.setDomain(readString());
            break;
         case 2:
            ipReport.setStartTime(readDate());
            break;
         case 3:
            ipReport.setEndTime(readDate());
            break;
         case 4:
            if (_type == 1) {
                  ipReport.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  ipReport.addDomain(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) {
              Period periods = new Period();

              visitPeriod(periods);
              m_linker.onPeriod(ipReport, periods);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Period periods = new Period();

                 visitPeriod(periods);
                 m_linker.onPeriod(ipReport, periods);
               }
            }
            break;
      }
   }

   @Override
   public void visitPeriod(Period period) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitPeriodChildren(period, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitPeriodChildren(Period period, int _field, int _type) {
      switch (_field) {
         case 1:
            period.setMinute(readInt());
            break;
         case 33:
            if (_type == 1) {
              Ip ips = new Ip();

              visitIp(ips);
              m_linker.onIp(period, ips);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Ip ips = new Ip();

                 visitIp(ips);
                 m_linker.onIp(period, ips);
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

   private int readInt() {
      try {
         return (int) readVarint(32);
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
