package com.dianping.cat.consumer.matrix.model.transform;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dianping.cat.consumer.matrix.model.IVisitor;
import com.dianping.cat.consumer.matrix.model.entity.Matrix;
import com.dianping.cat.consumer.matrix.model.entity.MatrixReport;
import com.dianping.cat.consumer.matrix.model.entity.Ratio;

public class DefaultNativeParser implements IVisitor {

   private DefaultLinker m_linker = new DefaultLinker(true);

   private DataInputStream m_in;

   public DefaultNativeParser(InputStream in) {
      m_in = new DataInputStream(in);
   }

   public static MatrixReport parse(byte[] data) {
      return parse(new ByteArrayInputStream(data));
   }

   public static MatrixReport parse(InputStream in) {
      DefaultNativeParser parser = new DefaultNativeParser(in);
      MatrixReport matrixReport = new MatrixReport();

      try {
         matrixReport.accept(parser);
      } catch (RuntimeException e) {
         if (e.getCause() !=null && e.getCause() instanceof java.io.EOFException) {
            // ignore it
         } else {
            throw e;
         }
      }
      
      parser.m_linker.finish();
      return matrixReport;
   }

   @Override
   public void visitMatrix(Matrix matrix) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitMatrixChildren(matrix, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMatrixChildren(Matrix matrix, int _field, int _type) {
      switch (_field) {
         case 1:
            matrix.setType(readString());
            break;
         case 2:
            matrix.setName(readString());
            break;
         case 3:
            matrix.setCount(readInt());
            break;
         case 4:
            matrix.setTotalTime(readLong());
            break;
         case 5:
            matrix.setUrl(readString());
            break;
         case 33:
            if (_type == 1) {
              Ratio ratios = new Ratio();

              visitRatio(ratios);
              m_linker.onRatio(matrix, ratios);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Ratio ratios = new Ratio();

                 visitRatio(ratios);
                 m_linker.onRatio(matrix, ratios);
               }
            }
            break;
      }
   }

   @Override
   public void visitMatrixReport(MatrixReport matrixReport) {
      byte tag;

      if ((tag = readTag()) != -4) {
         throw new RuntimeException(String.format("Malformed payload, expected: %s, but was: %s!", -4, tag));
      }

      while ((tag = readTag()) != -1) {
         visitMatrixReportChildren(matrixReport, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitMatrixReportChildren(MatrixReport matrixReport, int _field, int _type) {
      switch (_field) {
         case 1:
            matrixReport.setDomain(readString());
            break;
         case 2:
            matrixReport.setStartTime(readDate());
            break;
         case 3:
            matrixReport.setEndTime(readDate());
            break;
         case 4:
            if (_type == 1) {
                  matrixReport.addDomain(readString());
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                  matrixReport.addDomain(readString());
               }
            }
            break;
         case 33:
            if (_type == 1) {
              Matrix matrixs = new Matrix();

              visitMatrix(matrixs);
              m_linker.onMatrix(matrixReport, matrixs);
            } else if (_type == 2) {
               for (int i = readInt(); i > 0; i--) {
                 Matrix matrixs = new Matrix();

                 visitMatrix(matrixs);
                 m_linker.onMatrix(matrixReport, matrixs);
               }
            }
            break;
      }
   }

   @Override
   public void visitRatio(Ratio ratio) {
      byte tag;

      while ((tag = readTag()) != -1) {
         visitRatioChildren(ratio, (tag & 0xFF) >> 2, tag & 0x3);
      }
   }

   protected void visitRatioChildren(Ratio ratio, int _field, int _type) {
      switch (_field) {
         case 1:
            ratio.setType(readString());
            break;
         case 2:
            ratio.setMin(readInt());
            break;
         case 3:
            ratio.setMax(readInt());
            break;
         case 4:
            ratio.setTotalCount(readInt());
            break;
         case 5:
            ratio.setTotalTime(readLong());
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
