// src/app/api/todos/route.js
import { NextResponse } from 'next/server'
import prisma from '@/lib/prisma'

// GET: タスク一覧を取得
export async function GET() {
  try {
    const todos = await prisma.todo.findMany({
      orderBy: { id: 'desc' },
    });
    return NextResponse.json(todos);
  } catch (error) {
    console.error('GET error:', error);
    return NextResponse.json([], { status: 500 });
  }
}

// POST: 新しいタスクを作成
export async function POST(req) {
  try {
    const { task } = await req.json();

    if (!task || task.trim() === '') {
      return NextResponse.json({ error: 'Invalid task' }, { status: 400 });
    }

    await prisma.todo.create({
      data: { task },
    });

    return NextResponse.json({ message: 'Created' }, { status: 201 });
  } catch (error) {
    console.error('POST error:', error);
    return NextResponse.json({ error: 'DB error' }, { status: 500 });
  }
}

// DELETE: タスクを削除
export async function DELETE(req) {
  try {
    const { id } = await req.json();

    await prisma.todo.delete({
      where: { id },
    });

    return NextResponse.json({ message: 'Deleted' }, { status: 200 });
  } catch (error) {
    console.error('DELETE error:', error);
    return NextResponse.json({ error: 'DB error' }, { status: 500 });
  }
}

export const dynamic = 'force-dynamic';
