package com.apress.springrecipes.sequence;

/**
 * Date: 1/20/11
 * Time: 3:18 PM
 */
public interface SequenceDao {

    public Sequence getSequence(String sequenceId);
    public int      getNextValue(String sequenceId);
    
}
