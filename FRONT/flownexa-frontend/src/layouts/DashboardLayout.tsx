import type { ReactNode } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../features/auth/context/AuthContext';
import styles from './dashboardLayout.module.css';

interface DashboardLayoutProps {
  children: ReactNode;
}

export function DashboardLayout({ children }: DashboardLayoutProps) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate('/login');
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <span className={styles.email}>{user?.email}</span>
        <button className={styles.logoutButton} onClick={handleLogout}>
          Logout
        </button>
      </header>
      <main className={styles.content}>{children}</main>
    </div>
  );
}