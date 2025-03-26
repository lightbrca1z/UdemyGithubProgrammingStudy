// lib/supabase.js
import { createClient } from '@supabase/supabase-js'

const supabaseUrl = 'https://oesffgaqlbbwhxtjqxwp.supabase.co'  // あなたのSupabase URL
const supabaseKey = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9lc2ZmZ2FxbGJid2h4dGpxeHdwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDI5NTE3NDgsImV4cCI6MjA1ODUyNzc0OH0.xDiZ91rN30T4dmR9d0qrfHQ88IHgEXCQLIgvTFIUgBE'             // あなたのanon key

export const supabase = createClient(supabaseUrl, supabaseKey)
