import { useState } from 'react';
import { authService } from '../services/auth.service';
import type { ApiErrorResponse } from '../types/auth.types';

export function useForgotPassword() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [submitted, setSubmitted] = useState(false);

  const requestReset = async (email: string) => {
    setLoading(true);
    setError(null);

    try {
      await authService.forgotPassword({ email });
      setSubmitted(true);
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.body;
      setError(apiError?.message ?? 'Something went wrong. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return { requestReset, loading, error, submitted };
}