# 新增岗位

### 功能说明
新增保存对应机构下岗位信息

### URI
#### URI格式  
POST /system/v1/orgs/{orgId}/posts

#### 参数说明  
无

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
POST http://{Endpoint}/system/v1/orgs
```
##### 请求body样例
```json
{
	"orgName": "0101",
	"fullName": "0101",
	"parentOrgId": "01"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的组织机构ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 9000,
	"data": "0103",
	"message": "success",
	"request_id": "4e9218fb4fcb4bd69c9fc34e057d0d6b"
}
```
##### 异常响应
```json
{
	"code": 500,
	"data": null,
	"message": "could not execute statement; SQL [n/a]; constraint [UK_rjjy9bmaqmpo2hna5urj15g93]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement",
	"request_id": "d800f3ac3d6c4feabd466ff93f36f82b"
}
```
请参考 [响应结果说明](../../../common/response/result.md#异常响应样例)

### 响应状态码
请参考 [响应状态码](../../../common/response/status.md)

### 错误码
请参考 [错误码说明](../../../common/errorCode/README.md)