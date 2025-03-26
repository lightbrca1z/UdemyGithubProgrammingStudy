// lib/mysql.js
import mysql from 'mysql2/promise';

// 接続用関数
export async function connectDB() {
  const connection = await mysql.createConnection({
    host: 'localhost',       // or '127.0.0.1'
    user: 'root',            // あなたのMySQLユーザー名
    password: '',            // パスワード（ある場合）
    database: 'todos',       // 使用するデータベース名
  });

  return connection;
}
