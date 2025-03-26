// /app/api/todos/route.js
import mysql from 'mysql2/promise';
import { connectDB } from '@/lib/mysql'; 
import { NextResponse } from 'next/server';

export async function GET() {
  try {
    const db = await connectDB();
    const [rows] = await db.execute('SELECT * FROM todos ORDER BY id DESC');
    return NextResponse.json(rows); // ← Response → NextResponse
  } catch (error) {
    console.error('GET error:', error);
    return NextResponse.json({ error: 'DB error' }, { status: 500 });
  }
}

export async function POST(req) {
  try {
    const body = await req.json();
    const task = body.task;

    if (!task || task.trim() === '') {
      return NextResponse.json({ error: 'Invalid task' }, { status: 400 });
    }

    const db = await connectDB();
    await db.execute('INSERT INTO todos (task) VALUES (?)', [task]);
    return NextResponse.json({ message: 'Created' }, { status: 201 });
  } catch (error) {
    console.error('POST error:', error);
    return NextResponse.json({ error: 'DB error' }, { status: 500 });
  }
}

export async function DELETE(req) {
  try {
    const body = await req.json();
    const id = body.id;

    const db = await connectDB();
    await db.execute('DELETE FROM todos WHERE id = ?', [id]);
    return NextResponse.json({ message: 'Deleted' }, { status: 200 });
  } catch (error) {
    console.error('DELETE error:', error);
    return NextResponse.json({ error: 'DB error' }, { status: 500 });
  }
}

export const dynamic = 'force-dynamic';
