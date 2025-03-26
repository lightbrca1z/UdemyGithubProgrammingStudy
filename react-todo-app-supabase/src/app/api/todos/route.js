// /app/api/todos/route.js
import { supabase } from '@/lib/supabase';
import { NextResponse } from 'next/server';

export async function GET() {
  const { data, error } = await supabase
    .from('todos')
    .select('*')
    .order('id', { ascending: false });

  if (error) {
    console.error('GET error:', error);
    return NextResponse.json({ error: 'Supabase error' }, { status: 500 });
  }

  return NextResponse.json(data);
}

export async function POST(req) {
  const body = await req.json();
  const task = body.task;

  if (!task || task.trim() === '') {
    return NextResponse.json({ error: 'Invalid task' }, { status: 400 });
  }

  const { error } = await supabase
    .from('todos')
    .insert([{ task }]);

  if (error) {
    console.error('POST error:', error);
    return NextResponse.json({ error: 'Supabase error' }, { status: 500 });
  }

  return NextResponse.json({ message: 'Created' }, { status: 201 });
}

export async function DELETE(req) {
  const body = await req.json();
  const id = body.id;

  const { error } = await supabase
    .from('todos')
    .delete()
    .eq('id', id);

  if (error) {
    console.error('DELETE error:', error);
    return NextResponse.json({ error: 'Supabase error' }, { status: 500 });
  }

  return NextResponse.json({ message: 'Deleted' }, { status: 200 });
}

export const dynamic = 'force-dynamic';
