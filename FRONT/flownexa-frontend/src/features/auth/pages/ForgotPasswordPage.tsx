// features/auth/pages/ForgotPasswordPage.tsx
import { AuthLayout } from '../../../layouts/AuthLayout';
import { ForgotPasswordForm } from '../components/ForgotPasswordForm';
import { useForgotPassword } from '../hooks/useForgotPassword';


export function ForgotPasswordPage() {
  const { requestReset, loading, error, submitted } = useForgotPassword();

  return (
    <AuthLayout
      title="Forgot your password?"
      subtitle="Enter your work email and we'll send you a link to reset it."
    >
      <ForgotPasswordForm onSubmit={requestReset} loading={loading} error={error} submitted={submitted} />
    </AuthLayout>
  );
}