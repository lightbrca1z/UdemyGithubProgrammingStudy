import { ButtonHTMLAttributes } from 'react';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  children: React.ReactNode;
}

export function Button({ children, className = '', ...props }: ButtonProps) {
  return (
    <button
      className={`px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600 disabled:opacity-50 ${className}`}
      {...props}
    >
      {children}
    </button>
  );
} 