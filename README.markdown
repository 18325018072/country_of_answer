**部署**：  
user_vm133：

- docker-user服务（security）
- docker-mysql
- docker-redis：登录验证码
- rocketmq 5.1.0

test_vm132：

- docker-test服务
- docker-mysql
- docker-redis：热门试题

Windows：

- nacos
- SpringCloudGateway

**网络**：

- 确认Windows的ip、掩码、网关
- 设置VMware的NAT的子网、掩码、网关。
- 修改虚拟机的/etc/sysconfig/network-scripts/ifcfg-ens33

```
TYPE="Ethernet"
PROXY_METHOD="none"
BROWSER_ONLY="no"
BOOTPROTO="static"
DEFROUTE="yes"
IPV4_FAILURE_FATAL="no"
IPV6INIT="yes"
IPV6_AUTOCONF="yes"
IPV6_DEFROUTE="yes"
IPV6_FAILURE_FATAL="no"
IPV6_ADDR_GEN_MODE="stable-privacy"
NAME="ens33"
UUID="8645f69f-9496-42fe-99c3-1d8780d313b4"
DEVICE="ens33"
ONBOOT="yes"
IPADDR="192.168.156.148"
GATEWAY="192.168.156.192"
NETMASK="255.255.255.0"
DNS1="8.8.8.8"
```