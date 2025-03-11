package com.effort.entity;

public class FormPageSpec {
    private int pageId;
    private long formSpecId;
    private String pageTitle;
    private int pageOrder;
    public int getPageId() {
        return pageId;
    }
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
    public long getFormSpecId() {
        return formSpecId;
    }
    public void setFormSpecId(long formSpecId) {
        this.formSpecId = formSpecId;
    }
    public String getPageTitle() {
        return pageTitle;
    }
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
    public int getPageOrder() {
        return pageOrder;
    }
    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }
    @Override
    public String toString() {
        return "FormPageSpec [pageId=" + pageId + ", formSpecId=" + formSpecId
                + ", pageTitle=" + pageTitle + ", pageOrder=" + pageOrder + "]";
    }
}