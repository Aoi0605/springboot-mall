# springboot-mall

此專案是一個基於 Spring Boot 的後端應用，提供會員、訂單及商品管理功能。主要使用 RESTful API 進行資料傳輸，並採用了 Spring MVC、Spring JDBC 及單元測試技術。

## 使用技術
- **Spring Boot**: 框架核心
- **RESTful API**: 資料傳輸
- **Spring MVC**: 控制層架構
- **Spring JDBC**: 資料庫操作
- **Unit Testing**: 單元測試框架

## 功能模組

### 1. 會員功能
- **註冊**: 會員可以創建帳號，並使用 hash value 加密密碼 
- **登入**: 支援會員登入功能

### 2. 訂單功能
- **建立訂單**: 用戶可以建立新的訂單
- **查看訂單**: 用戶可以查詢訂單狀態
- **查詢訂單**: 支援訂單取消功能

### 3. 商品功能
- **商品查詢**: 列出可購買的商品清單，有分類及分頁查詢
- **商品詳細資料**: 查看指定商品的詳細資訊
- **新增/刪除商品**: 管理員可對商品進行新增、刪除操作

### 會員 API
| HTTP 方法 | 路徑                    | 描述    | 範例                    |
| --------- | ----------------------- |-------| ----------------------- |
| POST      | `/users/register`       | 註冊新會員 | `/users/register`       |
| POST      | `/users/login`          | 會員登入  | `/users/login`          |

### 訂單 API
| HTTP 方法 | 路徑                           | 描述               | 請求範例               |
 --------- | ------------------------------ | ------------------ | ---------------------- |
| GET       | `/users/{userId}/orders`       | 取得會員訂單列表   | `/users/1/orders?limit=10&offset=0` |
| POST      | `/users/{userId}/orders`       | 建立新訂單         | `/users/1/orders`      |

### 商品 API
| HTTP 方法 | 路徑                     | 描述             | 請求範例                  |
| --------- | ------------------------ | ---------------- | ------------------------- |
| GET       | `/products`              | 取得商品列表     | `/products?category=ELECTRONICS&limit=5&offset=0` |
| GET       | `/products/{productId}`  | 取得商品詳細資訊 | `/products/1`             |
| POST      | `/products`              | 新增商品         | `/products`               |
| PUT       | `/products/{productId}`  | 更新商品資訊     | `/products/1`             |
| DELETE    | `/products/{productId}`  | 刪除商品         | `/products/1`             |


## 單元測試
- 本專案使用單元測試來確保各功能的正確性。
- **測試工具**: JUnit5
- **測試範圍**:
    - **會員功能**: 註冊、登入、資料更新測試
    - **訂單功能**: 建立、查詢、取消訂單測試
    - **商品功能**: 商品查詢、管理功能測試

## 使用說明
1. **環境要求**: JDK 17, Maven, MariaDB
2. **步驟**:
    - Clone 此專案到本地
    - 在 MariaDB 中建立資料庫並配置 application.properties
    - 使用 `mvn spring-boot:run` 運行應用程式


