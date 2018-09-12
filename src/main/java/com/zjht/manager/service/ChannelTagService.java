package com.zjht.manager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zjht.manager.entity.Select;
import com.zjht.manager.entity.channelProduct.ChannelTag;

public interface ChannelTagService {

	ChannelTag getTag(String id);

	int addTag(ChannelTag tag);

	int updateTag(ChannelTag tag);

	int deleteTag(String id);

	PageInfo<ChannelTag> getPage(ChannelTag tag, int cpn, int pageSize);

	List<Select> getSelect(String tagName, String channelCode);

}
