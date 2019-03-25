package ru.vichukano.crvt_test.service;

import ru.vichukano.crvt_test.domain.Item;

/**
 * Interface for parser classes.
 */
public interface Parser {

    String getPhone(String text);

    String getName(String text);

    String getCompany(String text);

    String getEmail(String text);

    Item convertTextToObject(String text);
}
