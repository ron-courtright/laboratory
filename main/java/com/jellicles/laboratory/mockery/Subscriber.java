package com.jellicles.laboratory.mockery;

import java.util.Collection;

public interface Subscriber {
    public void recieve(String string);

    public void recieve(Collection<?> collection);
}
