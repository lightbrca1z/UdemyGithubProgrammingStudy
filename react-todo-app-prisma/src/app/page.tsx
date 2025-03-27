"use client";

import { useEffect, useState } from "react";
import styles from "./page.module.css";
import { FaTrashAlt } from "react-icons/fa";

export default function Home() {
  const [task, setTask] = useState("");
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const res = await fetch("/api/todos");
      const data = await res.json();

      if (Array.isArray(data)) {
        setTodos(data);
      } else {
        console.error("APIレスポンスが配列ではありません:", data);
        setTodos([]);
      }
    } catch (error) {
      console.error("fetchTodosエラー:", error);
      setTodos([]);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (task.trim() === "") return;

    await fetch("/api/todos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ task }),
    });

    setTask("");
    fetchTodos();
  };

  const handleDelete = async (id) => {
    await fetch("/api/todos", {
      method: "DELETE",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id }),
    });

    fetchTodos();
  };

  return (
    <main className={styles.main}>
      <h1 className={styles.title}>Todoアプリ（Prisma連携）</h1>

      <form onSubmit={handleSubmit} className={styles.form}>
        <input
          type="text"
          value={task}
          onChange={(e) => setTask(e.target.value)}
          placeholder="タスクを入力"
          className={styles.input}
        />
        <button type="submit" className={styles.button}>＋</button>
      </form>

      <ul className={styles.todoList}>
        {todos.map((todo) => (
          <li key={todo.id} className={styles.todoItem}>
            ✅ {todo.task}
            <button
              className={styles.deleteButton}
              onClick={() => handleDelete(todo.id)}
              aria-label="削除"
            >
              <FaTrashAlt />
            </button>
          </li>
        ))}
      </ul>
    </main>
  );
}
