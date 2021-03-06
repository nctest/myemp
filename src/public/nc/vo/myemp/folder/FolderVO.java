/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.myemp.folder;
	
import nc.vo.pub.SuperVO;

/**
 * <b> 在此处简要描述此类的功能 </b>
 * <p>
 *     在此处添加此类的描述信息
 * </p>
 * 创建日期:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
public class FolderVO extends SuperVO {
	private java.lang.String id;
	private java.lang.String code;
	private java.lang.String name;
	private nc.vo.pub.lang.UFDouble fsize;
	private java.lang.String p_id;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String ID = "id";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String FSIZE = "fsize";
	public static final String P_ID = "p_id";
			
	/**
	 * 属性id的Getter方法.属性名：文件夹ID
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getId () {
		return id;
	}   
	/**
	 * 属性id的Setter方法.属性名：文件夹ID
	 * 创建日期:
	 * @param newId java.lang.String
	 */
	public void setId (java.lang.String newId ) {
	 	this.id = newId;
	} 	  
	/**
	 * 属性code的Getter方法.属性名：文件夹编码
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getCode () {
		return code;
	}   
	/**
	 * 属性code的Setter方法.属性名：文件夹编码
	 * 创建日期:
	 * @param newCode java.lang.String
	 */
	public void setCode (java.lang.String newCode ) {
	 	this.code = newCode;
	} 	  
	/**
	 * 属性name的Getter方法.属性名：文件夹名称
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getName () {
		return name;
	}   
	/**
	 * 属性name的Setter方法.属性名：文件夹名称
	 * 创建日期:
	 * @param newName java.lang.String
	 */
	public void setName (java.lang.String newName ) {
	 	this.name = newName;
	} 	  
	/**
	 * 属性fsize的Getter方法.属性名：文件夹大小
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public nc.vo.pub.lang.UFDouble getFsize () {
		return fsize;
	}   
	/**
	 * 属性fsize的Setter方法.属性名：文件夹大小
	 * 创建日期:
	 * @param newFsize nc.vo.pub.lang.UFDouble
	 */
	public void setFsize (nc.vo.pub.lang.UFDouble newFsize ) {
	 	this.fsize = newFsize;
	} 	  
	/**
	 * 属性p_id的Getter方法.属性名：上级文件夹
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getP_id () {
		return p_id;
	}   
	/**
	 * 属性p_id的Setter方法.属性名：上级文件夹
	 * 创建日期:
	 * @param newP_id java.lang.String
	 */
	public void setP_id (java.lang.String newP_id ) {
	 	this.p_id = newP_id;
	} 	  
	/**
	 * 属性dr的Getter方法.属性名：dr
	 * 创建日期:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * 属性dr的Setter方法.属性名：dr
	 * 创建日期:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * 属性ts的Getter方法.属性名：ts
	 * 创建日期:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * 属性ts的Setter方法.属性名：ts
	 * 创建日期:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>取得父VO主键字段.
	  * <p>
	  * 创建日期:
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>取得表主键.
	  * <p>
	  * 创建日期:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "id";
	}
    
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "folder";
	}    
	
	/**
	 * <p>返回表名称.
	 * <p>
	 * 创建日期:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "folder";
	}    
    
    /**
	  * 按照默认方式创建构造子.
	  *
	  * 创建日期:
	  */
     public FolderVO() {
		super();	
	}    
} 


