package com.jm.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class Resource implements Serializable {
    
    /**
     * 视图实体类
     */
    private static final long serialVersionUID = 1L;
    private String menuId;//菜单id
    private String parentId;//父类id
    private Integer menuLevel;//菜单级别
    private Integer sort;//菜单顺序
    private String title;//菜单名称
    private String href;//菜单路径
    private String icon;//菜单图标
    private Date addTime;//添加时间
    private Date updateTime;//修改时间
    private boolean spread = false;
    private List<Resource> children;
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isSpread() {
		return spread;
	}
	public void setSpread(boolean spread) {
		this.spread = spread;
	}
	public List<Resource> getChildren() {
		return children;
	}
	public void setChildren(List<Resource> children) {
		this.children = children;
	}
	public Resource(String menuId, String parentId, Integer menuLevel,
			Integer sort, String title, String href, String icon, Date addTime,
			Date updateTime, boolean spread, List<Resource> children) {
		super();
		this.menuId = menuId;
		this.parentId = parentId;
		this.menuLevel = menuLevel;
		this.sort = sort;
		this.title = title;
		this.href = href;
		this.icon = icon;
		this.addTime = addTime;
		this.updateTime = updateTime;
		this.spread = spread;
		this.children = children;
	}
	public Resource() {
		super();
		// TODO Auto-generated constructor stub
	}
	
    
  
   
}


