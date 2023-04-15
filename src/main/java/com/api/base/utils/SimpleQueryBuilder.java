package com.api.base.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;

public class SimpleQueryBuilder {
    private List<String> columns = new ArrayList<>();
    private List<String> tables = new ArrayList<>();
    private List<String> joins = new ArrayList<>();
    private List<String> wheres = new ArrayList<>();
    private List<String> orderBys = new ArrayList<>();
    private List<String> groupBys = new ArrayList<>();
    private String selectStatement;
    private boolean isDistinct = false;
    private int limit = -1;
    private int offset = -1;

    public SimpleQueryBuilder() {
    }

    public SimpleQueryBuilder(String selectStatement) {
        this.selectStatement = selectStatement;
    }

    private void appendQuery(StringBuilder sql, List<String> listElement, String firstElement, String separator) {
        boolean first = true;
        for (String element : listElement) {
            if (first) {
                sql.append(firstElement);
            } else {
                sql.append(separator);
            }
            sql.append(element);
            first = false;
        }
    }

    public SimpleQueryBuilder addColumn(String name) {
        columns.add(name);
        return this;
    }

    public SimpleQueryBuilder addColumn(String name, boolean groupBy) {
        columns.add(name);
        if (groupBy) {
            groupBys.add(name);
        }
        return this;
    }

    public SimpleQueryBuilder from(String table) {
        tables.add(table);
        return this;
    }

    public SimpleQueryBuilder where(String expr) {
        wheres.add(expr);
        return this;
    }

    public SimpleQueryBuilder groupBy(String expr) {
        groupBys.add(expr);
        return this;
    }

    public SimpleQueryBuilder joinExp(String join) {
        joins.add(join);
        return this;
    }

    public SimpleQueryBuilder orderBy(String name, Direction direction) {
        orderBys.add(name + StringUtils.SPACE + direction.name());
        return this;
    }

    public SimpleQueryBuilder orderBy(String name, boolean isASC, boolean isNullFirst) {
        orderBys.add(
                name + StringUtils.SPACE + (isASC ? "ASC" : "DESC") + (isNullFirst ? " NULLS FIRST " : " NULLS LAST "));
        return this;
    }

    public SimpleQueryBuilder limit(int max) {
        limit = max;
        return this;
    }

    public SimpleQueryBuilder offset(int max) {
        offset = max;
        return this;
    }

    public String build() {
        StringBuilder sql = new StringBuilder();
        if (Objects.nonNull(this.selectStatement)) {
            sql.append(this.selectStatement);
        } else {
            sql = new StringBuilder("SELECT ");
            if (isDistinct) {
                sql.append("DISTINCT ");
            }
            if (columns.isEmpty()) {
                sql.append("*");
            } else {
                appendQuery(sql, columns, "", ", ");
            }
            appendQuery(sql, tables, " FROM ", ", ");
        }

        appendQuery(sql, joins, " ", " ");
        appendQuery(sql, wheres, " WHERE ", " AND ");
        appendQuery(sql, groupBys, " GROUP BY ", ", ");
        appendQuery(sql, orderBys, " ORDER BY ", ", ");

        if (CollectionUtils.isNotEmpty(orderBys)) {
            String query = sql.toString();
            query += " OFFSET " + offset + " ROWS";
            query += " FETCH NEXT " + limit + " ROWS ONLY";
            return query;
        }
        return sql.toString();
    }

    public String buildCount() {
        StringBuilder sql = new StringBuilder();
        if (Objects.nonNull(this.selectStatement)) {
            sql.append(this.selectStatement);
        } else {
            sql = new StringBuilder("SELECT ");
                sql.append("COUNT(1)");
            appendQuery(sql, tables, " FROM ", ", ");
        }

        appendQuery(sql, joins, " ", " ");
        appendQuery(sql, wheres, " WHERE ", " AND ");
        appendQuery(sql, groupBys, " GROUP BY ", ", ");
        return sql.toString();
    }

    public boolean getIsDistinct() {
        return isDistinct;
    }

    public void setIsDistinct(boolean distinct) {
        isDistinct = distinct;
    }
}
