package org.oslo.aspects.introduction;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: May 23, 2003
 * Time: 12:10:04 AM
 * To change this template use Options | File Templates.
 */
public class RantProcessIDIntroductionImpl implements RantProcessIDIntroduction, Serializable {
    private String processID = null;
    private long sequenceID;

    public void setRantProcessID(String processID) {
        this.processID = processID;
    }

    public String getRantProcessID() {
        return processID;
    }

    public void setRantSequenceID(long sequenceID) {
        this.sequenceID = sequenceID;
    }

    public long getRantSequenceID() {
        return sequenceID;
    }
}
