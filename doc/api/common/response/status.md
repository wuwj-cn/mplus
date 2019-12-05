# 响应状态码

### 功能说明
提供统一的请求响应状态码说明

### 正常响应
| 返回值 | 说明 |
| --- | --- |
| 200 | 任务提交成功 |
| 201 | 创建成功 |

### 异常响应
| 返回值 | 说明 |
| --- | --- |
| 400 Bad Request | 请求参数错误 |
| 401 Unauthorized | 鉴权失败 |
| 403 Forbidden | 没有权限对资源访问 |
| 404 Not Found | 请求的资源不存在 |
| 500 Internal Server Error | 请求未完成，服务异常 |
| 501 Not Implemented | 请求未完成，服务器不支持所请求的功能 |
| 502 Bad Gateway | 请求未完成，服务器从上游服务器收到一个无效的响应 |
| 503 Service Unavailable | 请求未完成，系统暂时异常 |
| 504 Gateway Timeout | 网关超时 |
