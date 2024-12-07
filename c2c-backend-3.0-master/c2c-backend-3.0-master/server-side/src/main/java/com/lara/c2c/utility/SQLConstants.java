package com.lara.c2c.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants for SQL building for easing the String pool load
 *
 * @author ritesh kumar
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SQLConstants {

    // SQL query constants

    public static final String SELECT = " SELECT ";
    public static final String DELETE = " DELETE ";
    public static final String UPDATE = " UPDATE ";
    public static final String SET = " SET ";
    public static final String INSERT = " INSERT ";
    public static final String UNION_ALL = " UNION ALL ";

    public static final String FROM = " FROM ";
    public static final String WHERE = " WHERE ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String GROUP_BY = " GROUP BY ";
    public static final String UNION = " UNION ";

    public static final String AND = " AND ";
    public static final String OR = " OR ";
    public static final String AS = " AS ";

    public static final String COMMA = " , ";

    // SQL param constants
    public static final String QUERY_PARAM_USER_ID = "userId";
}