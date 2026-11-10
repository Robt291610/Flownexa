import { useSearchParams } from 'react-router-dom';
import { Alert } from '@mui/material';
import { AuthLayout } from '../../../layouts/AuthLayout';
import { ResetPasswordForm } from '../components/ResetPasswordForm';
import { useResetPassword } from '../hooks/useResetPassword';

export function ResetPasswordPage() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const { resetPassword, loading, error, success } = useResetPassword(token);

  return (
    <AuthLayout
      title="Set a new password"
      subtitle="Choose a strong password you haven't used before."
    >
      {!token && (
        <Alert severity="error" sx={{ mb: 2, borderRadius: 2 }}>
          This reset link is missing or malformed. Please request a new one.
        </Alert>
      )}
      <ResetPasswordForm onSubmit={resetPassword} loading={loading} error={error} success={success} />
    </AuthLayout>
  );
}