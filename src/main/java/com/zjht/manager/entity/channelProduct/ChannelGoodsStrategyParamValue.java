package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_goods_strategy_param_value")
public class ChannelGoodsStrategyParamValue implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -606798060498150538L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_GOODSSTRATEGYPARAMVALUE'),12,'0')")
    private String id;

    /**
     * 渠道单品策略id
     */
    @Column(name = "goods_strategy_id")
    private String goodsStrategyId;

    /**
     * 渠道策略id
     */
    @Column(name = "strategy_id")
    private String strategyId;

    /**
     * 渠道策略参数id
     */
    @Column(name = "strategy_param_id")
    private String strategyParamId;

    /**
     * 渠道策略参数值
     */
    @Column(name = "strategy_param_value")
    private String strategyParamValue;
    
    /**
     * 参数代码
     */
    @Transient
    @Column(name = "parameter_code")
    private String parameterCode;

    /**
     * 参数名称
     */
    @Transient
    @Column(name = "parameter_name")
    private String parameterName;

    /**
     * 参数类型
     */
    @Transient
    @Column(name = "parameter_type")
    private Byte parameterType;      

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取渠道单品策略id
     *
     * @return goods_strategy_id - 渠道单品策略id
     */
    public String getGoodsStrategyId() {
        return goodsStrategyId;
    }

    /**
     * 设置渠道单品策略id
     *
     * @param goodsStrategyId 渠道单品策略id
     */
    public void setGoodsStrategyId(String goodsStrategyId) {
        this.goodsStrategyId = goodsStrategyId;
    }

    /**
     * 获取渠道策略id
     *
     * @return strategy_id - 渠道策略id
     */
    public String getStrategyId() {
        return strategyId;
    }

    /**
     * 设置渠道策略id
     *
     * @param strategyId 渠道策略id
     */
    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    /**
     * 获取渠道策略参数id
     *
     * @return strategy_param_id - 渠道策略参数id
     */
    public String getStrategyParamId() {
        return strategyParamId;
    }

    /**
     * 设置渠道策略参数id
     *
     * @param strategyParamId 渠道策略参数id
     */
    public void setStrategyParamId(String strategyParamId) {
        this.strategyParamId = strategyParamId;
    }

    /**
     * 获取渠道策略参数值
     *
     * @return strategy_param_value - 渠道策略参数值
     */
    public String getStrategyParamValue() {
        return strategyParamValue;
    }

    /**
     * 设置渠道策略参数值
     *
     * @param strategyParamValue 渠道策略参数值
     */
    public void setStrategyParamValue(String strategyParamValue) {
        this.strategyParamValue = strategyParamValue;
    }

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Byte getParameterType() {
		return parameterType;
	}

	public void setParameterType(Byte parameterType) {
		this.parameterType = parameterType;
	}    
}