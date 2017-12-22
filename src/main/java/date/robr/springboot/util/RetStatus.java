package date.robr.springboot.util;

import java.io.Serializable;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * 用于json返回对象 可以自定义信息实现国际化 根据业务需要 是否使用msg字段，前端如果协调统一则无需使用
 * @author 岳亦祥
 *
 * @param <T>
 */
@Component
//@Scope("prototype")
public class RetStatus  implements Serializable {
	
	private static final long serialVersionUID = 8386348307016054815L;
	private String code;//错误代码
	private boolean success;//是否成功
	private String msg;//错误信息
	private Map<String, Object>  resultList;//结果存储
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Map<String, Object> getResultList() {
		return resultList;
	}
	public void setResultList(Map<String, Object> resultList) {
		this.resultList = resultList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public RetStatus returnRet(String code,Map<String, Object>  resultList){//只有代码和结果 即为成功
		this.setCode(code);
		this.setSuccess(true);
		this.setResultList(resultList);
		if(null!=this.msg && !this.msg.isEmpty())this.setMsg(null);
		return this;
	}
	public RetStatus returnRet(String code){//只有代码即为不成功 
		this.setCode(code);
		this.setSuccess(false);
		if(null!=this.msg && !this.msg.isEmpty())this.setMsg(null);
		if(null!=this.resultList && !this.resultList.isEmpty())this.resultList.clear();
		return this;
	}
	public RetStatus returnRet(String code,String msg){//只有代码和信息 即为不成功
		this.setCode(code);
		this.setSuccess(false);
		this.setMsg(msg);
		if(null!=this.resultList && !this.resultList.isEmpty())this.resultList.clear();
		return this;
	}
	public RetStatus returnRet(boolean success,String code,String msg){
		this.setSuccess(success);
		this.setCode(code);
		this.setMsg(msg);
		if(false == success && null!=this.resultList && !this.resultList.isEmpty())this.resultList.clear();
		return this;
	}
	public RetStatus returnRet(String code,String msg,Map<String, Object>  resultList){
		this.setCode(code);
		this.setMsg(msg);
		this.setSuccess(true);
		this.setResultList(resultList);
		return this;
	}
	public RetStatus returnRet(boolean success,String code,String msg,Map<String, Object>  resultList){
		this.setCode(code);
		this.setMsg(msg);
		this.setSuccess(success);
		this.setResultList(resultList);
		return this;
	}
	

}
