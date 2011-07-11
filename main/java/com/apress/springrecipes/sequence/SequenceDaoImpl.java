package com.apress.springrecipes.sequence;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 1/20/11
 * Time: 3:19 PM
 */
@Repository("sequenceDao")
public class SequenceDaoImpl implements SequenceDao {

    private Map<String,Sequence> sequences;
    private Map<String,Integer>  values;

    public SequenceDaoImpl() {
        sequences = new HashMap<String,Sequence>();
        sequences.put("IT", new Sequence("IT", "30", "A"));
        values = Collections.synchronizedMap(new HashMap<String,Integer>());
        values.put("IT", 100000);
    }

    public Sequence getSequence(String sequenceId) {
        return sequences.get(sequenceId);
    }

    public synchronized int getNextValue(String sequenceId) {
        int value = values.get(sequenceId);
        values.put(sequenceId, value + 1);
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + sequences.hashCode();
        hash = hash * 31 + values.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SequenceDaoImpl that = (SequenceDaoImpl) o;

        if (sequences != null ? !sequences.equals(that.sequences) : that.sequences != null) return false;
        if (values != null ? !values.equals(that.values) : that.values != null) return false;

        return true;
    }
}
