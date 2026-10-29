import { LoginForm } from '../components/LoginForm';
import { useLogin } from '../hooks/useLogin';
import styles from '../styles/login.module.css';

export function LoginPage() {
  const { login, loading, error } = useLogin();

  const handleSubmit = (email: string, password: string) => {
    login({ email, password });
  };

  return (
    <div className={styles.page}>
      <LoginForm onSubmit={handleSubmit} loading={loading} error={error} />
    </div>
  );
}