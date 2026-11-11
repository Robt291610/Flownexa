import { useState } from 'react';
import { authService } from '../services/auth.service';
import type { JoinCompanyRequest, ApiErrorResponse } from '../types/auth.types';

export function useJoinCompany() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [submitted, setSubmitted] = useState(false);

  const joinCompany = async (payload: JoinCompanyRequest) => {
    setLoading(true);
    setError(null);

    try {
      await authService.joinCompany(payload);
      setSubmitted(true);
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.body;
      setError(apiError?.message ?? 'Could not submit your request. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return { joinCompany, loading, error, submitted };
}