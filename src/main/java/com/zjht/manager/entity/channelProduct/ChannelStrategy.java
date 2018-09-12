package com.zjht.manager.entity.channelProduct;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "t_channel_strategy")
public class ChannelStrategy implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7375822020125275119L;

	/**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select lpad(nextval('SEQ_CHANNELSTRATEGY'),12,'0')")
    private String id;
    
    /**
     * 策略编码
     */
    @Column(name = "strategy_Code")
    private String strategyCode;    

    /**
     * 策略名
     */
    @Column(name = "strategy_name")
    private String strategyName;

    /**
     * 渠道编码
     */
    @Column(name = "channel_code")
    private String channelCode;
    
    /**
     * 策略说明
     */
    @Column(name = "strategy_summary")
    private String strategySummary;    

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

    public String getStrategyCode() {
		return strategyCode;
	}

	public void setStrategyCode(String strategyCode) {
		this.strategyCode = strategyCode;
	}

	/**
     * 获取策略名
     *
     * @return strategy_name - 策略名
     */
    public String getStrategyName() {
        return strategyName;
    }

    /**
     * 设置策略名
     *
     * @param strategyName 策略名
     */
    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    /**
     * 获取渠道编码
     *
     * @return channel_code - 渠道编码
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置渠道编码
     *
     * @param channelCode 渠道编码
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

	public String getStrategySummary() {
		return strategySummary;
	}

	public void setStrategySummary(String strategySummary) {
		this.strategySummary = strategySummary;
	}
        
}