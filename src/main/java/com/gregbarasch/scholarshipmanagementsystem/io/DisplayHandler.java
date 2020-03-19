package com.gregbarasch.scholarshipmanagementsystem.io;

import java.util.Collection;

public interface DisplayHandler<T> {
    void display(Collection<T> items);
    void display(T item);
}
