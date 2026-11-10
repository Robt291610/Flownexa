import { createBrowserRouter , Navigate} from 'react-router-dom';
import { LoginPage } from '../features/auth/pages/LoginPage';
import { RegisterPage } from '../features/auth/pages/RegisterPage';
import { ForgotPasswordPage } from '../features/auth/pages/ForgotPasswordPage';
import { ProtectedRoute } from '../components/common/ProtectedRoute';
import { DashboardLayout } from '../layouts/DashboardLayout';
import { ResetPasswordPage } from '../features/auth/pages/ResetPasswordPage';


function DashboardPlaceholder() {
  return <div>Dashboard (protected)</div>;
}

export const router = createBrowserRouter([
  {
    path: '/',
    element: <Navigate to="/login" replace />,
  },
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/register',
    element: <RegisterPage />,
  },
  {
    path: '/forgot-password',
    element: <ForgotPasswordPage />,
  },
  {
    path: '/reset-password',
    element: <ResetPasswordPage />,
  },
  {
    path: '/dashboard',
    element: (
      <ProtectedRoute>
        <DashboardLayout>
          <DashboardPlaceholder />
        </DashboardLayout>
      </ProtectedRoute>
    ),
  },
]);