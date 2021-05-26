package com.nined.esportsota.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "user")
@DynamicInsert
public class Users implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //姓名
    private String name;

    //密码
    private String password;

    //昵称
    private String nickName;

    //性别(0其他，1男，2女)
    private Integer gender;

    //微信id
    private String weixinId;

    //微信号
    private String weixinNo;

    //手机号
    private String mobile;

    //头像
    private String faceImage;

    private Integer isAdmin;

    //创建时间
    private Timestamp createTime;

    private Integer status;



    /*//创建来源(1PC端，2IOS端，3安卓端，4TV端)
    private Integer createSource;

    //支付宝id
    private String alipayId;

    //qq号
    private String qq;

    //qqid
    //private String qqId;

    //手机号
    private String mobile;

    //邮箱
    private String email;

    //生日
    private Timestamp birthday;

    //省
    private String province;

    //市
    private String city;

    //区
    private String region;

    //地址
    private String address;

    //邮政编码
    private String postCode;

    //个性签名
    private String signature;

    //是否管理员(0否1是)
    private Integer isAdmin;

    //登录ip
    private String loginIp;

    //登录时间
    private Timestamp loginTime;

    //创建ip
    private String createIp;

    //登录次数
    private Integer loginCount;

    //状态(0禁用，1启用)
    private Integer status;

    //等级(0普通会员，1....星会员)
    private Integer rank;

    //成长值
    private Integer exp;

    //邀请人用户id
    private Integer fromUserId;
    private String fromUserPath;

    //积分
    private Integer point;

    //app device token
    private String deviceToken;

    //邀请
    private String invitationCode;

    //金币数
    private Double gold;

    //赠送金币数
    private Double giveGold;

    //冰点
    //private Double icePoint;

    //是否已验证金币账户(1是.0否)
    private Integer isVerifyGold;

    //是否已实名验证(0未验证，1已验证，2验证中)
    private Integer verifyRealname;

    //邀请好友数量(级)
    private Integer levelInviteNum;

    //来源(101推广)
    private String source;
    private String sessionId;
    private String sessionSign;

    //邀请码
    private String userCode;

    //来自邀请码
    private String fromUserCode;

    //点赞能量
    private Double likedPower;

    //最后点赞时间
    private Timestamp lastLikedTime;

    //会员到期时间
    //private Timestamp vipExpireTime;

    //
    private Integer computing;

    //
    private Integer computingNode;

    //社区禁言(0不禁言，1按时间禁言，2永久禁言)
    private Integer disableForum;

    //禁言到期时间
    private Timestamp disableForumExpire;

    //是否代理(0否，1是)
    //private Integer isAgent;

    //消息推送token
    private String pushDeviceToken;

    //设备唯一序列号
    private String deviceSerialNumber;

    //设备唯一ID
    private String deviceUniqueId;

    //登录平台(1安卓，2IOS)
    private Integer loginPlatform;

    //使用平台(1安卓，2IOS，3双端)
    //private Integer usePlatform;*/
}
