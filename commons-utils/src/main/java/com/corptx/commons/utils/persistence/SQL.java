/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.corptx.commons.utils.persistence;

/**
 *
 * @author William
 */
public class SQL {

    private FromParameter[] from;
    private FilterParameter[] where;
    private OrderBy[] orderBy;
    private String groupBy;

    public SQL() {
    }

    /**
     * @return the from
     */
    public FromParameter[] getFrom() {
        return from;
    }

    /**
     * @param from the from to set
     */
    public void setFrom(FromParameter... from) {
        this.from = from;
    }

    /**
     * @return the where
     */
    public FilterParameter[] getWhere() {
        return where;
    }

    /**
     * @param where the where to set
     */
    public void setWhere(FilterParameter... where) {
        this.where = where;
    }

    /**
     * @return the orderBy
     */
    public OrderBy[] getOrderBy() {
        return orderBy;
    }

    /**
     * @param groupBy the groupBy to set
     */
    public void setOrderBy(OrderBy... orderBy) {
        this.orderBy = orderBy;
    }

    public String getGroupBy() {
        return groupBy;
    }

     /**
     * @param orderBy the groupBy to set
     */

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

}
