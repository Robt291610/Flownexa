import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/auth.service';
import type { CreateCompanyRequest, ApiErrorResponse } from '../types/auth.types';

export function useCreateCompany() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const createCompany = async (payload: CreateCompanyRequest) => {
    setLoading(true);
    setError(null);

    try {
      await authService.createCompany(payload);
      navigate('/login', { state: { message: 'Company created. You can now sign in.' } });
    } catch (err: any) {
      const apiError: ApiErrorResponse | undefined = err?.body;
      setError(apiError?.message ?? 'Could not create the company. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return { createCompany, loading, error };
}