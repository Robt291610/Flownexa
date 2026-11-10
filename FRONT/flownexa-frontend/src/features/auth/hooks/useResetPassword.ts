import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/auth.service';
import type { ApiErrorResponse } from '../types/auth.types';

export function useResetPassword(token: string | null) {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  const resetPassword = async (newPassword: string) => {
    if (!token) {
      setError('Missing or invalid reset link.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      await authService.resetPassword({ token, newPassword });
      setSuccess(true);
      setTimeout(() => navigate('/login'), 2500);
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.body;
      setError(apiError?.message ?? 'This link is invalid or has expired.');
    } finally {
      setLoading(false);
    }
  };

  return { resetPassword, loading, error, success };
}