import { createBrowserRouter , Navigate} from 'react-router-dom';
import { LoginPage } from '../features/auth/pages/LoginPage';
import { ProtectedRoute } from '../components/common/ProtectedRoute';

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
    path: '/dashboard',
    element: (
      <ProtectedRoute>
        <DashboardPlaceholder />
      </ProtectedRoute>
    ),
  },
]);