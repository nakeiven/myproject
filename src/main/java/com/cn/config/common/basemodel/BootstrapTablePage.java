package com.cn.config.common.basemodel;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;

/**
 * @ClassName: BootstrapTable
 * @Description: BootstrapTable封装类
 * @date 2016-11-2 下午7:15:37
 * @param <T>
 */
public class BootstrapTablePage<T> implements Serializable {

    private static final long   serialVersionUID = -8326206193790978196L;
    private final static String OFFSET           = "offset";
    private final static String LIMIT            = "limit";
    private final static String ORDER_COLUMN     = "sort";
    private final static String ORDER_DIR        = "order";

    private List<T>             rows;                                    // 数据List
    private long                total            = 0;                    // 总条数
    private int                 offset           = 0;                    // 数据起始位置
    private int                 limit            = 10;                   // 每页显示条数
    private String              orderStr;                                // 排序，如
                                                                          // userId
                                                                          // desc

    public BootstrapTablePage(){
    }

    public BootstrapTablePage(HttpServletRequest request){
        String offset = request.getParameter(OFFSET);
        String limit = request.getParameter(LIMIT);
        String column = request.getParameter(ORDER_COLUMN);
        String dir = request.getParameter(ORDER_DIR);
        if (dir != null && !dir.trim().isEmpty()) {
            dir = "desc".equalsIgnoreCase(dir.trim()) ? "desc" : "asc";
        }
        if (column != null && !column.trim().isEmpty()) {
            column = column.trim();
            if (column.contains("alter ") || column.contains("create ") || column.contains("truncate ")
                || column.contains("drop ") || column.contains("lock table ") || column.contains("insert ")
                || column.contains("update ") || column.contains("delete ") || column.contains("select ")
                || column.contains("grant ") || column.contains("and ") || column.contains("where ")
                || column.contains("or ") || column.contains("count ") || column.contains("exec ")
                || column.contains("union ") || column.contains("' || '' || '") || column.contains("=")
                || column.contains(" ") || column.contains(")") || column.contains("(")) column = "";
            this.orderStr = " " + column + " " + dir;
        }
        if (offset != null && !offset.trim().isEmpty()) {
            this.offset = Integer.valueOf(offset);
        }
        if (limit != null && !limit.trim().isEmpty()) {
            this.limit = Integer.valueOf(limit);
        }
    }

    public void transfer(PageInfo<T> pageInfo) {
        setRows(pageInfo.getList());
        setTotal(pageInfo.getTotal());
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
