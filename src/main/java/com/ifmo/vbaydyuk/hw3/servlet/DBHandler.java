package com.ifmo.vbaydyuk.hw3.servlet;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author volhovm
 */
@FunctionalInterface
public interface DBHandler<T> {
    void apply(T t) throws SQLException, IOException;
}
