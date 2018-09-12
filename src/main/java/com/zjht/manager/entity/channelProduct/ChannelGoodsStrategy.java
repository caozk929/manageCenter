package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_goods_strategy")
public class ChannelGoodsStrategy implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2385875853939838351L;

	/**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELGOODSSTRATEGY'),12,'0')")
    private String id;

    /**
     * 渠道单品编码
     */
    @Column(name = "channel_goods_code")
    private String channelGoodsCode;

    /**
     * 渠道策略id
     */
    @Column(name = "strategy_id")
    private String strategyId;
    
    /**
     * 策略编码
     */
    @Transient
    @Column(name = "strategy_Code")
    private String strategyCode;    

    /**
     * 策略名
     */
    @Transient
    @Column(name = "strategy_name")
    private String strategyName;

    /**
     * 渠道编码
     */
    @Transient
    @Column(name = "channel_code")
    private String channelCode; 
    
    //渠道商品策略参数
    @Transient
    private String strategyParamView;      

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

    /**
     * 获取渠道单品编码
     *
     * @return channel_goods_code - 渠道单品编码
     */
    public String getChannelGoodsCode() {
        return channelGoodsCode;
    }

    /**
     * 设置渠道单品编码
     *
     * @param channelGoodsCode 渠道单品编码
     */
    public void setChannelGoodsCode(String channelGoodsCode) {
        this.channelGoodsCode = channelGoodsCode;
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

	public String getStrategyCode() {
		return strategyCode;
	}

	public void setStrategyCode(String strategyCode) {
		this.strategyCode = strategyCode;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getStrategyParamView() {
		return strategyParamView;
	}

	public void setStrategyParamView(String strategyParamView) {
		this.strategyParamView = strategyParamView;
	} 	
}