import { useState } from 'react';
import type React from 'react';
import styles from '../styles/login.module.css';

interface LoginFormProps {
  onSubmit: (email: string, password: string) => void;
  loading: boolean;
  error: string | null;
}

export function LoginForm({ onSubmit, loading, error }: LoginFormProps) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e: React.SyntheticEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(email, password);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h1 className={styles.title}>Sign In</h1>

      {error && <div className={styles.error}>{error}</div>}

      <label className={styles.label} htmlFor="email">
        Email
      </label>
      <input
        id="email"
        type="email"
        className={styles.input}
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
        autoComplete="email"
      />

      <label className={styles.label} htmlFor="password">
        Password
      </label>
      <input
        id="password"
        type="password"
        className={styles.input}
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
        autoComplete="current-password"
      />

      <a className={styles.forgotLink} href="/forgot-password">
        Forgot password?
      </a>

      <button type="submit" className={styles.submitButton} disabled={loading}>
        {loading ? 'Signing in...' : 'Sign In'}
      </button>
    </form>
  );
}