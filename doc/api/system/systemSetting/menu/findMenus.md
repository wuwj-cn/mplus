# 查询菜单列表

### 功能说明
根据条件查询菜单列表

### URI
#### URI格式  
GET /system/v1/menus?moduleCode={moduleCode}&menuCode={menuCode}&menuName={menuName}&parentMenuCode={parentMenuCode}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| moduleCode | 否 | String | 模块编码 |
| menuCode | 否 | String | 菜单编码 |
| menuName | 否 | String | 菜单名称，支持模糊匹配 |
| parentMenuCode | 否 | String | 上级菜单编码 |

### 请求消息
#### 参数说明  
无

#### 请求样例  
```
GET http://{Endpoint}/system/v1/menus?parentMenuCode=0101
```
##### 请求body样例
无

### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | Menu结构体数组 | 请参见 [Menu结构体说明](#menu结构体说明) |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

##### Menu结构体说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| menuCode | String | 菜单编码 |
| menuName | String | 菜单名称 |
| moduleCode | String | 所属模块编码 |
| moduleName | String | 所属模块名称 |
| parentMenuCode | String | 上级菜单编码 |
| parentMenuName | String | 上级菜单名称 |
| url | String | 菜单URL |
| createTime | String | 创建时间 |
| modifyTime | String | 修改时间 |

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": [
		{
			"createTime": "2020-01-01 18:02:53",
			"menuCode": "010101",
			"menuName": "010101",
			"modifyTime": "2020-01-03 10:49:30",
			"moduleCode": "01",
			"moduleName": "01",
			"parentMenuCode": "0101",
			"parentMenuName": "0101",
			"url": "/menus/b"
		},
		{
			"createTime": "2020-01-01 21:23:36",
			"menuCode": "010102",
			"menuName": "010102",
			"modifyTime": "2020-01-01 21:23:36",
			"moduleCode": "01",
			"moduleName": "01",
			"parentMenuCode": "0101",
			"parentMenuName": "0101",
			"url": "/menus/b"
		},
		{
			"createTime": "2020-01-01 21:23:47",
			"menuCode": "010103",
			"menuName": "010103",
			"modifyTime": "2020-01-01 21:23:47",
			"moduleCode": "01",
			"moduleName": "01",
			"parentMenuCode": "0101",
			"parentMenuName": "0101",
			"url": "/menus/b"
		}
	],
	"message": "success",
	"request_id": "ef54313e71d24d76b25604ac94ae5052"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)