package pojo;

import java.util.List;

/**
 * @author 必燃
 * @version 1.0
 * @create 2022-10-24 16:14
 */
public class PageBean <T>{
    private int CurrentPageNo;

    public int getCurrentPageNo() {
        return CurrentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        CurrentPageNo = currentPageNo;
    }

    private int totalCount;
    private List<T> rows;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
