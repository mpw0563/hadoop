/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.protocol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.fs.Path;

import com.google.common.base.Objects;
import org.apache.hadoop.hdfs.DFSUtilClient;

/**
<<<<<<< HEAD
 * This class represents to end users the difference between two snapshots of 
=======
 * This class represents to end users the difference between two snapshots of
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
 * the same directory, or the difference between a snapshot of the directory and
 * its current state. Instead of capturing all the details of the diff, this
 * class only lists where the changes happened and their types.
 */
public class SnapshotDiffReport {
  private final static String LINE_SEPARATOR = System.getProperty(
      "line.separator", "\n");

  /**
   * Types of the difference, which include CREATE, MODIFY, DELETE, and RENAME.
   * Each type has a label for representation: +/M/-/R represent CREATE, MODIFY,
   * DELETE, and RENAME respectively.
   */
  public enum DiffType {
<<<<<<< HEAD
    CREATE("+"),     
    MODIFY("M"),    
    DELETE("-"), 
    RENAME("R");
    
    private final String label;
    
    private DiffType(String label) {
      this.label = label;
    }
    
    public String getLabel() {
      return label;
    }
    
=======
    CREATE("+"),
    MODIFY("M"),
    DELETE("-"),
    RENAME("R");

    private final String label;

    DiffType(String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    public static DiffType getTypeFromLabel(String label) {
      if (label.equals(CREATE.getLabel())) {
        return CREATE;
      } else if (label.equals(MODIFY.getLabel())) {
        return MODIFY;
      } else if (label.equals(DELETE.getLabel())) {
        return DELETE;
      } else if (label.equals(RENAME.getLabel())) {
        return RENAME;
      }
      return null;
    }
<<<<<<< HEAD
  };
  
=======
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /**
   * Representing the full path and diff type of a file/directory where changes
   * have happened.
   */
  public static class DiffReportEntry {
    /** The type of the difference. */
    private final DiffType type;
    /**
     * The relative path (related to the snapshot root) of 1) the file/directory
     * where changes have happened, or 2) the source file/dir of a rename op.
     */
    private final byte[] sourcePath;
    private final byte[] targetPath;

    public DiffReportEntry(DiffType type, byte[] sourcePath) {
      this(type, sourcePath, null);
    }

    public DiffReportEntry(DiffType type, byte[][] sourcePathComponents) {
      this(type, sourcePathComponents, null);
    }

    public DiffReportEntry(DiffType type, byte[] sourcePath, byte[] targetPath) {
      this.type = type;
      this.sourcePath = sourcePath;
      this.targetPath = targetPath;
    }
<<<<<<< HEAD
    
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    public DiffReportEntry(DiffType type, byte[][] sourcePathComponents,
        byte[][] targetPathComponents) {
      this.type = type;
      this.sourcePath = DFSUtilClient.byteArray2bytes(sourcePathComponents);
      this.targetPath = targetPathComponents == null ? null : DFSUtilClient
          .byteArray2bytes(targetPathComponents);
    }
<<<<<<< HEAD
    
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    @Override
    public String toString() {
      String str = type.getLabel() + "\t" + getPathString(sourcePath);
      if (type == DiffType.RENAME) {
        str += " -> " + getPathString(targetPath);
      }
      return str;
    }
<<<<<<< HEAD
    
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    public DiffType getType() {
      return type;
    }

    static String getPathString(byte[] path) {
      String pathStr = DFSUtilClient.bytes2String(path);
      if (pathStr.isEmpty()) {
        return Path.CUR_DIR;
      } else {
        return Path.CUR_DIR + Path.SEPARATOR + pathStr;
      }
    }

    public byte[] getSourcePath() {
      return sourcePath;
    }

    public byte[] getTargetPath() {
      return targetPath;
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) {
        return true;
<<<<<<< HEAD
      } 
=======
      }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      if (other != null && other instanceof DiffReportEntry) {
        DiffReportEntry entry = (DiffReportEntry) other;
        return type.equals(entry.getType())
            && Arrays.equals(sourcePath, entry.getSourcePath())
            && Arrays.equals(targetPath, entry.getTargetPath());
      }
      return false;
    }
<<<<<<< HEAD
    
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    @Override
    public int hashCode() {
      return Objects.hashCode(getSourcePath(), getTargetPath());
    }
  }
<<<<<<< HEAD
  
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /** snapshot root full path */
  private final String snapshotRoot;

  /** start point of the diff */
  private final String fromSnapshot;
<<<<<<< HEAD
  
  /** end point of the diff */
  private final String toSnapshot;
  
  /** list of diff */
  private final List<DiffReportEntry> diffList;
  
=======

  /** end point of the diff */
  private final String toSnapshot;

  /** list of diff */
  private final List<DiffReportEntry> diffList;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  public SnapshotDiffReport(String snapshotRoot, String fromSnapshot,
      String toSnapshot, List<DiffReportEntry> entryList) {
    this.snapshotRoot = snapshotRoot;
    this.fromSnapshot = fromSnapshot;
    this.toSnapshot = toSnapshot;
    this.diffList = entryList != null ? entryList : Collections
        .<DiffReportEntry> emptyList();
  }
<<<<<<< HEAD
  
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /** @return {@link #snapshotRoot}*/
  public String getSnapshotRoot() {
    return snapshotRoot;
  }

  /** @return {@link #fromSnapshot} */
  public String getFromSnapshot() {
    return fromSnapshot;
  }

  /** @return {@link #toSnapshot} */
  public String getLaterSnapshotName() {
    return toSnapshot;
  }
<<<<<<< HEAD
  
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /** @return {@link #diffList} */
  public List<DiffReportEntry> getDiffList() {
    return diffList;
  }
<<<<<<< HEAD
  
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    String from = fromSnapshot == null || fromSnapshot.isEmpty() ? 
        "current directory" : "snapshot " + fromSnapshot;
    String to = toSnapshot == null || toSnapshot.isEmpty() ? "current directory"
        : "snapshot " + toSnapshot;
    str.append("Difference between " + from + " and " + to
        + " under directory " + snapshotRoot + ":" + LINE_SEPARATOR);
    for (DiffReportEntry entry : diffList) {
      str.append(entry.toString() + LINE_SEPARATOR);
=======

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    String from = fromSnapshot == null || fromSnapshot.isEmpty() ?
        "current directory" : "snapshot " + fromSnapshot;
    String to = toSnapshot == null || toSnapshot.isEmpty() ? "current directory"
        : "snapshot " + toSnapshot;
    str.append("Difference between ").append(from).append(" and ").append(to)
        .append(" under directory ").append(snapshotRoot).append(":")
        .append(LINE_SEPARATOR);
    for (DiffReportEntry entry : diffList) {
      str.append(entry.toString()).append(LINE_SEPARATOR);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
    return str.toString();
  }
}
