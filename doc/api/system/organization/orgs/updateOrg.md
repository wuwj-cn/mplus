# 更新机构

### 功能说明
更新指定组织机构的信息

### URI
#### URI格式  
PUT /system/v1/orgs/{orgId}

#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgId | 是 | String | 机构ID |

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| orgName | 是 | String | 机构简称 |
| fullName | 否 | String | 机构全称 |
| parentOrgId | 是 | String | 上级机构ID |
| remark | 否 | String | 备注 |
#### 请求样例  
```
PUT http://{Endpoint}/system/v1/orgs/0101
```
##### 请求body样例
```json
{
	"orgName": "0101-test",
	"fullName": "0101",
	"parentOrgId": "01"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 更新的组织机构ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "0101",
	"message": "success",
	"request_id": "be4295eae186411e9d083004c0fc5dbe"
}
```
##### 异常响应
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)