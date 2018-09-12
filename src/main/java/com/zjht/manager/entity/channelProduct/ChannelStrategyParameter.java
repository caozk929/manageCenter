package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_strategy_parameter")
public class ChannelStrategyParameter implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3950558793364339893L;

	/**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELSTRATEGYPARAMETER'),12,'0')")
    private String id;

    /**
     *  策略id
     */
    @Column(name = "strategy_id")
    private String strategyId;

    /**
     * 参数代码
     */
    @Column(name = "parameter_code")
    private String parameterCode;

    /**
     * 参数名称
     */
    @Column(name = "parameter_name")
    private String parameterName;

    /**
     * 参数类型
     */
    @Column(name = "parameter_type")
    private Byte parameterType;  

    /**
     * 获取id
     *
     * @return id - id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(String strategyId) {
		this.strategyId = strategyId;
	}

	/**
     * 获取参数代码
     *
     * @return parameter_code - 参数代码
     */
    public String getParameterCode() {
        return parameterCode;
    }

    /**
     * 设置参数代码
     *
     * @param parameterCode 参数代码
     */
    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    /**
     * 获取参数名称
     *
     * @return parameter_name - 参数名称
     */
    public String getParameterName() {
        return parameterName;
    }

    /**
     * 设置参数名称
     *
     * @param parameterName 参数名称
     */
    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    /**
     * 获取参数类型
     *
     * @return parameter_type - 参数类型
     */
    public Byte getParameterType() {
        return parameterType;
    }

    /**
     * 设置参数类型
     *
     * @param parameterType 参数类型
     */
    public void setParameterType(Byte parameterType) {
        this.parameterType = parameterType;
    }

	public String getParameterTypeStr() {
		int type = this.parameterType.intValue();
		switch(type) {
		case 0 :
			return "字符";
		case 1 :
			return "数字";
		case 2 :
			return "金额";
		case 3 :
			return "时间";
		
		}
		return "";
	}
   
}