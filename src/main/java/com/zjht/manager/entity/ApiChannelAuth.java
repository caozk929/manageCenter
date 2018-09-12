package com.zjht.manager.entity;

import com.zjht.manager.entity.base.BaseApiChannelAuth;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Column;

@Table(name = "t_api_channel_auth")
public class ApiChannelAuth extends BaseApiChannelAuth {

    private static final long serialVersionUID = 3049281556288517770L;
}