# 新增用户

### 功能说明
新增保存用户信息

### URI
#### URI格式  
POST /system/v1/users

#### 参数说明  
无

### 请求消息
#### 参数说明  
| 名称 | 是否必选 | 参数类型 | 说明 |
| --- | --- | --- | --- |
| userName | 是 | String | 用户名 |
| nickName | 否 | String | 用户昵称 |
| orgId | 是 | String | 归属机构ID |
| email | 否 | String | 邮箱 |
| mobile | 否 | String | 手机号 |
#### 请求样例  
```
POST http://{Endpoint}/system/v1/users
```
##### 请求body样例
```json
{
	"userName": "wuwj",
	"nickName": "wuwj",
	"email": "11@qq.com",
	"orgId": "0"
}
```
### 响应消息
#### 要素说明
| 名称 | 参数类型 | 说明 |
| --- | --- | --- |
| data | String | 新增的用户ID |

详细请参考 [响应结果说明](../../../common/response/result.md#要素说明)  

#### 响应样例
##### 正常响应
```json
{
	"code": 900,
	"data": "00020",
	"message": "success",
	"request_id": "2dd0a746aadd4a0d9dce0bfda67d46f1"
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