"use client";

import { useEffect, useState } from "react";
import styles from "./page.module.css"; // ← CSSモジュールを正しくimport
import { FaTrashAlt } from "react-icons/fa";
import { supabase } from "@/lib/supabase"; // Supabaseクライアント

interface Todo {
  id: number;
  task: string;
}

export default function Home() {
  const [task, setTask] = useState("");
  const [todos, setTodos] = useState<Todo[]>([]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    const { data, error } = await supabase
      .from("todos")
      .select("*")
      .order("id", { ascending: false });

    if (error) {
      console.error("Fetch error:", error);
    } else {
      setTodos(data || []);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (task.trim() === "") return;

    const { error } = await supabase
      .from("todos")
      .insert([{ task }]);

    if (error) {
      console.error("Insert error:", error);
    } else {
      setTask("");
      fetchTodos();
    }
  };

  const handleDelete = async (id) => {
    const { error } = await supabase
      .from("todos")
      .delete()
      .eq("id", id);

    if (error) {
      console.error("Delete error:", error);
    } else {
      fetchTodos();
    }
  };

  return (
    <main className={styles.main}>
      <h1 className={styles.title}>Todoアプリ（Supabase連携）</h1>

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
