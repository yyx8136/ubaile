package date.robr.springboot.base.domian;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
public class BaseDomian {
	 
	    /** 
	     * 创建时间 
	     */  
	    @Column  
	    private Date createTime;  
		/** 
	     * 最后修改时间 
	     */  
	    @Column  
	    private Date lastModifyTime;  
	      
	    public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public Date getLastModifyTime() {
			return lastModifyTime;
		}
		public void setLastModifyTime(Date lastModifyTime) {
			this.lastModifyTime = lastModifyTime;
		}
	
		

	
}
